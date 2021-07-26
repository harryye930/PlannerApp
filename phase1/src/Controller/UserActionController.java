package Controller;

import Interface.Presenter;
import UseCase.PlannerManager;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

/**
 * UserActionController manages user actions.
 * Allocates specific tasks to controllers that should be responsible for them.
 */
public class UserActionController {

    AccessController accessController;
    TemplateController templateController;
    PlannerController plannerController;
    Presenter presenter;

    Scanner scanner;

    private final String[] USER_DECISION = {"yes", "no"};
    private final String QUIT = "q";
    private final String MAIN_MENU = "m";
    private String currentRetriever;

    public UserActionController() {
        accessController = new AccessController();
        accessController.load();
        templateController = new TemplateController();
        templateController.load();
        plannerController = new PlannerController();
        // pc.load();

        presenter = new Presenter(templateController, plannerController, accessController);
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
        presenter.showWelcomeScreen();
        while (startProgram()) { // This user can proceed with using the program.
            while (useProgram()) {
                presenter.showReturnToMainScreen();
            }
            presenter.interfaceScreen("Returning to the start page...");
        }
        presenter.showClosingScreen();
    }

    /**
     * Returns true if the user can proceed with using the features of the program; otherwise, return false.
     * @return boolean indicating whether a program should run or not.
     */
    private boolean startProgram() {
        presenter.showLoginMenu();
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
                currentRetriever = accessController.createAccount("", "", "");
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
     * Enables a successfully logged in user or a guest account user to use the features of the template.
     * Returns true if the user wants to continue to stay in the main menu; otherwise, returns false.
     * @return a boolean value indicating their interest for using the program (i.e., stay in the main menu).
     */
    private boolean useProgram() {
        String userInput; // Stores selection input from the user
        String[] mainMenuOptions = {"A", "B", "C", "D"}; // Options this user can choose from

        presenter.showMainMenu();
        userInput = validInput(mainMenuOptions);
        switch (userInput) {
            case "A":  // Selected actions on planner
                while (plannerOptions()) {
                    presenter.showReturnToPlannerMenuMessage();
                }
                break;
            case "B":  // Selected actions on template
                while (templateOptions()) {
                    presenter.showReturnToTemplateMenuMessage();
                }
                break;
            case "C":  // Selected actions on account
                while (accountOptions(currentRetriever)) {
                    presenter.showReturnToAccountMenuMessage();
                }
                break;
            case "D":  // Log out and exit
                // Except for the guest account, save all the files (i.e., account, planner, template) and log out user
                // from their account.
                saveProgram();
                accessController.logOut(currentRetriever);

                return false;
        }
        // A user wants to continue using the features available in the main menu.
        return true;
    }

    /**
     * Saving the program information, including account, template, and planner information.
     * Precondition: startProgram()
     */
    private void saveProgram() {
        presenter.showSavingInfoScreen();
        accessController.save();
        templateController.save();
        // pc.save()
        presenter.showSavingSuccessfulScreen();
    }

    /**
     * Takes in user input and checks if the entered user option is valid. The whitespace before and after the entered
     * user input are removed.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    private String validInput(String[] valid_options) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<String> options = Arrays.asList(valid_options);
        while (!options.contains(input.trim())) {
            presenter.showInvalidInputScreen();
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Returns true if a user is an admin; otherwise it returns false and prints out a message to the user.
     */
    private boolean isAdmin() {
        // TODO: below is commented for testing purpose with guest account - uncomment it when done
//        if (currentRetriever.equals("guest") || !accessController.isAdmin(currentRetriever).equals("admin")) {
//            presenter.showCheckAccountPermMessage(); // show message saying "checking account type and permissions"
//            presenter.showRequiresAdminMessage(); // show message saying "feature requires an admin account"
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
        presenter.showCreateNewAccountScreen(0); // ask user for email
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        presenter.showCreateNewAccountScreen(1); // ask user for username
        String username = scanner.nextLine();
        // Allows user to create and confirm their password. The user must confirm their password before proceeding
        // with their account creation.
        boolean passwordConfirmed = false;
        String password;
        do {
            presenter.showCreateNewAccountScreen(2); // ask user for password
            password = scanner.nextLine();
            presenter.showConfirmPasswordScreen(); // display message asking user to confirm password
            String confirmPassword = scanner.nextLine();
            if (password.equals(confirmPassword)) {
                passwordConfirmed = true;
            } else {
                presenter.showPasswordUnmatchedScreen(); // display message showing that the password doesn't match
            }
        } while (!passwordConfirmed); // continue if the password is not confirmed
        currentRetriever = accessController.createAccount(email, username, password);
        // ac.save();
        presenter.showAccountCreatedScreen(currentRetriever); // display message showing that new account with username has been created
    }

    /**
     * Allows a user to log-in to their account.
     */
    private void logIn() {
        String userRetriever;
        String password;
        boolean loginSuccess = false;  // indicates whether the log-in was successful or not
        do {
            presenter.showLoginScreen(0); // ask user for userRetriever or email
            Scanner scanner = new Scanner(System.in);
            userRetriever = scanner.nextLine();
            presenter.showLoginScreen(1); // ask user for password
            password = scanner.nextLine();
            if (accessController.logIn(userRetriever, password)) {
                currentRetriever = userRetriever;
                loginSuccess = true;
            } else {
                presenter.showLoginFailedScreen(); // display message showing invalid login credentials entered
            }
        } while (!loginSuccess);
        // We know that the user logged in to their account successfully.
        presenter.showLoginSuccessfulScreen(); // login successful message
    }

    /**
     * Account Options. The user will remain in this Account Options menu unless they wish to return to the main menu.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @param retriever is userID or email of the user that is unique and can be used to identify the account holder.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean accountOptions(String retriever) {
        String userInput;
        String[] accountOptions = {"A", "B", MAIN_MENU};

        presenter.showAccountMenu(); // display account options for the user to choose from
        userInput = validInput(accountOptions);

        switch (userInput) {
            case "A": // TODO: change this to delete account
                accessController.logOut(retriever);
                return false;
            case "B": // edit account info
                while (accountSetting(retriever)) {
                    presenter.showReturnToAccountSettingsMessage();
                }
                break;
            case "C":
                return false;
        }
        return true;
    }

    /**
     * Enables a user to change either edit their username or password.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @param retriever is userID or email of the user that is unique and can be used to identify the account holder.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean accountSetting(String retriever) {
        String userInput;
        String[] viewOptions = {"A", "B", "C"};

        presenter.showEditAccountMenu(); // display options for editing account for user to choose from
        userInput = validInput(viewOptions);

        switch (userInput) {
            case "A": // edit username
                presenter.showEditAccountPrompts(0); // display message asking user to enter new user name
                String newName = scanner.nextLine();
                accessController.changeUserName(retriever, newName);
                break;
            case "B": // edit password
                presenter.showEditAccountPrompts(1); // display message asking user to enter current password
                String oldPassword = scanner.nextLine();
                presenter.showEditAccountPrompts(2);// display message asking user to enter new password
                String newPassword = scanner.nextLine();
                accessController.changePassword(retriever, oldPassword, newPassword);
                break;
            case "C": // return to account menu
                return false;
        }
        return true;
    }

    //=================================================================================================================
    // 2. Planner Related Methods - all the helper methods mainly involving planners are listed below.
    //=================================================================================================================

    /**
     * Planner Options. The user will remain in this Planner Options menu unless they wish to return to the main menu.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean plannerOptions() {
        String userInput;
        String[] plannerOptions = {"A", "B", "C", MAIN_MENU};

        presenter.showPlannerMenu(); // display planner menu (e.g., view, edit, create, quit)
        userInput = validInput(plannerOptions);

        switch (userInput) {
            case "A": // view planners
                plannerViewOptions(currentRetriever);
                break;
            case "B": // edit an existing planner
                // plannerManager.getPlannersByAuthor(currentRetriever); TODO: Harry to double check if we need this
                while (plannerEditOptions()) {
                    presenter.showReturnToPlannerEditMenuMessage();
                }
            case "C": // create a new planner
                plannerCreateOptions(currentRetriever);
                break;
            case "D": // exit
                return false;
        }
        // We know that the user didn't select return to main menu and that the requested action by the user is completed.
        // The user will wants to remain in the <plannerOptions> menu until they explicitly indicate their interest to
        // return to the main menu.
        return true;
    }

    /**
     * Planner options helper method. Enables several options for editing planners.
     * Planner edit options includes edit personal planners, edit other public planners, return to planner menu.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean plannerEditOptions() {
        String userInput;
        String[] plannerEditOptions = {"A", "B", "C"};

        presenter.showEditPlannerMenu(); // display planner edit menu
        userInput = validInput(plannerEditOptions);
        int plannerID;

        switch(userInput){
            case "A": //edit personal planners
                presenter.showAllPersonalPlanners(currentRetriever); // display all personal planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                personalPlannerEditOptions(plannerID);
                break;
            case "B":  //edit other public planners
                presenter.showAllPublicPlanners(); // display all public planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                publicPlannerEditOptions(plannerID);
                break;
            case "C":
                return false;
        }
        // We know that the user didn't select return to prev. menu and that the requested action by the user is completed.
        // The user will wants to remain in the <plannerEditOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }

    /**
     * Provides different edit options for editing a personal planner.
     * @param plannerID is the unique id of the planner being edited.
     */
    private void personalPlannerEditOptions(int plannerID){
        String userInput;
        String[] plannerEditOptions = {"A", "B", "C", "D"}; //options user can choose from

        presenter.showObjIntroMessage("planner", plannerID);
        presenter.showDetailViewPlanner(plannerID);
        presenter.showEditPersonalPlannerMenu(); // display personal planner edit menu
        userInput = validInput(plannerEditOptions);

        switch (userInput) {
            case "A": // edit planner agenda
                editPlannerAgendaOptions(plannerID);
                break;
            case "B": // change privacy setting
                System.out.println("select private/public");
                String privacyState = scanner.nextLine();
                plannerController.changePrivacyStatus(plannerID, privacyState);
                break;
            case "C": // delete planner
                presenter.showFeatureUnavailableScreen(); // display message showing feature not yet available
                break;
            case "D": //return to edit planner menu
                break;
        }
    }

    /**
     * Provides different edit options for editing a public planner.
     * @param plannerID is the unique id of the planner being edited.
     */
    private void publicPlannerEditOptions(int plannerID){
        String userInput;
        String[] plannerEditOptions = {"A", "B"};

        presenter.showObjIntroMessage("planner", plannerID);
        presenter.showDetailViewPlanner(plannerID);
        presenter.showEditPublicPlannerMenu(); // display public planner edit menu
        userInput = validInput(plannerEditOptions);

        switch (userInput){
            case "A": // edit planner agenda
                editPlannerAgendaOptions(plannerID);
                break;
            case "B": // return to edit planner menu
                break;
        }
    }

    /**
     * Provides different edit options for editing a planner agenda.
     * @param plannerID is the unique id of the planner being edited.
     */
    private void editPlannerAgendaOptions(int plannerID){
        String type = plannerController.getType(plannerID);

        presenter.showObjIntroMessage("planner", plannerID); // intro message showing what planner looks like now
        presenter.showDetailViewPlanner(plannerID);
        switch (type){
            case "daily":
                presenter.showPlannerEditTimeQuestion(); // TODO: Harry to double check how the closest time works
                String time = scanner.nextLine();
                presenter.showPlannerEditAgendaQuestion(); // show message asking user to edit the new agenda for the
                                                            // time chosen
                String agenda = scanner.nextLine();
                plannerController.edit(plannerID, time, agenda);
                presenter.showUpdateCompletedMessage();
                break;
            case "project":
                presenter.showPlannerEditIndexQuestion();
                //TODO: Raymond to double check if this should be a (while i > plannerController.getNumAgendas(plannerID)) loop?
                int i = scanner.nextInt();
                scanner.nextLine();
                if (i <= plannerController.getNumAgendas(plannerID)){
                    presenter.showPlannerEditAgendaQuestion();
                    String projectAgenda = scanner.nextLine();
                    plannerController.edit(plannerID, i, projectAgenda);
                    presenter.showUpdateCompletedMessage();
                }
                else{
                    presenter.showPlannerReEnterIndexMessage();
                }
                break;

        }
    }

    /**
     * Planner options helper method. Allows different options for planner creation.
     */
    private void plannerCreateOptions(String userId) {
        String userInput;
        String[] createOptions = {"A", "B", "C"};

        presenter.showPlannerCreateMenu(); // display planner creation options: daily, project, exit to planner menu
        userInput = validInput(createOptions);
        switch (userInput) {
            case "A": // daily
                int dailyPlannerId = plannerController.createNewDailyPlanner();
                plannerController.setPlannerAuthor(dailyPlannerId, userId);
                accessController.setPlanner(currentRetriever, ((Integer) dailyPlannerId).toString());
                presenter.showPlannerCreatedMessage(StringUtils.capitalize(plannerController.getType(dailyPlannerId)));
                presenter.showDetailViewPlanner(dailyPlannerId);
                break;
            case "B": // project
                int projectPlannerId = plannerController.createNewProjectPlanner();
                plannerController.setPlannerAuthor(projectPlannerId, userId);
                accessController.setPlanner(currentRetriever, ((Integer) projectPlannerId).toString());
                presenter.showPlannerCreatedMessage(StringUtils.capitalize(plannerController.getType(projectPlannerId)));
                presenter.showDetailViewPlanner(projectPlannerId);
                break;
            case "C": // exit to planner menu
                break;
        }
    }

    /**
     * Planner options helper method. Allows different options for planner viewing.
     */
    private void plannerViewOptions(String userId) {
        String userInput;
        String[] viewOptions = {"A", "B", "C"};

        presenter.showPlannerViewMenu(); // display planner view options: personal, public, exit to planner menu
        userInput = validInput(viewOptions);
        switch (userInput) {
            case "A": // personal planners
                plannerController.getPlannerByAuthor(userId);
                break;
            case "B": // public planners created by others
                plannerController.getPublicPlanners();
                break;
            case "C": // exit to planner menu
                break;  // this is all this is required here for case C - don't worry!
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

        presenter.showTemplateMenu();
        userInput = validInput(templateOptions);

        switch (userInput) {
            case "A":  // View all templates
                templateViewOptions();
                break;
            case "B":  // Edit template (admin only)
                if (isAdmin()) {
                    // First, a user must select a template they would like to edit.
                    // Then, a user can proceed with selecting editing actions they can perform on the selected template.
                    presenter.showDetailViewAllTemplates();
                    presenter.showIDForEditQuestion("template");
                    int templateID = scanner.nextInt();
                    scanner.nextLine(); // Note: required as nextInt() does not read the new line created by enter
                    // Note that a user will remain in editing a given template options until they explicitly wish to
                    // exit from that menu.
                    while (aTemplateEditOptions(templateID)) {
                        presenter.showReturnToTemplateEditMenuMessage();
                    }
                }
                break;
            case "C":  // Create a new template (admin only)
                if (!isAdmin()) {
                    break;
                }
                // (phase 2) implement create template
                presenter.showFeatureUnavailableScreen();
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
     */
    private void templateViewOptions() {
        presenter.showTemplateViewMenu();
        String[] viewOptions = {"A", "B", "C"};

        String userInput = validInput(viewOptions);
        switch (userInput) {
            case "A": // summary view (preview)
                presenter.showPreviewAllTemplates();
                break;
            case "B": // detailed view
                presenter.showDetailViewAllTemplates();
                break;
            case "C": // exit to template menu
                break;
        }
    }

    /**
     * Template options helper method. Allows different options for editing this template.
     * Returns true if the template exists and the user wants to stay in this menu; otherwise, returns false.
     * @param templateID is the unique id of the template being edited.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean aTemplateEditOptions(int templateID) {
        String userInput;
        String[] editOptions = {"A", "B", "C", "D"};

        presenter.showObjIntroMessage("template", templateID); // intro message for showing what a template currently looks like
        presenter.showDetailViewTemplate(templateID);
        presenter.showEditTemplateMenu();// display edit options for user to choose from
        userInput = validInput(editOptions);

        switch (userInput) {
            case "A": // edit template name
                presenter.showEditNewNameQuestion("template"); // display message asking user to enter a new name
                String newTemplateName = scanner.nextLine();  // new template name user wants to assign
                presenter.showUpdatingTemplateMessage(); // show message saying template is being updated please wait
                templateController.editTemplateName(templateID, newTemplateName);
                presenter.showUpdateCompletedMessage(); // show message saying that update is completed
                presenter.showDetailViewTemplate(templateID); // prints out detail view of template
                break;
            case "B": // edit template prompts
                while (editPrompts(templateID)) {
                    presenter.showReturnToEditPromptsMenuMessage(); //show message saying returning to edit prompts menu
                }
                break;
            case "C": // delete template
                presenter.showConfirmDeleteQuestion("template"); // confirm if user wants to delete template
                presenter.showDetailViewTemplate(templateID);// visualize the template
                if (validInput(USER_DECISION).equals("yes")) {
                    // (phase 2) implement delete template
                    presenter.showFeatureUnavailableScreen();
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
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @param templateID ID of the template containing the prompts to be edited.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean editPrompts (int templateID) {
        String userInput;
        String[] editOptions = {"A", "B", "C", "D"};  // options user can choose from

        presenter.showEditTemplatePromptsMenu(); //display options for the user to choose from
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
                    presenter.showTemplatePromptsIntroScreen(); // display intro message for showing current template prompts
                    presenter.showDetailViewTemplate(templateID); // display detailed view of template including current prompts
                    presenter.showIDForEditPromptQuestion("rename"); // display message asking user for prompt ID
                    int promptID = scanner.nextInt();
                    scanner.nextLine(); // Note: required as nextInt() does not read the new line created by enter
                    presenter.showEditNewNameQuestion("prompt"); // display message asking user to enter desired new name
                    String newPromptName = scanner.nextLine();

                    presenter.showUpdatingTemplateMessage(); // show message saying that template is being updated
                                                             // please wait
                    templateController.renameTemplatePrompt(templateID, promptID, newPromptName);
                    presenter.showUpdateCompletedMessage(); // show message saying that update is completed
                    presenter.showDetailViewTemplate(templateID); // show detail view of template

                    // Ask user if they wish to continue editing other prompts.
                    presenter.showIfContinueEditQuestion();
                    userDecision = validInput(userOptions);
                }
                break;
            case "B": // add prompt
                presenter.showIDForEditPromptQuestion("add");
                // display message asking user for ID of prompt they want to add
                int idForNewPrompt = scanner.nextInt();
                presenter.showEditNewNameQuestion("prompt"); // display message asking user to enter name for the new prompt
                String nameForNewPrompt = scanner.nextLine();
                templateController.addTemplatePrompt(templateID, idForNewPrompt, nameForNewPrompt);
                break;
            case "C": // delete prompt
                presenter.showIDForEditPromptQuestion("delete");
                // display message asking user for ID of prompt they want to delete
                int promptIDToDelete = scanner.nextInt();
                templateController.removeTemplatePrompt(templateID, promptIDToDelete);
                break;
            case "D":
                return false;
        }
        // We know that the user didn't select return to prev. menu and that the requested action by the user is completed.
        // The user will wants to remain in the <editOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }
}
