package com.darfik.skycast.user;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.utils.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Log4j2
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
            log.error("Couldn't save user, try again");
        }
    }

    public Optional<User> find(String username) {
        User user = null;
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM User WHERE username = :username";
            user = session.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (HibernateException e) {
            log.error("Couldn't find user");
        }
        return Optional.ofNullable(user);
    }

        @Override
    public List<User> getAll() {
        List<User> users = null;
        try (Session session = HibernateUtil.getSession()) {
            users = session.createQuery("FROM User", User.class).getResultList();
        } catch (HibernateException e) {
            log.error("Couldn't get the a list of location");
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
            log.error("Couldn't update the location");
        }

    }
}
