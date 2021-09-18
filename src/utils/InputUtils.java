package utils;

import dto.JabProfile;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import services.InjectionListManagement;

/**
 *
 * @author kiman
 */
public class InputUtils {

    private static void continueInputField(Exception e) throws IllegalArgumentException{
        System.out.println(e.getMessage());
        boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
        if (check == false) {
            throw new IllegalArgumentException("Failed to input");
        }
    }

    public static int inputId(String message, int min, int max, boolean loop, ArrayList<Integer> idList, boolean notContain) throws IllegalArgumentException, InputMismatchException {
        do {
            try {
                int input = inputInt(message, min, max, false);

                if (notContain == true && idList.contains(new Integer(input))) {
                    throw new IllegalArgumentException("This id already existed!");
                }
                if (notContain == false && !idList.contains(new Integer(input))) {
                    throw new IllegalArgumentException("This id doesn't exist");
                }
                return input;
            } catch (Exception e) {
                if (loop == false) { //if user doesn't want to loop -> throw exception for the outter function
                    throw e;
                }
                continueInputField(e);
            }
        } while (true);
    }

    public static String inputString(String message, int min, int max, boolean loop) throws IllegalArgumentException {
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(message);
                String input = sc.nextLine();
                String exceptionMessage = "input length must be from " + min + " to " + max;
                if (input.trim().length() > max || input.trim().length() < min) { //check if user enter double space also
                    throw new IllegalArgumentException(exceptionMessage);
                }
                return input;
            } catch (Exception e) {
                if (loop == false) {
                    throw e;
                }
                continueInputField(e);
            }
        } while (true);
    }

    public static int inputInt(String message, int min, int max, boolean loop) throws IllegalArgumentException, InputMismatchException {
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(message);
                int input = 0;
                try {
                    input = sc.nextInt();
                } catch (InputMismatchException | NumberFormatException e) {
                    throw new InputMismatchException("invalid input type");
                }
                String exceptionMessage = "input value must be from " + min + " to " + max;
                if (input > max || input < min) {
                    throw new IllegalArgumentException(exceptionMessage);
                }
                return input;
            } catch (Exception e) {
                if (loop == false) {
                    throw e;
                }
                continueInputField(e);
            }
        } while (true);
    }

    public static double inputDouble(String message, double min, double max, boolean loop) throws IllegalArgumentException, InputMismatchException {
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(message);
                double input = 0;
                try {
                    input = sc.nextDouble();
                } catch (InputMismatchException | NumberFormatException e) {
                    throw new InputMismatchException("invalid input type");
                }
                String exceptionMessage = "input value must be from " + min + " to " + max;
                if (input > max || input < min) {
                    throw new IllegalArgumentException(exceptionMessage);
                }
                return input;
            } catch (Exception e) {
                if (loop == false) {
                    throw e;
                }
                continueInputField(e);
            }
        } while (true);
    }

    public static boolean inputYesNo(String message) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            String choice = sc.nextLine();
            if (choice.toLowerCase().equals("y")) {
                return true;
            } else if (choice.toLowerCase().equals("n")) {
                return false;
            }
        } while (true);
    }

    public static LocalDate inputDate(String message, boolean allowBlank, boolean loop) throws IllegalArgumentException {
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println(message);
                System.out.print("Please enter with the format yyyy-MM-dd (Example: 2020-05-06): ");

                String dateInString = sc.nextLine();
                if (dateInString.equals("") && allowBlank == true) { //allowBlank is for second jab because second jab can be omiited when adding injection
                    return null;
                } else if (dateInString.equals("")) {
                    throw new IllegalArgumentException("Youre not allowed to omit this field");
                }

                try {
                    LocalDate date = LocalDate.parse(dateInString);
                    return date;
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format");
                }

            } catch (IllegalArgumentException e) {
                if (loop == false) {
                    throw e;
                }
                continueInputField(e);
            }
        } while (true);
    }

    public static int inputStudentIdOfInjection(int min, int max, ArrayList<Integer> studentIdList, InjectionListManagement injectionManagement, boolean loop) throws IllegalArgumentException {
        int studentId = 0;
        do {
            try {
                studentId = InputUtils.inputId("Enter this injection's student's id: ", min, max, loop, studentIdList, false);

                if (injectionManagement.searchInjectionByStudentId(studentId) != null) {
                    throw new IllegalArgumentException("This injection's student's id already existed");
                }
                return studentId;
            } catch (Exception e) {
                if (loop == false) {
                    throw e;
                }
                continueInputField(e);
            }
        } while (true);
    }

    public static JabProfile inputSecondJab(boolean isAddInjection, LocalDate firstJabDate) throws IllegalArgumentException {
        LocalDate secondJabDate = null;
        System.out.println("Enter information for second jab: ");
        do {
            try {
                secondJabDate = InputUtils.inputDate("Enter new second jab's date: ", isAddInjection, true); //add injection == true -> allow blank
                if (secondJabDate == null && isAddInjection == true) { //add injection -> second jab's information can be omitted
                    break;
                }

                LocalDate beforeSecondDate4Weeks = secondJabDate.minusWeeks(4);
                LocalDate beforeSecondDate12Weeks = secondJabDate.minusWeeks(12);

                if (beforeSecondDate4Weeks.isBefore(firstJabDate) || beforeSecondDate12Weeks.isAfter(firstJabDate)) {
                    throw new IllegalArgumentException("The date of second jab must be between 4-12 weeks");
                }
                break;
            } catch (Exception e) {
                continueInputField(e);
            }
        } while (true);

        int minOfSecondJabPlace = isAddInjection ? 0 : 1; //for add injection, the place can be omiited
        String secondJabPlace = InputUtils.inputString("Enter new second jab's place: ", minOfSecondJabPlace, 20, true);
        return new JabProfile(secondJabDate, secondJabPlace);
    }
}
