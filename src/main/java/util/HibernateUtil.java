package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            System.out.println("Hibernate init started");

            sessionFactory = new Configuration()
                    .configure(new File("config/hibernate.cfg.xml"))
                    .buildSessionFactory();

            System.out.println("Hibernate init success");

        } catch (Throwable ex) {
            System.out.println("Hibernate init FAILED");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
