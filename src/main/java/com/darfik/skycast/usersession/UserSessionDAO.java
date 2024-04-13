package com.darfik.skycast.usersession;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserSessionDAO implements DAO<UserSession> {

    private static UserSessionDAO userSessionDAO;
    private UserSessionDAO() {}

    public static synchronized UserSessionDAO getInstance() {
        if (userSessionDAO == null) {
            userSessionDAO = new UserSessionDAO();
        }
        return userSessionDAO;
    }
    @Override
    public void save(UserSession userSession) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(userSession);
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }
    }

    @Override
    public Optional<UserSession> get(int id) {
        UserSession userSession = null;
        try (Session session = HibernateUtil.getSession()) {
            userSession = session.get(UserSession.class, id);
        } catch (HibernateException e) {
            //TODO
        }
        return Optional.ofNullable(userSession);
    }

    @Override
    public List<UserSession> getAll() {
        List<UserSession> userSessions = null;
        try (Session session = HibernateUtil.getSession()) {
            userSessions = session.createQuery("FROM UserSession ", UserSession.class).getResultList();
        } catch (HibernateException e) {
            //TODO
        }
        return userSessions;
    }

    @Override
    public void update(UserSession userSession, String[] params) {
        userSession.setId(Objects.requireNonNull(params[0], "Name cannot be null"));
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(userSession);
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }
    }

    public void deleteExpiredSessions() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM UserSession WHERE expiresAt < :currentTime")
                    .setParameter("currentTime", LocalDateTime.now())
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }
    }

    public void delete(String id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM UserSession WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            //TODO
        }
    }
}
