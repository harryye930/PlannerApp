package UserInterface.SubPresenter.Planner;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.SubPresenter.Planner.PlannerChecker;
import UserInterface.Widgets.*;

public class PlannerPresenter extends GeneralPresenter {
    private final IController controller;
    private GeneralPresenter plannerChecker;

    public PlannerPresenter(IController controller) {
        this.controller = controller;
        this.plannerChecker = new PlannerChecker(controller, this);
    }

    public PlannerPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
        this.plannerChecker = new PlannerChecker(controller, this);
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
        this.controller.checkPlanner(id);
        this.plannerChecker.runMenu();
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
        this.controller.checkPlanner(id);
        this.plannerChecker.runMenu();
    }


    private void viewUserPlanners(Widget parent) {
        System.out.println(controller.viewUserPlanners());
        this.runMenu();
    }

}
