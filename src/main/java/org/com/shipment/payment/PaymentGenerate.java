package org.com.shipment.payment;

import org.com.shipment.Shipment;
import org.com.shipment.ShipmentDaoFalse;
import org.com.shipment.analyzer.Analyzer;
import org.com.shipment.watch.Watch;
import org.com.shipment.watch.WatchSystem;

import java.util.Calendar;
import java.util.List;

public class PaymentGenerate {

    private RepositoryPayment payments;
    private ShipmentDaoFalse dao;
    private Analyzer analyzer;
    private Watch watch;

    public PaymentGenerate(RepositoryPayment payments,
                           ShipmentDaoFalse dao,
                           Analyzer analyzer,
                           Watch watch){
        this.payments = payments;
        this.dao = dao;
        this.analyzer = analyzer;
        this.watch = watch;
    }

    public PaymentGenerate(RepositoryPayment payments,
                           ShipmentDaoFalse dao,
                           Analyzer analyzer){
        this.payments = payments;
        this.dao = dao;
        this.analyzer = analyzer;
        this.watch = new WatchSystem();
    }

    public void generate(){
        List<Shipment> shipments = dao.allShipmentConcludes();

        for (Shipment shipment : shipments){
            analyzer.findBiggestAndSmallestOrder(shipment);

            Payment payment = new Payment(analyzer.getBiggerOrder(),
                    checkWorkingDay());
            payments.save(payment);
        }
    }

    public Calendar checkWorkingDay(){
        Calendar dateActual = watch.today();

        if (dateActual.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            dateActual.add(Calendar.DAY_OF_MONTH,2);
        } else if (dateActual.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            dateActual.add(Calendar.DAY_OF_MONTH,1);
        }

        return dateActual;
    }
}
