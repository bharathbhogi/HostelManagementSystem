package dao;

import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class StudentHibernateDAO {


    public void addStudent(Student student) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
            System.out.println("Student added successfully (Hibernate)");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }


    public void viewStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> students =
                    session.createQuery("FROM Student", Student.class).list();

            for (Student s : students) {
                System.out.println(
                        s.getId() + " " +
                                s.getName() + " " +
                                s.getRoomNo() + " " +
                                s.getPhone()
                );
            }
        }
    }


    public void viewStudentsByRoom(String roomNo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Student> students =
                    session.createQuery(
                                    "FROM Student WHERE roomNo = :room",
                                    Student.class
                            )
                            .setParameter("room", roomNo)
                            .list();

            if (students.isEmpty()) {
                System.out.println("No students found in room " + roomNo);
                return;
            }

            System.out.println("\nStudents in Room " + roomNo + ":");
            System.out.println("-----------------------------");

            for (Student s : students) {
                System.out.println(
                        s.getId() + " " +
                                s.getName() + " " +
                                s.getPhone()
                );
            }
        }
    }


    public List<Student> getAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }



    public boolean isRoomFull(String roomNo, int capacity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Long count = session.createQuery(
                            "SELECT COUNT(s) FROM Student s WHERE s.roomNo = :room",
                            Long.class
                    )
                    .setParameter("room", roomNo)
                    .uniqueResult();

            return count != null && count >= capacity;
        }
    }
}
