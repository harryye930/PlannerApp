package Controller;

import Interface.Presenter;
import UseCase.PlannerManager;

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
    PlannerManager pm;
    Presenter p;

    Scanner scanner;

    private final String[] USER_DECISION = {"yes", "no"};
    private final String QUIT = "q";
    private final String MAIN_MENU = "m";
    private String currentRetriever;

    public UserActionController() {
        ac = new AccessController();
        // TODO: ac.load();
        tc = new TemplateController();
        tc.load();
        // pc = new PlannerController();
        pm = new PlannerManager();
        // TODO: pc.load();

        p = new Presenter(tc, pm);
        scanner = new Scanner(System.in);
    }

    /**
     * Public method to test the methods being created during the implementation process.
     * This method will be deleted once the implementation is completed.
     */
    public void test() {
        runProgram();
    }

    /**
     * Run the program using the following method. This method is all that has to be called in the main menu after
     * creating an instance of this UserActionController in the main.
     */
    public void runProgram() {
        p.showWelcomeScreen();
        if (startProgram()) { // This user can proceed with using the program.
            useProgram();
        }
        p.showClosingScreen();
    }

    /**
     * Returns true if the user can proceed with using the features of the program; otherwise, return false.
     * @return boolean indicating whether a program should run or not.
     */
    private boolean startProgram() {
        p.showLoginMenu();
        String[] userOptions = {"A", "B", "C", QUIT};  // Options this user can choose from.
        String userInput = validInput(userOptions);

        switch (userInput) {
            case "A":  // new account
                createNewAccount();
                break;
            case "B":  // log in
                logIn();
                break;
            case "C": // guest
                currentRetriever = "guest";
                break;
            case QUIT:
                // User wants to close the program.
                saveProgram();
                return false;
        }
        // We know that the user can start using the program.
        return true;
    }

    /**
     * Presents the options and allows actions after the user successfully logged in, including as a guest.
     * Precondition: initiatingProgram()
     */
    private void useProgram() {
        String userInput; // Stores selection input from the user
        String[] mainMenuOptions = {"A", "B", "C", "D"}; // Options this user can choose from
        // This program allows user to exit the program though logging out of their current account; otherwise, the
        // program continues to display this <mainMenuOptions> after each task (i.e., option) is completed.
        do {
            p.showMainMenu();
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
                case "D":  // Log out and exit
                    if (!currentRetriever.equals("guest")) {
                    saveProgram();  // Saves all the account, planner, and template information
                    ac.logOut(currentRetriever);  // logs out of the current user
                    }
                    break;
            }
        } while (!userInput.equals("D"));
        // We know that the user wants to exit the program (i.e., userInput.equals("D") == true)
        p.interfaceScreen("Returning to the start page...");
    }

    /**
     * Saving the program information, including account, template, and planner information.
     * Precondition: initiatingProgram()
     */
    private void saveProgram() {
        p.showSavingInfoScreen();
        // TODO: ac.save();
        // tc.save();
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
     * Returns true if a user is an admin; otherwise it returns false and prints out a message to the user.
     */
    private boolean isAdmin() {
        if (currentRetriever.equals("guest") || !ac.isAdmin(currentRetriever).equals("admin")) {
            p.interfaceScreen("Checking your account status...");  // TODO
            System.out.println("Sorry, this feature requires an admin status.");  // TODO
            return false;
        }
        return true;
    }

    //=================================================================================================================
    // 1. Account Related Methods - all the helper methods mainly involving accounts are listed below.
    //=================================================================================================================

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
            if (!userInput.equals(MAIN_MENU)) {  // We know that user requested to return to the account menu.
                p.showReturnToPrevMenu();
            }
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
        p.showReturnToPrevMenu();
    }

    //=================================================================================================================
    // 2. Planner Related Methods - all the helper methods mainly involving planners are listed below.
    //=================================================================================================================

    /**
     * Planner Options. The user will remain in this Planner Options menu unless they wish to return to the main menu.
     */
    private void plannerOptions() {
        String userInput;
        do {
            // TODO: for people who worked on planner!!! - can be done on Sunday
            p.showPlannerMenu(); // display planner menu (e.g., view, edit, create, quit)
            String[] plannerOptions = {"A", "B", "C", MAIN_MENU};  // options user can choose from
            userInput = validInput(plannerOptions);

            switch (userInput) {
                case "A": // view planners
                    plannerViewOptions();
                    break;
                case "B": // edit an existing planner
                    p.showAllPlanners();
                    // TODO: H&R to implement this method in presenter
                    //  (present all existing personal planners and their ids)
                    plannerEditOptions();
//                    p.showIDForEditQuestion("planner"); // ask for ID of planner to edit
//                    // update planner entity and change planner ID into int??????
//                    String plannerID = scanner.nextLine();  // Unique ID of the template they wish to edit
//                    // Then, a user can proceed with selecting editing actions they can perform on the selected planner.
//                    aPlannerEditOptions(plannerID);
//                    break;
                case "C": // create a new planner
                    plannerCreateOptions();
                    break;
            }
            // We know that: either the requested action is completed or user requested to "quit".
        } while (!userInput.equals(MAIN_MENU));
    }

    /**
     * Shows planner edit options: edit personal planners, edit other public planners, return to planner menu.
     */
    private void plannerEditOptions(){//TODO: add do while
        String userInput;
        p.showEditPlannerMenu(); // display planner edit menu
        String[] plannerEditOptions = {"A", "B", "C"}; // options user can choose from
        userInput = validInput(plannerEditOptions);

        switch(userInput){
            case "A": //edit personal planners
                p.showAllPersonalPlanners(); // display all personal planners
                p.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                String plannerID = scanner.nextLine(); // gets ID of planner user wants to edit
                personalPlannerEditOptions(plannerID);
                break;
            case "B":  //edit other public planners
                p.showAllPublicPlanners(); // display all public planners
                p.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = scanner.nextLine(); // gets ID of planner user wants to edit
                publicPlannerEditOptions(plannerID);
                break;
            case "C":
                //TODO: implement this: return to planner menu
                break;
        }
    }

    private void personalPlannerEditOptions(String plannerID){
        // p.showObjIntroMessage("planner", plannerID); //TODO: H&R change plannerID to int then uncomment this method
        p.showDetailViewPlanner(plannerID);
        p.showEditPersonalPlannerMenu(); // display personal planner edit menu
        String[] plannerEditOptions = {"A", "B", "C", "D"}; //options user can choose from
        String userInput = validInput(plannerEditOptions);

        switch (userInput){
            case "A": // edit planner agenda
                //editPlannerAgendaOptions(plannerID); //TODO: uncomment after implementing this method
                break;
            case "B": // change privacy setting
                break; //TODO: implement this
            case "C": // delete planner
                p.showFeatureUnavailableScreen(); // display message showing feature not yet available
                //TODO: (phase 2) implement delete planner --- I think we can save it for phase 2???
                break;
            case "D": //return to edit planner menu
                break; //TODO: implement this
        }
    }

    private void publicPlannerEditOptions(String plannerID){
        //p.showObjIntroMessage("planner", plannerID); // TODO: H&R change plannerID to int then uncomment this method
        p.showDetailViewPlanner(plannerID);
        p.showEditPublicPlannerMenu(); // display public planner edit menu
        String[] plannerEditOptions = {"A", "B"}; //options user can choose from
        String userInput = validInput(plannerEditOptions);

        switch (userInput){
            case "A": // edit planner agenda
                //editPlannerAgendaOptions(plannerID); //TODO: uncomment after implementing this method
                break;
            case "B": // return to edit planner menu
                break; //TODO: implement this
        }
    }

    private void editPlannerAgendaOptions(String plannerID){
        //String type = pc.getType(plannerID);
        // TODO: H&R to implement getType(plannerID): returns type of planner: "daily" or "project"
        // p.showPlannerType(type);
        // display type of selected planner: daily or project //TODO: uncomment after previous line fixed
//        switch (type){
//            case "daily":
//                // TODO: Finish implementing
//                pc.edit(plannerID, "10:00", "good day");
//                // TODO: Presenter message
//                System.out.println("Successfully edited"); // TODO: delete
//                break;
//            case "project":
//                // TODO: Finish implementing
//                pc.edit(plannerID, 2, "good project");
//                // TODO: Presenter message
//                System.out.println("Successfully edited"); // TODO: delete
//                break;
//        } //TODO: implement switch statements
    }

    /**
     * Planner options helper method. Allows different options for planner creation.
     */
    private void plannerCreateOptions() {
        p.showPlannerCreateMenu(); // display planner creation options: daily, project, exit to planner menu
        String[] createOptions = {"A", "B", "C"};  // options user can choose from
        String userInput = validInput(createOptions);

        switch (userInput) {
            case "A": // daily
                // TODO: to be separated into presenter and USA
                System.out.println("Successfully created Daily Planner, " +
                        "these are the information: \n" + pc.createNewDailyPlanner());
                break;
            case "B": // project
                // TODO: to be separated into presenter and USA
                System.out.println("Successfully created Project Planner, " +
                        "these are the information: \n" + pc.createNewProjectPlanner());
                break;
            case "C": // exit to planner menu
                break; //TODO: implement this
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }

    //=================================================================================================================
    // 3. Template Related Methods - all the helper methods mainly involving templates are listed below.
    //=================================================================================================================

    /**
     * Template Options.
     * The user will remain in this Template Options menu unless they wish to return to the main menu.
     */
    private void templateOptions() {
        String userInput;
        String[] templateOptions = {"A", "B", "C", MAIN_MENU};
        do {
            p.showTemplateMenu();
            userInput = validInput(templateOptions);

            switch (userInput) {
                case "A":  // View all templates
                    templateViewOptions();
                    break;
                case "B":  // Edit template (admin only)
                    if (!isAdmin()) {
                        break;
                    }
                    // First, a user must select a template they would like to edit.
                    p.showDetailViewAllTemplates(); // present all existing templates and their ids
                    p.showIDForEditQuestion("template"); // ask for ID of template to edit
                    int templateID = scanner.nextInt();  // Unique ID of the template they wish to edit
                    // Then, a user can proceed with selecting editing actions they can perform on the selected template.
                    aTemplateEditOptions(templateID);
                    break;
                case "C":  // Create a new template (admin only)
                    if (!isAdmin()) {
                        break;
                    }
                    // TODO: (phase 2) implement create template
                    p.showFeatureUnavailableScreen();
                    break;
                case MAIN_MENU:
                    break;
            }
        } while (!userInput.equals(MAIN_MENU));
    }

    /**
     * Template options helper method. Allows different options for template viewing.
     */
    private void templateViewOptions() {
        p.showTemplateViewMenu();
        String[] viewOptions = {"A", "B", "C"};

        String userInput = validInput(viewOptions);
        switch (userInput) {
            case "A": // summary view (preview)
                p.showPreviewAllTemplates();
                System.out.println("preview template executed"); // TODO: delete
                break;
            case "B": // detailed view
                p.showDetailViewAllTemplates();
                System.out.println("detailed template view executed"); // TODO: delete
                break;
            case "C": // exit to template menu
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
        p.showReturnToPrevMenu();
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
            p.showObjIntroMessage("template", templateID); // intro message for showing what a template currently looks like
            p.showDetailViewTemplate(templateID);
            p.showEditTemplateMenu();// display edit options for user to choose from
            String[] editOptions = {"A", "B", "C", "D"};  // options user can choose from
            userInput = validInput(editOptions);

            switch (userInput) {
                case "A": // edit template name
                    p.showEditNewNameQuestion("template"); // display message asking user to enter a new name
                    String newTemplateName = scanner.nextLine();  // new template name user wants to assign
                    p.interfaceScreen("Please wait while we are updating your template..."); // TODO
                    tc.editTemplateName(templateID, newTemplateName);
                    break;
                case "B": // edit template prompts
                    // at the end of each edit, allows user to consecutively edit prompts without returning to the previous
                    // menu.
                    String userDecision;
                    do {
                        editPrompts(templateID);
                        p.showIfContinueEditQuestion();
                        userDecision = validInput(USER_DECISION);
                    } while (userDecision.equals("yes"));
                    break;
                case "C": // delete template
                    p.showConfirmDeleteQuestion("template"); // confirm if user wants to delete template
                    p.showDetailViewTemplate(templateID);// visualize the template
                    if (validInput(USER_DECISION).equals("yes")) {
                        // TODO: (phase 2) implement delete template
                        p.showFeatureUnavailableScreen();
                        // TODO: (phase 2) make sure to update templateDeleted to true once the template is deleted
                    }
                    break;
                case "D": // Exit to previous menu
                    break;
            }
        } while (!userInput.equals("D") && !templateDeleted);  // TODO: fix this magic letter issue later
        // We know that: either the requested action is completed or user requested to "quit".
        // Return to the previous menu (i.e., templateOptions)
        p.showReturnToPrevMenu();
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
                    p.showDetailViewTemplate(templateID); // display detailed view of template including current prompts
                    p.showIDForEditPromptQuestion("rename");
                    // display message asking user for ID of prompt they want to rename
                    int promptID = scanner.nextInt();
                    p.showEditNewNameQuestion("prompt"); // display message asking user to enter desired new name
                    String newPromptName = scanner.nextLine();
                    p.interfaceScreen("Please wait while we are updating your template...");  // TODO
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
        p.showReturnToPrevMenu();
    }

    /**
     * Planner options helper method. Allows different options for template viewing.
     */
    private void plannerViewOptions() {
        p.showPlannerViewMenu(); // display planner view options: personal, public, exit to planner menu
        String[] viewOptions = {"A", "B", "C"};  // options user can choose from
        String userInput = validInput(viewOptions);

        switch (userInput) {
            case "A": // personal planners
                // TODO: remains to be implemented
                System.out.println("view my planners executed"); // TODO: delete
                break;
            case "B": // public planners created by others
                // TODO: remains to be implemented (use AccessController method???)
                System.out.println("view public planners executed"); // TODO: delete
                break;
            case "C": // exit to planner menu
                //TODO: implement this
                break;
        }
        // We know that: either the requested action is completed or user requested to "quit".
    }
}
