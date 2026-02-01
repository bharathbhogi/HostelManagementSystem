package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RoomHibernateDAO;
import dao.StudentHibernateDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Message;
import model.Student;

import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {

    private final StudentHibernateDAO dao = new StudentHibernateDAO();
    private final RoomHibernateDAO roomDao = new RoomHibernateDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Student student = mapper.readValue(req.getInputStream(), Student.class);

            String roomNo = student.getRoomNo();
            if (roomNo == null || roomNo.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(),
                        new Message("Room number is required"));
                return;
            }

            var room = roomDao.getRoomByRoomNo(roomNo);
            if (room == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(),
                        new Message("Room does not exist"));
                return;
            }

            boolean isFull = dao.isRoomFull(roomNo, room.getCapacity());
            if (isFull) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(),
                        new Message("Room is already full"));
                return;
            }

            dao.saveStudent(student);

            long count = dao.getStudentsByRoomNo(roomNo).size();
            if (count >= room.getCapacity()) {
                roomDao.updateRoomStatus(roomNo, "OCCUPIED");
            } else {
                roomDao.updateRoomStatus(roomNo, "AVAILABLE");
            }

            resp.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(resp.getWriter(),
                    new Message("Student added successfully"));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(),
                    new Message("Failed to add student"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String roomNo = req.getParameter("roomNo");

        try {
            List<Student> students;
            if (roomNo != null && !roomNo.isEmpty()) {
                students = dao.getStudentsByRoomNo(roomNo);
            } else {
                students = dao.getAllStudents();
            }

            mapper.writeValue(resp.getWriter(), students);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(),
                    new Message("Failed to fetch students"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(),
                    new Message("Student id is required"));
            return;
        }

        int id = Integer.parseInt(idParam);
        dao.deleteStudentById(id);

        mapper.writeValue(resp.getWriter(),
                new Message("Student removed successfully"));
    }
}
