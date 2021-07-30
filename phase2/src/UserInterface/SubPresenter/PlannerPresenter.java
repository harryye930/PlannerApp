package UserInterface.SubPresenter;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.Widgets.*;

public class PlannerPresenter extends GeneralPresenter {
    private final IController controller;
    private String plannerId;
    private GeneralPresenter plannerChecker;

    public PlannerPresenter(IController controller) {
        this.controller = controller;
        this.plannerChecker = new EditPlanner(controller);
    }

    public PlannerPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
        this.plannerChecker = new EditPlanner(controller);
    }

    @Override
    public void run() {
        this.plannerOptions();
    }

    @Override
    public void runMenu() {this.plannerOptions();}

    private void plannerOptions() {
        MultiOptions regularOptions = new MultiOptions(null, "Please select the things you wanna do");

        Option viewUserPlanners = new Option(regularOptions, "View all my planners"); //A
        Option checkPlanner = new Option(regularOptions, "Check planner by ID");  //B
        Option createPlanner = new Option(regularOptions, "Create new planner by template"); //C
        Option logout = new Option(regularOptions, "Logout to menu"); // D

        regularOptions.trigger();
        this.regularOptionsIdentify(regularOptions);
    }

    private void plannerOptions(String id, Widget parent) {
        String prom;
        if (this.controller.getPlannerStatus(id)) {
            prom = "Make planner private";
        } else {
            prom = "Make planner public";
        }
        MultiOptions plannerOps = new MultiOptions(parent, "Please select the option you want to do:");

        Option deletePlanner = new Option(plannerOps, "Delete planner");
        Option editPlanner = new Option(plannerOps, "Edit planner");
        //TODO:Option changeStatus = new Option(plannerOps, prom); //Change privacy status.
        Option back = new Option(plannerOps, "Go back");

        plannerOps.trigger();
        this.plannerOptionsIdentify(plannerOps);
    }

    private void regularOptionsIdentify(MultiOptions parent) {
        if (parent.getChosenOp() == 'A') {
            this.viewUserPlanners(parent);
        } else if (parent.getChosenOp() == 'B') {
            this.plannerChecker.run();
        } else if (parent.getChosenOp() == 'C') {
            this.createPlannerMulti(parent);
        } else if (parent.getChosenOp() == 'D') {
            this.getParent().run();
        }
    }

    private void createPlannerMulti(MultiOptions parent) {
        MultiOptions createPlanner = new MultiOptions(parent,
                "Please select the type of planner you manna create.");
        Option createDailyPlanner = new Option(createPlanner, "create daily planner");
        Option createProjectPlanner = new Option(createPlanner, "create project planner");
        Option goBack = new Option(createPlanner, "Go back");

        createPlanner.trigger();

        if (createPlanner.getChosenOp() == 'A') {
            this.createDailyPlanner(parent);
        } else if (createPlanner.getChosenOp() == 'B') {
            this.createProjectPlanner(parent);
        } else if (createPlanner.getChosenOp() == 'C') {
            parent.trigger();
        }
    }

    private void createProjectPlanner(MultiOptions parent) {
        String id = controller.createProjectPlanner();
        System.out.println("Project planner created.");
        System.out.println("============================");
        System.out.println(this.controller.viewPlanner(id));
        this.plannerId = id;
        this.plannerOptions(id, parent);
    }

    private void createDailyPlanner(MultiOptions parent) {
        //System.out.println("Please check the available templates that you can refer to:");
        //System.out.println(this.controller.viewTemplates());

        //Text temId = new Text(parent, "Template ID:", true);
        //parent.unhold();

        //if (!this.controller.checkTemplate(temId.getText())) {
        //    System.out.println("Invalid ID, please try again");
        //    temId.rollBack();
        //} else {
        //    id = controller.createDailyPlanner();
        //    System.out.println("Daily planner created.");
        //    System.out.println(this.controller.viewPlanner(id));
        //}
        //this.plannerOptions(id, parent);
        String id = controller.createDailyPlanner();
        System.out.println("Daily planner created.");
        System.out.println("============================");
        System.out.println(this.controller.viewPlanner(id));
        this.plannerId = id;
        this.plannerOptions(id, parent);
    }

    private void plannerOptionsIdentify(MultiOptions plannerOps) {
        if (plannerOps.getChosenOp() == 'A') {
            this.deletePlanner(plannerOps);
        } else if(plannerOps.getChosenOp() == 'B') {
            this.plannerChecker.run();
        } else if (plannerOps.getChosenOp() == 'C') {
            this.changeStatus(plannerOps);
        } else if (plannerOps.getChosenOp() == 'D') {
            this.plannerOptions();
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

        if (this.controller.deletePlanner(plannerId)) {
            System.out.println("Planner deleted.");
            plannerOps.rollBack();
        } else {
            System.out.println("Deletion failed, please check your planner ID");
            plannerOps.unhold();
        }
    }

    private void checkPlanner(Widget parent) {
        Text plannerId = new Text(parent, "Please enter the planner id you want to operate on:", true);
        if (plannerId.getText().equals("q")) {
            parent.trigger();
        }
        if (controller.checkPlanner(plannerId.getText())) {
            System.out.println(controller.viewPlanner(plannerId.getText()));
            this.plannerId = plannerId.getText();
            this.plannerOptions(plannerId.getText(), parent);
        } else {
            System.out.println("Invalid Id, please try again");
            this.checkPlanner(parent);
        }
    }

    private void viewUserPlanners(Widget parent) {
        System.out.println(controller.viewUserPlanners());
        Text plannerId = new Text(parent, "Please enter the planner id you want to operate on:", true);
        if (controller.checkPlanner(plannerId.getText())) {
            System.out.println(controller.viewPlanner(plannerId.getText()));
            this.plannerId = plannerId.getText();
            this.plannerOptions(plannerId.getText(), parent);
        } else {
            System.out.println("Invalid Id, please try again");
            this.viewUserPlanners(parent);
        }
    }

}
