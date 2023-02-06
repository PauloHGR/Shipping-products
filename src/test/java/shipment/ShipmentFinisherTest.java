package shipment;

import org.com.shipment.*;
import org.com.shipment.model.Shipment;
import org.com.shipment.service.CreatorShipment;
import org.com.shipment.service.ShipmentDaoFalse;
import org.com.shipment.service.ShipmentFinisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.*;

public class ShipmentFinisherTest {

    private ShipmentDaoFalse dao;
    private Notifier notifier;
    private ShipmentFinisher finisher;

    @Before
    public void instanceFinisher(){
       this.dao = mock(ShipmentDaoFalse.class);
       this.notifier = mock(Notifier.class);
       this.finisher = new ShipmentFinisher(dao,notifier);
    }

    @Test
    public void finishShipmentsFromOneWeekAgo(){

        Calendar dateOld = Calendar.getInstance();
        dateOld.set(2022,10,2);


        Shipment shipment1 = new CreatorShipment()
                .toShipment("HTY-826")
                .onDate(dateOld)
                .build();

        Shipment shipment2 = new CreatorShipment()
                .toShipment("BBH-21")
                .onDate(dateOld)
                .build();

        List<Shipment> shipments = Arrays.asList(shipment1, shipment2);

        when(dao.allShipmentCurrents()).thenReturn(shipments);

        finisher.finish();

        Assert.assertEquals(2,finisher.getTotalConcludes());
        Assert.assertTrue(shipment1.isFinish());
        Assert.assertTrue(shipment2.isFinish());
    }
    @Test
    public void refreshShipmentsFinished(){
        Calendar dateOld = Calendar.getInstance();
        dateOld.set(2022,10,2);

        Shipment shipment1 = new CreatorShipment()
                .toShipment("HTY-826")
                .onDate(dateOld)
                .build();

        List<Shipment> shipments = Arrays.asList(shipment1);

        when(dao.allShipmentCurrents()).thenReturn(shipments);

        finisher.finish();

        verify(dao, times(1)).refresh(shipment1);
    }

    @Test
    public void continueSaveShipmentAfterOneException(){

        Calendar dateOld = Calendar.getInstance();
        dateOld.set(2022,10,2);

        Shipment shipment1 = new CreatorShipment()
                .toShipment("HTY-826")
                .onDate(dateOld)
                .build();

        Shipment shipment2 = new CreatorShipment()
                .toShipment("BBH-21")
                .onDate(dateOld)
                .build();

        List<Shipment> shipments = Arrays.asList(shipment1, shipment2);

        when(dao.allShipmentCurrents()).thenReturn(shipments);
        doThrow(new RuntimeException()).when(dao).refresh(shipment1);

        finisher.finish();

        verify(dao).refresh(shipment2);
        verify(notifier).send(shipment2);
    }
}
