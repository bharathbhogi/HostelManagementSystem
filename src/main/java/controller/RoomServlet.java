package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RoomHibernateDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Room;

import java.io.IOException;
import java.util.List;

public class RoomServlet extends HttpServlet {

    private final RoomHibernateDAO dao = new RoomHibernateDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String status = req.getParameter("status");
        List<Room> rooms;

        try {
            if (status != null && !status.isEmpty()) {
                rooms = dao.getRoomsByStatus(status);
            } else {
                rooms = dao.getAllRooms();
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getWriter(), rooms);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(),
                    new MessageResponse("Failed to fetch rooms"));
        }
    }

    // Simple response wrapper
    static class MessageResponse {
        public String message;
        public MessageResponse(String message) {
            this.message = message;
        }
    }
}
