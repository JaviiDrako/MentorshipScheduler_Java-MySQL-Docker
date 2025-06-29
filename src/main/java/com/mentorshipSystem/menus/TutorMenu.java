package com.mentorshipSystem.menus;

import com.mentorshipSystem.crud.TutorDAO;

import java.util.Scanner;
import java.sql.Timestamp;
import java.util.List;

// create clas for tutors
public class TutorMenu {

    private final TutorDAO tutorDAO;
    private final int tutorId;
    private final String tutorName;

    public TutorMenu(int tutorId, String tutorName) {
        this.tutorDAO = new TutorDAO();
        this.tutorId = tutorId;
        this.tutorName = tutorName;
    }

    public void showTutorMenu(Scanner scanner) {
        boolean exit = false;

        // asking tutors her option
        while (!exit) {
            System.out.print("==== Bienvenido " + tutorName + " ====\n" +
                    "---- Por favor elija una opción ----\n" +
                    "1. Ver tutorías agendadas\n" +
                    "2. Crear nueva tutoría\n" +
                    "3. Registrar nueva materia\n" +
                    "4. Ver materias disponibles\n" +
                    "5. Salir del sistema.\n" +
                    "Selecciona una opción: ");
            String option = scanner.nextLine();

            //dependign tutor's option, we show anything
            switch (option) {
                case "1":
                    showMentorships(scanner);
                    break;
                case "2":
                    createMentorship(scanner);
                    break;
                case "3":
                    registerSubject(scanner);
                    break;
                case "4":
                    showAllSubjects(scanner);
                    break;
                case "5":
                    System.out.println("Saliendo de la aplicacion...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    private void showMentorships(Scanner scanner) {
        boolean backMenu = false;

        while(!backMenu) {
            System.out.println("==== Ver tutorías registradas ====");

            List<String> mentorships = tutorDAO.getMentorships(tutorId);

            if (mentorships.isEmpty()) {
                System.out.println("No tienes tutorías programadas");
            } else {
                for (String mentorship : mentorships){
                    System.out.println(mentorship);
                }
            }

            System.out.println("---- Opciones ----\n" +
                    "1. Crear tutoria\n" +
                    "2. Volver al menú principal\n" +
                    "Selecciona una opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createMentorship(scanner);
                    break;
                case "2":
                    System.out.println("Volviendo a la pantalla anterior");
                    backMenu = true;
                    break;
                default:
                    System.out.println("La opcion ingresada es invalida :(, " +
                            "Por favor ingrese una que sea correcta");
                    break;
            }
        }
    }

    private void createMentorship(Scanner scanner) {
        System.out.println("==== Crear nueva tutoría ====");

        List<String> subjects = tutorDAO.getAllSubjects();

        System.out.println("Materias disponibles:");

        for (String subject : subjects) {
            System.out.println(subject);
        }


        System.out.print("Ingrese el ID de la materia: ");
        int subjectId = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese fecha y hora (formato: yyyy-MM-dd HH:mm:ss): ");
        String dateTime = scanner.nextLine();

        String result = tutorDAO.createMentorship(dateTime, tutorId, subjectId);
        System.out.println(result);
    }

    private void registerSubject(Scanner scanner) {
        System.out.println("==== Registrar Nueva Materia ====");
        System.out.print("Nombre de la materia: ");
        String subjectName = scanner.nextLine();

        System.out.print("Enter subject term: ");
        String subjectTerm = scanner.nextLine(); //Fix make a paser method

        String result = tutorDAO.registerSubject(subjectName, 1); 
        System.out.println(result);
    }

    private void showAllSubjects(Scanner scanner) {
        boolean backMenu = false;

        while(!backMenu) {
            System.out.println("==== Materias Disponibles ====");

            List<String> subjects = tutorDAO.getAllSubjects();

            if(subjects.isEmpty()) {
                System.out.println("No hay materias registradas");
            } else {
                subjects.forEach(System.out::println);
            }

            System.out.println("---- Opciones ----\n" +
                    "1. Registrar Materia\n" +
                    "2. Volver al menu tutor\n" +
                    "Su opción: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registerSubject(scanner);
                    break;
                case "2":
                    System.out.println("Volviendo a la pantalla anterior");
                    backMenu = true;
                    break;
                default:
                    System.out.println("La opcion ingresada es invalida :(, " +
                            "Por favor ingrese una que sea correcta");
                    break;
            }
        }
    }
}
