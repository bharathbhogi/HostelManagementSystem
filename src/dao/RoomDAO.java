package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import util.DBConnection;

public class RoomDAO {

    public void viewAvailableRooms() {
        String sql = "SELECT room_no FROM rooms WHERE status = 'AVAILABLE'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Available Room: " + rs.getString("room_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewAllRooms() {
        String sql = "SELECT room_no, status FROM rooms";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nRoom No\tStatus");
            System.out.println("----------------");

            while (rs.next()) {
                System.out.println(
                        rs.getString("room_no") + "\t" +
                                rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
