package UserInterface.SubPresenter;

import UserInterface.GeneralPresenter;
import Interface.IController;
import UserInterface.Widgets.*;
import UserInterface.SubPresenter.Planner.*;

import java.util.ArrayList;

public class AdminPresenter extends GeneralPresenter {
    private final IController controller;
    private String plannerId;
    private final AdminPlannerPresenter adminPlannerPresenter;

    public AdminPresenter(IController controller) {
        this.controller = controller;
        this.adminPlannerPresenter = new AdminPlannerPresenter(controller, this);
    }

    public AdminPresenter(IController controller, GeneralPresenter parent) {
        this.controller = controller;
        this.setParent(parent);
        this.adminPlannerPresenter = new AdminPlannerPresenter(controller, this);
    }

    @Override
    public void run() {
        this.adminOptions();
    }

    @Override
    public void runMenu() {this.adminOptions();}

    private void adminOptions() {
        MultiOptions adminOptions = new MultiOptions(null, "Please select the things you want to do");

        Option editTemplate = new Option(adminOptions, "Edit template.");
        Option allUsers = new Option(adminOptions, "See all user's info. ");
        Option findUser = new Option(adminOptions, "find a user by id. ");
        Option logOut = new Option(adminOptions, "Logout to main menu.");

        adminOptions.trigger();

        this.adminOptionsIdentify(adminOptions);
    }

    private void adminOptionsIdentify(MultiOptions parent) {
        if (parent.getChosenOp() == 'A') {
            this.editTemplate(parent);
        } else if (parent.getChosenOp() == 'B') {
            System.out.print(controller.allUserInfo());
            this.adminOptions();
        }else if (parent.getChosenOp() == 'C'){
            String userId = findUser(parent);
            userOptions(userId);
        }else if (parent.getChosenOp() == 'D') {
            this.getParent().runMenu();
        }
    }

    private void editTemplate(Widget parent) {

    }
    private String findUser(Widget parent){
        Text userId = new Text(parent, "Please enter the user id that you'd like to find: ", true);
        userId.trigger();
        return userId.getText();
    }

    private void userOptions(String userId){
        MultiOptions userOptions = new MultiOptions(null, "What would you like to do to this user? ");

        Option suspendUser = new Option(userOptions, "Suspend the user. ");
        Option seePlanners = new Option(userOptions, "See all creations of the user. ");
        Option goback = new Option(userOptions, "Go back to previous menu. ");
        userOptions.trigger();

        userOptionsIdentify(userOptions, userId);
    }

    private void userOptionsIdentify(MultiOptions parent, String userId){
        if (parent.getChosenOp() == 'A') {
            //TODO
        } else if (parent.getChosenOp() == 'B') {
            this.controller.viewUserPlanners(userId);
            this.userOptions(userId);
        } else if (parent.getChosenOp() == 'C'){
            this.adminOptions();
        }
    }

}
