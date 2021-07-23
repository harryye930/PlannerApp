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

    public int createNewDailyPlanner(){
        return plannerManager.NewDailyPlanner("daily planner", "09:00", "18:00");
    }


    public int createNewProjectPlanner(){
        return plannerManager.NewProjectPlanner("project planner");
    }

    public void setPlannerAuthor(int id, String userRetriever){
        plannerManager.setPlannerAuthor(id, userRetriever);
    }

    /** Pass on request to get a string representation of a planner
     *
     * @return a string of the planner tasks
     */
    public String toString(int id){
        return plannerManager.findPlanner(id).toString();
    }


    /** Pass on request to get a string representation of the daily planner remain tasks
     *
     * @return a string of the daily planner remain tasks.
     */
    public String DailyPlannerRemainTasks(int id){
        return plannerManager.DailyPlannerRemainTasks(id);
    }

    /** Pass on request to edit planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly requested to change based on current planner
     */
    public boolean edit(int id, int i, String agenda){
        return plannerManager.Edit(id, i, agenda);
    }

    /** Pass on request to edit daily planner.
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly request to change.
     */
    public boolean edit(int id, String time, String newAgenda){
        return plannerManager.Edit(id, time, newAgenda);
    }


    /** Pass on request to change their own planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly requested to change. (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(int id, String status){
        return plannerManager.ChangePrivacyStatus(id, status);
    }

    public boolean deletePlanner(int id){
        return this.plannerManager.DeletePlanner(id);
    }

    public String showAllPlanners (){
        return plannerManager.showAllPlanners();
    }

    public ArrayList<Integer> getPlannerByAuthor(String author){
        return plannerManager.getPlannersByAuthor(author);
    }

    public ArrayList<Integer> getPublicPlanners(){
        return plannerManager.getPublicPlanners();
    }

    public String getType(int id){
        return plannerManager.plannerType(id);
    }

}
