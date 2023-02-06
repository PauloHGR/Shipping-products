package org.com.shipment.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int order_id;

    /*@ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Order_Product",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    private List<Product> product = new ArrayList<>();*/

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = true)
    private Product product;

    private int amount;

    public Order(Product product, int amount){
        //this.product.add(product);
        this.amount = amount;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
