package Controller;

import Interface.Presenter;

import javax.sound.midi.Soundbank;
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
    Presenter p;

    Scanner scanner;

    private final String[] USER_DECISION = {"yes", "no"};
    private final String QUIT = "q";
    private String currentRetriever = "guest";  // default

    public UserActionController() {
        ac = new AccessController();
        // ac.load();
        tc = new TemplateController();
        // TODO: tc.load();
        // pc = new PlannerController();
        // TODO: pc.load();

        p = new Presenter();
        scanner = new Scanner(System.in);
    }

    /**
     * Public method to test the methods being created during the implementation process.
     * This method will be deleted once the implementation is completed.
     */
    public void test() {
        runningProgram();
    }

    /**
     * Run the program using the following method. This method is all that has to be called in the main menu after
     * creating an instance of this UserActionController in the main.
     */
    public void runProgram() {
        p.showWelcomeScreen();
        initiatingProgram();  // starting up the program, includes closing program.
        p.showClosingScreen();
    }

    /**
     * Initiate and runs the program. Provides options user can take in order to start using the features in the program.
     * TODO: I think this can be combined with runProgram() into one method - can be done later!!
     */
    private void initiatingProgram() {
        p.showLoginOptions();
        // TODO: For Text UI method, how about it takes in a character as an argument (e.g., "q")
        // Re-prompts the user until the user enters the valid input.
        String[] userOptions = {"A", "B", "C", "q"};  // options user can choose from
        String userInput = validInput(userOptions);

        switch (userInput) {
            case "A":  // new account
                createNewAccount();
                break;
            case "B":  // log in
                logIn();
                break;
            case "C": // quest
                // TODO: determine what should be done here...do we have to even do something?
                // TODO: do we need createGuestAccount in account controller?
        }
        // We know that the userInput is one of the options suggested by our program.
        if (!userInput.equals("q")) {
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
        String user_input;
        do {  // Allows the user to continue to perform different user actions until they select to quit the program.
            p.showMainMenu();  // TODO: add "q" as an option (e.g., click "q" to exit)
            String[] main_menu_options = {"A", "B", "C", "D", "q"};  // options user can choose from
            user_input = validInput(main_menu_options);
            switch (user_input) {
                case "A":  // Selected actions on planner
                    plannerOptions();
                    break;
                case "B":  // Selected actions on template
                    templateOptions();
                    break;
                case "C":  // Selected actions on account
                    accountOptions(currentRetriever);
                    break;
                case "D":  // Log out
                    ac.logOut(currentRetriever);
                    // TODO: are we allowing user to log-in to different accounts after they logged out????
                    break;
            }
        } while (!user_input.equals(QUIT));
    }

    /**
     * Closing the program after the user completed using it.
     * Precondition: initiatingProgram()
     */
    private void closingProgram() {
        p.showSavingInfoScreen();
        // ac.save();
        // TODO: tc.save() and pc.save()
        p.showSavingSuccessfulScreen();
    }

    /**
     * Takes in user input and checks if the entered user option is valid.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    private String validInput(String[] valid_options) {  // TODO: we can switch it to do-while loop later
        String input = scanner.nextLine();
        List<String> options = Arrays.asList(valid_options);
        while (!options.contains(input.trim())) {
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
        currentRetriever = username;  // TODO: ask if there is a different way to retrieve retriever
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
        currentRetriever = username; // TODO: ask if there is a different way to retrieve retriever
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
        System.out.println("Select from the following: view, edit, create, quit"); // TODO: delete after TextUI implementation
        String[] template_options = {"view", "edit", "create", "quit"};  // options user can choose from
        String user_input = validInput(template_options);

        switch (user_input) {
            case "view":  // View all templates
                templateViewOptions();
                break;
            case "create":  // Create a new template (admin only)
                // TODO: create template - the function is not yet available
                // TODO: TextUI - system message notifying that the feature is not yet available
                break;
            case "edit":  // Edit template name (admin only)
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
        System.out.println("Select from the following: preview, detailed, quit"); // TODO: delete after TextUI imp.
        String[] view_options = {"preview", "detailed", "quit"};  // options user can choose from
        String user_input = validInput(view_options);

        switch (user_input) {
            // TODO: for people who worked on template
            case "preview":
                tc.previewAllTemplates();
                System.out.println("preview template executed"); // TODO: delete
                break;
            case "detailed":
                tc.detailViewAllTemplates();
                System.out.println("detailed template view executed"); // TODO: delete
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
        System.out.println("Select from: change name, edit prompts, delete, quit"); // TODO - delete post TextUI imp.
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
        System.out.println("select from: edit, add, delete, quit"); // TODO: delete post TextUI imp.
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
                    System.out.println("id (int), type new prompt (str)"); // TODO: delete post TextUI imp.
                    int promptID = scanner.nextInt();
                    // TODO: TextUI - "Enter the desired new name for the prompt"
                    String newPromptName = scanner.nextLine();
                    tc.renameTemplatePrompt(templateID, promptID, newPromptName);
                    // TODO: TextUI - "Would you like to continue editing more prompts?"
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
