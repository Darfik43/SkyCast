package com.darfik.skycast.dao;

import com.darfik.skycast.model.Location;
import com.darfik.skycast.model.User;
import com.darfik.skycast.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Log4j2
public class LocationDAO {

    private static LocationDAO locationDAO;

    private LocationDAO() {
    }

    public static synchronized LocationDAO getInstance() {
        if (locationDAO == null) {
            locationDAO = new LocationDAO();
        }
        return locationDAO;
    }

    public void save(Location location) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(location);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't save location, try again");
        }
    }

    public List<Location> findLocationsByUser(User user) {
        List<Location> locations = null;
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Location WHERE user = :user";
            locations = session.createQuery(hql, Location.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (HibernateException e) {
            log.error("Couldn't find the location by name");
        }
        return locations;
    }
    public void delete(Location location, User user) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Location WHERE latitude = :latitude" +
                            " AND longitude =:longitude AND user.id = :userID")
                    .setParameter("latitude", location.getLatitude())
                    .setParameter("longitude", location.getLongitude())
                    .setParameter("userID", user.getId())
                    .executeUpdate();
            tx.commit();
        }
    }
}
