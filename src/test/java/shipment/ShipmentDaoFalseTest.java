package shipment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.com.shipment.model.Shipment;
import org.com.shipment.service.ShipmentDaoFalse;
import org.com.shipment.model.Order;
import org.com.shipment.model.Product;
import org.com.shipment.service.UserDAO;
import org.com.shipment.model.Users;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

public class ShipmentDaoFalseTest {

    private Product pen;
    private Product shoe;

    private ShipmentDaoFalse shipmentDaoFalse;
    private UserDAO userDAO;
    private EntityManager session;

    @Before
    public void instanceValues(){

        this.pen = new Product("Blue Bis");
        this.shoe = new Product("Adidas");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        //SessionFactory factory = metadata.getSessionFactoryBuilder().build();

        EntityManagerFactory factory = metadata.getSessionFactoryBuilder().build();

        this.session = factory.createEntityManager();

        this.userDAO = new UserDAO(session);
        this.shipmentDaoFalse = new ShipmentDaoFalse(session);
    }

    @After
    public void endTest(){
        session.close();
    }

    @Test
    public void countShipmentsNoFinished(){

        Users user = new Users("Paulo Henrique","paulohenriquegrocha@gmail.com");

        Shipment shipmentActive = new Shipment("CGY-12", user);
        shipmentActive.registerOrder(new Order(pen,21));
        shipmentActive.registerOrder(new Order(shoe,8));

        Shipment shipmentFinished = new Shipment("CGY-10", user);
        shipmentFinished.registerOrder(new Order(pen,11));

        shipmentFinished.finish();

        userDAO.save(user);
        shipmentDaoFalse.save(shipmentActive);
        shipmentDaoFalse.save(shipmentFinished);

        Assert.assertEquals(1,shipmentDaoFalse.totalShipments());
    }

    @Test
    public void mustReturnShipmentsNoFinishedInPeriod(){

        Calendar beginInterval = Calendar.getInstance();
        beginInterval.add(Calendar.DAY_OF_MONTH, -10);
        Calendar endInterval = Calendar.getInstance();
        Calendar dateShipment1 = Calendar.getInstance();
        dateShipment1.add(Calendar.DAY_OF_MONTH, -2);
        Calendar dateShipment2 = Calendar.getInstance();
        dateShipment2.add(Calendar.DAY_OF_MONTH, -20);

        Users user = new Users("Paulo Rocha","rocha@hotmail.com");

        Shipment shipment1 = new Shipment("HTY-12", new Order(pen, 34));
        shipment1.setCreateDate(dateShipment1);

        Shipment shipment2 = new Shipment("HTY-11", new Order(shoe,2));
        shipment2.setCreateDate(dateShipment2);

        userDAO.save(user);
        shipmentDaoFalse.save(shipment1);
        shipmentDaoFalse.save(shipment2);

        List<Shipment> shipments = shipmentDaoFalse.perPeriod(beginInterval,endInterval);

        Assert.assertEquals(1,shipments.size());
        Assert.assertEquals("HTY-12", shipments.get(0).getName());
    }

    @Test
    public void mustIgnoreShipmentsFinishedInPeriod(){
        Calendar beginInterval = Calendar.getInstance();
        beginInterval.add(Calendar.DAY_OF_MONTH, -10);
        Calendar endInterval = Calendar.getInstance();
        Calendar dateShipment = Calendar.getInstance();
        dateShipment.add(Calendar.DAY_OF_MONTH, -2);

        Users user = new Users("Paulo Rocha","rocha@hotmail.com");

        Shipment shipment = new Shipment("HTY-12", new Order(pen, 34));
        shipment.setCreateDate(dateShipment);
        shipment.finish();

        userDAO.save(user);
        shipmentDaoFalse.save(shipment);

        List<Shipment> shipments = shipmentDaoFalse.perPeriod(beginInterval,endInterval);

        Assert.assertEquals(0,shipments.size());
    }
}
