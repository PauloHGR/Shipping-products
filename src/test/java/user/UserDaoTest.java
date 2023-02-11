package user;

import org.com.shipment.model.Users;
import org.com.shipment.service.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class UserDaoTest {

    private Session session;
    @Autowired
    private UserDAO userDAO;

    @Before
    public void initSession(){

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        SessionFactory factory = metadata.getSessionFactoryBuilder().build();

        session = factory.openSession();
        userDAO = new UserDAO(session);

    }

    @After
    public void closeSession(){
        session.close();
    }

    @Test
    public void findByNameAndEmail(){

        Users newUser = new Users("Rocha","rocha@hotmail.com");
        userDAO.save(newUser);
        Users userFinded = userDAO.findByNameAndEmail("Rocha","rocha@hotmail.com");

        Assert.assertEquals("Rocha", userFinded.getName());
        Assert.assertEquals("rocha@hotmail.com",userFinded.getEmail());

        session.close();
    }

    @Test
    public void deleteOneUser(){

        Users user = new Users("Paulo ROcha", "pr@gmail.com.br");
        userDAO.save(user);
        userDAO.delete(user);


        Users userInDB = userDAO.findByNameAndEmail("Paulo Rocha", "pr@gmail.com.br");

        Assert.assertNull(userInDB);
    }

    @Test
    public void getUsers(){
        Users user1 = new Users("Paulo Rocha", "pr@gmail.com.br");
        Users user2 = new Users("Patricia Rocha", "patr@gmail.com.br");

        userDAO.save(user1);
        userDAO.save(user2);

        List<Users> usersList = userDAO.getUsers();

        Assert.assertEquals(usersList.size(),2);
        Assert.assertEquals(usersList.get(0).getName(),"Paulo Rocha");
        Assert.assertEquals(usersList.get(1).getName(),"Patricia Rocha");
    }

    @Test
    public void getUsersEmpty(){
        List<Users> usersList = userDAO.getUsers();

        Assert.assertEquals(usersList.size(),0);
    }
}
