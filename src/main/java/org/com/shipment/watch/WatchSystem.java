package org.com.shipment.watch;

import java.util.Calendar;

public class WatchSystem implements Watch {

    public Calendar today(){
        return Calendar.getInstance();
    }
}
