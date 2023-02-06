package org.com.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class ShipmentStartWebApplication {
    public static void main(String[] args){
        SpringApplication.run(ShipmentStartWebApplication.class,args);
    }
}
