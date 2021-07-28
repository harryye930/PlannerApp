package Controller;

import Interface.Presenter;
import UseCase.AccountManager;
import UseCase.PlannerManager;
import UseCase.TemplateManager;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserInputController {

    AccessController accountManager;
    PlannerController plannerManager;
    TemplateManager templateManager;

    Presenter presenter;
    Scanner scanner;

    public UserInputController(){
        accountManager = new AccessController();
        plannerManager = new PlannerController();
        templateManager = new TemplateManager();
        presenter = new Presenter(templateManager, plannerManager, accountManager);
        scanner = new Scanner(System.in);
    }

    /**
     * Takes in user input and checks if the entered user option is valid. The whitespace before and after the entered
     * user input are removed.
     * @param valid_options are valid options presented to the user by the program to choose from.
     * @return the valid option user has entered.
     */
    public String validInput(String[] valid_options) {
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
    public boolean isAdmin() {
        // TODO: below is commented for testing purpose with guest account - uncomment it when done
//        if (currentRetriever.equals("guest") || !accessController.isAdmin(currentRetriever).equals("admin")) {
//            presenter.showCheckAccountPermMessage(); // show message saying "checking account type and permissions"
//            presenter.showRequiresAdminMessage(); // show message saying "feature requires an admin account"
//            return false;
//        }
        return true;
    }
}
