package UserInterface.SubPresenter;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.MultiOptions;
import UserInterface.Widgets.Option;
import UserInterface.Widgets.Text;
import UserInterface.Widgets.Widget;

public class PlannerChecker extends GeneralPresenter {
    private final String separator = "=============================";
    private final IController controller;

    private final GeneralPresenter editPlanner;

    public PlannerChecker(IController controller) {
        this.controller = controller;
        this.editPlanner = new EditPlanner(controller, this);
    }

    public PlannerChecker(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.editPlanner = new EditPlanner(controller, this);
        this.setParent(parent);
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
        String prom;
        if (this.controller.getPlannerStatus()) {
            prom = "Make planner private";
        } else {
            prom = "Make planner public";
        }
        MultiOptions plannerOps = new MultiOptions(null, "Please select the option you want to do:");

        Option deletePlanner = new Option(plannerOps, "Delete planner");
        Option editPlanner = new Option(plannerOps, "Edit planner");
        Option changeStatus = new Option(plannerOps, prom); //Change privacy status.
        Option back = new Option(plannerOps, "Go back");

        plannerOps.trigger();
        this.plannerOptionsIdentify(plannerOps);
    }

    private void plannerOptionsIdentify(MultiOptions plannerOps) {
        if (plannerOps.getChosenOp() == 'A') {
            this.deletePlanner(plannerOps);
        } else if(plannerOps.getChosenOp() == 'B') {
            this.editPlanner.run();
        } else if (plannerOps.getChosenOp() == 'C') {
            this.changeStatus(plannerOps);
        } else if (plannerOps.getChosenOp() == 'D') {
            this.getParent().runMenu();
        }
    }

    private void changeStatus(Widget parent) {
        this.controller.setPlannerStatus();
        System.out.println("The planner status has now been set to" + controller.getPlannerStatus());
        parent.trigger();
    }

    private void deletePlanner(MultiOptions plannerOps) {
        Text idToDelete = new Text(plannerOps, "Please enter the planner id you want to delete", true);
        idToDelete.trigger();

        if (this.controller.deletePlanner(idToDelete.getText())) {
            System.out.println("Planner deleted.");
            this.returnToMenu();
        } else {
            System.out.println("Deletion failed, please check your planner ID");
            this.deletePlanner(plannerOps);
        }
    }

}
