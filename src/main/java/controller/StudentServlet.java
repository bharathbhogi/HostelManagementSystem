package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.StudentHibernateDAO;
import model.Student;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {

    private final StudentHibernateDAO dao = new StudentHibernateDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Student> students = dao.getAllStudents();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        mapper.writeValue(resp.getWriter(), students);
    }
}
