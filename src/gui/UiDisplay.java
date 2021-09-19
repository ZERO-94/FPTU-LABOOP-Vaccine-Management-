package gui;

import dto.Injection;
import dto.JabProfile;
import dto.Student;
import dto.Vaccine;
import utils.FileUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.InputUtils;
import java.util.InputMismatchException;
import services.InjectionListManagement;
import services.StudentListManagement;
import services.VaccineListManagement;
import static utils.InputUtils.inputYesNo;

/**
 *
 * @author kiman
 */
public class UiDisplay {

    private final String INJECTION_FILE_NAME = "injections.txt";
    private final String STUDENT_FILE_NAME = "students.txt";
    private final String VACCINE_FILE_NAME = "vaccines.txt";
    private final int MAX_VACCINE_ID = 1000;
    private final int MAX_STUDENT_ID = 1000;
    private final int MAX_INJECTION_ID = 1000;
    private final int MIN_VACCINE_ID = 1;
    private final int MIN_STUDENT_ID = 1;
    private final int MIN_INJECTION_ID = 1;

    private Scanner sc = new Scanner(System.in);
    private Menu mainMenu = new Menu();
    private InjectionListManagement injectionManagement = new InjectionListManagement();
    private VaccineListManagement vaccineManagement = new VaccineListManagement(new ArrayList<>());
    private StudentListManagement studentManagement = new StudentListManagement(new ArrayList<>());

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
            List<Student> studentsList = FileUtils.readStudentsFromFileText(STUDENT_FILE_NAME);
            List<Vaccine> vaccinesList = FileUtils.readVaccinesFromFileText(VACCINE_FILE_NAME);
            List<Injection> injectionsList = FileUtils.readInjectionsFromFileText(INJECTION_FILE_NAME);

            studentManagement = new StudentListManagement(studentsList);
            vaccineManagement = new VaccineListManagement(vaccinesList);
            injectionManagement = new InjectionListManagement(injectionsList);

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
                Injection newInjection = createNewInjection();
                String result = injectionManagement.addInjection(newInjection) ? "Added successful!" : "Failed to add!";
                System.out.println(result);
            } catch (IllegalArgumentException e) {
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

            JabProfile firstJab = neededInjection.getFirstJab();
            JabProfile secondJab = createSecondJabProfile("Enter second jab information: ", firstJab, false);

            neededInjection.setSecondJab(secondJab);
            System.out.println("Student has completed 2 injection!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchInjection() {
        sc = new Scanner(System.in);
        boolean stillContinue = false;

        do {
            try {
                int studentId = InputUtils.inputIdContainInList("Enter injection's student's id: ", 1, 1000, false, studentManagement.getStudentIdList());

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

            //Check information again before saving
            System.out.println("The injection you want to remove is: ");
            System.out.println(neededInjection);

            boolean choice = InputUtils.inputYesNo("Are you sure you want to remove this injection?(Y/n)");
            if (choice == false) {
                return;
            }

            String result = injectionManagement.removeInjection(neededInjection) ? "Removed successful!" : "Failed to removed!";
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void storeInFile() {
        List<Injection> injectionsCollection = injectionManagement.getInjectionsCollection();

        //Check information again before saving
        System.out.println("This is the information you are gonna save");
        injectionsCollection.stream().forEach(System.out::println);
        boolean check = InputUtils.inputYesNo("Are you sure you want to save these?(Y/n)");
        if (check == false) {
            return;
        }
        try {
            FileUtils.writeInjectionsToFileText(INJECTION_FILE_NAME, injectionsCollection);
            System.out.println("Saved succesfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Injection createNewInjection() throws IllegalArgumentException {

        System.out.println("Enter new injection information: ");

        int id = InputUtils.inputInt("Enter new injection's id: ", MIN_INJECTION_ID, MAX_INJECTION_ID, true);
        if (injectionManagement.searchInjectionById(id) != null) {
            throw new IllegalArgumentException("This injection id already existed");
        }

        int studentId = getStudentIdOfInjectionFromUser(true);

        int vaccineId = InputUtils.inputIdContainInList("Enter this injection's vaccine's id: ", MIN_VACCINE_ID, MAX_VACCINE_ID, true, vaccineManagement.getVaccineIdList());

        Injection newInjection = new Injection(id, studentId, vaccineId, null, null);

        JabProfile firstJab = createFirstJabProfile("Enter information for first jab: ");
        newInjection.setFirstJab(firstJab);

        JabProfile secondJab = createSecondJabProfile("Enter information for first jab: ", firstJab, true);
        newInjection.setSecondJab(secondJab);

        return newInjection;
    }

    private int getStudentIdOfInjectionFromUser(boolean loop) throws IllegalArgumentException {
        int studentId;
        do {
            studentId = InputUtils.inputIdContainInList("Enter this injection's student's id: ", MIN_STUDENT_ID, MAX_STUDENT_ID, loop, studentManagement.getStudentIdList());

            if (injectionManagement.searchInjectionByStudentId(studentId) == null) {
                return studentId;
            }
            String exceptionMessage = "This student id already has its own injection";
            InputUtils.handleInvalidInput(loop, exceptionMessage);
        } while (true);
    }

    private JabProfile createFirstJabProfile(String message) throws IllegalArgumentException {
        System.out.println(message);
        LocalDate firstJabDate = InputUtils.inputDate("Enter new first jab's date: ", false, true);
        String firstJabPlace = InputUtils.inputString("Enter new first jab's place: ", 1, 20, true);

        JabProfile firstJab = new JabProfile(firstJabDate, firstJabPlace);
        return firstJab;
    }

    private JabProfile createSecondJabProfile(String message, JabProfile firstJab, boolean isAddInjection) throws IllegalArgumentException {
        System.out.println(message);
        LocalDate secondJabDate = InputUtils.inputDateWithWeekLimit("Enter new second jab's date: ",
                                                                    firstJab.getDate(),
                                                                    4,
                                                                    12,
                                                                    isAddInjection, //can omit second jab while adding new injection
                                                                    true); 

        int minOfSecondJabPlaceLength = isAddInjection ? 0 : 1; //for add injection, the place can be omiited
        String secondJabPlace = InputUtils.inputString("Enter new second jab's place: ", minOfSecondJabPlaceLength, 20, true);
        if (isAddInjection) {
            if (secondJabDate == null ^ secondJabPlace.length() == 0) {
                System.out.println("Both information must be filled (Second jab's information will be null, you can update later)");
                return new JabProfile();
            }
        }
        return new JabProfile(secondJabDate, secondJabPlace);
    }
}
