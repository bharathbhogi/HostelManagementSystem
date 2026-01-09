package util;

import controller.StudentServlet;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

    public static void start() throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.getConnector();

        var context = tomcat.addContext("", null);

        Tomcat.addServlet(context, "studentServlet", new StudentServlet());
        context.addServletMappingDecoded("/students", "studentServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
