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
    public PlannerManager plannerManager;

    public PlannerController(){
        this.plannerManager = new PlannerManager();
        this.plannerManager.NewDailyPlanner("Test ABC");


        PlannerManager dailyplannerManager = new PlannerManager();
    }

    /** Pass on request to get a string representation of a planner
     *
     * @return a string of the planner tasks
     */
    public String toString(){
        return plannerManager.toString();
    }


    /** Pass on request to get a string representation of the daily planner remain tasks
     *
     * @return a string of the daily planner remain tasks.
     */
    public String DailyPlannerRemainTasks(){
        return plannerManager.DailyPlannerRemainTasks();
    }

    /** Pass on request to edit planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly requested to change based on current planner
     */
    public boolean Edit(int i, String agenda){
        return plannerManager.Edit(i, agenda);
    }

    /** Pass on request to edit daily planner.
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly request to change.
     */
    public boolean Edit(String time, String newAgenda){
        return plannerManager.Edit(time, newAgenda);
    }



    /** Pass on request to change their own planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly requested to change. (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(String status){
        return plannerManager.ChangePrivacyStatus(status);
    }
}
