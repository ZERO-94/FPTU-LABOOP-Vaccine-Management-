package utils;

import dto.JabProfile;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import services.InjectionListManagement;

/**
 *
 * @author kiman
 */
public class InputUtils {

    private static void continueInputField(Exception e) throws IllegalArgumentException {
        System.out.println(e.getMessage());
        boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
        if (check == false) {
            throw new IllegalArgumentException("Failed to input");
        }
    }

    public static int inputIdNotContainInList(String message, int min, int max, boolean loop, List<Integer> idList) throws IllegalArgumentException {
        String exceptionMessage;
        do {
            int input = inputInt(message, min, max, loop);

            if (idList.contains(new Integer(input))) {
                exceptionMessage = "This id already existed!";
                handleInvalidInput(loop, exceptionMessage);
                continue;
            }

            return input;
        } while (true);
    }
    
    public static int inputIdContainInList(String message, int min, int max, boolean loop, List<Integer> idList) throws IllegalArgumentException {
        String exceptionMessage;
        do {
            int input = inputInt(message, min, max, loop);

            if (!idList.contains(new Integer(input))) {
                exceptionMessage = "This id doesn't exist";
                handleInvalidInput(loop, exceptionMessage);
                continue;
            }

            return input;
        } while (true);
    }

    public static String inputString(String message, int min, int max, boolean loop) throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String input;
        String exceptionMessage = "input length must be from " + min + " to " + max;
        do {

            System.out.print(message);
            input = sc.nextLine();
            if (input.trim().length() <= max && input.trim().length() >= min) {
                return input;
            }
            handleInvalidInput(loop, exceptionMessage);
        } while (true);
    }

    public static int inputInt(String message, int min, int max, boolean loop) throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String exceptionMessage;
        do {
            int input = 0;
            sc = new Scanner(System.in); //reset input stream
            System.out.print(message);

            try {
                input = sc.nextInt();
            } catch (InputMismatchException | NumberFormatException e) {
                exceptionMessage = "Invalid inpyt type";
                handleInvalidInput(loop, exceptionMessage);
                continue;
            }

            if (input <= max && input >= min) {
                return input;
            }
            exceptionMessage = "input value must be from " + min + " to " + max;
            handleInvalidInput(loop, exceptionMessage);
        } while (true);
    }

    public static double inputDouble(String message, double min, double max, boolean loop) throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);
        String exceptionMessage;
        do {
            double input = 0;
            sc = new Scanner(System.in); //reset input stream
            System.out.print(message);
            try {
                input = sc.nextDouble();
            } catch (InputMismatchException | NumberFormatException e) {
                exceptionMessage = "invalid input type";
                handleInvalidInput(loop, exceptionMessage);
                continue;
            }

            if (input <= max && input >= min) {
                return input;
            }

            exceptionMessage = "input value must be from " + min + " to " + max;
            handleInvalidInput(loop, exceptionMessage);
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

            System.out.println("Please enter 'y' or 'n' only");
        } while (true);
    }

    public static LocalDate inputDate(String message, boolean allowBlank, boolean loop) throws IllegalArgumentException { //allow blank -> can omit this input
        Scanner sc = new Scanner(System.in);
        String dateInString;
        LocalDate date;
        String exceptionMessage;
        do {
            System.out.println(message);
            System.out.print("Please enter with the format yyyy-MM-dd (Example: 2020-05-06): ");
            dateInString = sc.nextLine();

            if (dateInString.trim().length() == 0) {
                if (allowBlank) {
                    return null;
                }

                exceptionMessage = "You can't omit this field";
                handleInvalidInput(loop, exceptionMessage);
                continue;
            }

            try {
                date = LocalDate.parse(dateInString);
                return date;
            } catch (DateTimeParseException e) {
                exceptionMessage = "invalid date format";
                handleInvalidInput(loop, exceptionMessage);
            }
        } while (true);
    }

    public static LocalDate inputDateWithWeekLimit(String message, LocalDate limitDate, int min, int max, boolean allowBlank, boolean loop) throws IllegalArgumentException {
        LocalDate date;
        do {
            date = InputUtils.inputDate(message, allowBlank, loop);
            if (date == null && allowBlank == true) { 
                return date;
            }

            if (limitDate.isBefore(date.minusWeeks(max)) || limitDate.isAfter(date.minusWeeks(min))) {
                String exceptionMessage = "The date of second jab must be between 4-12 weeks";
                handleInvalidInput(loop, exceptionMessage);
                continue;
            }

            return date;
        } while (true);
    }

    public static void handleInvalidInput(boolean isLoop, String exceptionMessage) throws IllegalArgumentException {
        if (isLoop == false) {
            throw new IllegalArgumentException(exceptionMessage);
        }
        System.out.println(exceptionMessage);
        boolean isContinue = inputYesNo("Continue to enter this field ?(Y/n)");
        if (!isContinue) {
            throw new IllegalArgumentException("Failed to input");
        }
    }
}
