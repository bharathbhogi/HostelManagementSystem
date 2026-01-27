package dao;

import model.Room;
import org.hibernate.Session;
import util.HibernateUtil;
import dao.RoomHibernateDAO;
import java.util.List;

public class RoomHibernateDAO {



    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        }
    }

    public List<Room> getRoomsByStatus(String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Room where status = :status", Room.class
                    ).setParameter("status", status)
                    .list();
        }
    }

    public Room getRoomByRoomNo(String roomNo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, roomNo);
        }
    }



}
