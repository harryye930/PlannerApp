package UseCase;
import Controller.PlannerController;
import Entity.DailyPlanner;
import Entity.Planner;
import Entity.ProjectPlanner;

import java.util.ArrayList;
import java.util.HashMap;



public class PlannerManager extends PlannerController {
    private HashMap<Integer, Planner> idToPlanner;


    /**
     * Initialize the PlannerManager.
     */
    public PlannerManager() {
        this.idToPlanner = new HashMap<>();
    }


    /** Create new DailyPlanner -- default interval 60 mins
     *
     * @param plannerName name of the planner
     * @param startTime start time of planner
     * @param endTime end time of planner
     * @return true iff a new DailyPlanner is created
     */
    public int newDailyPlanner(String plannerName, String startTime, String endTime){

        DailyPlanner dailyPlanner = new DailyPlanner(plannerName, startTime, endTime, 60);
        this.idToPlanner.put(dailyPlanner.getID(), dailyPlanner);
        return dailyPlanner.getID();
    }


    /** Create an empty Project Planner
     *
     * @param plannerName name of planner
     * @return true iff a new ProjectPlanner is correctly created
     */
    public int newProjectPlanner(String plannerName){

        ProjectPlanner projectPlanner = new ProjectPlanner(plannerName);
        this.idToPlanner.put(projectPlanner.getID(), projectPlanner);
        return projectPlanner.getID();
    }


    /** Create a string representation of planner tasks
     *
     * @param id A String representing the id number.
     * @return a string representation of the planner
     */
    public String toString(int id){ return this.findPlanner(id).toString(); }


    /** Create a string representation of daily planner remain tasks
     *
     * @return a string representation of the remain tasks for the daily planners.
     */
    public String dailyPlannerRemainTasks(int id){
        if (this.findPlanner(id).getClass() == DailyPlanner.class) {
            return ((DailyPlanner) this.findPlanner(id)).remainTasks();
        }
        return null;
    }


    /** Set the idToPlanner Attributes
     *
     * @param hm The HashMap object we want to assign.
     */
    public void setIdToPlanner(HashMap<Integer, Planner> hm) {
        this.idToPlanner = hm;
    }


    /** Edit agenda on current planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public boolean edit(int id, int i, String agenda){
        this.findPlanner(id).edit(i, agenda);
        return true;
    }


    /** Edit agenda on DailyPlanner base on time stamp
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly edited
     */
    public boolean edit(int id, String time, String newAgenda){

        if (this.findPlanner(id).getClass() == DailyPlanner.class){
            ((DailyPlanner) this.findPlanner(id)).edit(time, newAgenda);
            return true;
        }
        else{
            return false;
        }
    }


    /** Return the Planner with given ID
     *
     * @param id A String representing the id number.
     * @return A Planner object with given ID.
     */
    public Planner findPlanner(int id) {
        return this.idToPlanner.get(id);
    }


    /** Change privacy status of current planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly changed (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(int id, String status){
        return this.findPlanner(id).ChangePrivacyStatus(status);
    }


    /** Return all the planner in a Array List.
     *
     * @return An ArrayList containing all the Planners.
     */
    public ArrayList<Planner> getAllPlanner() {
        return new ArrayList<>(this.idToPlanner.values());
    }


    /**
     * print all planners in the system
     * @return String representation of all planners
     */
    public String showAllPlanners (){
        ArrayList<Planner> allPlanners = getAllPlanner();
        StringBuilder allPlannersStringBuilder= new StringBuilder();
        for (Planner planner : allPlanners){
            allPlannersStringBuilder.append(toString(planner.getID()));
            allPlannersStringBuilder.append("\n");
        }
        return allPlannersStringBuilder.toString();
    }


    /**
     * delete a planner from all planners.
     * @param id the id of the planner to be deleted
     * @return true if successfully deleted; false otherwise
     */
    public Boolean deletePlanner(int id) {
        if (this.idToPlanner.containsKey(id)){
            this.idToPlanner.remove(id);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * set the author of the planner
     * @param id the integer id of the planner
     * @param author the identifier of a user
     */
    public void setPlannerAuthor(int id, String author){
        findPlanner(id).setAuthor(author);
    }

    /**
     * Get all planners of one author
     * @param author the identifier of a user
     * @return the ArrayList of planners owned by the user
     */
    public ArrayList<Integer> getPlannersByAuthor(String author){
        ArrayList<Integer> plannersByAuthor = new ArrayList<Integer>();
        for (Planner planner : this.idToPlanner.values()) {
            if (planner.getAuthor().equals(author)){
                Integer ID = (Integer) planner.getID();
                plannersByAuthor.add(ID);
            }
        }
        return plannersByAuthor;
    }

    /**
     * return all public planners
     * @return all public planners
     */
    public ArrayList<Integer> getPublicPlanners(){
        ArrayList<Integer> publicPlanners = new ArrayList<Integer>();
        for (Planner planner : this.idToPlanner.values()) {
            if (planner.getPrivacyStatus().equals("public")){
                publicPlanners.add(planner.getID());
            }
        }
        return publicPlanners;
    }

    /**
     * Return the type of the planner
     * @param id the integer id of the planner
     * @return the String representing the planner
     */
    public String plannerType(int id){
        if (this.idToPlanner.get(id).getType().equals("daily")){
            return "daily";
        }
        else {
            return "project";
        }
    }

    /**
     * return the number of agendas in this planner
     * @param id the integer id of the planner
     * @return the number of agendas in this planner
     */
    public int getNumAgendas(int id){
        return this.findPlanner(id).getNumAgendas();
    }

    /**
     * return the privacy status of the planner
     * @param id the integer id of the planner
     * @return return the privacy status, "private" or "public"
     */
    public String getPrivacyStatus(int id) {
        Planner planner = this.idToPlanner.get(id);
        return planner.getPrivacyStatus();
    }
}
