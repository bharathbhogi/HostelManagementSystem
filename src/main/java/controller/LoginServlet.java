package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.Message;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Admin creds = mapper.readValue(req.getInputStream(), Admin.class);

        // ✅ hardcoded admin login (Option B)
        if ("admin".equals(creds.getUsername())
                && "admin123".equals(creds.getPassword())) {

            HttpSession session = req.getSession(true);
            session.setAttribute("admin", creds.getUsername());

            mapper.writeValue(resp.getWriter(),
                    new Message("Login successful"));

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(resp.getWriter(),
                    new Message("Invalid credentials"));
        }
    }
}
