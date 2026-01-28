package controller;
import dao.RoomHibernateDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.StudentHibernateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;
import model.Message;
import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {

    private final StudentHibernateDAO dao = new StudentHibernateDAO();
    private final ObjectMapper mapper = new ObjectMapper();
    private final RoomHibernateDAO roomDao = new RoomHibernateDAO();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Student student = mapper.readValue(req.getInputStream(), Student.class);

            String roomNo = student.getRoomNo();
            if (roomNo == null || roomNo.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(),
                        new MessageResponse("Room number is required"));
                return;
            }

            var room = roomDao.getRoomByRoomNo(roomNo);
            if (room == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(),
                        new MessageResponse("Room does not exist"));
                return;
            }

            boolean isFull = dao.isRoomFull(roomNo, room.getCapacity());
            if (isFull) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(),
                        new MessageResponse("Room is already full"));
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
                    new MessageResponse("Student added successfully"));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(),
                    new MessageResponse("Failed to add student"));
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String roomNo = req.getParameter("roomNo");
        List<Student> students;

        try {
            if (roomNo != null && !roomNo.isEmpty()) {
                students = dao.getStudentsByRoomNo(roomNo);
            } else {
                students = dao.getAllStudents();
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getWriter(), students);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(),
                    new MessageResponse("Failed to fetch students"));
        }
    }


    static class MessageResponse {
        public String message;
        public MessageResponse(String message) {
            this.message = message;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
