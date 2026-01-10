import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;

import java.io.File;

import controller.StudentServlet;

public class Main {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();

        // ✅ REQUIRED CONNECTOR
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        tomcat.getHost().setAppBase(".");

        Context ctx = tomcat.addWebapp(
                "",
                new File("web").getCanonicalPath()
        );

        Tomcat.addServlet(ctx, "studentServlet", new StudentServlet());
        ctx.addServletMappingDecoded("/students", "studentServlet");

        try {
            tomcat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tomcat.getServer().await();
    }
}
