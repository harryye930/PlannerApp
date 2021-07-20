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
        // ac.load();
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
        while (startProgram()) { // This user can proceed with using the program.
            while (useProgram()) {
                p.showReturnToMainScreen();
            }
            p.interfaceScreen("Returning to the start page...");
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
     * TODO: update java doc.
     */
    private boolean useProgram() {
        String userInput; // Stores selection input from the user
        String[] mainMenuOptions = {"A", "B", "C", "D"}; // Options this user can choose from

        p.showMainMenu();
        userInput = validInput(mainMenuOptions);
        switch (userInput) {
            case "A":  // Selected actions on planner
                while (plannerOptions()) {
                    p.interfaceScreen("Returning to planner options menu...");
                }
                break;
            case "B":  // Selected actions on template
                while (templateOptions()) {
                    p.interfaceScreen("Returning to template options menu...");
                };
                break;
            case "C":  // Selected actions on account
                while (accountOptions(currentRetriever)) {
                    p.interfaceScreen("Returning to account options menu...");
                }
                break;
            case "D":  // Log out and exit
                if (!currentRetriever.equals("guest")) {
                saveProgram();  // Saves all the account, planner, and template information
                ac.logOut(currentRetriever);  // logs out of the current user
                }
                return false;
        }
        return true;
    }

    /**
     * Saving the program information, including account, template, and planner information.
     * Precondition: startProgram()
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
        // TODO: below is commented for testing purpose with guest account - uncomment it when done
//        if (currentRetriever.equals("guest") || !ac.isAdmin(currentRetriever).equals("admin")) {
//            p.interfaceScreen("Checking your account status...");  // TODO
//            System.out.println("Sorry, this feature requires an admin status.");  // TODO
//            return false;
//        }
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
        ac.createAccount(email, username, password);  // TODO: can this USERID being returned be used as currentRetriever
        currentRetriever = username;
        // ac.save(); // TODO: I think account information should be saved after account creation
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
     * TODO: update java doc
     */
    private boolean accountOptions(String retriever) {
        String userInput;
        String[] accountOptions = {"A", "B", MAIN_MENU};

        p.showAccountMenu(); // display account options for the user to choose from
        userInput = validInput(accountOptions);

        switch (userInput) {
            case "A": // log out
                ac.logOut(retriever);
                return false;
            case "B": // edit account info
                accountSetting(retriever);
                break;
            case "C":
                return false;
        }
        return true;
    }

    private void accountSetting(String retriever) {
        String userInput;
        String[] viewOptions = {"A", "B", "C"};

        p.showEditAccountMenu(); // display options for editing account for user to choose from
        userInput = validInput(viewOptions);
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
    }

    //=================================================================================================================
    // 2. Planner Related Methods - all the helper methods mainly involving planners are listed below.
    //=================================================================================================================

    /**
     * Planner Options.
     * TODO: I am too tired to update javadoc rn, but basically it returns true when you want to remain in this
     * TODO: menu and false when you want to return to the previous menu.
     * TODO: just think of return as "DO YOU WANT TO STAY IN THIS MENU? YES/NO".
     */
    private boolean plannerOptions() {
        String userInput;
        String[] plannerOptions = {"A", "B", "C", MAIN_MENU};

        p.showPlannerMenu(); // display planner menu (e.g., view, edit, create, quit)
        userInput = validInput(plannerOptions);

        switch (userInput) {
            case "A": // view planners
                plannerViewOptions();
                break;
            case "B": // edit an existing planner
                p.showAllPlanners();
                // TODO: H&R to implement this method in presenter
                //  (present all existing personal planners and their ids)
                while (plannerEditOptions()) {
                    p.interfaceScreen("Returning to planner edit options...");
                }
            case "C": // create a new planner
                plannerCreateOptions();
                break;
            case "D": // exit
                return false;
        }
        return true;
    }

    /**
     * Shows planner edit options: edit personal planners, edit other public planners, return to planner menu.
     * TODO: I am too tired to update javadoc rn, but basically it returns true when you want to remain in this
     * TODO: menu and false when you want to return to the previous menu.
     * TODO: just think of return as "DO YOU WANT TO STAY IN THIS MENU? YES/NO".
     */
    private boolean plannerEditOptions() {
        String userInput;
        String[] plannerEditOptions = {"A", "B", "C"};

        p.showEditPlannerMenu(); // display planner edit menu
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
                return false;
        }
        return true;
    }

    private void personalPlannerEditOptions(String plannerID){
        String userInput;
        String[] plannerEditOptions = {"A", "B", "C", "D"}; //options user can choose from

        // p.showObjIntroMessage("planner", plannerID); //TODO: H&R change plannerID to int then uncomment this method
        p.showDetailViewPlanner(plannerID);
        p.showEditPersonalPlannerMenu(); // display personal planner edit menu
        userInput = validInput(plannerEditOptions);

        switch (userInput) {
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
        String userInput;
        String[] plannerEditOptions = {"A", "B"};

        //p.showObjIntroMessage("planner", plannerID); // TODO: H&R change plannerID to int then uncomment this method
        p.showDetailViewPlanner(plannerID);
        p.showEditPublicPlannerMenu(); // display public planner edit menu
        userInput = validInput(plannerEditOptions);

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
        String userInput;
        String[] createOptions = {"A", "B", "C"};

        p.showPlannerCreateMenu(); // display planner creation options: daily, project, exit to planner menu
        userInput = validInput(createOptions);
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

    /**
     * Planner options helper method. Allows different options for template viewing.
     */
    private void plannerViewOptions() {
        String userInput;
        String[] viewOptions = {"A", "B", "C"};

        p.showPlannerViewMenu(); // display planner view options: personal, public, exit to planner menu
        userInput = validInput(viewOptions);
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
                break;  // TODO: this is all this is required here for case C - don't worry!
        }
    }

    //=================================================================================================================
    // 3. Template Related Methods - all the helper methods mainly involving templates are listed below.
    //=================================================================================================================

    /**
     * Template Options. The user will remain in this Template Options menu unless they wish to return to the main menu.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean templateOptions() {
        String userInput;
        String[] templateOptions = {"A", "B", "C", MAIN_MENU};

        p.showTemplateMenu();
        userInput = validInput(templateOptions);

        switch (userInput) {
            case "A":  // View all templates
                templateViewOptions();
                break;
            case "B":  // Edit template (admin only)
                if (isAdmin()) {
                    // First, a user must select a template they would like to edit.
                    // Then, a user can proceed with selecting editing actions they can perform on the selected template.
                    p.showDetailViewAllTemplates();
                    p.showIDForEditQuestion("template");
                    int templateID = scanner.nextInt();
                    scanner.nextLine(); // Note: required as nextInt() does not read the new line created by enter
                    // Note that a user will remain in editing a given template options until they explicitly wish to
                    // exit from that menu.
                    while (aTemplateEditOptions(templateID)) {
                        p.interfaceScreen("Returning to template edit options...");
                    }
                }
                break;
            case "C":  // Create a new template (admin only)
                if (!isAdmin()) {
                    break;
                }
                // TODO: (phase 2) implement create template
                p.showFeatureUnavailableScreen();
                break;
            case MAIN_MENU:
                return false;
        }
        // We know that the user didn't select return to main menu and that the requested action by the user is completed.
        // The user will wants to remain in the <templateOptions> menu until they explicitly indicate their interest to
        // return to the main menu.
        return true;
    }

    /**
     * Template options helper method. Allows different options for template viewing.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @return a boolean value indicating their interest to remain in the current menu.
     * TODO: return not necessary as we do not want to return to view options - fix later
     */
    private boolean templateViewOptions() {
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
                return false;
        }
        // We know that the user didn't select return to main menu and that the requested action by the user is completed.
        // The user will wants to remain in the <viewOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }

    /**
     * Template options helper method. Allows different options for editing this template.
     * Returns true if the template exists and the user wants to stay in this menu; otherwise, returns false.
     * @param templateID is the unique id of the template to be edited.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean aTemplateEditOptions(int templateID) {
        String userInput;
        String[] editOptions = {"A", "B", "C", "D"};

        p.showObjIntroMessage("template", templateID); // intro message for showing what a template currently looks like
        p.showDetailViewTemplate(templateID);
        p.showEditTemplateMenu();// display edit options for user to choose from
        userInput = validInput(editOptions);

        switch (userInput) {
            case "A": // edit template name
                p.showEditNewNameQuestion("template"); // display message asking user to enter a new name
                String newTemplateName = scanner.nextLine();  // new template name user wants to assign
                p.interfaceScreen("Please wait while we are updating your template..."); // TODO
                tc.editTemplateName(templateID, newTemplateName);
                System.out.println("Update is completed: "); // TODO: added
                System.out.println(tc.detailViewTemplate(templateID));;  // TODO: added
                break;
            case "B": // edit template prompts
                while (editPrompts(templateID)) {
                    p.interfaceScreen("Returning to the edit prompts menu...");
                }
                break;
            case "C": // delete template
                p.showConfirmDeleteQuestion("template"); // confirm if user wants to delete template
                p.showDetailViewTemplate(templateID);// visualize the template
                if (validInput(USER_DECISION).equals("yes")) {
                    // TODO: (phase 2) implement delete template
                    p.showFeatureUnavailableScreen();
                }
                return false;
            case "D": // Exit to previous menu
                return false;
            }
        // We know that (1) the user didn't select return to previous menu, (2) the template is not deleted,
        // (3) and the requested action by the user is completed.
        // The user will wants to remain in the <editOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }

    /**
     * Provides different edit options for editing prompts of this template.
     * TODO: update java doc
     */
    private boolean editPrompts (int templateID) {
        String userInput;
        String[] editOptions = {"A", "B", "C", "D"};  // options user can choose from

        p.showEditTemplatePromptsMenu(); //display options for the user to choose from
        userInput = validInput(editOptions);
        switch (userInput) {
            case "A": // rename prompt
                // It is logical that users are allowed to edit prompts consecutively without exiting this menu.
                // At the end of editing each prompt, a user will be asked if they wish to continue editing other prompts.
                // editPrompts feature will only be exited when a user explicitly indicates its interest to stop editing
                // any further prompts.
                String userDecision = "yes";  // Since a user indicated its interest to edit prompt, the default is "yes"
                String[] userOptions = {"yes", "no"};

                while (userDecision.equals("yes")) {
                    p.showTemplatePromptsIntroScreen(); // display intro message for showing current template prompts
                    p.showDetailViewTemplate(templateID); // display detailed view of template including current prompts
                    p.showIDForEditPromptQuestion("rename"); // display message asking user for prompt ID
                    int promptID = scanner.nextInt();
                    scanner.nextLine(); // Note: required as nextInt() does not read the new line created by enter
                    p.showEditNewNameQuestion("prompt"); // display message asking user to enter desired new name
                    String newPromptName = scanner.nextLine();

                    p.interfaceScreen("Please wait while we are updating your template...");  // TODO
                    tc.renameTemplatePrompt(templateID, promptID, newPromptName);
                    System.out.println("Update is completed: "); // TODO: added
                    System.out.println(tc.detailViewTemplate(templateID));;  // TODO: added

                    // Ask user if they wish to continue editing other prompts.
                    p.showIfContinueEditQuestion();
                    userDecision = validInput(userOptions);
                }
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
            case "D":
                return false;
        }
        return true;
    }
}
