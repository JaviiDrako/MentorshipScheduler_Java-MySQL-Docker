package com.mentorshipSystem;

import com.mentorshipSystem.crud.TutorDAO;
import com.mentorshipSystem.crud.StudentDAO;
import com.mentorshipSystem.crud.UserDAO;
import com.mentorshipSystem.menus.TutorMenu;
import com.mentorshipSystem.menus.StudentMenu;
import com.mentorshipSystem.models.Student;
import com.mentorshipSystem.models.Tutor;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║==== Welcome to the MentorshipScheduler ====║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. Sign in                                 ║");
            System.out.println("║ 2. Log in                                  ║");
            System.out.println("║ 3. Exit                                    ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("Choose one option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registerUser(scanner);
                    break;
                case "2":
                    logInUser(scanner);
                    break;
                case "3":
                    exit = true;
                    System.out.println("Exiting the system... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid options. Try again.");
            }
        }
        scanner.close();
    }

    private static void registerUser(Scanner scanner) {
        UserDAO userDAO = new UserDAO();

        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║============ User Registration ============║");
        System.out.println("╠═══════════════════════════════════════════╣");

        String email = validateEmail(scanner);
        String password = validatePassword(scanner);
        String role = validateRole(scanner);

        userDAO.registerUser(email, password, role);
        int userId = userDAO.getUserId(email);

        if (role.equalsIgnoreCase("student")) {
            registerStudentInfo(scanner, userId);
        }else{
            registerTutorInfo(scanner, userId);
        }

        System.out.println("Succesful registration. You can now log in.");
    }

    private static void logInUser(Scanner scanner) {
        UserDAO userDAO = new UserDAO();
        TutorDAO tutorDAO = new TutorDAO();
        StudentDAO studentDAO = new StudentDAO();
        Student student = null;
        Tutor tutor = null;

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║================ User Login ================║");
        System.out.println("╠════════════════════════════════════════════╣");

        System.out.print("╚> Email: ");
        String email = scanner.nextLine();

        System.out.print("╚> Password: ");
        String password = scanner.nextLine();

        boolean isRegistered = userDAO.logIn(email, password);

        if(isRegistered){
            String role = userDAO.getRole(email);
            int userId = userDAO.getUserId(email);
            if(role.equalsIgnoreCase("student")){
                student = studentDAO.getStudentInfo(userId);
                StudentMenu studentMenu = new StudentMenu(studentDAO, student);
                studentMenu.showStudentMenu(scanner);
            }else{
                tutor = tutorDAO.getTutorInfo(userId);
                TutorMenu tutorMenu = new TutorMenu(tutorDAO, tutor);
                tutorMenu.showTutorMenu(scanner);
            }
        }else{
            System.out.println("User not found. Please register as a new user or try again.");
        }
    }

    public static void registerStudentInfo(Scanner scanner, int userId) {
        StudentDAO studentDAO = new StudentDAO();

        System.out.print("╚> Full Name: ");
        String name = scanner.nextLine();
        System.out.print("╚> Term: ");
        int term = scanner.nextInt();
        System.out.print("╚> Phone number: ");
        String phone = scanner.nextLine();

        studentDAO.registerStudent(userId, name, term, phone);
    }

    public static void registerTutorInfo(Scanner scanner, int userId) {
        TutorDAO tutorDAO = new TutorDAO();

        System.out.print("╚> Full Name: ");
        String name = scanner.nextLine();
        System.out.print("╚> Phone number: ");
        String phone = scanner.nextLine();
        System.out.print("╚> Area: ");
        String area = scanner.nextLine();

        tutorDAO.registerTutor(userId, name, phone, area);
    }


    public static String validateEmail(Scanner scanner) {
        while (true) {
            System.out.print("╚> Enter an email: ");
            String email = scanner.nextLine();
            if (email.contains("@")) {
                return email;
            }
            System.out.println();
            System.out.println("Error: Invalid email, it must contain '@'");
            System.out.println();
        }
    }

    public static String validatePassword(Scanner scanner) {
        while(true) {
            System.out.print("╚> Enter a password (minimun 6 characters): ");
            String password = scanner.nextLine();
            if (password.length() > 6) {
                return password;
            }
            System.out.println();
            System.out.println("Error: Password must be al least 6 characters long.");
            System.out.println();
        }
    }

    public static String validateRole(Scanner scanner) {
        while (true) {
            System.out.print("╚> Enter a role (Student/Tutor): ");
            String rol = scanner.nextLine();
            if (rol.equalsIgnoreCase("Student") || rol.equalsIgnoreCase("Tutor")) {
                return rol;
            }
            System.out.println();
            System.out.println("Error: Invalid role. Must be 'Student' or 'Tutor'.");
            System.out.println();
        }
    }
}

