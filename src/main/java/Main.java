import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import controller.LoginServlet;
import controller.LogoutServlet;
import controller.StudentServlet;
import controller.RoomServlet;
import controller.FeeServlet;
import controller.AdminDashboardServlet;
import filter.AuthFilter;

//  Tomcat 10 correct imports
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

public class Main {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.getConnector(); // force connector

        String webDir = System.getProperty("user.dir") + "/web";

        Context ctx = tomcat.addWebapp("", webDir);


        // -------- SERVLETS --------
        Tomcat.addServlet(ctx, "login", new LoginServlet());
        ctx.addServletMappingDecoded("/login", "login");

        Tomcat.addServlet(ctx, "logout", new LogoutServlet());
        ctx.addServletMappingDecoded("/logout", "logout");

        Tomcat.addServlet(ctx, "students", new StudentServlet());
        ctx.addServletMappingDecoded("/students", "students");

        Tomcat.addServlet(ctx, "rooms", new RoomServlet());
        ctx.addServletMappingDecoded("/rooms", "rooms");

        Tomcat.addServlet(ctx, "fees", new FeeServlet());
        ctx.addServletMappingDecoded("/fees", "fees");

        Tomcat.addServlet(ctx, "admin", new AdminDashboardServlet());
        ctx.addServletMappingDecoded("/admin/summary", "admin");

        // -------- FILTER (AUTH) --------
        FilterDef authFilterDef = new FilterDef();
        authFilterDef.setFilterName("authFilter");
        authFilterDef.setFilterClass(AuthFilter.class.getName());
        ctx.addFilterDef(authFilterDef);

        FilterMap authFilterMap = new FilterMap();
        authFilterMap.setFilterName("authFilter");
        authFilterMap.addURLPattern("/*");
        ctx.addFilterMap(authFilterMap);

        // -------- START TOMCAT --------
        tomcat.start();
        System.out.println("Tomcat started on http://localhost:8080");

        tomcat.getServer().await();
    }
}
