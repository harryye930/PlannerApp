package Entity;

import java.io.Serializable;
import java.util.UUID;

/** the planner entity
 * @author Runlong, Zifan
 * @version 1.0
 */

public abstract class Planner implements Serializable {
    public String plannerName;
    public String ID;
    public String privacyStatus;


    public Planner(String plannerName){
        this.plannerName = plannerName;
        this.ID = UUID.randomUUID().toString();
        this.privacyStatus = "private";

    }


    /** Show the current planner
     *
     * @return a string represent this planner's content
     */
    public abstract String toString();


    /** Return the Id of current planner.
     *
     * @return A String representing the ID of this planner.
     */
    public String getID() {return this.ID;}


    /** Add agenda to current planner
     *
     * @param s agenda of the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean Add(String s);


    /** Edit agenda to current planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean Edit(int i, String agenda);


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
