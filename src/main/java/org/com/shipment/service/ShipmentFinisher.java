package org.com.shipment.service;

import org.com.shipment.Notifier;
import org.com.shipment.model.Shipment;
import org.com.shipment.service.ShipmentDaoFalse;

import java.util.Calendar;
import java.util.List;

public class ShipmentFinisher {

    private int total = 0;
    private final ShipmentDaoFalse dao;
    private final Notifier notifier;

    public ShipmentFinisher(ShipmentDaoFalse dao, Notifier notifier){
        this.dao = dao;
        this.notifier = notifier;
    }

    public void finish(){

        List<Shipment> shipments = dao.allShipmentCurrents();

        for(Shipment shipment : shipments){

            try {
                if (startedLastWeek(shipment)) {
                    shipment.finish();
                    total++;
                    dao.refresh(shipment);
                    notifier.send(shipment);
                }
            } catch (Exception exception){

                //loop forward
            }
        }
    }

    private boolean startedLastWeek(Shipment shipment){
        return daysBetween(shipment.getCreateDate(), Calendar.getInstance()) >= 7;
    }

    private int daysBetween(Calendar begin, Calendar end){
        Calendar beginClone = (Calendar) begin.clone();
        int interval = 0;

        while (beginClone.before(end)){
            beginClone.add(Calendar.DAY_OF_MONTH,1);
            interval++;
        }
        return interval;
    }

    public int getTotalConcludes(){
        return total;
    }
}
