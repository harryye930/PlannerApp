package UserInterface.SubPresenter;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.Widgets.*;

public class AdminPresenter extends GeneralPresenter {
    private final IController controller;
    private String plannerId;
    private GeneralPresenter prevInterface;

    public AdminPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.prevInterface = parent;
    }

    public void run() {
        this.adminOptions();
    }

    private void adminOptions() {
        MultiOptions adminOptions = new MultiOptions(null, "Please select the things you wanna do");

        Option editTemplate = new Option(adminOptions, "Edit template.");
        Option logOut = new Option(adminOptions, "Logout to main menu.");

        adminOptions.trigger();

        this.adminOptionsIdentify(adminOptions);
    }

    private void adminOptionsIdentify(MultiOptions parent) {
        if (parent.getChosenOp() == 'A') {
            this.editTemplate(parent);
        } else if (parent.getChosenOp() == 'B') {
            this.prevInterface.run();
        }
    }

    private void editTemplate(Widget parent) {

    }
}
