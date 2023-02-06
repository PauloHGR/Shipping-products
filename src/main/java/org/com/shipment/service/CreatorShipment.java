package org.com.shipment.service;

import org.com.shipment.model.Order;
import org.com.shipment.model.Product;
import org.com.shipment.model.Shipment;

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
