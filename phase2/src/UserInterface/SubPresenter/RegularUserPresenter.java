package UserInterface.SubPresenter;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.SubPresenter.Planner.PlannerPresenter;
import UserInterface.Widgets.*;

public class RegularUserPresenter extends GeneralPresenter {
    private final IController controller;
    private GeneralPresenter plannerPresenter;
    private GeneralPresenter templatePresenter;
    private GeneralPresenter regularAccSetting;

    //TODO: Add template presenter.
    public RegularUserPresenter(IController controller) {
        this.controller = controller;
        this.plannerPresenter = new PlannerPresenter(controller, this);
        this.regularAccSetting = new RegularAccSetting(controller, this);
    }

    public RegularUserPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
        this.plannerPresenter = new PlannerPresenter(controller, this);
        this.regularAccSetting = new RegularAccSetting(controller, this);
    }

    @Override
    public void run() {
        this.regularOptions();
    }

    @Override
    public void runMenu() {this.regularOptions();}

    private void regularOptions() {
        MultiOptions regularOptions = new MultiOptions(null, "Please select the things you wanna do");

        Option viewUserPlanners = new Option(regularOptions, "Planner option"); //A
        Option checkPlanner = new Option(regularOptions, "Template option");  //B
        Option createPlanner = new Option(regularOptions, "Account setting."); //C
        Option logout = new Option(regularOptions, "Logout to menu"); // D

        regularOptions.trigger();
        this.regularOptionsIdentify(regularOptions);
    }

    private void regularOptionsIdentify(MultiOptions parent) {
        if (parent.getChosenOp() == 'A') {
            this.plannerPresenter.run();
        } else if (parent.getChosenOp() == 'B') {
            this.templatePresenter.run();
        } else if (parent.getChosenOp() == 'C') {
            this.regularAccSetting.run();
        } else if (parent.getChosenOp() == 'D') {
            this.getParent().run();
        }
    }
}
