package analyzer;

import org.com.shipment.service.CreatorShipment;
import org.com.shipment.model.Shipment;
import org.com.shipment.model.Analyzer;
import org.com.shipment.model.Order;
import org.com.shipment.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AnalyzerTest {

    private Analyzer analyzer;
    private Product pen;
    private  Product shoe;
    private Product wearable;
    private Product smartphone;
    private Product book;

    @Before
    public void instanceValues(){
        this.analyzer = new Analyzer();
        this.pen = new Product("Blue Bis");
        this.shoe = new Product("Adidas");
        this.wearable = new Product("Mi band 7");
        this.smartphone = new Product("Iphone XR");
        this.book = new Product("Design Pattern");
    }

    @Test
    public void testBiggerAndSmallerAmountOfOrder() {
        Shipment shipment = new CreatorShipment()
                .toShipment("XGBT-12")
                .order(pen,7)
                .order(shoe,10)
                .order(wearable,25)
                .build();

        analyzer.findBiggestAndSmallestOrder(shipment);

        int biggerOrderExpected = 25;
        int smallerOrderExpected = 7;

        //Must print 25
        Assert.assertEquals(biggerOrderExpected,
                analyzer.getBiggerOrder());
        //Must print 7
        Assert.assertEquals(smallerOrderExpected,
                analyzer.getSmallerOrder());
    }

    @Test
    public void testSmallerAndBiggerOneOrder(){
        Shipment shipment = new CreatorShipment()
                .toShipment("XGBT-12")
                .order(pen,7)
                .build();

        analyzer.findBiggestAndSmallestOrder(shipment);

        int biggerOrderExpected = 7;
        int smallerOrderExpected = 7;

        //Must print 7
        Assert.assertEquals(biggerOrderExpected,
                analyzer.getBiggerOrder());
        //Must print 7
        Assert.assertEquals(smallerOrderExpected,
                analyzer.getSmallerOrder());
    }

    @Test(expected = RuntimeException.class)
    public void testSmallerAndBiggerWithoutOrder(){
        Shipment shipment = new CreatorShipment()
                .toShipment("XGBT-12")
                .build();

        analyzer.findBiggestAndSmallestOrder(shipment);
    }

    @Test
    public void checkTopThreeOrders(){
        Shipment shipment = new CreatorShipment()
                .toShipment("XGBT-13")
                .order(pen,7)
                .order(shoe,10)
                .order(wearable, 25)
                .order(smartphone,12)
                .order(book,40)
                .build();

        analyzer.findTopThreeOrders(shipment);

        List<Order> orders = analyzer.getThreeBigger();

        Assert.assertEquals(3, orders.size());
        Assert.assertEquals(40, orders.get(0).getAmount());
        Assert.assertEquals(25, orders.get(1).getAmount());
        Assert.assertEquals(12, orders.get(2).getAmount());
    }

    @Test
    public void checkTopThreeOrdersWithLessOrders(){
        Shipment shipment = new CreatorShipment()
                .toShipment("XGBT-13")
                .order(smartphone,12)
                .order(book,40)
                .build();

        analyzer.findTopThreeOrders(shipment);

        List<Order> orders = analyzer.getThreeBigger();

        Assert.assertEquals(2, orders.size());
        Assert.assertEquals(40, orders.get(0).getAmount());
        Assert.assertEquals(12, orders.get(1).getAmount());
    }
}