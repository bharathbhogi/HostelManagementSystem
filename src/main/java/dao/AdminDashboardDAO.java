package dao;

import org.hibernate.Session;
import util.HibernateUtil;
import java.math.BigDecimal;


public class AdminDashboardDAO {

    public long getTotalRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(r) from Room r", Long.class)
                    .uniqueResult();
        }
    }

    public long getAvailableRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select count(r) from Room r where r.status = 'AVAILABLE'",
                    Long.class
            ).uniqueResult();
        }
    }

    public long getTotalStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(s) from Student s", Long.class)
                    .uniqueResult();
        }
    }

    public BigDecimal getTotalFeesCollected() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            BigDecimal sum = session.createQuery(
                    "select sum(f.amount) from Fee f",
                    BigDecimal.class
            ).uniqueResult();
            return sum != null ? sum : BigDecimal.ZERO;
        }
    }

}
