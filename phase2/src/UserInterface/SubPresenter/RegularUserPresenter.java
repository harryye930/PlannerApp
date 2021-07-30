package UserInterface.SubPresenter;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.Widgets.*;

public class RegularUserPresenter extends GeneralPresenter {
    private final IController controller;
    private GeneralPresenter plannerP;
    private GeneralPresenter templateP;
    private GeneralPresenter userP;

    public RegularUserPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
    }

    public void setPlannerP(GeneralPresenter plannerP) {
        this.plannerP = plannerP;
    }

    public void setUserP(GeneralPresenter userP) {
        this.userP = userP;
    }

    public void setTemplateP(GeneralPresenter templateP) {
        this.templateP = templateP;
    }

    public void run() {
        this.regularOptions();
    }

    private void regularOptions() {
        MultiOptions regularOptions = new MultiOptions(null, "Please select the things you wanna do");

        Option viewUserPlanners = new Option(regularOptions, "Planner option"); //A
        Option checkPlanner = new Option(regularOptions, "Template option");  //B
        Option createPlanner = new Option(regularOptions, "User setting."); //C
        Option logout = new Option(regularOptions, "Logout to menu"); // D

        regularOptions.trigger();
        this.regularOptionsIdentify(regularOptions);
    }

    private void regularOptionsIdentify(MultiOptions parent) {
        if (parent.getChosenOp() == 'A') {
            this.plannerP.run();
        } else if (parent.getChosenOp() == 'B') {
            this.templateP.run();
        } else if (parent.getChosenOp() == 'C') {
            this.userP.run();
        } else if (parent.getChosenOp() == 'D') {
            this.getParent().run();
        }
    }
}
