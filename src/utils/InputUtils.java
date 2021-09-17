package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author kiman
 */
public class InputUtils {

    public static int inputId(String message, int min, int max, boolean loop, ArrayList<Integer> idList, boolean notContain) throws IllegalArgumentException, InputMismatchException {
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
                if (notContain == true && idList.contains(new Integer(input))) {
                    throw new IllegalArgumentException("This id already existed!");
                }
                if (notContain == false && !idList.contains(new Integer(input))) {
                    throw new IllegalArgumentException("This id doesn't exist");
                }
                return input;
            } catch (Exception e) {
                if (loop == false) {
                    throw e;
                }
                System.out.println(e.getMessage());
                boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
                if (check == false) {
                    throw new IllegalArgumentException("Failed to input");
                }
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
                if (input.trim().length() > max || input.trim().length() < min) {
                    throw new IllegalArgumentException(exceptionMessage);
                }
                return input;
            } catch (Exception e) {
                if (loop == false) {
                    throw e;
                }
                System.out.println(e.getMessage());
                boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
                if (check == false) {
                    throw new IllegalArgumentException("Failed to input");
                }
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
                System.out.println(e.getMessage());
                boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
                if (check == false) {
                    throw new IllegalArgumentException("Failed to input");
                }
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
                System.out.println(e.getMessage());
                boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
                if (check == false) {
                    throw new IllegalArgumentException("Failed to input");
                }
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
                String dateInString = "";
                LocalDate expiredDate = null;
                try {
                    LocalDate date;
                    dateInString = sc.nextLine();
                    if (dateInString.equals("") && allowBlank == true) {
                        date = null;
                    } else if (dateInString.equals("")) {
                        throw new IllegalArgumentException();
                    } else {
                        date = LocalDate.parse(dateInString);
                    }
                    return date;
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Youre not allowed to omit this field");
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid date format");
                }
            } catch (IllegalArgumentException e) {
                if (loop == false) {
                    throw e;
                }
                System.out.println(e.getMessage());
                boolean check = inputYesNo("Continue to enter this field ?(Y/n)");
                if (check == false) {
                    throw new IllegalArgumentException("Failed to input");
                }
            }
        } while (true);
    }
}
