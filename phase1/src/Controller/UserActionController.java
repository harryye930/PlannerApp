package Controller;

import Interface.Presenter;

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
    private final String MAIN_MENU = "m";
    private String currentRetriever = "guest";  // default

    public UserActionController() {
        ac = new AccessController();
        // TODO: ac.load();
        tc = new TemplateController();
        tc.load();
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
        p.showLoginMenu();
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
            case "C": // guest
                // TODO: determine what should be done here...do we have to even do something?
                // TODO: do we need createGuestAccount in account controller?
        }
        // We know that the userInput is one of the options suggested by our program.
        if (!userInput.equals(QUIT)) {
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
        String userInput;
        do {  // Allows the user to continue to perform different user actions until they select to quit the program.
            p.showMainMenu();
            String[] mainMenuOptions = {"A", "B", "C", "D", "q"};  // options user can choose from
            userInput = validInput(mainMenuOptions);
            switch (userInput) {
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
            if (!userInput.equals(QUIT)) {  // Adds in a delay before returning to the main menu.
                returnToMainTimeDelay();
            }
        } while (!userInput.equals(QUIT));
    }

    /**
     * Closing the program after the user completed using it.
     * Precondition: initiatingProgram()
     */
    private void closingProgram() {
        p.showSavingInfoScreen();
        // TODO: ac.save();
        tc.save();
        // TODO: pc.save()
        p.showSavingSuccessfulScreen();
    }

    /**
     * Takes in user input and checks if the entered user option is valid.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    private String validInput(String[] valid_options) {
        String input = scanner.nextLine();
        List<String> options = Arrays.asList(valid_options);
        while (!options.contains(input.trim())) {
            p.showInvalidInputScreen();
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Generates delay for returning to main menu.
     * TODO: To be used when implementing do-while loops into the program!
     */
    private void returnToMainTimeDelay() {
        p.showReturnToMainScreen();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows a user to create an account. The same password must be entered consecutively for the program to proceed
     * with creating a new account for this user.
     */
    private void createNewAccount() {
        p.showCreateNewAccountScreen(0); // ask user for email
        String email = scanner.nextLine();
        p.showCreateNewAccountScreen(1); // ask user for username
        String username = scanner.nextLine();
        // Allows user to create and confirm their password. The user must confirm their password before proceeding
        // with their account creation.
        boolean passwordConfirmed = false;
        String password;
        do {
            p.showCreateNewAccountScreen(2); // ask user for password
            password = scanner.nextLine();
            p.showConfirmPasswordScreen(); // display message asking user to confirm password
            String confirmPassword = scanner.nextLine();
            if (password.equals(confirmPassword)) {
                passwordConfirmed = true;
            } else {
                p.showPasswordUnmatchedScreen(); // display message showing that the password doesn't match
            }
        } while (!passwordConfirmed); // continue if the password is not confirmed
        ac.createAccount(email, username, password);
        currentRetriever = username;  // TODO: ask if there is a different way to retrieve retriever
        p.showAccountCreatedScreen(username); // display message showing that new account with username has been created
    }

    /**
     * Allows a user to log-in to their account.
     */
    private void logIn() {
        String username;
        String password;
        boolean loginSuccess = false;  // indicates whether the log-in was successful or not
        do {
            p.showLoginScreen(0); // ask user for username or email
            username = scanner.nextLine();
            p.showLoginScreen(1); // ask user for password
            password = scanner.nextLine();
            if (ac.logIn(username, password)) {
                currentRetriever = username;
                loginSuccess = true;
            } else {
                p.showLoginFailedScreen(); // display message showing invalid login credentials entered
            }
        } while (!loginSuccess);
        // We know that the user logged in to their account successfully.
        p.showLoginSuccessfulScreen(); // login successful message
    }

    /**
     * Planner Options. The user will remain in this Planner Options menu unless they wish to return to the main menu.
     */
    private void plannerOptions() {
        String userInput;
        do {
            // TODO: for people who worked on planner!!! - can be done on Sunday
            p.showPlannerMenu();// display planner menu (e.g., view, edit, create, quit)
            String[] plannerOptions = {"A", "edit", "create", MAIN_MENU};  // options user can choose from
            userInput = validInput(plannerOptions);

            switch (userInput) {
                case "view":
                    plannerViewOptions();
                    break;
                case "edit":
                    // First, a user must select a planner they would like to edit.
                    // TODO: presenter -  present all existing personal planners and their ids
                    // TODO: presenter -  ask for ID of template to edit
                    // TODO: update planner entity and change planner ID into int (OR change template ID into String)
                    System.out.println("Please enter the ID of the planner you wish to edit."); // TODO: delete
                    String plannerID = scanner.nextLine();  // Unique ID of the template they wish to edit
                    // Then, a user can proceed with selecting editing actions they can perform on the selected planner.
                    aPlannerEditOptions(plannerID);
                    break;
                case "create":
                    plannerCreateOptions();
                    break;
            }
            // We know that: either the requested action is completed or user requested to "quit".
        } while (!userInput.equals(MAIN_MENU));
    }

    /**
     * Template Options. The user will remain in this Template Options menu unless they wish to return to the main menu.
     */
    private void templateOptions() {
        String userInput;
        do {
            p.showTemplateMenu();
            String[] templateOptions = {"A", "B", "C", MAIN_MENU};  // options user can choose from
            userInput = validInput(templateOptions);

            switch (userInput) {
                case "A":  // View all templates
                    templateViewOptions();
                    break;
                case "B":  // Edit template (admin only)
                    // TODO: checks admin status
                    // First, a user must select a template they would like to edit.
                    tc.detailViewAllTemplates(); // present all existing templates and their ids
                    p.showIDForEditQuestion("template"); // ask for ID of template to edit
                    int templateID = scanner.nextInt();  // Unique ID of the template they wish to edit
                    // Then, a user can proceed with selecting editing actions they can perform on the selected template.
                    aTemplateEditOptions(templateID);
                case "C":  // Create a new template (admin only)
                    // TODO: (phase 2) implement create template
                    p.showFeatureUnavailableScreen();
                    break;
            }
            // We know that: either the requested action is completed or user requested to "quit".
        } while (!userInput.equals(MAIN_MENU));
    }

    /**
     * Account Options. The user will remain in this Account Options menu unless they wish to return to the main menu.
     */
    private void accountOptions(String retriever) {
        String userInput;
        do {
            p.showAccountMenu(); // display account options for the user to choose from
            String[] accountOptions = {"A", "B", MAIN_MENU};  // options user can choose from
            userInput = validInput(accountOptions);

            switch (userInput) {
                case "A": // log out
                    ac.logOut(retriever);
                    break;
                case "B": // edit account info
                    accountSetting(retriever);
                    break;
            }
            // We know that: either the requested action is completed or user requested to "quit".
        } while (!userInput.equals(MAIN_MENU));
    }

    private void accountSetting(String retriever) {
        p.showEditAccountMenu(); // display options for editing account for user to choose from
        String[] viewOptions = {"A", "B", "C"};  // options user can choose from
        String userInput = validInput(viewOptions);
        switch (userInput) {
            case "A": // edit username
                p.showEditAccountPrompts(0); // display message asking user to enter new user name
                String newName = scanner.nextLine();
                ac.changeUserName(retriever, newName);
                break;
            case "B": // edit password
                p.showEditAccountPrompts(1); // display message asking user to enter current password
                String oldPassword = scanner.nextLine();
                p.showEditAccountPrompts(2);// display message asking user to enter new password
                String newPassword = scanner.nextLine();
                ac.changePassword(retriever, oldPassword, newPassword);
                break;
            case "C": // return to account menu
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Template options helper method. Allows different options for template viewing.
     */
    private void templateViewOptions() {
        p.showTemplateViewMenu();
        String[] viewOptions = {"A", "B", "C"};  // options user can choose from
        String userInput = validInput(viewOptions);

        switch (userInput) {
            case "A": // summary view (preview)
                System.out.println(tc.previewAllTemplates());
                System.out.println("preview template executed"); // TODO: delete
                break;
            case "B": // detailed view
                System.out.println(tc.detailViewAllTemplates());
                System.out.println("detailed template view executed"); // TODO: delete
                break;
            case "C": // exit to template menu
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Template options helper method. Allows different options for editing this template. The user remains in the edit
     * menu until they wish to return back to the previous menu.
     * @param templateID is the unique id of the template to be edited.
     */
    private void aTemplateEditOptions(int templateID) {
        String userInput;
        boolean templateDeleted = false;
        do {
            p.showTemplateIntroMessage(templateID); // intro message for showing what a template currently looks like
            tc.detailViewTemplate(templateID);
            p.showEditTemplateMenu();// display edit options for user to choose from
            String[] editOptions = {"A", "B", "C", "D"};  // options user can choose from
            userInput = validInput(editOptions);

            switch (userInput) {
                case "A": // edit template name
                    p.showEditNewNameQuestion("template"); // display message asking user to enter a new name
                    String newTemplateName = scanner.nextLine();  // new template name user wants to assign
                    tc.editTemplateName(templateID, newTemplateName);
                    break;
                case "B": // edit template prompts
                    // at the end of each edit, allows user to consecutively edit prompts without returning to the previous
                    // menu.
                    do {
                        editPrompts(templateID);
                        p.showIfContinueEditQuestion();
                    } while (validInput(USER_DECISION).equals("yes"));
                    break;
                case "C": // delete template
                    p.showConfirmDeleteQuestion("template"); // confirm if user wants to delete template
                    tc.detailViewTemplate(templateID); // visualize the template
                    if (validInput(USER_DECISION).equals("yes")) {
                        // TODO: (phase 2) implement delete template
                        p.showFeatureUnavailableScreen();
                        // TODO: (phase 2) make sure to update templateDeleted to true once the template is deleted
                        break;
                    }
            }
            // We know that: either the requested action is completed or user requested to "quit".
        } while (!userInput.equals("D") && !templateDeleted);  // TODO: fix this magic letter issue later
    }

    /**
     * Provides different edit options for editing prompts of this template.
     */
    private void editPrompts (int templateID) {
        p.showEditTemplatePromptsMenu(); //display options for the user to choose from
        String[] editOptions = {"A", "B", "C", "D"};  // options user can choose from
        String userInput = validInput(editOptions);
        switch (userInput) {
            case "A": // rename prompt
                // At the end of each edit, allows user to consecutively edit prompts without returning to the previous
                // menu.
                String[] userOptions = {"yes", "no"};
                do {
                    p.showTemplatePromptsIntroScreen(); // display intro message for showing current template prompts
                    tc.detailViewTemplate(templateID); // display detailed view of template including current prompts
                    p.showIDForEditPromptQuestion("rename");
                    // display message asking user for ID of prompt they want to rename
                    int promptID = scanner.nextInt();
                    p.showEditNewNameQuestion("prompt"); // display message asking user to enter desired new name
                    String newPromptName = scanner.nextLine();
                    tc.renameTemplatePrompt(templateID, promptID, newPromptName);
                    p.showIfContinueEditQuestion(); // ask user if they'd like to make another edit
                } while (validInput(userOptions).equals("yes"));
                break;
            case "B": // add prompt
                p.showIDForEditPromptQuestion("add");
                // display message asking user for ID of prompt they want to add
                int idForNewPrompt = scanner.nextInt();
                p.showEditNewNameQuestion("prompt"); // display message asking user to enter name for the new prompt
                String nameForNewPrompt = scanner.nextLine();
                tc.addTemplatePrompt(templateID, idForNewPrompt, nameForNewPrompt);
                break;
            case "C": // delete prompt
                p.showIDForEditPromptQuestion("delete");
                // display message asking user for ID of prompt they want to delete
                int promptIDToDelete = scanner.nextInt();
                tc.removeTemplatePrompt(templateID, promptIDToDelete);
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Planner options helper method. Allows different options for template viewing.
     */
    private void plannerViewOptions() {
        // TODO: planner controller view options ("personal", "public")
        System.out.println("Please enter from the following: my planners, public planners, q");
        String[] viewOptions = {"my planners", "public planners", QUIT};  // options user can choose from
        String userInput = validInput(viewOptions);

        switch (userInput) {
            case "my planners":
                // TODO: remains to be implemented
                System.out.println("view my planners executed"); // TODO: delete
                break;
            case "public planners":
                // TODO: remains to be implemented (use AccessController method???)
                System.out.println("view public planners executed"); // TODO: delete
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Planner options helper method. Allows different options for editing this planner.
     * @param plannerID is the unique id of the planner to be edited.
     */
    private void aPlannerEditOptions(String plannerID) { // TODO: change planner ID to int???
        // TODO: Presenter - similarly to aTemplateEditOptions first 3 lines
        System.out.println("Please enter from the following: daily, project, delete, q"); // TODO: delete
        String[] editOptions = {"daily", "project", "delete", QUIT};  // options user can choose from
        String userInput = validInput(editOptions);

        switch (userInput) {
            case "daily": // edit template name
                // TODO: Finish implementing
                pc.edit(plannerID, "10:00", "good day");
                // TODO: Presenter message
                System.out.println("Successfully edited"); // TODO: delete
                break;
            case "project": // edit template prompts
                // TODO: Finish implementing
                pc.edit(plannerID, 2, "good project");
                // TODO: Presenter message
                System.out.println("Successfully edited"); // TODO: delete
                break;
            case "delete": // delete planner
                // TODO: confirm if user wants to delete this planner
                // TODO: visualize the planner
                if (validInput(USER_DECISION).equals("yes")) {
                    if (pc.deletePlanner(plannerID)) {
                        // TODO: presenter
                        System.out.println("Successfully delete."); // TODO: delete
                    }
                    else {
                        // TODO: presenter
                        System.out.println("There is no such planner."); // TODO: delete
                    }
                }
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    /**
     * Planner options helper method. Allows different options for template viewing.
     */
    private void plannerCreateOptions() {
        // TODO: presenter - planner view options ("daily", "project")
        System.out.println("Please choose from the following: daily. project, q"); //TODO: delete
        String[] createOptions = {"daily", "project", "q"};  // options user can choose from
        String userInput = validInput(createOptions);

        switch (userInput) {
            case "daily":
                // TODO: to be separated into presenter and USA
                System.out.println("Successfully created Daily Planner, " +
                        "these are the information: \n" + pc.createNewDailyPlanner());
                break;
            case "project":
                // TODO: to be separated into presenter and USA
                System.out.println("Successfully created Project Planner, " +
                        "these are the information: \n" + pc.createNewProjectPlanner());
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }
}
