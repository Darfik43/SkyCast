package com.darfik.skycast.dao;

import com.darfik.skycast.model.User;
import com.darfik.skycast.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
@Log4j2
public class UserDAO{

    private static UserDAO userDAO;
    private UserDAO() {}

    public static synchronized UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public void save(User user) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (HibernateException e) {
            throw new HibernateException("User already exists");
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
}
