package org.com.shipment;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShipmentDaoFalse {
    private static List<Shipment> shipments = new ArrayList<Shipment>();
    private EntityManager session;

    public ShipmentDaoFalse(EntityManager session){
        this.session = session;
    }

    public void save(Shipment shipment) {

        shipments.add(shipment);

        try {
            session.getTransaction().begin();

            session.persist(shipment);
            session.getTransaction().commit();
        } catch (Exception e){
            if( session.getTransaction() != null){
                session.getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }

    public List<Shipment> allShipmentConcludes() {
        List<Shipment> filters = new ArrayList<Shipment>();
        for(Shipment shipment : shipments) {
            if(shipment.isFinish()) filters.add(shipment);
        }
        return filters;
    }
    public List<Shipment> allShipmentCurrents() {
        List<Shipment> filters = new ArrayList<Shipment>();
        for(Shipment shipment : shipments) {
            if(!shipment.isFinish()) filters.add(shipment);
        }
        return filters;
    }

    public void refresh(Shipment shipment) { /* Do nothing */ }

    public int totalShipments(){
        Query query = (Query) session.createQuery("select count(s) from " +
                "Shipment s where s.isFinish = false");
        return ((Number) query.uniqueResult()).intValue();
    }

    public List<Shipment> perPeriod(Calendar begin, Calendar end){
        return session.createQuery("from Shipment s where s.createDate " +
                "between :begin and :end and s.isFinish = false")
                .setParameter("begin",begin)
                .setParameter("end",end)
                .getResultList();
    }

}
