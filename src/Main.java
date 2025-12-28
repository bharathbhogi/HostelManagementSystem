import java.util.Scanner;

import dao.StudentDAO;
import dao.FeeDAO;
import dao.RoomDAO;
import model.Student;
import util.DBConnection;

public class Main {

    public static void main(String[] args) {

        // Load env + verify DB connection
        DBConnection.getConnection();

        Scanner sc = new Scanner(System.in);

        StudentDAO studentDAO = new StudentDAO();
        FeeDAO feeDAO = new FeeDAO();
        RoomDAO roomDAO = new RoomDAO();

        while (true) {
            System.out.println("\n==== Hostel Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Pay Fee");
            System.out.println("4. View Available Rooms");
            System.out.println("5. View All Rooms");
            System.out.println("6. Search Students by Room No");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Room No: ");
                    String room = sc.nextLine();

                    if (studentDAO.isRoomFull(room)) {
                        System.out.println("Room is already full!");
                        break;   // exits this case safely
                    }

                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    studentDAO.addStudent(new Student(name, room, phone));
                    break;


                case 2:
                    studentDAO.viewStudents();
                    break;

                case 3:
                    System.out.print("Student ID: ");
                    int studentId = sc.nextInt();

                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();

                    feeDAO.payFee(studentId, amount);
                    break;

                case 4:
                    roomDAO.viewAvailableRooms();
                    break;

                case 5:
                    roomDAO.viewAllRooms();
                    break;

                case 6:
                    System.out.print("Enter Room No: ");
                    String roomNo = sc.nextLine();
                    studentDAO.viewStudentsByRoom(roomNo);
                    break;

                case 7:
                    System.out.println("Thank You❤️");
                    System.exit(0);


                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
