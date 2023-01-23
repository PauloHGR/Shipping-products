package org.com.shipment.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDAO {

    private final EntityManager session;

    public UserDAO(EntityManager session){
        this.session = session;
    }

    //public Users findById(int id){
        //return session.load(User.class,id);
    //}

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
}
