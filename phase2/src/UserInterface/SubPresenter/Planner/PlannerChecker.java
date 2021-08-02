package UserInterface.SubPresenter.Planner;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.MultiOptions;
import UserInterface.Widgets.Option;
import UserInterface.Widgets.Text;
import UserInterface.Widgets.Widget;

public class PlannerChecker extends GeneralPresenter {
    private final String separator = "=============================";
    private final IController controller;
    private final AdminPlannerPresenter adminPlannerPresenter;
    private final RegularPlannerPresenter regularPlannerPresenter;

    public PlannerChecker(IController controller) {
        this.controller = controller;
        this.adminPlannerPresenter = new AdminPlannerPresenter(controller, this.getParent());
        this.regularPlannerPresenter = new RegularPlannerPresenter(controller, this.getParent());
    }

    public PlannerChecker(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
        this.adminPlannerPresenter = new AdminPlannerPresenter(controller, this.getParent());
        this.regularPlannerPresenter = new RegularPlannerPresenter(controller, this.getParent());
    }

    @Override
    public void run() {
        this.checkPlanner();
    }

    @Override
    public void runMenu() {
        this.plannerOptions();
    }


    private void checkPlanner() {
        String plannerCheckerPrompt = "Please enter the planner id you want to operate on:(enter q to quit)";
        Text plannerId = new Text(null, plannerCheckerPrompt, true);
        plannerId.trigger();
        String QUIT = "q";
        if (plannerId.getText().equals(QUIT)) {
            this.getParent().runMenu();
        }
        if (controller.checkPlanner(plannerId.getText())) {
            System.out.println(controller.viewPlanner(plannerId.getText()));
            this.plannerOptions();
        } else {
            System.out.println("Invalid Id, please try again");
            this.checkPlanner();
        }
    }

    private void plannerOptions() {
        if (this.controller.accountRole().equals("admin")) {
            this.adminPlannerPresenter.run();
        } else {
            this.regularPlannerPresenter.run();
        }
    }


}
