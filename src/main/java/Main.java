import util.RestServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("MAIN CLASS EXECUTED");
        try {
            RestServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
