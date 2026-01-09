package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.StudentHibernateDAO;
import model.Student;

import java.io.OutputStream;
import java.util.List;

public class StudentController implements HttpHandler {

    private final StudentHibernateDAO studentDAO = new StudentHibernateDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) {

        try {
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            List<Student> students = studentDAO.getAllStudents();
            String json = mapper.writeValueAsString(students);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, 0);

            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.flush();
            os.close();

            exchange.close();   // THIS LINE IS CRITICAL

        } catch (Exception e) {
            e.printStackTrace();
            try {
                exchange.sendResponseHeaders(500, -1);
                exchange.close();
            } catch (Exception ignored) {}
        }
    }
}
