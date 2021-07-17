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

    Scanner scanner;

    public UserActionController() {
        ac = new AccessController();
        tc = new TemplateController();
        pc = new PlannerController();

        scanner = new Scanner(System.in);
    }

    /**
     * Run the program using the following method.
     */
    public void runProgram() {
        // TODO: TextUI - welcoming page
        initiatingProgram();  // starting up the program.
        runningProgram();  // running the program after the user logged in, includes closing the program option
        // TODO: TextUI - thanking the user for using the program.
    }

    /**
     * Initiate the program.Provides options user can take in order to start using the features in the program
     * (e.g., log in with an existing account or create a new account).
     */
    private void initiatingProgram() {
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
     * Precondition: initiatingProgram()
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
     * Precondition: initiatingProgram()
     */
    private void closingProgram() {
        // TODO: TextUI - successful / unsuccessful

    }

    /**
     * Takes in user input and checks if the entered user option is valid.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    private String validInput(String[] valid_options) {  // TODO: we can switch it to do-while loop later
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
            case "A":  // View all templates
                templateViewOptions();
                break;
            case "B":  // Create a new template (admin only)
                break;
            case "C":  // Edit template name (admin only)
                // TODO: checks admin status
                // First, a user must select a template they would like to edit.
                // TODO: TextUI - ask for template ID (present different templates and their ids)
                int templateID = scanner.nextInt();  // Unique ID of the template they wish to edit
                // Then, a user can proceed with selecting editing actions they can perform on a selected template.
                aTemplateEditOptions(templateID);
            case "E":
                // TODO: TextUI - returning to previous menu
                break;
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

    /**
     * Template options helper method. Allows different options for template viewing.
     */
    private void templateViewOptions() {
        String[] view_options = {"A", "B", "C"};  // options user can choose from
        String user_input = validInput(view_options);

        switch (user_input) {
            // TODO: for people who worked on template
            case "A":
                tc.previewAllTemplates();
                break;
            case "B":
                tc.detailViewAllTemplates();
                break;
            default:
                // TODO: TextUI - returning to previous menu
                break;
        }
    }

    /**
     * Template options helper method. Allows different options for editing this template.
     * @param templateID is the unique id of the template to be edited.
     */
    private void aTemplateEditOptions(int templateID) {
        String[] edit_options = {"name", "prompts", "delete"};  // options user can choose from
        String user_input = validInput(edit_options);

        // Then, a user can select
        switch (user_input) {
            case "name":
                // TODO: TextUI - ask new name
                String newTemplateName = scanner.nextLine();  // New template name they want to assign
                tc.editTemplateName(templateID, newTemplateName);
                break;
            case "prompts":
                // At the end of each edit, allows user to consecutively edit prompts without returning to the previous
                // menu.
                String[] user_options = {"yes", "no"};
                do {
                    editPrompts(templateID);
                } while (validInput(user_options).equals("yes"));
                break;
            case "delete":
                // TODO: TextUI -
                break;
            default:
                // TODO: TextUI - returning to the previous menu
                break;
        }
    }

    /**
     * It provides different edit options for editing prompts of this template.
     *
     */
    private void editPrompts (int templateID) {
        String[] edit_options = {"edit", "add", "delete"};  // options user can choose from
        String user_input = validInput(edit_options);
        switch (user_input) {
            case "edit":
                // At the end of each edit, allows user to consecutively edit prompts without returning to the previous
                // menu.
                String[] user_options = {"yes", "no"};
                do {
                    // TODO: TextUI - display the current prompts and ask which prompt to edit + newPrompt
                    // TODO:    pass on detailed view of that specific template to TextUI??
                    int promptID = scanner.nextInt();
                    String newPromptName = scanner.nextLine(); // TODO: why is it prompt name??
                    tc.renameTemplatePrompt(templateID, promptID, newPromptName);
                } while (validInput(user_options).equals("yes"));
                break;
            case "add":
                // TODO: TextUI - ask
                break;
            case "delete":
                break;
            default:
                // TODO: TextUI - returning to the previous menu
                break;
        }

    }
}
