package gui;

import dto.Injection;
import dto.JabProfile;
import dto.Student;
import dto.Vaccine;
import utils.FileUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.InputUtils;
import java.util.InputMismatchException;
import services.InjectionListManagement;
import static utils.InputUtils.inputYesNo;

/**
 *
 * @author kiman
 */
public class UiDisplay {
    private final String INJECTION_FILE_NAME = "injections.txt";
    private final String STUDENT_FILE_NAME = "students.txt";
    private final String VACCINE_FILE_NAME = "vaccines.txt";

    private Scanner sc = new Scanner(System.in);
    private Menu mainMenu = new Menu();
    private InjectionListManagement injectionManagement = new InjectionListManagement();
    private List<Student> studentsList = new ArrayList<>();
    private List<Vaccine> vaccinesList = new ArrayList<>();

    public UiDisplay() {
        mainMenu.addOption("Get all data in files");
        mainMenu.addOption("Show students have been injected");
        mainMenu.addOption("Add student's vaccine injection information");
        mainMenu.addOption("Update student's vaccine injection information");
        mainMenu.addOption("Delete student vaccine injection information");
        mainMenu.addOption("Search vaccine injection information by studentId");
        mainMenu.addOption("Save injections into injection file");
        mainMenu.addOption("Quit");
    }

