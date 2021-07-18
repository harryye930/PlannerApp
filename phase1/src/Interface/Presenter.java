package Interface;

public class Presenter {

    /**
     * Prints out message for welcome screen at the start of the program.
     */
    public void showWelcomeScreen(){
        String welcomeScreen = "Welcome to your planner manager!";
        System.out.println(welcomeScreen);
    }

    /**
     * Prints out message for closing screen at the end of the program.
     */
    public void showClosingScreen(){
        String closingScreen = "Thank you for using the program, have a productive day!";
        System.out.println(closingScreen);
    }

    /**
     * Prints out log in options.
     */
    public void showLoginMenu(){
        String loginMenu = "=========================================================================\n" +
                "Select one of the options below to get started! " +
                "Please enter the letter associated with that option.\n"+
                "A. Create a new account\n" +
                "B. Log in (have an existing account)\n" +
                "C. Continue as guest (note that any changes made won't be saved)\n" +
                "\nTo exit the program, enter \"q\"\n" +
                "=========================================================================";
        System.out.println(loginMenu);
    }

    /**
     * Prints out Main Menu after user is logged in.
     */
    public void showMainMenu(){
        String mainMenu = "=========================================================================\n" +
                "This is the Main Menu. " +
                "Please choose one of the options below and enter the letter associated with it.\n" +
                "A. Planners options\n" +
                "B. Templates options\n" +
                "C. Account options\n" +
                "D. Log out\n" +
                "\nTo exit the program, enter \"q\"\n" +
                "=========================================================================";
        System.out.println(mainMenu);
    }

    /**
     * Prints out message showing that the program is saving information.
     */
    public void showSavingInfoScreen(){
        String savingInfoScreen = "Saving information...";
        System.out.println(savingInfoScreen);
    }

    /**
     * Prints out message showing that information has been successfully saved in the program.
     */
    public void showSavingSuccessfulScreen(){
        String savingSuccessfulScreen = "Saving successful!";
        System.out.println(savingSuccessfulScreen);
    }

    /**
     * Prints out message showing that the user input is invalid (does not match with any of the options provided).
     */
    public void showInvalidInputScreen(){
        String invalidInputScreen = "Invalid input, please try again.";
        System.out.println(invalidInputScreen);
    }

