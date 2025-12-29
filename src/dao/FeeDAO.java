package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import util.DBConnection;

public class FeeDAO {

    public void payFee(int studentId, double amount) {

        String sql = "INSERT INTO fees(student_id, amount, paid_date) VALUES (?, ?, CURDATE())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            System.out.println("Fee Paid Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewFees() {

        String sql = "SELECT student_id, amount, paid_date FROM fees";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nStudent ID\tAmount\tPaid Date");
            System.out.println("----------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("student_id") + "\t\t" +
                                rs.getDouble("amount") + "\t" +
                                rs.getDate("paid_date")
                );
            }

            if (!found) {
                System.out.println("No fee records found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
