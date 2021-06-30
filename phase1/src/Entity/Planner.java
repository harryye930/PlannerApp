package Entity;

import java.sql.Time;

/** the planner entity
 * @author Runlong, Zifan
 * @version 1.0
 */

public abstract class Planner {


    /** show the current planner
     * @return a string represent this planner's content
     */
    public abstract Object Display();


    /** add agenda to current planner
     * @param s agenda of the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean Add(String s);



    /** edit agenda to current planner
     * @param i index of the agenda user wish to edit
     * @param s content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean Edit(int i, String s);

    /** delete agenda to current planner
     * @param i index of the agenda user wish to delete
     * @return true iff the agenda is correctly deleted from current planner
     */
    public abstract boolean Delete(int i);



}