    public void start() {
        int choice;
        do {
            choice = 0;
            System.out.println("Welcome to Vaccine Management - @ 2021 by SE161002 - Tran Vu Kim Anh");
            mainMenu.displayMenu();
            this.sc = new Scanner(System.in);
            try {
                choice = InputUtils.inputInt("", 0, 7, false);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            switch (choice) {
                case 0:
                    getInformationFromFiles();
                    break;
                case 1:
                    printInjection();
                    break;
                case 2:
                    addNewInjection();
                    break;
                case 3:
                    updateInjection();
                    break;
                case 4:
                    removeInjection();
                    break;
                case 5:
                    searchInjection();
                    break;
                case 6:
                    storeInFile();
                    break;
            }
        } while (choice <= 6);
    }

    public void getInformationFromFiles() {
        try {
            studentsList = FileUtils.readStudentsFromFileText(STUDENT_FILE_NAME);
            vaccinesList = FileUtils.readVaccinesFromFileText(VACCINE_FILE_NAME);

            injectionManagement = new InjectionListManagement(FileUtils.readInjectionsFromFileText(INJECTION_FILE_NAME));
            System.out.println("Student list: ");
            studentsList.stream().forEach(System.out::println);
            System.out.println("Vaccine list: ");
            vaccinesList.stream().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printInjection() {
        if (injectionManagement.isInjectionCollectionEmpty()) {
            System.out.println("There isn't any injection");
            return;
        }

        System.out.println("List of injection");
        injectionManagement.getInjectionsCollection()
                .stream()
                .forEach(food -> System.out.println(food));
    }

    public void addNewInjection() {
        sc = new Scanner(System.in);
        boolean stillContinue = false;
        do {
            try {
                Injection newInjection = null;
                newInjection = createNewInjection();
                System.out.println(newInjection);
                String result = injectionManagement.addInjection(newInjection) ? "Added successful!" : "Failed to add!";
                System.out.println(result);
            } catch (InputMismatchException e) {
                System.out.println("invalid input type");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                stillContinue = InputUtils.inputYesNo("Continue to add new injection?(Y/n)");
            }
        } while (stillContinue);
    }

    public void updateInjection() {
        sc = new Scanner(System.in);
        try {
            int injectionId = InputUtils.inputInt("Enter injection id: ", 1, 1000, true);
            Injection neededInjection = injectionManagement.searchInjectionById(injectionId);

            if (neededInjection == null) {
                System.out.println("Injection does not exist");
                return;
            }

            JabProfile curSecondJab = neededInjection.getSecondJab();
            if (curSecondJab.getDate() != null && curSecondJab.getPlace() != null) {
                System.out.println("This student already injected 2 times!");
                return;
            }

            System.out.println("The injection you want to update is: ");
            System.out.println(neededInjection);

            LocalDate firstJabDate = neededInjection.getFirstJab().getDate();
            JabProfile secondJab = InputUtils.inputSecondJab(false, firstJabDate);

            neededInjection.setSecondJab(secondJab);
            System.out.println("Student has completed 2 injection!");
        } catch (InputMismatchException e) {
            System.out.println("invalid input type");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchInjection() {
        sc = new Scanner(System.in);
        boolean stillContinue = false;

        //prepare studentIdList to check existed Id later
        ArrayList<Integer> studentIdList = new ArrayList<>();
        for (Student student : studentsList) {
            studentIdList.add(new Integer(student.getId()));
        }

        do {
            try {
                int studentId = InputUtils.inputId("Enter injection's student's id: ", 1, 1000, false, studentIdList, false);

                Injection neededInjection = injectionManagement.searchInjectionByStudentId(studentId);
                if (neededInjection != null) {
                    System.out.println(neededInjection);
                } else {
                    System.out.println("This injection's student id does not exist");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                stillContinue = InputUtils.inputYesNo("Continue?(Y/n)");
            }
        } while (stillContinue);
    }

    public void removeInjection() {
        try {
            sc = new Scanner(System.in);
            int id = InputUtils.inputInt("Enter injection's id: ", 1, 1000, true);

            Injection neededInjection = injectionManagement.searchInjectionById(id);
            if (neededInjection == null) {
                System.out.println("This injection doesn't exist");
                return;
            }

            System.out.println("The injection you want to remove is: ");
            System.out.println(neededInjection);

            boolean choice = InputUtils.inputYesNo("Are you sure you want to remove this injection?(Y/n)");
            if (choice == false) {
                return;
            }

            String result = injectionManagement.removeInjection(neededInjection) ? "Removed successful!" : "Failed to removed!";
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void storeInFile() {
        try {
            List<Injection> injectionsCollection = injectionManagement.getInjectionsCollection();
            
            //Check information again before saving
            System.out.println("This is the information you are gonna save");
            injectionsCollection.stream().forEach(System.out::println);
            boolean check = InputUtils.inputYesNo("Are you sure you want to save these?(Y/n)");
            if(check == false) return;
            
            FileUtils.writeInjectionsToFileText(INJECTION_FILE_NAME, injectionsCollection);
            System.out.println("Saved succesfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    

    public Injection createNewInjection() throws IllegalArgumentException, InputMismatchException {
        //Prepare studentId list and vaccineId list to check existed 
        ArrayList<Integer> studentIdList = new ArrayList<>();
        for (Student student : studentsList) {
            studentIdList.add(new Integer(student.getId()));
        }
        ArrayList<Integer> vaccineIdList = new ArrayList<>();
        for (Vaccine vaccine : vaccinesList) {
            vaccineIdList.add(new Integer(vaccine.getId()));
        }
        
        System.out.println("Enter new injection information: ");

        int id = InputUtils.inputInt("Enter new injection's id: ", 1, 1000, true);
        if (injectionManagement.searchInjectionById(id) != null) {
            throw new IllegalArgumentException("This injection id already existed");
        }

        int studentId = InputUtils.inputStudentIdOfInjection(1, 1000, studentIdList, this.injectionManagement, true);
        
        int vaccineId = InputUtils.inputId("Enter this injection's vaccine's id: ", 1, 1000, true, vaccineIdList, false);

        Injection newInjection = new Injection(id, studentId, vaccineId, null, null);

        System.out.println("Enter information for first jab: ");
        LocalDate firstJabDate = InputUtils.inputDate("Enter new first jab's date: ", false, true);
        String firstJabPlace = InputUtils.inputString("Enter new first jab's place: ", 1, 20, true);

        JabProfile firstJab = new JabProfile(firstJabDate, firstJabPlace);
        newInjection.setFirstJab(firstJab);

        JabProfile secondJab = InputUtils.inputSecondJab(true, firstJabDate);
        newInjection.setSecondJab(secondJab);

        return newInjection;
    }
}
