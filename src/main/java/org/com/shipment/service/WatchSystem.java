package org.com.shipment.service;

import org.com.shipment.watch.Watch;

import java.util.Calendar;

public class WatchSystem implements Watch {

    public Calendar today(){
        return Calendar.getInstance();
    }
}
