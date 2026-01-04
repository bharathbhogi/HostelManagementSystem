package dao;

import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class StudentHibernateDAO {

    public void addStudent(Student student) {

        Transaction tx = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.save(student);
            tx.commit();

            System.out.println("Student added successfully (Hibernate)");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void viewStudents() {

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Student> list =
                    session.createQuery("from Student", Student.class).list();

            for (Student s : list) {
                System.out.println(
                        s.getId() + " " +
                                s.getName() + " " +
                                s.getRoomNo() + " " +
                                s.getPhone()
                );
            }
        }
    }
}
