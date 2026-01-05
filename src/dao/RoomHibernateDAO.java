package dao;

import model.Room;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class RoomHibernateDAO {

    public void viewAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Room> rooms = session.createQuery("from Room", Room.class).list();

            System.out.println("\nRoom No\tStatus\tCapacity");
            System.out.println("------------------------");

            for (Room r : rooms) {
                System.out.println(
                        r.getRoomNo() + "\t" +
                                r.getStatus() + "\t" +
                                r.getCapacity()
                );
            }
        }
    }

    public void viewAvailableRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Room> rooms = session.createQuery(
                    "from Room where status='AVAILABLE'", Room.class
            ).list();

            rooms.forEach(r ->
                    System.out.println("Available Room: " + r.getRoomNo())
            );
        }
    }
}
