package dao;

import model.Fee;
import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.math.BigDecimal;
import java.util.List;
public class FeeHibernateDAO {

    public void payFee(int studentId, BigDecimal amount) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {


            Student student = session.get(Student.class, studentId);
            if (student == null) {
                System.out.println("Student ID not found. Fee not recorded.");
                return;
            }

            tx = session.beginTransaction();

            Fee fee = new Fee(studentId, amount);
            session.persist(fee);

            tx.commit();
            System.out.println("Fee paid successfully");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }


    public void viewFees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Fee> fees = session.createQuery("from Fee", Fee.class).list();

            System.out.println("\nStudent ID\tAmount\tPaid Date");
            System.out.println("----------------------------------");

            fees.forEach(f ->
                    System.out.println(
                            f.getStudentId() + "\t\t" +
                                    f.getAmount() + "\t" +
                                    f.getPaidDate()
                    )
            );
        }
    }
}
