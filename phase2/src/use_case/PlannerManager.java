package use_case;

import entity.DailyPlanner;
import entity.Planner;
import entity.ProjectPlanner;
import entity.ReminderPlanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores and manages planners.
 */
public class PlannerManager{
    private Map<Integer, Planner> idToPlanner;
    private int numPlannersLoaded;
    private boolean hasInitialized = false;

    /**
     * Initializes the PlannerManager.
     */
    public PlannerManager() {
        this.idToPlanner = new HashMap<>();
    }

    /**
     * Sets the numPlannersLoaded attribute.
     * @param numPlannersLoaded Number of planners read in from external file at the start of the program.
     */
    public void setNumPlannersLoaded(int numPlannersLoaded){
        this.numPlannersLoaded = numPlannersLoaded;
    }

    /**
     * Creates an empty planner of type plannerType.
     * @param plannerType Type of the planner to be created.
     * @param plannerName The name of the planner.
     * @param firstInput The first input needed to create a planner.
     * @param secondInput The second input needed to create a planner.
     * @param thirdInput The third input needed to create a planner.
     * @return The ID of the created planner.
     */
    public Integer createPlanner(String plannerType,
                                             String plannerName, String firstInput, String secondInput, String thirdInput){
        Planner planner = getPlanner(plannerType, plannerName, firstInput, secondInput, thirdInput);
        if (planner == null){
            System.out.printf("Planner of type %s cannot be created.", plannerType);
            return null;
        } else {
            this.idToPlanner.put(planner.getID(), planner);
            return planner.getID();
        }
    }

    /**
     * Factory method for creating a Planner.
     * @param plannerType Type of the planner to create. Must be one of: "daily", "project", "reminders".
     * @param plannerName Name for the planner.
     * @param firstInput The first input needed to create a planner.
     * @param secondInput The second input needed to create a planner.
     * @param thirdInput The third input needed to create a planner.
     * @return A Planner object.
     */
    private Planner getPlanner(String plannerType,
                               String plannerName, String firstInput, String secondInput, String thirdInput){
        if (!hasInitialized){
            switch (plannerType) {
                case "daily":
                    hasInitialized = true;
                    return new DailyPlanner(numPlannersLoaded, plannerName, firstInput, secondInput, Integer.parseInt(thirdInput));
                case "project":
                    hasInitialized = true;
                    return new ProjectPlanner(numPlannersLoaded, plannerName, firstInput, secondInput, thirdInput);
                case "reminders":
                    hasInitialized = true;
                    return new ReminderPlanner(numPlannersLoaded, plannerName, firstInput, secondInput, thirdInput);
                default:
                    System.out.printf("Planner type %s is undefined for this program.", plannerType);
                    return null;
            }
        } else {
            switch (plannerType) {
                case "daily":
                    return new DailyPlanner(plannerName, firstInput, secondInput, Integer.parseInt(thirdInput));
                case "project":
                    return new ProjectPlanner(plannerName, firstInput, secondInput, thirdInput);
                case "reminders":
                    return new ReminderPlanner(plannerName, firstInput, secondInput, thirdInput);
                default:
                    System.out.printf("Planner type %s is undefined for this program.", plannerType);
                    return null;
            }
        }
    }

    /**
     * Creates a string representation of the planner with the specified id.
     * @param id An integer representing the id number of a planner.
     * @return A string representation of the planner with the id.
     */
    public String toString(int id){ return this.findPlanner(id).toString(); }

    /**
     * Sets the idToPlanner attribute.
     * @param idToPlanner A HashMap object we want to set the idToPlanner attribute to.
     */
    public void setIdToPlanner(HashMap<Integer, Planner> idToPlanner) {
        this.idToPlanner = idToPlanner;
    }

    /**
     * Edits agenda corresponding to timeOrName in planner with id.
     * @param timeOrName: Time slot in DailyPlanner, or column name in ProjectPlanner or RemindersPlanner.
     * @param agenda: New agenda item.
     * @return true iff is edit is successful.
     */
    public boolean edit(int id, String timeOrName, String agenda){
        return this.findPlanner(id).edit(timeOrName, agenda);
    }

    /**
     * Returns the Planner with id.
     * @param id An integer representing the id number.
     * @return A Planner object with given id.
     */
    public Planner findPlanner(int id) {
        return this.idToPlanner.get(id);
    }

    /** Changes privacy status of current planner.
     * @param status New status to change the privacy status of the planner to.
     *               Must be "private", "public", or "friends-only".
     * @return true iff the privacy status is successfully changed to status.
     */
    public boolean changePrivacyStatus(int id, String status){
        return this.findPlanner(id).ChangePrivacyStatus(status);
    }

    /**
     * @return List containing all the Planners stored in this PlannerManager.
     */
    public List<Planner> getAllPlanner() {
        return new ArrayList<>(this.idToPlanner.values());
    }

    /**
     * Deletes a planner from this PlannerManager.
     * @param id The id of the planner to be deleted.
     * @return true if successfully deleted; false otherwise.
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
     * @return List containing ID's of all public planners.
     */
    public List<Integer> getPublicPlanners(){
        List<Integer> publicPlanners = new ArrayList<>();
        for (Planner planner : this.idToPlanner.values()) {
            if (planner.getPrivacyStatus().equals("public")){
                publicPlanners.add(planner.getID());
            }
        }
        return publicPlanners;
    }

    /**
     * Returns the type of the planner with id.
     * @param id The integer id of the planner.
     * @return The String representing the type of the planner.
     */
    public String plannerType(int id){
        return this.idToPlanner.get(id).getType();
    }

    /**
     * Returns the privacy status of the planner with id.
     * @param id The integer id of the planner.
     * @return The privacy status of the planner.
     */
    public String getPrivacyStatus(int id) {
        Planner planner = this.idToPlanner.get(id);
        return planner.getPrivacyStatus();
    }

    /**
     * Returns the IDs (in string format) of all planners stored in this PlannerManager.
     * @return List of strings representing the planner ids.
     */
    public List<String> getAllPlannerId() {
        List<String> res = new ArrayList<>();
        for (Integer id: this.idToPlanner.keySet()) {
            res.add(id.toString());
        }
        return res;
    }

    /**
     * Adds new agenda or task to the planner with id.
     * @param id An integer representing the id of the planner.
     * @param i The first String input depending on the planner type.
     * @param j The second String input depending on the planner type.
     * @return true if the planner is correctly added.
     */
    public boolean add(int id, String i, String j) {
        return this.findPlanner(id).add(i, j);
    }

    /**
     * Changes the status of the given task.
     * @param id An integer representing the id of the planner.
     * @param taskName A String representing the task name.
     * @param status A String representing the task status.
     * @return A boolean value representing whether the change is successful.
     */
    public boolean changeTaskStatus(int id, String taskName, String status) {
        return this.findPlanner(id).ChangeTaskStatus(taskName, status);
    }
}
