package UserInterface.SubPresenter.Planner;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.MultiOptions;
import UserInterface.Widgets.Option;
import UserInterface.Widgets.Text;
import UserInterface.Widgets.Widget;

public class RegularPlannerPresenter extends GeneralPresenter {
    private final IController controller;
    private final GeneralPresenter editPlanner;

    public RegularPlannerPresenter(IController controller) {
        this.controller = controller;
        this.editPlanner = new EditPlanner(controller, this);
    }

    public RegularPlannerPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
        this.editPlanner = new EditPlanner(controller, this);
    }

    @Override
    public void run() {this.plannerOptions();}

    @Override
    public void runMenu() {this.plannerOptions();}

    private void plannerOptions() {
        String prom;
        if (this.controller.getPlannerStatus()) {
            prom = "Make planner public";
        } else {
            prom = "Make planner private";
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
        String status = "";
        this.controller.setPlannerStatus();

        if (controller.getPlannerStatus()) {
            status = "private";
        } else if (!controller.getPlannerStatus()) {
            status = "public";
        }
        System.out.println("The planner status has now been set to " + status);
        this.plannerOptions();
    }

    private void deletePlanner(MultiOptions plannerOps) {
        Text idToDelete = new Text(plannerOps, "Please enter the planner id you want to delete", true);
        idToDelete.trigger();

        if (this.controller.deletePlanner(idToDelete.getText())) {
            System.out.println("Planner deleted.");
            this.getParent().runMenu();
        } else {
            System.out.println("Deletion failed, please check your planner ID");
            this.deletePlanner(plannerOps);
        }
    }
}
