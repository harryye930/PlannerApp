package use_case;

import entity.DailyPlanner;
import entity.Planner;
import entity.ProjectPlanner;
import entity.ReminderPlanner;

import java.util.ArrayList;
import java.util.HashMap;



public class PlannerManager{
    private HashMap<Integer, Planner> idToPlanner;


    /**
     * Initializes the PlannerManager.
     */
    public PlannerManager() {
        this.idToPlanner = new HashMap<>();
    }

    /** Creates an empty Daily Planner -- default interval 60 mins.
     * @param plannerName Name of the planner.
     * @param startTime Start time of planner.
     * @param endTime End time of planner.
     * @return ID of the created Daily Planner.
     */
    public int newDailyPlanner(String plannerName, String startTime, String endTime, String interval){

        DailyPlanner dailyPlanner = new DailyPlanner(plannerName, startTime, endTime, Integer.parseInt(interval));
        this.idToPlanner.put(dailyPlanner.getID(), dailyPlanner);
        return dailyPlanner.getID();
    }

    /**
     * Creates an empty Project Planner.
     * @param plannerName The name of the planner.
     * @param firstColumn The column heading for the first status column.
     * @param secondColumn The column heading for the second status column.
     * @param thirdColumn The column heading for the third status column.
     * @return The ID of the created Project Planner.
     */
    public int newProjectPlanner(String plannerName, String firstColumn, String secondColumn, String thirdColumn){

        ProjectPlanner projectPlanner = new ProjectPlanner(plannerName, firstColumn, secondColumn, thirdColumn);
        this.idToPlanner.put(projectPlanner.getID(), projectPlanner);
        return projectPlanner.getID();
    }


    /** Creates a string representation of the planner with the specified id.
     * @param id A String representing the id number of a planner.
     * @return A string representation of the planner with the id.
     */
    public String toString(int id){ return this.findPlanner(id).toString(); }

    /** Sets the idToPlanner attribute.
     * @param hm A HashMap object we want to set the idToPlanner attribute to.
     */
    public void setIdToPlanner(HashMap<Integer, Planner> hm) {
        this.idToPlanner = hm;
    }


    /** Edit agenda on DailyPlanner base on time stamp
     *
     * @param timeOrName: time slot on DailyPlanner, HH:MM
     * @param agenda: new agenda item
     * @return true iff is correctly edited
     */
    public boolean edit(int id, String timeOrName, String agenda){
        return this.findPlanner(id).edit(timeOrName, agenda);
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
     * @param status "private" or "public" or "friends-only"
     * @return true iff the status is correctly changed
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
        ArrayList<Integer> plannersByAuthor = new ArrayList<>();
        for (Planner planner : this.idToPlanner.values()) {
            if (planner.getAuthor().equals(author)){
                Integer ID = planner.getID();
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
        ArrayList<Integer> publicPlanners = new ArrayList<>();
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


    /**
     * Return a collection of the planner ids.
     * @return An arraylist representing the planner ids.
     */
    public ArrayList<String> getAllPlannerId() {
        ArrayList<String> res = new ArrayList<>();
        for (Integer id: this.idToPlanner.keySet()) {
            res.add(id.toString());
        }
        return res;
    }

    /**
     * Add new agenda or task to the planner
     * @param id An integer representing the id of the planner
     * @param i the first String input depending on the planner type
     * @param j the second String input depending on the planner type.
     * @return true if the planner is correctly added
     */
    public boolean add(int id, String i, String j) {
        return this.findPlanner(id).add(i, j);
    }

    /**
     * Change the status of the given task.
     * @param id An integer representing the id of the planner.
     * @param taskName A String representing the task name.
     * @param status A String representing the task status.
     * @return a boolean value representing whether the change is successful or not.
     */
    public boolean changTaskStatus(int id, String taskName, String status) {
        return this.findPlanner(id).ChangeTaskStatus(taskName, status);
    }
}