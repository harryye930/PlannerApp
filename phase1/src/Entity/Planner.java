package entity;

import UseCase.PlannerManager;

import java.io.Serializable;
import java.util.UUID;

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
    public Planner(){
        this.plannerName = "New Planner";
        ID++;
        this.privacyStatus = "private";
        this.author = "TODO";

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
     * @return a int representing of the number of planner agendas.
     */
    public abstract int getNumAgendas();


    /** Add agenda to current planner
     *
     * @param s agenda of the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean add(String s);


    /** Edit agenda to current planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean edit(int i, String agenda);


    /** Change the planner's privacy status
     *
     * @param status : expected status of this planner: public or private
     * @return true iff the planner is correctly changed the privacy status
     */
    public boolean ChangePrivacyStatus(String status){
        if (status.equals("public")) {
            if (this.privacyStatus.equals("private")) {
                this.privacyStatus = "public";
                return true;
            }
        }
        else if (status.equals("private")){
            if (this.privacyStatus.equals("public")){
                this.privacyStatus = "private";
                return true;
            }
            else {
                return false;
            }
        }
        return false; // the current status is the same as input status
    }

}
