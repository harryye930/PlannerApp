package Controller;

import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

/**
 * UserActionController manages user actions.
 * Allocates specific tasks to controllers that are responsible for.
 */
public class UserActionController {

    AccessController ac;
    TemplateController tc;
    PlannerController pc;

    public UserActionController() {
        ac = new AccessController();
        tc = new TemplateController();
        pc = new PlannerController();
    }

    /**
     * Run the program using the following method.
     */
    public void runProgram() {
        // TODO: TextUI - welcoming page
        initiateProgram();  // starting up the program.
        runningProgram();  // running the program after the user logged in, includes closing the program option
        // TODO: TextUI - thanking the user for using the program.
    }

    /**
     * Initiate the program.Provides options user can take in order to start using the features in the program
     * (e.g., log in with an existing account or create a new account).
     */
    private void initiateProgram() {
        // TODO: TextUI - main menu (options - 1. login or 2. create new account or click "q" to quit)
        // TODO:            For Text UI method, how about it takes in a character as an argument (e.g., "q")
        // Re-prompts the user until the user enters the valid input.
        String[] user_options = {"yes", "no"};  // options user can choose from
        String user_input = validInput(user_options);

        switch (user_input) {
            case "no":  // the user does not have an existing account
                createNewAccount();
                // TODO: TextUI - account is created.
                break;
            case "yes":  // the user has an existing account
                logIn();
                // TODO: TextUI - welcome the existing user.
                break;
        }
    }

    /**
     * Presents the options and allows actions after the user successfully logged in, including as a guest.
     * Precondition: initiateProgram()
     */
    private void runningProgram() {
        // TODO: TextUI - main menu - Presents options users can choose from after logging in.
        // QUESTION: should options be different for different type of users?
        String[] main_menu_options = {"A", "B", "C", "D"};  // options user can choose from
        String user_input = validInput(main_menu_options);

        switch(user_input) {
            case "A":  // Selected actions on planner
                plannerOptions();
            case "B":  // Selected actions on template
                templateOptions();
            case "C":  // Selected actions on account
                accountOptions();
            case "D":  // Log out
                closingProgram();
        }
    }

    /**
     * Closing the program after the user completed using it.
     * Precondition: initiateProgram()
     */
    private void closingProgram() {
        // TODO: TextUI - successful / unsuccessful

    }

    /**
     * Takes in user input and checks if the entered user option is valid.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    private String validInput(String[] valid_options) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<String> options = Arrays.asList(valid_options);
        while (!options.contains(input)) {
            // TODO: TextUI - invalid response, please try again
            // TODO: TextUI - re-show the options
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Allows a user to create an account.
     */
    private void createNewAccount() {
        Scanner scanner = new Scanner(System.in);
        // TextUI for create new account (1. email, 2. username, 3. pw)
        String email = scanner.nextLine();
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        ac.createAccount(email, username, password);
    }

    /**
     * Allows a user to log-in to their account.
     */
    private void logIn() {
        Scanner scanner = new Scanner(System.in);
        // TextUI for log-in
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        ac.logIn(username, password);
    }

    /**
     * Planner Options.
     */
    private void plannerOptions() {
        // TODO: for people who worked on planner
        String[] planner_options = {"A", "B", "C", "D"};  // options user can choose from
        String user_input = validInput(planner_options);
    }

    /**
     * Template Options.
     */
    private void templateOptions() {

        String[] template_options = {"A", "B", "C", "D"};  // options user can choose from
        String user_input = validInput(template_options);

        switch (user_input) {
            // TODO: for people who worked on template
        }

    }

    /**
     * Account Options.
     */
    private void accountOptions() {
        // TODO: for people who worked on account
        String[] account_options = {"A", "B", "C", "D"};  // options user can choose from
        String user_input = validInput(account_options);
    }
}
