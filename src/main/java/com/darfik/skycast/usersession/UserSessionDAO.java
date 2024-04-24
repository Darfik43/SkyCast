package com.darfik.skycast.usersession;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
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
            log.error("Couldn't save session, try again");
        }
    }

    @Override
    public Optional<UserSession> find(String id) {
        UserSession userSession = null;
        try (Session session = HibernateUtil.getSession()) {
            userSession = session.get(UserSession.class, id);
        } catch (HibernateException e) {
            log.error("Couldn't find the session");
        }
        return Optional.ofNullable(userSession);
    }

    @Override
    public List<UserSession> getAll() {
        List<UserSession> userSessions = null;
        try (Session session = HibernateUtil.getSession()) {
            userSessions = session.createQuery("FROM UserSession ", UserSession.class).getResultList();
        } catch (HibernateException e) {
            log.error("Couldn't get a list of sessions");
        }
        return userSessions;
    }

    @Override
    public void update(UserSession userSession) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(userSession);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't update the location");
        }
    }

    public void deleteExpiredSessions() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createMutationQuery("DELETE FROM UserSession WHERE expiresAt < :currentTime")
                    .setParameter("currentTime", LocalDateTime.now())
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't delete expired sessions");
        }
    }

    public boolean isExpired(String id) {
        boolean isExpired = false;
        try (Session session = HibernateUtil.getSession()) {
            UserSession userSession = session.get(UserSession.class, id);
            isExpired = userSession.getExpiresAt().isAfter(LocalDateTime.now());
            if (isExpired) {
                deleteExpiredSession(id);
            }
        } catch (HibernateException e) {
            log.error("Couldn't find the session");
        }
        return isExpired;
    }

    private void deleteExpiredSession(String id) {
        try (Session session = HibernateUtil.getSession()) {
            if (isExpired(id)) {
                Transaction tx = session.beginTransaction();
                session.createMutationQuery("DELETE FROM UserSession WHERE id = :id")
                        .setParameter("id", id)
                        .executeUpdate();
                tx.commit();
            }
        } catch (HibernateException e) {
            log.error("Couldn't delete the session");
        }
    }
    public void delete(String id) {
        try (Session session = HibernateUtil.getSession()) {
                Transaction tx = session.beginTransaction();
                session.createMutationQuery("DELETE FROM UserSession WHERE id = :id")
                        .setParameter("id", id)
                        .executeUpdate();
                tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't delete the session");
        }
    }
}
