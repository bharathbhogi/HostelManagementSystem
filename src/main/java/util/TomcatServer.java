package util;

import controller.RoomServlet;
import controller.StudentServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatServer {

    public static void start() throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector(); // forces connector creation


        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context context = tomcat.addContext("", docBase.getAbsolutePath());


        context.setParentClassLoader(
                TomcatServer.class.getClassLoader()
        );


        Tomcat.addServlet(context, "studentServlet", new StudentServlet());
        context.addServletMappingDecoded("/students", "studentServlet");

        Tomcat.addServlet(context, "roomServlet", new RoomServlet());
        context.addServletMappingDecoded("/rooms", "roomServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
