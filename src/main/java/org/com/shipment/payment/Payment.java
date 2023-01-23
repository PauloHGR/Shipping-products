package org.com.shipment.payment;

import java.util.Calendar;

public class Payment {

    private int value;
    private Calendar date;

    public Payment(int value, Calendar date){
        this.value = value;
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public Calendar getDate() {
        return date;
    }
}
