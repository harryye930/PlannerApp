package Controller;

import Entity.DailyPlanner;
import Entity.ProjectPlanner;
import UseCase.PlannerManager;
import Entity.Planner;

import java.util.ArrayList;

/**
 *  the Planner controller.
 */
public class PlannerController {
    private PlannerManager plannerManager;


    public PlannerController(){
        this.plannerManager = new PlannerManager();


    }

    public String createNewDailyPlanner(){
        String DailyPlannerID = plannerManager.NewDailyPlanner("daily planner", "09:00", "18:00");
        return this.plannerManager.toString(DailyPlannerID);
    }


    public String createNewProjectPlanner(){
        plannerManager.NewProjectPlanner("project planner");
        String ProjectPlannerID = plannerManager.NewProjectPlanner("project planner");
        return this.plannerManager.toString(ProjectPlannerID);
    }


    /** Pass on request to get a string representation of a planner
     *
     * @return a string of the planner tasks
     */
    public String toString(String id){
        return plannerManager.findPlanner(id).toString();
    }


    /** Pass on request to get a string representation of the daily planner remain tasks
     *
     * @return a string of the daily planner remain tasks.
     */
    public String DailyPlannerRemainTasks(String id){
        return plannerManager.DailyPlannerRemainTasks(id);
    }

    /** Pass on request to edit planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly requested to change based on current planner
     */
    public boolean edit(String id, int i, String agenda){
        return plannerManager.Edit(id, i, agenda);
    }

    /** Pass on request to edit daily planner.
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly request to change.
     */
    public boolean edit(String id, String time, String newAgenda){
        return plannerManager.Edit(id, time, newAgenda);
    }


    /** Pass on request to change their own planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly requested to change. (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(String id, String status){
        return plannerManager.ChangePrivacyStatus(id, status);
    }

    public boolean deletePlanner(String id){
        return this.plannerManager.DeletePlanner(id);
    }

    public String showAllPlanners (){
        return plannerManager.showAllPlanners();
    }
}
