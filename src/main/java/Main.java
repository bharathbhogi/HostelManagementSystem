import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;

import java.io.File;
import controller.RoomServlet;
import controller.StudentServlet;
import controller.FeeServlet;


public class Main {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();


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

        Tomcat.addServlet(ctx, "roomServlet", new RoomServlet());
        ctx.addServletMappingDecoded("/rooms", "roomServlet");

        Tomcat.addServlet(ctx, "feeServlet", new FeeServlet());
        ctx.addServletMappingDecoded("/fees", "feeServlet");



        try {
            tomcat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tomcat.getServer().await();
    }
}
