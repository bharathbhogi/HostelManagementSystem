Hostel Management System (Java + Hibernate)

Description :- This project is a backend application written in Java to manage hostel operations like students, rooms, and fees.

Project Does :- It is like Hostel admin office, an Admin can perform operations like following;

* Add students to the hostel
* View all students
* Check which rooms are available
* Assign students to rooms
* Record hostel fee payments
* View fee payment history
Everything runs in the terminal / console.

Here are the Steps to clone this repo in your Local System and run this project(Windows/MacOS)
Before starting, make sure these are installed on your system.

1) Java (Very Important)-Java is needed to run the application.
   -Check if Java is installed: by running this command in Terminal(java -version)
2) Git is needed to download (clone) the project from GitHub.
   -check if Git is installed: by running this command in Terminal(git --version)
3) The project uses MySQL to store data like students, rooms, and fees.
   -You need: MySQL Workbench
4) IntelliJ IDEA :- This is the software used to open and run the project.
   -Community Edition is enough: https://www.jetbrains.com/idea/download/

Steps for Cloning a repo from Git:-
1) Open Terminal (macOS) or Command Prompt (Windows) and run:-
     git clone https://github.com/bharathbhogi/HostelManagementSystem.git 
     cd HostelManagementSystem
2) Create the database:-
     -Open MySQL Workbench and run the following commands one by one:

   CREATE DATABASE hostel_db;
   USE hostel_db;

   CREATE TABLE students (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(100),
   room_no VARCHAR(20),
   phone VARCHAR(15)
   );

   CREATE TABLE rooms (
   room_no VARCHAR(20) PRIMARY KEY,
   status VARCHAR(20),
   capacity INT
   );

   CREATE TABLE fees (
   id INT AUTO_INCREMENT PRIMARY KEY,
   student_id INT,
   amount DOUBLE,
   paid_date DATE
   );
These queries are required because databases are not shared on GitHub.

3) Set your database username and password:- Every computer has a different MySQL password, so you must update it.
   Open this file in the project:- src/hibernate.cfg.xml
   Find these lines :- <property name="hibernate.connection.username">root</property>
                       <property name="hibernate.connection.password">YOUR_PASSWORD</property>
   -Replace YOUR_PASSWORD with your MySQL password.

4) Open the project in IntelliJ 
   Open IntelliJ IDEA
   Click Open
   Select the HostelManagementSystem folder
   IntelliJ will automatically detect Maven
   Click Load / Import when asked
   Wait until all dependencies are downloaded

5) Run the application
   Open Main.java
   Click the Run ▶ button

You should see a menu like this:
==== Hostel Management System ====
1. Add Student
2. View Students
3. Pay Fee
4. View Available Rooms
5. View All Rooms
6. Search Students by Room No
7. View Fee Details
8. Exit

Author
Bhogi Bharath Gupta
