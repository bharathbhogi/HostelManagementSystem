package dao;

import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import dao.RoomHibernateDAO;
import java.util.List;
import org.hibernate.Transaction;

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

    public void updateRoomStatus(String roomNo, String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            Room room = session.get(Room.class, roomNo);
            if (room != null) {
                room.setStatus(status);
                session.merge(room);
            }
            tx.commit();
        }
    }

    public void addRoom(Room room) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(room);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }



}
