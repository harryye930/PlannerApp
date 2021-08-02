package UserInterface.SubPresenter;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.MultiOptions;
import UserInterface.Widgets.Option;

public class RegularTemplatePresenter extends GeneralPresenter{
    private final IController controller;

    public RegularTemplatePresenter(IController controller) {
        this.controller = controller;
    }

    public RegularTemplatePresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
    }

    private void menu() {
        MultiOptions menu = new MultiOptions(null, "Please select the option:");

        Option viewTemplates = new Option(menu, "View Templates");
        Option goBack = new Option(menu, "Go back");

        menu.trigger();
        this.menuOptionIdentifier(menu);
    }

    private void menuOptionIdentifier(MultiOptions menu) {
        if (menu.getChosenOp() == 'A') {
            this.viewTemplates();
        } else if (menu.getChosenOp() == 'B') {
            this.getParent().runMenu();
        }
    }

    private void viewTemplates() {
        this.controller.viewTemplates();
    }
}
