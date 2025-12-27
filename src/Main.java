import java.util.Scanner;
import dao.StudentDAO;
import model.Student;
import util.DBConnection;

public class Main {

    public static void main(String[] args) {
        DBConnection.getConnection();

        Scanner sc = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();

        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Room No: ");
                    String room = sc.nextLine();

                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    studentDAO.addStudent(new Student(name, room, phone));
                    break;

                case 2:
                    studentDAO.viewStudents();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
            }
        }
    }
}
