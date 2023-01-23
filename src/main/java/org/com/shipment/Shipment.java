package org.com.shipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.persistence.*;
import org.com.shipment.order.Order;
import org.com.shipment.user.Users;
import org.h2.engine.User;

@Entity
@Table(name = "Shipment")
public class Shipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private int shipment_id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = true)
    private Users user;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Shipment_Order",
            joinColumns = {@JoinColumn(name = "shipment_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")}
    )
    private List<Order> order = new ArrayList<Order>();

    @Column(name = "createDate")
    private Calendar createDate;

    @Column(name = "isFinish")
    private boolean isFinish;

    public Shipment(String name){
        this.name = name;
        isFinish = false;
    }

    public Shipment(String name,Order order){
        this.name = name;
        this.order.add(order) ;
        isFinish = false;
    }

    public Shipment(String name, Users user){
        this.name = name;
        isFinish = false;
        this.user = user;
    }

    public void registerOrder(Order order){
        this.order.add(order);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getUser(){ return user; }

    public void setUser(Users user){ this.user = user; }

    public void finish(){ isFinish = true; }

    public boolean isFinish(){ return isFinish; }

    public void setCreateDate(Calendar createDate){ this.createDate = createDate; }

    public Calendar getCreateDate(){ return createDate; }


    public List<Order> getValues(){
        return order;
    }
}
