package Entity;
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
     * @return true iff the agenda is correctly added to current planner
     */
    public abstract Boolean Add(String s);

    /** edit agenda to current planner
     * @return true iff the agenda is correctly edited on current planner
     */
    public abstract Boolean Edit(int i, String s);

    /** delete agenda to current planner
     * @return true iff the agenda is correctly deleted from current planner
     */
    public abstract boolean Delete(int i);



}
