package controller;

import Presenter.Presenter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInputController {

    Presenter presenter;
    Scanner scanner;

    public UserInputController(){
        presenter = new Presenter(null, null, null);
        scanner = new Scanner(System.in);
    }

    /**
     * Takes in user input and checks if the entered user option is valid. The whitespace before and after the entered
     * user input are removed.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    public String validInput(String[] valid_options) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toUpperCase(Locale.ROOT);
        List<String> options = Arrays.asList(valid_options);
        while (!options.contains(input.trim().toUpperCase())) {
            presenter.showInvalidInputScreen();
            input = scanner.nextLine();
        }
        return input.trim().toUpperCase();
    }

    /**
     * Returns true if a user is an admin; otherwise it returns false and prints out a message to the user.
     */
    public boolean isAdmin() {
        return true;
    }
}
