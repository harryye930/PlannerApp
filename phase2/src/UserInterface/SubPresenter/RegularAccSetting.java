package UserInterface.SubPresenter;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.MultiOptions;
import UserInterface.Widgets.Option;
import UserInterface.Widgets.Text;

public class RegularAccSetting extends GeneralPresenter {
    private final IController controller;

    public RegularAccSetting(IController controller) {
        this.controller = controller;
    }

    public RegularAccSetting(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
    }

    @Override
    public void run() {this.accountMenu();}

    @Override
    public void runMenu() {this.accountMenu();}


    private void accountMenu() {
        System.out.println(this.controller.getAccountInfo());
        System.out.println("======================");

        MultiOptions ops = new MultiOptions(null, "Please select the option you wanna do:");

        Option resetUserName = new Option(ops, "Change user name");
        Option changePassword = new Option(ops, "Change password");
        Option deleteAccount = new Option(ops, "Delete the account");
        Option goBack = new Option(ops, "Go back to the menu");

        ops.trigger();

        this.opsIdentifier(ops);
    }

    private void opsIdentifier(MultiOptions ops) {
        if (ops.getChosenOp() == 'A') {
            this.changeName(ops);
        } else if (ops.getChosenOp() == 'B') {
            this.changePassword(ops);
        } else if (ops.getChosenOp() == 'C') {
            this.deleteAccount(ops);
        } else if (ops.getChosenOp() == 'D') {
            this.returnToMenu();
        }
    }

    private void deleteAccount(MultiOptions ops) {
        Text warning = new Text(ops, "Are you sure to delete the account?(yes/no)", true);
        warning.trigger();
        if (warning.getText().equals("yes")) {
            this.controller.deleteAccount();
            System.out.println("account deleted, application terminated.");
        }
    }

    private void changePassword(MultiOptions ops) {
        Text origin = new Text(ops, "Please enter your original password:", false);
        Text newP = new Text(origin, "Please enter your new password:", true);

        origin.trigger();
        if (this.controller.changePassword(origin.getText(), newP.getText())) {
            System.out.println("Reset successful");
            this.accountMenu();
        }
    }

    private void changeName(MultiOptions ops) {
        Text newName = new Text(ops, "Please enter your new name", true);
        this.controller.changeUserName(newName.getText());
        System.out.println("Rename success");
        this.accountMenu();
    }
}
