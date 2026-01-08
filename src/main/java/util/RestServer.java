package util;

import com.sun.net.httpserver.HttpServer;
import controller.StudentController;
import java.net.InetSocketAddress;

public class RestServer {

    public static void start() throws Exception {

        System.out.println("RestServer start method called");

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        System.out.println("Registering students context");

        server.createContext("/students", new StudentController());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started");
    }
}
