package entity;

import java.io.Serializable;

/** the planner entity
 * @author Runlong, Zifan
 */

public abstract class Planner implements Serializable {
    protected String plannerName;
    private static int ID;
    protected String privacyStatus;
    protected String author;

    /**
     * Initialize the Planner.
     */
    public Planner(int numPlannersLoaded){
        this.plannerName = "New Planner";
        this.privacyStatus = "private";
        this.author = "TODO";
        ID = numPlannersLoaded;
        ID++;
    }

    public Planner(){
        this.plannerName = "New Planner";
        this.privacyStatus = "private";
        this.author = "TODO";
        ID++;
    }

    /**
     * Initialize the Planner.
     * @param plannerName: the planner name.
     */
    public Planner(String plannerName){
        this.plannerName = plannerName;
        ID++;
        this.privacyStatus = "private";
        this.author = "TODO";

    }


    /** Show the current planner
     *
     * @return a string represent this planner's content
     */
    public abstract String toString();


     /*** Show the id for the planner
      *
      * @return a int id for the planner.
      */
    public int getID(){
        return ID;
    }


     /** @return a String representing the planner status.
     */
    public String getPrivacyStatus() {
        return this.privacyStatus;
    }

    /** @return a String representing of the planner author.
     */
    public String getAuthor() {
        return this.author;
    }


    /** set a String representing of the planner author.
     */
    public void setAuthor(String author){
        this.author = author;
    }


    /** @return a String representing of the planner type.
     */
    public abstract String getType();


    /** Show the number of planner agendas.
     *
     * @return an int representing of the number of planner agendas.
     */
    public abstract int getNumAgendas();


    /** Add agenda to current planner
     *
     * @param s1 the first input of the add method.
     * @param s2 the second input of the add method.
     * @return true iff the agenda is correctly added to current planner.
     */
    public abstract Boolean add(String s1, String s2);


    /** Edit agenda to current planner
     *
     * @param OldAgenda the original agenda the user wants to change
     * @param NewAgenda the new content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean edit(String OldAgenda, String NewAgenda);


    /** Change the planner's privacy status
     *
     * @param status : expected status of this planner: public or private or friends-only
     * @return true iff the planner is correctly changed the privacy status
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
     *
     * @param TaskName the task name the user wants to change status
     * @param TaskStatus the status the user wants to change
     * @return true iff the planner is correctly changed to the right status
     */
    public abstract Boolean ChangeTaskStatus(String TaskName, String TaskStatus);

}
