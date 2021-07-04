package Entity;

/** the planner entity
 * @author Runlong, Zifan
 * @version 1.0
 */

public abstract class Planner {
    public String privacyStatus = "private";


    /** Show the current planner
     *
     * @return a string represent this planner's content
     */
    public abstract String toString();


    /** Add agenda to current planner
     *
     * @param s agenda of the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean Add(String s);


    /** Edit agenda to current planner
     *
     * @param i index of the agenda user wish to edit
     * @param s content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean Edit(int i, String s);

    /** Delete agenda to current planner
     *
     * @param i index of the agenda user wish to delete
     * @return true iff the agenda is correctly deleted from current planner
     */
    public abstract boolean Delete(int i);


    /** Change the planner's privacy status
     *
     * @param status : expected status of this planner: public or private
     * @return true iff the planner is correctly changed the privacy status
     */
    public boolean SetPrivacyStatus(String status){
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
        return false;
    }
}
