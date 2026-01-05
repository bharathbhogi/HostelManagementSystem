import java.math.BigDecimal;
import java.util.Scanner;

import dao.FeeHibernateDAO;
import dao.RoomHibernateDAO;
import dao.StudentHibernateDAO;
import model.Student;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentHibernateDAO studentHibernateDAO = new StudentHibernateDAO();
        RoomHibernateDAO roomHibernateDAO = new RoomHibernateDAO();
        FeeHibernateDAO feeHibernateDAO = new FeeHibernateDAO();

        while (true) {
            System.out.println("\n==== Hostel Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Pay Fee");
            System.out.println("4. View Available Rooms");
            System.out.println("5. View All Rooms");
            System.out.println("6. Search Students by Room No");
            System.out.println("7. View Fee Details");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
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

                    studentHibernateDAO.addStudent(new Student(name, room, phone));
                    break;

                case 2:
                    studentHibernateDAO.viewStudents();
                    break;

                case 3:
                    System.out.print("Student ID: ");
                    int studentId = sc.nextInt();

                    System.out.print("Amount: ");
                    BigDecimal amount = sc.nextBigDecimal();

                    feeHibernateDAO.payFee(studentId, amount);
                    break;

                case 4:
                    roomHibernateDAO.viewAvailableRooms();
                    break;

                case 5:
                    roomHibernateDAO.viewAllRooms();
                    break;

                case 6:
                    System.out.print("Enter Room No: ");
                    String roomNo = sc.nextLine();
                    studentHibernateDAO.viewStudentsByRoom(roomNo);
                    break;

                case 7:
                    feeHibernateDAO.viewFees();
                    break;

                case 8:
                    System.out.println("Thank You ❤️");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
