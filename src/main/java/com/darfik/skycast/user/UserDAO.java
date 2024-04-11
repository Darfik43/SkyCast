package com.darfik.skycast.user;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDAO implements DAO<User> {

    private static UserDAO userDAO;
    private UserDAO() {}

    public static synchronized UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    @Override
    public void save(User user) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }
    }

    @Override
    public Optional<User> get(int id) {
        User user = null;
        try (Session session = HibernateUtil.getSession()) {
            user = session.get(User.class, id);
        } catch (HibernateException e) {
            //TODO
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        try (Session session = HibernateUtil.getSession()) {
            users = session.createQuery("FROM User", User.class).getResultList();
        } catch (HibernateException e) {
            //TODO
        }
        return users;
    }

    @Override
    public void update(User user, String[] params) {
        user.setUsername(Objects.requireNonNull(params[0], "Name cannot be null"));
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }

    }
}
