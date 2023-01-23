package org.com.shipment.analyzer;

import org.com.shipment.Shipment;
import org.com.shipment.order.Order;
import org.com.shipment.order.SortByAmount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Analyzer {

    private int biggerOrder = Integer.MIN_VALUE;
    private int smallerOrder = Integer.MAX_VALUE;
    private List<Order> topThreeOrders;

    public Analyzer(){

    }

    public int getBiggerOrder() {
        return biggerOrder;
    }

    public int getSmallerOrder() {
        return smallerOrder;
    }

    public List<Order> getThreeBigger() {
        return topThreeOrders;
    }

    public void findBiggestAndSmallestOrder(Shipment shipment){

        if (shipment.getValues().size() == 0){
            throw new RuntimeException(
                "Não é possível avaliar pedidos vazios"
            );
        }

        for(Order order : shipment.getValues()){
            if (order.getAmount() > biggerOrder) {
                biggerOrder = order.getAmount();
            }
            if (order.getAmount() < smallerOrder){
                smallerOrder = order.getAmount();
            }
        }
    }

    public void findTopThreeOrders(Shipment shipment){

        List<Order> orderSort = shipment.getValues();

        Collections.sort(orderSort,
                new SortByAmount());

        if (orderSort.size() >= 3) topThreeOrders = orderSort.subList(0, 3);
        else topThreeOrders = orderSort.subList(0, orderSort.size());
    }
}
