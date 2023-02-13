package org.com.shipment.service;

import org.com.shipment.model.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

import java.util.List;


@Service
public class UserDAO{


    //private final EntityManager session;
    @Autowired
    private EntityManager session;

    public UserDAO(EntityManager session){
        this.session = session;
    }

    public Users findByNameAndEmail(String name, String email){
        try {
            //Session session = factory.openSession();

            session.getTransaction().begin();
            Query q = (Query) session.createQuery("from Users u where u.name = :name and u.email = :email")
                    .setParameter("name",name)
                    .setParameter("email",email);

            Users user = (Users) q.uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            if (session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }

    }

    public void save(Users user){

        try {
            session.getTransaction().begin();

            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e){
            if( session.getTransaction() != null){
                session.getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }
    public void delete(Users user){
        session.getTransaction().begin();
        session.remove(user);
        session.getTransaction().commit();
    }

    public List<Users> getUsers() {

        try {

            session.getTransaction().begin();
            Query q = (Query) session.createQuery("Select u from Users u");

            List<Users> users = q.list();
            session.getTransaction().commit();
            return users;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
}
