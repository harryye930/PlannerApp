package Controller;

import Gateway.PlannerGateway;
import UseCase.PlannerManager;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *  the Planner controller.
 */
public class PlannerController {
    private PlannerManager plannerManager;
    private PlannerGateway accGateway;

    /**
     * Initialize the PlannerController. Create a new PlannerManager.
     */
    public PlannerController(){
        this.plannerManager = new PlannerManager();
        this.accGateway = new PlannerGateway(plannerManager);
    }


    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.accGateway.save();
    }


    /**
     * Load in the data from database to AccountManager.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean load() {
        return this.accGateway.load();
    }


    /**
     * Create a new DailyPlanner with default name "daily planner", start time at 9 am, and endtime at 9 pm.
     * @return an integer representing the id of the planner
     */
    public int createNewDailyPlanner(String startTime, String endTime, String interval){
        return plannerManager.newDailyPlanner("daily planner", startTime, endTime, interval);
    }


    /**
     * Create a new Project Planner with default name "project planner".
     * @return the integer id of the planner
     */
    public int createNewProjectPlanner(){
        return plannerManager.newProjectPlanner("project planner");
    }


    /**
     * Set the author of the planner (a user)
     * @param id the id of the planner
     * @param userRetriever The userId of the user
     */
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
        return plannerManager.dailyPlannerRemainTasks(id);
    }


    /** Pass on request to edit planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly requested to change based on current planner
     */
    public boolean edit(int id, int i, String agenda){
        return plannerManager.edit(id, i, agenda);
    }


    /** Pass on request to edit daily planner.
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly request to change.
     */
    public boolean edit(int id, String time, String newAgenda){
        return plannerManager.edit(id, time, newAgenda);
    }


    /** Pass on request to change their own planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly requested to change. (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(int id, String status){
        return plannerManager.changePrivacyStatus(id, status);
    }


    /**
     * delete the planner corresponding to the given id.
     * @param id the integer id of the planner
     * @return true if successfully deleted, false if otherwise.
     */
    public boolean deletePlanner(int id){
        return this.plannerManager.deletePlanner(id);
    }


    /**
     * print all planners to the screen.
     * @return String representation of all planners.
     */
    public String showAllPlanners (){
        return plannerManager.showAllPlanners();
    }


    /**
     * Show all the planners id of one author.
     * @param author the userId of the author.
     * @return the ArrayList of integer id of planners.
     */
    public ArrayList<Integer> getPlannerByAuthor(String author){
        return plannerManager.getPlannersByAuthor(author);
    }


    /**
     * return an ArrayList of all integer id of all planners made public by all authors.
     * @return the ArrayList of all public planner's id
     */
    public ArrayList<Integer> getPublicPlanners(){
        return plannerManager.getPublicPlanners();
    }


    /**
     * get the type of the planner. could be "daily" or "project" planner.
     * @param id the id of the planner
     * @return the String representation of the type of planner.
     */
    public String getType(int id){
        return plannerManager.plannerType(id);
    }


    /**
     * Get the number of agendas of a planner corresponding to given integer id.
     * @param id the integer id of the planner
     * @return the number of agendas of the planner
     */
    public int getNumAgendas(int id) {
        return plannerManager.getNumAgendas(id);
    }


    /**
     * Return the privacy status of the planner corresponding to the given id.
     * @param id the integer id of the planner
     * @return the String "private" or "public".
     */
    public String  getPrivacyStatus(int id) {
        return this.plannerManager.getPrivacyStatus(id);
    }
}
