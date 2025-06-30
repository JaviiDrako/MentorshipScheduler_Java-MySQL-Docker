package com.mentorshipSystem.menus;

import com.mentorshipSystem.crud.TutorDAO;
import com.mentorshipSystem.models.Tutor;

import java.util.Scanner;
import java.util.List;

public class TutorMenu {

    private final TutorDAO tutorDAO;
    private final Tutor tutor;
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    public TutorMenu(TutorDAO tutorDAO, Tutor tutor) {
        this.tutorDAO = tutorDAO;
        this.tutor = tutor;
    }

    public void showTutorMenu(Scanner scanner) {
        boolean exit = false;
        String borderTop = "╔══════════════════════════════════════╗";
        String borderMiddle = "╠══════════════════════════════════════╣";
        String borderBottom = "╚══════════════════════════════════════╝";
        int width = 38;
        while (!exit) {
            String welcome = "Welcome tutor " + tutor.getName();

            int padding = (width - welcome.length()) / 2;
            String sideSpaces = " ".repeat(padding);
            String paddedWelcome =  sideSpaces + welcome + sideSpaces;

            if (paddedWelcome.length() < width) {
                paddedWelcome += " ";
            }
            System.out.println(borderTop);
            System.out.println("║" + paddedWelcome + "║");
            System.out.println(borderMiddle);
            System.out.println("║ 1. View registered mentorships       ║");
            System.out.println("║ 2. View registered subjects          ║");
            System.out.println("║ 3. Create a new mentorship           ║");
            System.out.println("║ 4. Register a new subject            ║");
            System.out.println("║ 5. Log out                           ║");
            System.out.println(borderBottom);
            System.out.print("Choose one option: ");
            String option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    showMentorships(scanner);
                    break;
                case "2":
                    showAllSubjects(scanner);
                    break;
                case "3":
                    createMentorship(scanner);
                    break;
                case "4":
                    registerSubject(scanner, 0);
                    break;
                case "5":
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println(ANSI_RED + "--- Invalid option" + ANSI_RESET);
                    System.out.println();
                    break;
            }
        }
    }

    private void showMentorships(Scanner scanner) {
        boolean backMenu = false;

        while(!backMenu) {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║========================================= Registered Mentorships ========================================║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            List<String> mentorships = tutorDAO.getMentorships(tutor.getId());

            if (mentorships.isEmpty()) {
                System.out.println("There are no registered mentorships");
                System.out.println();
            } else {
                System.out.printf("| %-13s | %-25s | %-20s | %-20s | %-13s |%n", "Mentorship ID", "Subject", "Student", "Date", "State");
                System.out.println("-----------------------------------------------------------------------------------------------------------");

                for (String m : mentorships) {
                    String[] parts = m.split(",");
                    String id = parts[0];
                    String subjectName = parts[1];
                    String studentName = parts[2];
                    if(studentName.equals("null")){
                        studentName = "No student yet";
                    }
                    String date = parts[3];
                    String state = parts[4];

                    System.out.printf("| %-13s | %-25s | %-20s | %-20s | %-13s |%n", id, subjectName, studentName, date, state);
                }
                System.out.println();
            }

            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║     ~~~~~~~~~~ Options ~~~~~~~~~~     ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ 1. Create a new mentorship            ║");
            System.out.println("║ 2. Cancel a regitered mentorship      ║");
            System.out.println("║ 3. Back to main menu                  ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Choose one option: ");
            String option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    createMentorship(scanner);
                    backMenu = true;
                    break;
                case "2":
                    cancelMentorship(scanner);
                    backMenu = true;
                    break;
                case "3":
                    System.out.println("Going back to main menu...");
                    backMenu = true;
                    break;
                default:
                    System.out.println(ANSI_RED + "--- Invalid option" + ANSI_RESET);
                    System.out.println();
                    break;
            }
        }
    }

    private void showAllSubjects(Scanner scanner) {
        boolean backMenu = false;

        while(!backMenu) {
            printSubjects();

            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║     ~~~~~~~~~~ Options ~~~~~~~~~~     ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ 1. Register a new subject             ║");
            System.out.println("║ 2. Back to main menu                  ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Choose one option: ");
            String option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    registerSubject(scanner, 1);
                    backMenu = true;
                    break;
                case "2":
                    System.out.println("Going back to main menu...");
                    backMenu = true;
                    break;
                default:
                    System.out.println(ANSI_RED + "--- Invalid option" + ANSI_RESET);
                    System.out.println();
                    break;
            }
        }
    }

    private void createMentorship(Scanner scanner) {
        boolean backMenu = false;
        while(!backMenu) {
            printSubjects();

            System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enter the subject ID to register a new mentorship, or type 0 to exit: ║");
            System.out.print("╚> ");
            int subjectId = getValidId(scanner.nextLine());

            if (subjectId < 1) {
                if (subjectId == 0) {
                    backMenu = true;
                }else {
                    continue;
                }
            }else{
                System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ Enter date and time (format: YYYY-MM-DD HH:MM:SS), or type 0 to exit: ║");
                System.out.print("╚> ");
                String date = scanner.nextLine(); //Validation for date is pending
                if (date.equals("0")) {
                    backMenu = true; 
                }else{
                    String result = tutorDAO.createMentorship(date, tutor.getId(), subjectId);
                    System.out.println();
                    if (result.charAt(0) == '+'){
                        System.out.println(ANSI_GREEN + result + ANSI_RESET);
                    }else{
                        System.out.println(ANSI_RED + result + ANSI_RESET);
                    }
                    System.out.println();
                    backMenu = true;
                }
            }
        }
    }

    private void registerSubject(Scanner scanner, int guide) {
        boolean backMenu = false;
        while(!backMenu) {
            if (guide == 0) {
                printSubjects();
            }
            System.out.println("╔═══════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enter the name of the subject to register, or type 0 to exit: ║");
            System.out.print("╚> ");
            String subjectName = scanner.nextLine();

            if (subjectName.equals("0")) {
                backMenu = true;
            }else{
                System.out.println("╔═══════════════════════════════════════════════════════════════╗");
                System.out.println("║ Enter the term of the subject to register, or type 0 to exit: ║");
                System.out.print("╚> ");
                int subjectTerm = getValidId(scanner.nextLine()); 
                if (subjectTerm == 0) {
                    backMenu = true; 
                }else{
                    String result = tutorDAO.registerSubject(subjectName, subjectTerm);
                    if (result.charAt(0) == '+'){
                        System.out.println(ANSI_GREEN + result + ANSI_RESET);
                    }else{
                        System.out.println(ANSI_RED + result + ANSI_RESET);
                    }
                    System.out.println();
                    backMenu = true;
                }
            }
        }
    }

    private void cancelMentorship(Scanner scanner){
        boolean backMenu = false;
        while(!backMenu) {
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enter the mentorship ID you want to cancel, or type 0 to exit: ║");
            System.out.print("╚> ");
            int mentorshipId = getValidId(scanner.nextLine());

            if (mentorshipId == 0) {
                backMenu = true;
            }else{
                String result = tutorDAO.cancelMentorship(mentorshipId);
                System.out.println();
                if (result.charAt(0) == '+'){
                    System.out.println(ANSI_GREEN + result + ANSI_RESET);
                }else{
                    System.out.println(ANSI_RED + result + ANSI_RESET);
                }
                System.out.println();
                backMenu = true;
            }
        }
    }

    private void printSubjects(){
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║================== Available Subjects ==================║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        List<String> subjects = tutorDAO.getAllSubjects();

        if (subjects.isEmpty()) {
            System.out.println("There are no registered subjects");
            System.out.println();
        } else {
            System.out.printf("| %-13s | %-25s | %-10s |%n", "Subject ID", "Subject", "Term");
            System.out.println("----------------------------------------------------------");

            for (String m : subjects) {
                String[] parts = m.split(",");
                String id = parts[0];
                String subjectName = parts[1];
                String term = parts[2];

                System.out.printf("| %-13s | %-25s | %-10s |%n", id, subjectName, term);
            }
            System.out.println();
        }
    }

    public int getValidId(String option) {
        int parsedId = -1;
        if (option.equals("0") ) {
            return parsedId = 0;
        }else{
            try {
                parsedId = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println(ANSI_RED + "--- Invalid ID." + ANSI_RESET);
            }
            System.out.println();
        }
        return parsedId;
    }

}
