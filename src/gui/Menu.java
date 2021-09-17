package gui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kiman
 */
public class Menu {

    List<String> options = new ArrayList<>();

    public boolean addOption(String option) {
        return options.add(option);
    }

    public void displayMenu() {
        System.out.println("Select the options below:");
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i) != null) {
                System.out.println(i + ". " + options.get(i));
            }
        }
        System.out.println("Enter your choice: ");
    }
}
