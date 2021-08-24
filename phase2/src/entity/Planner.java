package entity;

import java.io.Serializable;

/** The planner entity.
 * @author Runlong, Zifan
 */
public abstract class Planner implements Serializable {
    /**
     * plannerName: Name of the planner.
     * ID: ID of the planner.
     * privacyStatus: Privacy Status of the planner.
     */
    protected String plannerName;
    private static int ID;
    protected String privacyStatus;

    /**
     * Initializes the Planner.
     * @param numPlannersLoaded Number of planners already loaded in the program. So the ID of the planner will start
     *                            from numPlannersLoaded + 1.
     * @param plannerName Name of the planner.
     */
    public Planner(int numPlannersLoaded, String plannerName){
        this.plannerName = plannerName;
        this.privacyStatus = "private";
        ID = numPlannersLoaded;
        ID++;
    }

    /**
     * Initializes the Planner.
     * @param plannerName: the planner name.
     */
    public Planner(String plannerName){
        this.plannerName = plannerName;
        this.privacyStatus = "private";
        ID++;
    }

    /**
     * @return A string representation of this planner.
     */
    public abstract String toString();

     /**
      * @return An integer representing the id of this planner.
      */
    public int getID(){
        return ID;
    }

     /**
      * @return A String representing this planner's privacy status.
      */
    public String getPrivacyStatus() {
        return this.privacyStatus;
    }

    /**
     * @return A String representing the planner type.
     */
    public abstract String getType();

    /**
     * Shows the number of planner agendas.
     * @return An int representing the number of planner agendas.
     */
    public abstract int getNumAgendas();

    /**
     * Adds agenda to this planner.
     * @param s1 The first input of the add method.
     * @param s2 The second input of the add method.
     * @return true iff the agenda is correctly added to this planner.
     */
    public abstract Boolean add(String s1, String s2);

    /**
     * Edits agenda in this planner.
     * @param OldAgenda The original agenda that the user wants to change.
     * @param NewAgenda The new agenda that the user wants to change to.
     * @return true iff the agenda is correctly edited in this planner.
     */
    public abstract Boolean edit(String OldAgenda, String NewAgenda);


    /** Changes the planner's privacy status to status.
     * @param status: New status for this planner. Must be "public", "private", or "friends-only".
     * @return true iff the privacy status of this planner is correctly changed to status.
     */
    public boolean ChangePrivacyStatus(String status){
        if (this.privacyStatus.equals(status)){
            return false;
        } else {
            privacyStatus = status;
            return true;
        }
    }

    /**
     * Updates the status of Task with TaskName to TaskStatus.
     * @param TaskName The name of the task that the user wants to change status for.
     * @param TaskStatus The status the user wants to change to.
     * @return true iff the task is correctly changed to the given TaskStatus.
     */
    public abstract Boolean ChangeTaskStatus(String TaskName, String TaskStatus);

}
