package Entity;

import java.sql.Time;

public class DailyPlanner extends Planner {
    /**
     * Show the current planner
     *
     * @return a string represent this planner's content
     */
    @Override
    public String Display() {
        return null;
    }

    /**
     * add agenda to current planner
     *
     * @return true iff the agenda is correctly added to current planner
     */
    @Override
    public Boolean Add(String s) {
        return null;
    }

    /**
     * @param i index of new agenda item
     * @param s content of new item
     * @return true iff the agenda is correctly added
     */

    public Boolean Add(int i, String s) {
        return null;
    }

    /**
     * @param t the start time of this agenda item
     * @param i the duration of this agenda item
     * @param s the content of the agenda item
     * @return true iff the agenda is correctly added
     */

    public Boolean Add(Time t, int i, String s) {
        return null;
    }

    /**
     * edit agenda to current planner
     *
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean Edit(int i, String s) {
        return null;
    }

    /**
     * delete agenda to current planner
     *
     * @return true iff the agenda is correctly deleted from current planner
     */
    @Override
    public boolean Delete(int i) {
        return false;
    }
}
