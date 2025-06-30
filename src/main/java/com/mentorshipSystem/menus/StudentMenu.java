package com.mentorshipSystem.menus;

import com .mentorshipSystem.crud.StudentDAO;
import com.mentorshipSystem.models.Student;

import java.util.List;
import java.util.Scanner;


public class StudentMenu {
    private final StudentDAO studentDAO;
    private final Student student;
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    public StudentMenu(StudentDAO studentDAO, Student student) {
        this.studentDAO = studentDAO;
        this.student = student;
    }

    public void showStudentMenu(Scanner scanner) {
        boolean exit = false;
        String borderTop = "╔══════════════════════════════════════╗";
        String borderMiddle = "╠══════════════════════════════════════╣";
        String borderBottom = "╚══════════════════════════════════════╝";
        int width = 38;
        while (!exit) {
            String welcome = "Welcome " + student.getName();

            int padding = (width - welcome.length()) / 2;
            String sideSpaces = " ".repeat(padding);
            String paddedWelcome =  sideSpaces + welcome + sideSpaces;

            if (paddedWelcome.length() < width) {
                paddedWelcome += " ";
            }
            System.out.println(borderTop);
            System.out.println("║" + paddedWelcome + "║");
            System.out.println(borderMiddle);
            System.out.println("║ 1. View scheduled mentorships        ║");
            System.out.println("║ 2. Schedule a new mentorship         ║");
            System.out.println("║ 3. Log out                           ║");
            System.out.println(borderBottom);
            System.out.print("Choose one option: ");
            String option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    showMentorships(scanner);
                    break;
                case "2":
                    scheduleMentorship(scanner);
                    break;
                case "3":
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

    public void showMentorships(Scanner scanner) {
        boolean backMenu = false;

        while(!backMenu) {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║================================= Scheduled Mentorships =================================║");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════╝");

            List<String> mentorships = studentDAO.getMentorships(student.getId());

            if (mentorships.isEmpty()) {
                System.out.println("There are no scheduled mentorships");
                System.out.println();
            } else {
                System.out.printf("| %-13s | %-25s | %-20s | %-20s |%n", "Mentorship ID", "Subject", "Date", "Tutor");
                System.out.println("-------------------------------------------------------------------------------------------");

                for (String m : mentorships) {
                    String[] parts = m.split(",");
                    String id = parts[0];
                    String subjectName = parts[1];
                    String date = parts[2];
                    String tutorName = parts[3];

                    System.out.printf("| %-13s | %-25s | %-20s | %-20s |%n", id, subjectName, date, tutorName);
                }
                System.out.println();
            }

            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║     ~~~~~~~~~~ Options ~~~~~~~~~~     ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ 1. Cancel mentorship                  ║");
            System.out.println("║ 2. Back to main menu                  ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Choose one option: ");
            String option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    cancelMentorship(scanner);
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

    public void scheduleMentorship(Scanner scanner) {
        int subjectId = 0;
        int mentorshipId = 0;
        boolean backMenu = false;
        while(!backMenu) {
            showAvailableSubjects();

            System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enter the subject ID you want to schedule a mentorship for, or type 0 to exit: ║");
            System.out.print("╚> ");
            subjectId = getValidId(scanner.nextLine());

            if (subjectId < 1) {
                if (subjectId == 0) {
                    backMenu = true;
                }else {
                    continue;
                }
            }else{
                List<String> mentorships = studentDAO.getAvailableMentorshipsBySubject(subjectId);
                if(mentorships.isEmpty()) {
                    System.out.println(ANSI_RED + "--- There are no mentorships for this subject" + ANSI_RESET);
                    System.out.println();
                    backMenu = true;
                }else{
                    showAvailableMentorships(mentorships);
                    System.out.println("╔══════════════════════════════════════════════════════════════════╗");
                    System.out.println("║ Enter the mentorship ID you want to schedule, or type 0 to exit: ║");
                    System.out.print("╚> ");
                    mentorshipId = getValidId(scanner.nextLine());
                    if (mentorshipId < 1) {
                        if (mentorshipId == 0) {
                            backMenu = true;
                        }else {
                            continue;
                        }
                    }else{
                        String result = studentDAO.scheduleMentorship(mentorshipId, student.getId());
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
    }

    public void showAvailableSubjects() {
        List<String> subjects = studentDAO.getAvailableSubjectsByTerm(student.getTerm());

        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║===== Available Subjects for Term "+ student.getTerm() + " =====║");
        System.out.println("╚═════════════════════════════════════════╝");

        System.out.printf("| %-8s | %-28s |%n", "ID", "Nombre");
        System.out.println("-------------------------------------------");

        for (String subject : subjects) {
            String[] parts = subject.split(",");
            String id = parts[0];
            String name = parts[1];
            System.out.printf("| %-8s | %-28s |%n", id, name);
        }
        System.out.println();
    }

    public void showAvailableMentorships(List<String> mentorships) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║============================ Available Mentorships ============================║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
        System.out.printf("| %-13s | %-18s | %-18s | %-19s |%n", "Mentorship ID", "Tutor", "Subject", "Date");
        System.out.println("---------------------------------------------------------------------------------");

        for (String m : mentorships) {
            String[] parts = m.split(",");
            String id = parts[0];
            String tutor = parts[1];
            String materia = parts[2];
            String fecha = parts[3];

            System.out.printf("| %-13s | %-18s | %-18s | %-18s |%n", id, tutor, materia, fecha);
        }
        System.out.println();
    }

    public int getValidId(String option) {
        int parsedId = -1;
        if (option.equals("0") ) {
            return parsedId;
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

    private void cancelMentorship(Scanner scanner) {
        int mentorshipId;
        boolean backMenu = false;
        while(!backMenu) {
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enter the mentorship ID you want to cancel, or type 0 to exit: ║");
            System.out.print("╚> ");
            mentorshipId = getValidId(scanner.nextLine());

            if (mentorshipId < 1) {
                if (mentorshipId == 0) {
                    backMenu = true;
                }else {
                    continue;
                }
            }else{
                String result = studentDAO.cancelMentorship(mentorshipId);
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
