package payment;

import org.com.shipment.service.CreatorShipment;
import org.com.shipment.model.Shipment;
import org.com.shipment.service.ShipmentDaoFalse;
import org.com.shipment.model.Analyzer;
import org.com.shipment.model.Payment;
import org.com.shipment.service.PaymentGenerate;
import org.com.shipment.payment.RepositoryPayment;
import org.com.shipment.model.Product;
import org.com.shipment.watch.Watch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Calendar;

import static org.mockito.Mockito.*;

public class PaymentGenerateTest {

    private RepositoryPayment payments;
    private ShipmentDaoFalse dao;
    private Analyzer analyzer;

    @Before
    public void instancePaymentGenerate(){
        payments = mock(RepositoryPayment.class);
        dao = mock(ShipmentDaoFalse.class);
        analyzer = mock(Analyzer.class);
    }

    @Test
    public void generatePaymentForOneShipmentConclude(){

        Calendar dateOld = Calendar.getInstance();
        dateOld.set(2022,10,2);

        Shipment shipment = new CreatorShipment()
                .toShipment("HTY-826")
                .order(new Product("Keyboard"),30)
                .onDate(dateOld)
                .build();

        when(dao.allShipmentConcludes())
                .thenReturn(Arrays.asList(shipment));
        when(analyzer.getBiggerOrder())
                .thenReturn(30);

        PaymentGenerate paymentGenerate = new PaymentGenerate(payments,dao,analyzer);
        paymentGenerate.generate();

        ArgumentCaptor<Payment> argument = ArgumentCaptor.forClass(Payment.class);
        verify(payments).save(argument.capture());

        Payment payment = argument.getValue();
        Assert.assertEquals(30, payment.getValue());

    }

    @Test
    public void pushToMondayPaymentOnWeekend(){

        Watch watch = mock(Watch.class);

        Calendar saturday = Calendar.getInstance();
        saturday.set(2022,Calendar.NOVEMBER,5);

        Shipment shipment = new CreatorShipment()
                .toShipment("HTY-826")
                .order(new Product("Keyboard"),30)
                .order(new Product("Mouse"),12)
                .build();

        when(dao.allShipmentConcludes())
                .thenReturn(Arrays.asList(shipment));
        when(analyzer.getBiggerOrder())
                .thenReturn(30);
        when(watch.today()).thenReturn(saturday);

        PaymentGenerate paymentGenerate = new PaymentGenerate(payments,dao,analyzer,watch);
        paymentGenerate.generate();

        ArgumentCaptor<Payment> argument = ArgumentCaptor.forClass(Payment.class);
        verify(payments).save(argument.capture());

        Payment payment = argument.getValue();
        Assert.assertEquals(Calendar.MONDAY,
                payment.getDate().get(Calendar.DAY_OF_WEEK));
        Assert.assertEquals(7,
                payment.getDate().get(Calendar.DAY_OF_MONTH));
    }
}
