package Entity;
/** the planner entity
 * @author Runlong, Zifan
 * @version 1.0
 */

public abstract class Planner {

    /** Show the current planner
     * @return a string represent this planner's content
     */
    public abstract String Display();


    /** add agenda to current planner
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean Add();

    /** edit agenda to current planner
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean Edit();

    /** delete agenda to current planner
     * @return true iff the agenda is correctly deleted from current planner
     */
    public abstract boolean Delete();



}
