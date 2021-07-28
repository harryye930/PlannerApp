package Controller;

import Interface.Presenter;
import UseCase.AccountManager;
import UseCase.PlannerManager;
import UseCase.TemplateManager;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.*;

/**
 * UserActionController manages user actions.
 * Allocates specific tasks to controllers that should be responsible for them.
 */
public class UserActionController {

    AccessController accessController;
    TemplateController templateController;
    PlannerController plannerController;

    Scanner scanner;
    Presenter presenter;

    private final String QUIT = "Q";
    private final String MAIN_MENU = "M";
    private String currentRetriever;

    public UserActionController() {

        accessController = new AccessController();
        templateController = new TemplateController();
        plannerController = new PlannerController();

        presenter = new Presenter(templateController, plannerController, accessController);

        scanner = new Scanner(System.in);
    }


    /**
     * Run the program using the following method. This method is all that has to be called in the main after
     * creating an instance of this UserActionController in the main.
     */
    public void runProgram() {
        accessController.load();
        templateController.load();
        plannerController.load();
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
     * Enables a successfully logged in user or a guest account user to use the features of the program.
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
                while (templateController.templateOptions()) {
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
                presenter.showSavingInfoScreen();
                presenter.showSavingSuccessfulScreen();

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
        accessController.save();
        templateController.save();
        plannerController.save();
    }

    /**
     * Takes in user input and checks if the entered user option is valid. The whitespace before and after the entered
     * user input are removed.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    public String validInput(String[] valid_options) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toUpperCase(Locale.ROOT);
        List<String> options = Arrays.asList(valid_options);
        while (!options.contains(input.trim())) {
            presenter.showInvalidInputScreen();
            input = scanner.nextLine().toUpperCase(Locale.ROOT);
        }
        return input;
    }

    /**
     * Returns true if a user is an admin; otherwise it returns false and prints out a message to the user.
     */
    public boolean isAdmin() {
        if (accessController.isAdmin(currentRetriever).equals("admin")) {
            return true;
        } else {
            return false;
        }
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
        this.saveProgram();
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
            case "A": //
                this.saveProgram();
                return false;
            case "B": // edit account info
                while (accountSetting(retriever)) {
                    presenter.showReturnToAccountSettingsMessage();
                }
                break;
            case MAIN_MENU:
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
                if (accessController.changePassword(retriever, oldPassword, newPassword)) {
                    System.out.println("Reset success.");
                } else {
                    System.out.println("The password you entered is incorrect, please try again or enter q to bo back");
                }
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
                while (plannerEditOptions()) {
                    presenter.showReturnToPlannerEditMenuMessage();
                }
                plannerController.save();
                break;
            case "C": // create a new planner
                plannerCreateOptions();
                plannerController.save();
                break;
            case MAIN_MENU: // exit
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
        return this.plannerController.plannerEditOptions(this.presenter, currentRetriever);
    }


    /**
     * Planner options helper method. Allows different options for planner creation.
     */
    private void plannerCreateOptions() {
        String plannerId = this.plannerController.plannerCreateOptions(this.currentRetriever, this.presenter);
        if (plannerId != null) {
            this.accessController.setPlanner(this.currentRetriever, plannerId);
        }
        this.saveProgram();
    }

    /**
     * Planner options helper method. Allows different options for planner viewing.
     */
    private void plannerViewOptions(String userId) {
        String userInput;
        String[] viewOptions = {"A", "B", "C"};
        this.saveProgram();

        presenter.showPlannerViewMenu(); // display planner view options: personal, public, exit to planner menu
        userInput = validInput(viewOptions);
        switch (userInput) {
            case "A": // personal planners
                ArrayList<String> arr = accessController.getPlanners(currentRetriever);
                if (arr == null || arr.size() == 0) {
                    System.out.println("No personal planners available yet.");
                } else {
                    for (String plannerId : arr) {
                        System.out.println(plannerController.toString(Integer.parseInt(plannerId)));
                    }
                }
                break;
            case "B": // public planners created by others
                ArrayList<Integer> ar = plannerController.getPublicPlanners();
                if (ar.size() == 0) {
                    System.out.println("No public planners available yet.");
                } else {
                    for (int plannerId : ar) {
                        System.out.println(plannerController.toString(plannerId));
                    }
                }
                break;
            case "C": // exit to planner menu
                break;  // this is all this is required here for case C - don't worry!
        }
    }
}
