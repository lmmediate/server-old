package ru.easysales;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Path("items")
public class ItemResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Item> getItems() {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class)
                .buildSessionFactory();

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Item> items = session.createQuery("from Item", Item.class).getResultList();
            session.getTransaction().commit();
            return items;
        }

//        Item item1 = new Item();
//        item1.setName("SomeName");
//        item1.setCategory("SomeCategory");
//        item1.setDateIn(new Date());
//
//        Item item2 = new Item();
//        item2.setName("NewName");
//        item2.setCategory("NewCategory");
//        item2.setNewPrice(44.5);
//
//        List<Item> items = Arrays.asList(item1, item2);
//
//        return items;
    }
}
