package Controller;

import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * UserActionController manages user actions.
 * Allocates specific tasks to controllers that are responsible for.
 */
public class UserActionController {

    AccessController ac;
    TemplateController tc;
    PlannerController pc;

    Scanner scanner;

    private final String[] USER_DECISION = {"yes", "no"};
    private String current_retriever = "guest";  // default

    public UserActionController() {
        ac = new AccessController();
        ac.load();
        tc = new TemplateController();
        // TODO: tc.load();
        pc = new PlannerController();
        // TODO: pc.load();

        scanner = new Scanner(System.in);
    }

    /**
     * Run the program using the following method. This method is all that has to be called in the main menu after
     * creating an instance of this UserActionController in the main.
     */
    public void runProgram() {
        // TODO: TextUI - welcoming page
        // TODO: TextUI - "Welcome to your planner!""Welcome to your planner!"
        initiatingProgram();  // starting up the program, includes closing program.
        // TODO: TextUI - thanking the user for using the program.
    }

    /**
     * Initiate and runs the program.Provides options user can take in order to start using the features in the program.
     * TODO: I think this can be combined with runProgram() into one method - can be done later!!
     */
    private void initiatingProgram() {
        // TODO: TextUI - main menu (options - new account, login, guest, quit)
        // TODO:            For Text UI method, how about it takes in a character as an argument (e.g., "q")
        // Re-prompts the user until the user enters the valid input.
        String[] user_options = {"new account", "login", "guest", "quit"};  // options user can choose from
        String user_input = validInput(user_options);

        switch (user_input) {
            case "new account":  // the user does not have an existing account
                createNewAccount();
                // TODO: TextUI - account is created.
                break;
            case "login":  // the user has an existing account
                logIn();
                // TODO: TextUI - welcome the existing user.
                break;
            case "guest":
                // TODO: determine what should be done here...do we have to even do something?
                // TODO: do we need createGuestAccount in account controller?
        }
        // We know that the user_input is one of the options suggested by our program.
        if (!user_input.equals("quit")) {
            runningProgram();  // run the actual program (i.e., display and allow users to use features of the program)
        }
        closingProgram(); // The user either selected "quit" or finished using the program.
        // The program is closed.
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
                accountOptions(current_retriever);
            case "D":  // Log out
                ac.logOut(current_retriever);
        }
        // TODO: are we allowing user to log-in to different accounts after they logged out????
    }

    /**
     * Closing the program after the user completed using it.
     * Precondition: initiatingProgram()
     */
    private void closingProgram() {
        // TODO: TextUI - saving in progress...???
        ac.save();
        // TODO: tc.save() and pc.save()
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
            // TODO: TextUI - "Invalid input, please try again."
            // TODO: TextUI - re-show the options (e.g. please select from the following options: )
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Pause generated for returning to main menu.
     * TODO: To be used when implementing do-while loops into the program!
     */
    private void returnToMainTimeDelay() {
        // TODO: TextUI - "Returning to Main Menu..."
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows a user to create an account.
     */
    private void createNewAccount() {
        // TODO: TextUI - create new account (1. email, 2. username, 3. pw)
        String email = scanner.nextLine();
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        ac.createAccount(email, username, password);
        current_retriever = username;  // TODO: ask if there is a different way to retrieve retriever
        // TODO: TextUI - account created (e.g. "Please remember your ID:" + id)
    }

    /**
     * Allows a user to log-in to their account.
     */
    private void logIn() {
        // TODO: TextUI for log-in
        // TODO: TextUI - "Please enter your ID or Email:"
        String username = scanner.nextLine();
        // TODO: TextUI - "Please enter your password:"
        String password = scanner.nextLine();
        ac.logIn(username, password);
        current_retriever = username; // TODO: ask if there is a different way to retrieve retriever
        // TODO: TextUI - "Login success."
    }

    /**
     * Planner Options.
     */
    private void plannerOptions() {
        // TODO: for people who worked on planner!!! - can be done on Sunday
    }

    /**
     * Template Options.
     */
    private void templateOptions() {
        // TODO: TextUI - display options for the user to choose from
        String[] template_options = {"view", "edit", "create", "quit"};  // options user can choose from
        String user_input = validInput(template_options);

        switch (user_input) {
            case "view":  // View all templates
                templateViewOptions();
                break;
            case "edit":  // Create a new template (admin only)
                // TODO: create template - the function is not yet available
                // TODO: TextUI - system message notifying that the feature is not yet available
                break;
            case "create":  // Edit template name (admin only)
                // TODO: checks admin status
                // First, a user must select a template they would like to edit.
                // TODO: TextUI - ask for template ID (present different templates and their ids)
                // TODO: TextUI - "Which template would you like to edit? Enter the template ID"
                int templateID = scanner.nextInt();  // Unique ID of the template they wish to edit
                // Then, a user can proceed with selecting editing actions they can perform on a selected template.
                aTemplateEditOptions(templateID);
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Account Options.
     */
    private void accountOptions(String retriever) {
        // TODO: TextUI - display options for the user to choose from
        String[] account_options = {"log out", "edit", "quit"};  // options user can choose from
        String user_input = validInput(account_options);

        switch (user_input) {
            case "log out":
                ac.logOut(retriever);
                break;
            case "edit":
                accountSetting(retriever);
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    private void accountSetting(String retriever) {
        // TODO: TextUI - display options for the user to choose from
        String[] view_options = {"username", "password", "quit"};  // options user can choose from
        String user_input = validInput(view_options);
        // TODO: TextUI - ask whether they want to change their username or password
        switch (user_input) {
            case "username":
                // TODO: TextUI - "Please enter your new user name:"
                String newName = scanner.nextLine();
                ac.changeUserName(retriever, newName);
                break;
            case "password":
                // TODO: TextUI - "Please enter your original password:"
                String oldPassword = scanner.nextLine();
                // TODO: TextUI - "Please enter your new password:"
                String newPassword = scanner.nextLine();
                ac.changePassword(retriever, oldPassword, newPassword);
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Template options helper method. Allows different options for template viewing.
     */
    private void templateViewOptions() {
        // TODO: TextUI - display options for the user to choose from
        String[] view_options = {"preview", "detailed", "quit"};  // options user can choose from
        String user_input = validInput(view_options);

        switch (user_input) {
            // TODO: for people who worked on template
            case "preview":
                tc.previewAllTemplates();
                break;
            case "detailed":
                tc.detailViewAllTemplates();
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Template options helper method. Allows different options for editing this template.
     * @param templateID is the unique id of the template to be edited.
     */
    private void aTemplateEditOptions(int templateID) {
        // TODO: TextUI - show template: "This is the what the template currently looks like:\n"
        // TODO: TextUI - display options for the user to choose from
        String[] edit_options = {"change name", "edit prompts", "delete", "quit"};  // options user can choose from
        String user_input = validInput(edit_options);

        switch (user_input) {
            case "change name":
                // TODO: TextUI - "Enter new template name"
                String newTemplateName = scanner.nextLine();  // New template name they want to assign
                tc.editTemplateName(templateID, newTemplateName);
                break;
            case "edit prompts":
                // At the end of each edit, allows user to consecutively edit prompts without returning to the previous
                // menu.
                do {
                    editPrompts(templateID);
                    // TODO: TextUI - ask if they want to continue editing
                } while (validInput(USER_DECISION).equals("yes"));
                break;
            case "delete":
                // TODO: TextUI - confirm if they want to delete this template (maybe visualize the template)
                if (validInput(USER_DECISION).equals("yes")) {
                    // TODO: delete template - the function is not yet available
                    // TODO: TextUI - system message notifying that the feature is not yet available
                    break;
                }
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * It provides different edit options for editing prompts of this template.
     */
    private void editPrompts (int templateID) {
        // TODO: TextUI - display options for the user to choose from
        String[] edit_options = {"edit", "add", "delete", "quit"};  // options user can choose from
        String user_input = validInput(edit_options);
        switch (user_input) {
            case "edit":
                // At the end of each edit, allows user to consecutively edit prompts without returning to the previous
                // menu.
                String[] user_options = {"yes", "no"};
                do {
                    // TODO: TextUI - "Here are the current prompts: \n"
                    // TODO:    pass on detailed view of that specific template to TextUI??
                    // TODO: TextUI - "Enter the ID of the prompt you'd like to rename"
                    int promptID = scanner.nextInt();
                    // TODO: TextUI - "Enter the desired new name for the prompt"
                    String newPromptName = scanner.nextLine();
                    tc.renameTemplatePrompt(templateID, promptID, newPromptName);
                } while (validInput(user_options).equals("yes"));
                break;
            case "add":
                // TODO: TextUI - "Enter the desired ID for the new prompt"
                int idForNewPrompt = scanner.nextInt();
                // TODO: TextUI - "Enter the name for the new prompt"
                String nameForNewPrompt = scanner.nextLine();
                tc.addTemplatePrompt(templateID, idForNewPrompt, nameForNewPrompt);
                break;
            case "delete":
                // TODO: TextUI - "Enter the ID of the prompt you'd like to delete"
                int promptIDToDelete = scanner.nextInt();
                tc.removeTemplatePrompt(templateID, promptIDToDelete);
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }
}
