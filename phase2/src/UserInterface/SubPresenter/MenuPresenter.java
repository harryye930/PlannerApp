package UserInterface.SubPresenter;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.Widgets.*;

public class MenuPresenter extends GeneralPresenter {
    private final IController controller;
    private GeneralPresenter adminPresenter;
    private GeneralPresenter regularPresenter;

    public MenuPresenter(IController controller) {
        this.controller = controller;
        this.adminPresenter = new AdminPresenter(controller, this);
        this.regularPresenter = new RegularUserPresenter(controller, this);
    }


    @Override
    public void run() {
        this.menuStage();
    }


    @Override
    public void runMenu() { this.menuStage(); }


    private void menuStage() {
        MultiOptions menu = new MultiOptions();
        menu.setPrompt("Welcome! Please choose the option:");

        Option logIn = new Option(menu, "Login"); //"A: Login"
        Option createAccount = new Option(menu, "Create New account:"); // "B: "
        Option terminate = new Option(menu, "Save and exit.");

        menu.trigger();
        this.menuOptionIdentify(menu);
    }

    private void menuOptionIdentify(MultiOptions parent) {
        if (parent.getChosenOp() == 'A') {
            this.logIn(parent);
        } else if (parent.getChosenOp() == 'B') {
            this.createAccount(parent);
        } else if (parent.getChosenOp() == 'C') {
            System.out.println("Saving the program:");
            this.controller.saveProgram();
            System.out.println("program saved.");
        }
    }

    private void createAccount(Widget parent) {
        Text email = new Text(parent, "Please enter your email", false);
        Text userName = new Text(email, "Please enter your user name", false);
        Text password = new Text(userName, "Please enter your password", true);

        email.trigger();

        System.out.println("Register success");
        System.out.println("Please remember your ID (you can also login by email)");
        System.out.println(this.controller.createAccount(email.getText(), userName.getText(), password.getText()));
        // Automatically login
        this.controller.logIn(email.getText(), password.getText());
        System.out.println("You are automatically logged in.");
        this.accountRoleIdentifier();
    }

    private void accountRoleIdentifier() {
        if (this.controller.accountRole().equals("regular")) {
            this.regularPresenter.run();
        } else if (this.controller.accountRole().equals("admin")) {
            this.adminPresenter.run();
        }
    }

    private void logIn(Widget parent) {
        Text loginId = new Text(parent, "Please Enter your ID:", false);
        Text loginPassword = new Text(loginId, "Please enter your password", true);

        loginId.trigger();

        if (this.controller.logIn(loginId.getText(), loginPassword.getText())) {
            System.out.println("Login success!");
            this.accountRoleIdentifier();
        } else {
            System.out.println("Invalid ID or Password, please try again.");
            //loginId.rollBack();
            this.menuStage();
        }
    }
}
