package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.FeeHibernateDAO;
import dao.StudentHibernateDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Fee;
import model.Message;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FeeServlet extends HttpServlet {

    private final FeeHibernateDAO feeDao = new FeeHibernateDAO();
    private final StudentHibernateDAO studentDao = new StudentHibernateDAO();
    private final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Fee fee = mapper.readValue(req.getInputStream(), Fee.class);

            if (fee.getAmount() == null || fee.getAmount().signum() <= 0) {

                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new Message("Invalid amount"));
                return;
            }

            var students = studentDao.getAllStudents();
            boolean exists = students.stream().anyMatch(s -> s.getId() == fee.getStudentId());
            if (!exists) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new Message("Student not found"));
                return;
            }

            if (fee.getPaidDate() == null) {
                fee.setPaidDate(java.time.LocalDate.now().toString());
            }


            feeDao.saveFee(fee);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(resp.getWriter(), new Message("Fee recorded"));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new Message("Failed to record fee"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String sid = req.getParameter("studentId");
        String summary = req.getParameter("summary");

        resp.setContentType("application/json");

        if (sid == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new Message("studentId is required"));
            return;
        }

        int studentId = Integer.parseInt(sid);

        if ("true".equals(summary)) {
            var result = new java.util.HashMap<String, Object>();
            result.put("studentId", studentId);
            result.put("totalPaid", feeDao.getTotalPaidByStudent(studentId));
            mapper.writeValue(resp.getWriter(), result);
            return;
        }

        List<Fee> fees = feeDao.getFeesByStudentId(studentId);
        mapper.writeValue(resp.getWriter(), fees);

    }

    static class Message {
        public String message;
        public Message(String message) { this.message = message; }
    }
}