    /**
     * Prints out message showing that the system is returning back to the main menu.
     */
    public void showReturnToMainScreen(){
        String returnToMainScreen = "Returning to main menu...";
        System.out.println(returnToMainScreen);
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
     * Prints out message showing that a new account has been created with the specified username.
     * @param username Username of the new account.
     */
    public void showAccountCreatedScreen(String username){
        String accountCreatedScreen = "A new account has been created.%nPlease remember your username: %s.%n";
        System.out.printf((accountCreatedScreen), username);
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
     * Prints out message showing that user has successfully logged in.
     */
    public void showLoginSuccessfulScreen(){
        String loginSuccessfulScreen = "Login successful.";
        System.out.println(loginSuccessfulScreen);
    }

    /**
     * Prints out Template Menu.
     */
    public void showTemplateMenu(){
        String templateMenu =
                "=========================================================================\n"+
                        "This is the Template Menu. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. View all templates\n" +
                        "B. Edit an existing template (Admin Only) \n" +
                        "C. Create a new template (Admin Only) \n" +
                        "\nTo return to the MAIN MENU, enter \"m\"\n" +
                        "=========================================================================";
        System.out.println(templateMenu);
    }

    /**
     * Prints out Template view options.
     */
    public void showTemplateViewMenu(){
        String TemplateViewMenu =
                "=========================================================================\n"+
                        "Options for viewing all templates. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Summary view \n" +
                        "B. Detailed view \n" +
                        "C. Exit to Template menu \n" +
                        "=========================================================================\n";
        System.out.println(TemplateViewMenu);
    }

    /**
     * Prints out message asking user for the ID of the template they would like to edit.
     */
    public void showTemplateIDForEditQuestion(){
        String templateIDToEditQuestion = "Which template would you like to edit? Please enter the template ID.";
        System.out.println(templateIDToEditQuestion);
    }

    /**
     * Prints out intro message for showing what an existing template with templateID currently looks like.
     * @param templateID ID of the template selected.
     */
    public void showTemplateIntroMessage(int templateID){
        String templateIntroMessage = "You have selected template with ID %d. " +
                "This is what the template currently looks like: %n";
        System.out.printf((templateIntroMessage), templateID);
    }

    /**
     * Prints out options for editing template.
     */
    public void showEditTemplateMenu(){
        String editTemplateMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a template. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit template name \n" +
                        "B. Edit template prompts \n" +
                        "C. Delete template \n" +
                        "D. Return to Template Menu \n" +
                        "=========================================================================\n";
        System.out.println(editTemplateMenu);
    }

    /**
     * Prints out message asking user to enter the desired new name.
     * @param obj Object of which user will give a new name. "obj" can be "template", "prompt", etc.
     */
    public void showEditNewNameQuestion(String obj){
        String editNewNameQuestion = "Please enter the desired new name of the %s.%n";
        System.out.printf((editNewNameQuestion), obj);
    }

    /**
     * Prints out message asking user if they would like to continue to make another edit.
     */
    public void showIfContinueEditQuestion(){
        String ifContinueEditQuestion = "Would you like to make another edit? Enter \"yes\" or \"no\".\n";
        System.out.println();
    }

    //TODO: add new things that can be deleted
    /**
     * Prints out message asking user to confirm if they want to delete obj (obj can be "template", "account", etc.
     * @param obj Object that user wants to delete.
     */
    public void showConfirmDeleteQuestion(String obj){
        String confirmDeleteQuestion = "Are you sure you want to delete this %s? Enter \"yes\" or \"no\".%n";
        System.out.printf((confirmDeleteQuestion), obj);
    }

    /**
     * Prints out message showing that a feature/a functionality in the program is not yet available.
     */
    public void showFeatureUnavailableScreen(){
        String featureUnavailableScreen = "Feature is not yet available. Please check back later!";
        System.out.println(featureUnavailableScreen);
    }

    /**
     * Prints out message showing the options for editing a template prompt.
     */
    public void showEditTemplatePromptsMenu(){
        String editTemplatePromptsMenu =
                "=========================================================================\n"+
                        "Here are the options for editing a prompt. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Rename prompt \n" +
                        "B. Add prompt \n" +
                        "C. Delete prompt \n" +
                        "D. Return to Edit Template Menu \n" +
                        "=========================================================================\n";
        System.out.println(editTemplatePromptsMenu);
    }

    /**
     * Prints out intro message for showing current template prompts.
     */
    public void showTemplatePromptsIntroScreen(){
        String templatePromptsIntroScreen = "Here are the current prompts: \n";
        System.out.println(templatePromptsIntroScreen);
    }

    /**
     * Prints out message asking user to enter ID of the prompt they'd like to rename.
     * @param action Action user would like to perform - can be "rename", "add", or "delete"
     */
    public void showIDForEditPromptQuestion(String action){
        String idForEditPromptQuestion = "Please enter the ID of the prompt you'd like to %s.%n";
        System.out.printf((idForEditPromptQuestion), action);
    }

    /**
     * Prints out account menu.
     */
    public void showAccountMenu(){
        String accountMenu =
                "=========================================================================\n"+
                        "This is the Account Menu. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Log out\n" +
                        "B. Edit account info\n" +
                        "\nTo return to the MAIN MENU, enter \"m\"\n" +
                        "=========================================================================";
        System.out.println(accountMenu);
    }

    /**
     * Prints out options for editing account.
     */
    public void showEditAccountMenu(){
        String editAccountMenu =
                "=========================================================================\n"+
                        "Here are the options for editing your account. " +
                        "Please choose one of the options below and enter the letter associated with it.\n" +
                        "A. Edit username \n" +
                        "B. Edit password \n" +
                        "C. Return to Account Menu \n" +
                        "=========================================================================\n";
        System.out.println(editAccountMenu);
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


}