package com.darfik.skycast.location;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.user.User;
import com.darfik.skycast.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@Log4j2
public class LocationDAO implements DAO<Location> {

    private static LocationDAO locationDAO;

    private LocationDAO() {
    }

    public static synchronized LocationDAO getInstance() {
        if (locationDAO == null) {
            locationDAO = new LocationDAO();
        }
        return locationDAO;
    }

    @Override
    public void save(Location location) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(location);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't save location, try again");
        }
    }

    @Override
    public Optional<Location> find(String name) {
        Location location = null;
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Location WHERE name = :name";
            location = session.createQuery(hql, Location.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (HibernateException e) {
            log.error("Couldn't find the location by name");
        }
        return Optional.ofNullable(location);
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

    @Override
    public List<Location> getAll() {
        List<Location> locations = null;
        try (Session session = HibernateUtil.getSession()) {
            locations = session.createQuery("FROM Location", Location.class).getResultList();
        } catch (HibernateException e) {
            log.error("Couldn't get a list of location");
        }
        return locations;
    }

    public void delete(Location location, User user) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Location WHERE name = :name AND user.id = :userID")
                    .setParameter("name", location.getName())
                    .setParameter("userID", user.getId())
                    .executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void update(Location location) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(location);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't update the location");
        }

    }
}
