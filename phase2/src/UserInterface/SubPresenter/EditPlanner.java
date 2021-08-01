package UserInterface.SubPresenter;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.Text;

public class EditPlanner extends GeneralPresenter {
    private final IController controller;


    public EditPlanner(IController controller) {
        this.controller = controller;
    }


    public EditPlanner(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
    }


    @Override
    public void run() {
        this.plannerTypeIdentifier();
    }


    @Override
    public void runMenu() {
        this.run();
    }


    private void plannerTypeIdentifier() {
        if (controller.getPlannerType().equals("daily")) {
            this.editDailyPlanner();
        } else if (controller.getPlannerType().equals("project")) {
            this.editProjectPlanner();
        }
    }


    private void editProjectPlanner() {
        Text index = new Text(null, "Please enter the index of the agenda you wanna edit", false);
        Text agenda = new Text(index, "Please enter the agenda you manna set", true);
        index.trigger();
        if (this.controller.editPlanner(index.getText(), agenda.getText())) {
            System.out.println("Successfully edited:");
            System.out.println(controller.viewPlanner());
            this.getParent().runMenu();
        } else if (index.getText().equals("q") || agenda.getText().equals("q")) {
            this.getParent().runMenu();
        } else {
            System.out.println("Invalid time slot, please try again.");
            this.editDailyPlanner();
        }
    }


    private void editDailyPlanner() {
        if (controller.getPlannerType().equals("daily")) {
            Text timeSlot = new Text(null, "Please enter the time slot you want to edit (in form of HH:MM).",
                    false);
            Text agenda = new Text(timeSlot, "Please enter the agenda you wanna add or reset(enter q to go back)",
                    true);
            timeSlot.trigger();
            if (this.controller.editPlanner(timeSlot.getText(), agenda.getText())) {
                System.out.println("Successfully edited:");
                System.out.println(controller.viewPlanner());
                this.getParent().runMenu();
            } else if (timeSlot.getText().equals("q") || agenda.getText().equals("q")) {
                this.getParent().runMenu();
            } else {
                System.out.println("Invalid time slot, please try again.");
                this.editDailyPlanner();
            }
        }
    }
}
