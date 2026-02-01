package dao;

import model.Admin;
import org.hibernate.Session;
import util.HibernateUtil;

public class AdminHibernateDAO {

    public Admin findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Admin.class, username);
        }
    }
}
