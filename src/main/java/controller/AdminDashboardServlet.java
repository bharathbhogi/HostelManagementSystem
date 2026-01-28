package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AdminDashboardDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboardServlet extends HttpServlet {

    private final AdminDashboardDAO dao = new AdminDashboardDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRooms", dao.getTotalRooms());
        summary.put("availableRooms", dao.getAvailableRooms());
        summary.put("totalStudents", dao.getTotalStudents());
        summary.put("totalFeesCollected", dao.getTotalFeesCollected());

        mapper.writeValue(resp.getWriter(), summary);
    }
}
