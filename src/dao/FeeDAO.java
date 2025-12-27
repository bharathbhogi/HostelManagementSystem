package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;

public class FeeDAO {

    public void payFee(int studentId, double amount) {
        String sql = "INSERT INTO fees(student_id, amount) VALUES (?,?)";

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
}
