package org.com.shipment;

import org.com.shipment.order.Order;
import org.com.shipment.product.Product;

import java.util.Calendar;

public class CreatorShipment {

    private Shipment shipment;

    public CreatorShipment(){

    }

    public CreatorShipment toShipment(String name){
        shipment = new Shipment(name);
        return this;
    }

    public CreatorShipment order(Product product, int amount){
        shipment.registerOrder(new Order(product, amount));
        return this;
    }

    public CreatorShipment onDate(Calendar createDate){
        shipment.setCreateDate(createDate);
        return this;
    }

    public Shipment build(){
        return shipment;
    }

}
