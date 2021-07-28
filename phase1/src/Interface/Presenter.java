package Interface;

import Controller.AccessController;
import Controller.PlannerController;
import UseCase.TemplateManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Presenter {

    private AccessController accessController;
    private TemplateManager templateManager;
    private PlannerController plannerController;

    // The section below contains all of the String representations that are presented to the users.
    //================================================================================================================
    // Contains all of the messages that are universal and could be reused throughout the program.
    private final HashMap<String, String> MESSAGES = new HashMap<String, String>() {{
        String welcome = "Welcome to your planner manager!";
        String closing = "Thank you for using the program, have a productive day!";
        String savingInfo = "Thank you for using the program, have a productive day!";
        String savingSuccessful = "Saving successful!";
        String invalidInput = "Invalid input, please try again.";
        String returnToMain = "Returning to main menu...";
        String returnToPrevMenu = "Returning to previous menu...";
        String idForEditQuestion = "Which %s would you like to edit? Please enter the ID.%n";
        String objectIntro = "You have selected %s with ID %d. " + "This is what the %s currently looks like: %n";
        String editNewNameQuestion = "Please enter the desired new name of the %s.%n";
        String ifContinueEditQuestion = "Would you like to make another edit? Enter \"yes\" or \"no\".\n";
        String confirmDeleteQuestion = "Are you sure you want to delete this %s? Enter \"yes\" or \"no\".%n";
        String featureUnavailable = "Feature is not yet available. Please check back later!";
        String idForEditPromptQuestion = "Please enter the ID of the prompt you'd like to %s.%n";
        String updateCompleted = "Update is completed: ";

        put("welcome", welcome);
        put("closing", closing);
        put("savingInfo", savingInfo);
        put("savingSuccessful", savingSuccessful);
        put("invalidInput", invalidInput);
        put("returnToMain", returnToMain);
        put("returnToPrevMenu", returnToPrevMenu);
        put("idForEditQuestion", idForEditQuestion);
        put("objectIntro", objectIntro);
        put("editNewNameQuestion", editNewNameQuestion);
        put("ifContinueEditQuestion", ifContinueEditQuestion);
        put("confirmDeleteQuestion", confirmDeleteQuestion);
        put("featureUnavailable", featureUnavailable);
        put("idForEditPromptQuestion", idForEditPromptQuestion);
        put("updateCompleted", updateCompleted);
    }};

    // Contains messages that are specific to account options.
    private final HashMap<String, String> ACCOUNT_MESSAGES = new HashMap<String, String>() {{
        String confirmPassword = "Please enter the password again to confirm.\n";
        String passwordUnmatch = "Password does not match. Please try again.\n";
        String accountCreated = "A new account has been created.%nPlease remember your ID: %s.%n";
        String loginFailed = "Invalid username or password. Please try again.\n";
        String loginSuccessful = "Login successful.";
        String checkAccountPermission = "Checking your account type and its permissions...";
        String requireAdminStatus = "Sorry, this feature requires an admin account.";
        String returnToAccountSettings = "Returning to account setting options...";
        String returnToAccountMenu = "Returning to Account Menu...";

        put("confirmPassword", confirmPassword);
        put("passwordUnmatch", passwordUnmatch);
        put("accountCreated", accountCreated);
        put("loginFailed", loginFailed);
        put("loginSuccessful", loginSuccessful);
        put("checkAccountPermission", checkAccountPermission);
        put("requireAdminStatus", requireAdminStatus);
        put("returnToAccountSettings", returnToAccountSettings);
        put("returnToAccountMenu", returnToAccountMenu);
    }};

    // Contains messages that are specific to template options.
    private final HashMap<String, String> TEMPLATE_MESSAGES = new HashMap<String, String>() {{
        String templatePromptsIntro = "Here are the current prompts: \n";
        String updatingTemplate = "Please wait while we are updating your template...";
        String returnToEditPromptsMenu = "Returning to the edit prompts menu...";
        String returnToTemplateEditMenu = "Returning to template edit options...";
        String returnToTemplateMenu = "Returning to Template Menu...";

        put("templatePromptsIntro", templatePromptsIntro);
        put("updatingTemplate", updatingTemplate);
        put("returnToEditPromptsMenu", returnToEditPromptsMenu);
        put("returnToTemplateEditMenu", returnToTemplateEditMenu);
        put("returnToTemplateMenu", returnToTemplateMenu);
    }};

    // Contains messages that are specific to planner options.
    private final HashMap<String, String> PLANNER_MESSAGES = new HashMap<String, String>() {{
        String showPlannerType = "You have selected a planner of type: %s.%n";
        String plannerCreated = "%s planner successfully created. This is what it looks like: %n";
        String plannerEditTimeQuestion = "Please enter the time slot for which you'd like to edit the agenda."; // closest time functionality will be added to phase 2
        String plannerEditAgendaQuestion = "Please enter the new agenda.";
        String plannerEditIndexQuestion = "Please enter the index for which you'd like to edit the agenda.";
        String plannerReEnterIndex = "The index entered is out of range (exceeds the number of existing " +
                "agendas). Please try again.";
        String returnToPlannerEditMenu = "Returning to planner edit options...";
        String returnToPlannerMenu = "Returning to Planner Menu...";

        put("showPlannerType", showPlannerType);
        put("plannerCreated", plannerCreated);
        put("plannerEditTimeQuestion", plannerEditTimeQuestion);
        put("plannerEditAgendaQuestion", plannerEditAgendaQuestion);
        put("plannerEditIndexQuestion", plannerEditIndexQuestion);
        put("plannerReEnterIndex", plannerReEnterIndex);
        put("returnToPlannerEditMenu", returnToPlannerEditMenu);
        put("returnToPlannerMenu", returnToPlannerMenu);
    }};

    // Contains all of the menus that are presented by the program.
    private final HashMap<String, String> MENUS = new HashMap<String, String>(){{
        String loginMenu =
                "=========================================================================\n" +
                        "Select one of the options below to get started! " +
                        "Please enter the letter associated with that option.\n"+
                        "A. Create a new account\n" +
                        "B. Log in (have an existing account)\n" +
                        "C. Continue as guest (note that any changes made won't be saved)\n" +
                        "\nTo close the program, enter \"q\".\n" +
                        "=========================================================================";
        String mainMenu =
                "=========================================================================\n" +
                        "This is the Main Menu. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Planners options\n" +
                        "B. Templates options\n" +
                        "C. Account options\n" +
                        "D. Log out and exit\n" +
                        "=========================================================================";
        String accountMenu =
                "=========================================================================\n"+
                        "This is the Account Menu. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Log out\n" +
                        "B. Edit account info\n" +
                        "\nTo return to the MAIN MENU, enter \"m\".\n" +
                        "=========================================================================";
        String templateMenu =
                "=========================================================================\n"+
                        "This is the Template Menu. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. View all templates\n" +
                        "B. Edit an existing template (Admin Only) \n" +
                        "C. Create a new template (Admin Only) \n" +
                        "\nTo return to the MAIN MENU, enter \"m\".\n" +
                        "=========================================================================";
        String plannerMenu =
                "=========================================================================\n"+
                        "This is the Planner Menu. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. View planners \n" +
                        "B. Edit an existing planner \n" +
                        "C. Create a new planner \n" +
                        "\nTo return to the MAIN MENU, enter \"m\".\n" +
                        "=========================================================================";
        // SUB-MENUS
        // Account sub-menus
        String editAccountMenu =
                "=========================================================================\n"+
                        "Here are the options for editing your account. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit username \n" +
                        "B. Edit password \n" +
                        "C. Return to Account Menu \n" +
                        "=========================================================================\n";
        // Template sub-menus
        String templateViewMenu =
                "=========================================================================\n"+
                        "Options for viewing all templates. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Summary view \n" +
                        "B. Detailed view \n" +
                        "C. Exit to Template menu \n" +
                        "=========================================================================\n";
        String editTemplateMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a template. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit template name \n" +
                        "B. Edit template prompts \n" +
                        "C. Delete template \n" +
                        "D. Return to Template Menu \n" +
                        "=========================================================================\n";
        String editTemplatePromptsMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a prompt. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Rename prompt \n" +
                        "B. Add prompt \n" +
                        "C. Delete prompt \n" +
                        "D. Return to Edit Template Menu \n" +
                        "=========================================================================\n";
        // Planner sub-menus
        String plannerViewMenu =
                "=========================================================================\n"+
                        "Options for viewing planners. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. View all personal planners \n" +
                        "B. View all public planners created by others \n" +
                        "C. Exit to Planner Menu \n" +
                        "=========================================================================\n";
        String editPlannerMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a planner. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit your personal planners \n" +
                        "B. Edit other public planners \n" +
                        "C. Return to Planner Menu \n" +
                        "=========================================================================\n";
        String editPersonalPlannerMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a personal planner. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit planner agenda \n" +
                        "B. Change privacy setting \n" +
                        "C. Delete Planner \n" +
                        "D. Return to Edit Planner Menu \n" +
                        "=========================================================================\n";
        String editPublicPlannerMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a public planner. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit planner agenda \n" +
                        "B. Return to Edit Planner Menu \n" +
                        "=========================================================================\n";
        String plannerCreateMenu =
                "=========================================================================\n"+
                        "Options for creating planners. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Create a daily planner \n" +
                        "B. Create a project planner \n" +
                        "C. Exit to Planner Menu \n" +
                        "=========================================================================\n";

        // Add all the menus to the HashMap
        put("LogIn", loginMenu);
        put("Main", mainMenu);
        put("Account", accountMenu);
        put("Template", templateMenu);
        put("Planner", plannerMenu);

        put("editAccountMenu", editAccountMenu);

        put("templateViewMenu", templateViewMenu);
        put("editTemplateMenu", editTemplateMenu);
        put("editTemplatePromptsMenu", editTemplatePromptsMenu);

        put("plannerViewMenu", plannerViewMenu);
        put("editPlannerMenu", editPlannerMenu);
        put("editPersonalPlannerMenu", editPersonalPlannerMenu);
        put("editPublicPlannerMenu", editPublicPlannerMenu);
        put("plannerCreateMenu", plannerCreateMenu);
    }};
    //================================================================================================================

    public Presenter(TemplateManager templateManager, PlannerController plannerController,
                     AccessController accountManager){
        this.templateManager = templateManager;
        this.accessController = accountManager;
        this.plannerController = plannerController;
    }

    /**
     * Prints out interface screen with a message.
     * @param message is the message being printed on the interface screen.
     */
    public void interfaceScreen(String message){
        System.out.println(message);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints out message for welcome screen at the start of the program.
     */
    public void showWelcomeScreen(){
        System.out.println(MESSAGES.get("welcome"));
    }

    /**
     * Prints out message for closing screen at the end of the program.
     */
    public void showClosingScreen(){
        System.out.println(MESSAGES.get("closing"));
    }

    /**
     * Prints out log in options.
     */
    public void showLoginMenu(){
//        System.out.println(loginMenu);
        System.out.println(MENUS.get("LogIn"));
    }

    /**
     * Prints out Main Menu after user is logged in.
     */
    public void showMainMenu(){
//        System.out.println(mainMenu);
        System.out.println(MENUS.get("Main"));
    }

    /**
     * Prints out message showing that the program is saving information.
     */
    public void showSavingInfoScreen(){
        System.out.println(MESSAGES.get("savingInfo"));
    }

    /**
     * Prints out message showing that information has been successfully saved in the program.
     */
    public void showSavingSuccessfulScreen(){
        System.out.println(MESSAGES.get("savingSuccessful"));
    }

    /**
     * Prints out message showing that the user input is invalid (does not match with any of the options provided).
     */
    public void showInvalidInputScreen(){
        System.out.println(MESSAGES.get("invalidInput"));
    }

    /**
     * Prints out message showing that the system is returning back to the main menu.
     */
    public void showReturnToMainScreen(){
        interfaceScreen(MESSAGES.get("returnToMain"));
    }

    /**
     * Prints out message showing that the system is returning back to the previous menu.
     */
    public void showReturnToPrevMenu(){
        interfaceScreen(MESSAGES.get("returnToPrevMenu"));
    }

    /**
     * Prints out message asking for user input when creating a new account.
     * @param index Index of the element in the String array that user wants to print out.
     *              0 - Intro message + Email, 1 - Username, 2 - Password.
     */
    public void showCreateNewAccountScreen(int index){
        String createNewAccountIntro = "You have chosen to create a new account.\n";
        String createNewAccountInfo = "Please enter %s.\n";
        String[] createNewAccountPrompts = {String.format(createNewAccountIntro + createNewAccountInfo, "Email"),
                                            String.format(createNewAccountInfo, "Username"),
                                            String.format(createNewAccountInfo, "Password")};

        System.out.println(createNewAccountPrompts[index]);
    }

    /**
     * Prints out message asking user to enter password again to confirm.
     */
    public void showConfirmPasswordScreen(){
        System.out.println(ACCOUNT_MESSAGES.get("confirmPassword"));
    }

    /**
     * Prints out message showing that password does not match and asking user to try again.
     */
    public void showPasswordUnmatchedScreen(){
        System.out.println(ACCOUNT_MESSAGES.get("passwordUnmatch"));
    }

    /**
     * Prints out message showing that a new account has been created with the specified username.
     * @param username Username of the new account.
     */
    public void showAccountCreatedScreen(String username){
        System.out.printf((ACCOUNT_MESSAGES.get("accountCreated")), username);
    }

    /**
     * Prints out message asking for user input when logging in with an existing account.
     * @param index Index of the element in the String array that user wants to print out.
     *              0 - Intro message + Username or Email, 1 - Password.
     */
    public void showLoginScreen(int index){
        String loginIntro = "You have chosen to log in with an existing account.\n";
        String loginInfo = "Please enter your %s.\n";
        String[] loginPrompts = {String.format(loginIntro + loginInfo, "Username or Email"),
                String.format(loginInfo, "Password")};
        System.out.println(loginPrompts[index]);
    }

    /**
     * Prints out message showing that invalid username or password is entered and asking user to try again.
     */
    public void showLoginFailedScreen(){
        System.out.println(ACCOUNT_MESSAGES.get("loginFailed"));
    }

    /**
     * Prints out message showing that user has successfully logged in.
     */
    public void showLoginSuccessfulScreen(){
        System.out.println(ACCOUNT_MESSAGES.get("loginSuccessful"));
    }

    /**
     * Prints out message showing that user account type and permissions are being checked, with delay.
     */
    public void showCheckAccountPermMessage(){
        interfaceScreen(ACCOUNT_MESSAGES.get("checkAccountPermission"));
    }

    /**
     * Prints out message showing that feature requires an admin account.
     */
    public void showRequiresAdminMessage(){
        System.out.println(ACCOUNT_MESSAGES.get("requireAdminStatus"));
    }

    /**
     * Prints out Template Menu.
     */
    public void showTemplateMenu(){
//        System.out.println(templateMenu);
        System.out.println(MENUS.get("Template"));
    }

    /**
     * Prints out Template view options.
     */
    public void showTemplateViewMenu(){
        System.out.println(MENUS.get("templateViewMenu"));
    }

    /**
     * Prints out detail view of all existing templates.
     */
    public void showDetailViewAllTemplates(){
        System.out.println(templateManager.detailViewAllTemplates());
    }

    /**
     * Prints out preview of all existing templates.
     */
    public void showPreviewAllTemplates(){
        System.out.println(templateManager.previewAllTemplates());
    }

    /**
     * Prints out detail view of template with templateID.
     * @param detailViewTemplate String representation of detail view of a template.
     */
    public void showDetailViewTemplate(String detailViewTemplate){
        System.out.println(detailViewTemplate);
    }

    /**
     * Prints out message asking user for the ID of the template or planner they would like to edit.
     * @param obj String representing the type of object. Can be "template" or "planner".
     */
    public void showIDForEditQuestion(String obj){
        System.out.printf(MESSAGES.get("idForEditQuestion"), obj);
    }

    /**
     * Prints out intro message for showing what an existing template or planner with ID currently looks like.
     * @param obj Type of object - can be "planner" or "template".
     * @param ID ID of the template or planner selected.
     */
    public void showObjIntroMessage(String obj, int ID){
        System.out.printf((MESSAGES.get("objectIntro")), obj, ID, obj);
    }

    /**
     * Prints out options for editing template.
     */
    public void showEditTemplateMenu(){
        System.out.println(MENUS.get("editTemplateMenu"));
    }

    /**
     * Prints out message asking user to enter the desired new name.
     * @param obj Object of which user will give a new name. "obj" can be "template", "prompt", etc.
     */
    public void showEditNewNameQuestion(String obj){
        System.out.printf((MESSAGES.get("editNewNameQuestion")), obj);
    }

    /**
     * Prints out message asking user if they would like to continue to make another edit.
     */
    public void showIfContinueEditQuestion(){
        System.out.println(MESSAGES.get("ifContinueEditQuestion"));
    }

    /**
     * Prints out message asking user to confirm if they want to delete obj (obj can be "template", "account", etc.
     * @param obj Object that user wants to delete.
     */
    public void showConfirmDeleteQuestion(String obj){
        System.out.printf((MESSAGES.get("confirmDeleteQuestion")), obj);
    }

    /**
     * Prints out message showing that a feature/a functionality in the program is not yet available.
     */
    public void showFeatureUnavailableScreen(){
        System.out.println(MESSAGES.get("featureUnavailable"));
    }

    /**
     * Prints out message showing the options for editing a template prompt.
     */
    public void showEditTemplatePromptsMenu(){
        System.out.println(MENUS.get("editTemplatePromptsMenu"));
    }

    /**
     * Prints out intro message for showing current template prompts.
     */
    public void showTemplatePromptsIntroScreen(){
        System.out.println(TEMPLATE_MESSAGES.get("templatePromptsIntro"));
    }

    /**
     * Prints out message asking user to enter ID of the prompt they'd like to rename.
     * @param action Action user would like to perform - can be "rename", "add", or "delete"
     */
    public void showIDForEditPromptQuestion(String action){
        System.out.printf((MESSAGES.get("idForEditPromptQuestion")), action);
    }

    /**
     * Prints out interface screen with message showing that template is being updated.
     */
    public void showUpdatingTemplateMessage(){
        interfaceScreen(TEMPLATE_MESSAGES.get("updatingTemplate"));
    }

    /**
     * Prints out message showing that update is completed. Universal message as it does not specify what update
     * has been completed.
     */
    public void showUpdateCompletedMessage(){
        System.out.println(MESSAGES.get("updateCompleted"));
    }

    /**
     * Prints out message showing that the program is returning to the edit prompts menu with delay.
     */
    public void showReturnToEditPromptsMenuMessage(){
        interfaceScreen(TEMPLATE_MESSAGES.get("returnToEditPromptsMenu"));
    }

    /**
     * Prints out message showing that the program is returning to the template edit menu with delay.
     */
    public void showReturnToTemplateEditMenuMessage(){
        interfaceScreen(TEMPLATE_MESSAGES.get("returnToTemplateEditMenu"));
    }

    /**
     * Prints out message showing that the program is returning to the Template Menu with delay.
     */
    public void showReturnToTemplateMenuMessage(){
        interfaceScreen(TEMPLATE_MESSAGES.get("returnToTemplateMenu"));
    }

    /**
     * Prints out account menu.
     */
    public void showAccountMenu(){
//        System.out.println(accountMenu);
        System.out.println(MENUS.get("Account"));
    }

    /**
     * Prints out options for editing account.
     */
    public void showEditAccountMenu(){
        System.out.println(MENUS.get("editAccountMenu"));
    }

    /**
     * Prints out message asking for user input when editing account info.
     * @param index Index of the element in the String array that user wants to print out.
     *              0 - new username, 1 - current password, 2 - new password.
     */
    public void showEditAccountPrompts(int index){
        String editAccountInfo = "Please enter your %s.\n";
        String[] editAccountPrompts = {String.format(editAccountInfo, "new username"),
                String.format(editAccountInfo, "current password"),
                String.format(editAccountInfo, "new password")};
        System.out.println(editAccountPrompts[index]);
    }

    /**
     * Prints out message showing that the program is returning to account setting options with delay.
     */
    public void showReturnToAccountSettingsMessage(){
        interfaceScreen(ACCOUNT_MESSAGES.get("returnToAccountSettings"));
    }

    /**
     * Prints out message showing that the program is returning to the Account Menu with delay.
     */
    public void showReturnToAccountMenuMessage(){
        interfaceScreen(ACCOUNT_MESSAGES.get("returnToAccountMenu"));
    }

    /**
     * Prints out Planner Menu.
     */
    public void showPlannerMenu(){
//        System.out.println(plannerMenu);
        System.out.println(MENUS.get("Planner"));
    }

    /**
     * Prints out options for editing planner.
     */
    public void showEditPlannerMenu(){
        System.out.println(MENUS.get("editPlannerMenu"));
    }

    /**
     * Prints out options for editing personal planner.
     */
    public void showEditPersonalPlannerMenu(){
        System.out.println(MENUS.get("editPersonalPlannerMenu"));
    }

    /**
     * Prints out options for editing public planner.
     */
    public void showEditPublicPlannerMenu(){
        System.out.println(MENUS.get("editPublicPlannerMenu"));
    }

    /**
     * Prints out details of all existing personal planners and planners of others that have been made public.
     */
    public void showAllPlanners(){
        System.out.println(plannerController.showAllPlanners());
    }

    /**
     * Prints out details of all existing personal planners.
     */
    public void showAllPersonalPlanners(String retriever){
        ArrayList<String> plannerIDs =  accessController.getPlanners(retriever);
        StringBuilder personalPlanners = new StringBuilder();
        for (String plannerID: plannerIDs){
            personalPlanners.append(plannerController.toString(Integer.parseInt(plannerID)));
            personalPlanners.append("\n");
        }
        System.out.println(personalPlanners);
    }

    /**
     * Prints out details of all existing public planners.
     */
    public void showAllPublicPlanners(){
        plannerController.getPublicPlanners();
    }

    /**
     * Prints out detail view of planner with plannerID.
     * @param plannerID ID of planner to get detail view for.
     */
    public void showDetailViewPlanner(int plannerID){
        System.out.println(plannerController.toString(plannerID));
    }

    /**
     * Prints out message showing the type of the planner with plannerID
     * @param type Type of the planner.
     */
    public void showPlannerType(String type){
        System.out.printf(PLANNER_MESSAGES.get("showPlannerType"), type);
    }

    /**
     * Prints out Planner view options.
     */
    public void showPlannerViewMenu(){
        System.out.println(MENUS.get("plannerViewMenu"));
    }

    /**
     * Prints out Planner creation options.
     */
    public void showPlannerCreateMenu(){
        System.out.println("The following templates are available to you for reference purpose:");
        System.out.println(this.templateManager.detailViewAllTemplates());
        System.out.println(MENUS.get("plannerCreateMenu"));
    }

    /**
     * Prints out message showing that planner of type plannerType has been created successfully.
     * @param plannerType Type of the created planner.
     */
    public void showPlannerCreatedMessage(String plannerType){
        System.out.printf(PLANNER_MESSAGES.get("plannerCreated"), plannerType);
    }

    /**
     * Prints out message asking for the time slot in the planner that the user would like to edit the agenda for.
     */
    public void showPlannerEditTimeQuestion(){
        System.out.println(PLANNER_MESSAGES.get("plannerEditTimeQuestion"));
    }

    /**
     * Prints out message asking user to enter the new agenda for the planner.
     */
    public void showPlannerEditAgendaQuestion(){
        System.out.println(PLANNER_MESSAGES.get("plannerEditAgendaQuestion"));
    }

    /**
     * Prints out message asking for the index in the planner that the user would like to edit the agenda for.
     */
    public void showPlannerEditIndexQuestion(){
        System.out.println(PLANNER_MESSAGES.get("plannerEditIndexQuestion"));
    }

    /**
     * Prints out message showing that the index entered for planner is out of range, and asking user to try again.
     */
    public void showPlannerReEnterIndexMessage(){
        System.out.println(PLANNER_MESSAGES.get("plannerReEnterIndex"));
    }

    /**
     * Prints out message showing that the program is returning to the planner edit menu with delay.
     */
    public void showReturnToPlannerEditMenuMessage(){
        interfaceScreen(PLANNER_MESSAGES.get("returnToPlannerEditMenu"));
    }

    /**
     * Prints out message showing that the program is returning to the Planner Menu with delay.
     */
    public void showReturnToPlannerMenuMessage(){
        interfaceScreen(PLANNER_MESSAGES.get("returnToPlannerMenu"));
    }
}