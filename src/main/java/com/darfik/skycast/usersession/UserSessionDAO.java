package com.darfik.skycast.usersession;

import com.darfik.skycast.commons.daos.DAO;
import com.darfik.skycast.user.User;
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

    private UserSessionDAO() {
    }

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

    public List<UserSession> findByUserId(User user) {
        List<UserSession> userSessions = null;
        try (Session session = HibernateUtil.getSession()) {
            Query<UserSession> query = session.createQuery("FROM UserSession WHERE user = :user", UserSession.class);
            query.setParameter("user", user);
            userSessions = query.getResultList();
        } catch (HibernateException e) {
            log.error("Couldn't find the session by user id");
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

    public boolean isExpired(UserSession userSession) {
        return userSession.getExpiresAt().isBefore(LocalDateTime.now());
    }


    // Could be one query to DB, have to think about it
    public void deleteExpiredSessions(User user) {
            List<UserSession> userSessions = findByUserId(user);
            for (UserSession userSession : userSessions) {
                if (isExpired(userSession)) {
                    delete(userSession.getId());
                }
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
