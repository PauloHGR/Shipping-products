package org.com.shipment.config;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public EntityManager factory(){

        EntityManager session;

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        SessionFactory factory = metadata.getSessionFactoryBuilder().build();

        session = factory.openSession();
        return session;
    }
}
