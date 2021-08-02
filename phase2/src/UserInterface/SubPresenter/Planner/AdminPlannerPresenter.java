package UserInterface.SubPresenter.Planner;

import Interface.IController;
import UserInterface.GeneralPresenter;
import UserInterface.Widgets.MultiOptions;
import UserInterface.Widgets.Option;
import UserInterface.Widgets.Text;

public class AdminPlannerPresenter extends GeneralPresenter{
    private final IController controller;
    private String plannerId;

    public AdminPlannerPresenter(IController controller) {
        this.controller = controller;
    }


    public AdminPlannerPresenter(IController controller, GeneralPresenter gp) {
        this.controller = controller;
        this.setParent(gp);
    }

    @Override
    public void run(){changePlanner();}

    @Override
    public void runMenu(){changePlanner();}


    private void changePlanner(){
        String prom;
        if (this.controller.getPlannerStatus()) {
            prom = "Make planner public";
        } else {
            prom = "Make planner private";
        }
        MultiOptions plannerOps = new MultiOptions(null, "Please select the option you want to do:");
        Option changeStat = new Option(plannerOps, "Change the privacy status of the planner. " + "(" + prom + ")");
        Option deletePlanner = new Option(plannerOps, "Delete a planner. ");
        Option goBack = new Option(plannerOps, "Go back to previous menu. ");
        plannerOps.trigger();

        plannerOptionIdentify(plannerOps);
    }

    private void plannerOptionIdentify(MultiOptions mo){
        if (mo.getChosenOp() == 'A'){
            controller.setPlannerStatus();
        } else if (mo.getChosenOp() == 'B'){
            controller.deletePlanner(plannerId);
        } else if (mo.getChosenOp() == 'C'){
            getParent().runMenu();
        }
    }
}
