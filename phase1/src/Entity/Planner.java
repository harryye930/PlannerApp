package Entity;

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



    public Planner(){
        this.plannerName = "New Planner";
        ID++;
        this.privacyStatus = "private";
        this.author = "TODO";

    }
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

    public int getID(){
        return ID;
    }

     /** @return a String representing of the planner status.
     */
    public String getPrivacyStatus() {
        return this.privacyStatus;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }


    /** Add agenda to current planner
     *
     * @param s agenda of the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean add(String s);

    public abstract String getType();

    public abstract int getNumAgendas();


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
