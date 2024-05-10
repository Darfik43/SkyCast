package com.darfik.skycast.usersession;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public Optional<UserSession> findByUserId(Long userId) {
        UserSession userSession = null;
        try (Session session = HibernateUtil.getSession()) {
            Query<UserSession> query = session.createQuery("FROM UserSession WHERE user.id = :userId", UserSession.class);
            query.setParameter("userId", userId);
            userSession = query.uniqueResult();
        } catch (HibernateException e) {
            log.error("Couldn't find the session by user id");
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


    //TODO Doesn't update existing session because it has a brand new id
    @Override
    public void update(UserSession userSession) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            //TODO UserSession userSession1 = session.createQuery("FROM UserSession ", UserSession.class).getSingleResultOrNull();
            session.merge(userSession);
            tx.commit();
        } catch (HibernateException e) {
            log.error("Couldn't update the location");
        }
    }

    public boolean isExpired(Long userId) {
        boolean isExpired = false;
        try (Session session = HibernateUtil.getSession()) {
            isExpired = findByUserId(userId)
                    .map(userSession -> userSession.getExpiresAt()
                            .isBefore(LocalDateTime.now()))
                    .orElse(false);
            if (isExpired) {
                deleteExpiredSession(userId);
            }
        } catch (HibernateException e) {
            log.error("Couldn't find the session");
        }
        return isExpired;
    }

    private void deleteExpiredSession(Long userId) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM UserSession WHERE user.id = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            tx.commit();
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
