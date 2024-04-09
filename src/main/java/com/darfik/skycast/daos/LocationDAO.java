package com.darfik.skycast.daos;

import com.darfik.skycast.models.Location;
import com.darfik.skycast.models.User;
import com.darfik.skycast.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LocationDAO implements DAO<Location>{

    private static LocationDAO locationDAO;
    private LocationDAO() {}

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
            //TODO
        }
    }

    @Override
    public Optional<Location> get(int id) {
        Location location = null;
        try (Session session = HibernateUtil.getSession()) {
            location = session.get(Location.class, id);
        } catch (HibernateException e) {
            //TODO
        }
        return Optional.ofNullable(location);
    }

    @Override
    public List<Location> getAll() {
        List<Location> locations = null;
        try (Session session = HibernateUtil.getSession()) {
            locations = session.createQuery("FROM Location", Location.class).getResultList();
        } catch (HibernateException e) {
            //TODO
        }
        return locations;
    }

    @Override
    public void update(Location location, String[] params) {
        location.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(location);
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }

    }
}
