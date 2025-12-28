package dao;

import model.Student;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentDAO {

    public void addStudent(Student s) {
        String sql = "INSERT INTO students(name, room_no, phone) VALUES (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getRoomNo());
            ps.setString(3, s.getPhone());
            ps.executeUpdate();

            System.out.println("Student Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewStudents() {
        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("room_no") + " " + rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewStudentsByRoom(String roomNo) {

        String sql = "SELECT * FROM students WHERE room_no = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\nStudents in Room " + roomNo + ":");
            System.out.println("-----------------------------");

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getString("phone")
                );
            }

            if (!found) {
                System.out.println("No students found in this room.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isRoomFull(String roomNo) {

        String sql = """
        SELECT COUNT(*) >= capacity
        FROM students s
        JOIN rooms r ON s.room_no = r.room_no
        WHERE r.room_no = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
