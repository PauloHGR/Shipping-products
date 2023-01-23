package org.com.shipment.order;

import java.util.Comparator;

public class SortByAmount implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {

        if (o1.getAmount() < o2.getAmount()){ return 1; }
        if (o1.getAmount() > o2.getAmount()){ return -1; }

        return 0;
    }
}
