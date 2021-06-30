package Entity;

import java.sql.Time;
import java.util.HashMap;

public class DailyPlanner extends Planner {
    HashMap dailyIntervalAndTask;
    public DailyPlanner(Time StartTime, Time EndTime, int Interval){
        // generate hashmap with given time interval with empty content
        // and a arraylist of time for reference since hashmap don't have order per se
        // https://facingissuesonit.com/2019/05/10/java-generate-15-minute-time-interval-am-pm/
        // 09:00
    }


    /**
     * Show the current planner
     *
     * @return a string represent this planner's content
     */
    @Override
    public String Display() {
        return null;
    }
        //TODO

    /**
     * add agenda to current planner
     *
     * @return true iff the agenda is correctly added to current planner
     */
    @Override
    public Boolean Add(String s) {
        // add task to the available time slot closest to start time and last an hour
        // call overloaded complete add method
        return false;
    }


    /**
     * @param t the start time of this agenda item
     * @param i the duration of this agenda item
     * @param s the content of the agenda item
     * @return true iff the agenda is correctly added
     */

    public Boolean Add(Time t, int i, String s) {
        // assume start time and duration on whole clock (10:00, 10:15 ... if interval for daily planner is 15 mins)
        // check if time slots already occupied, check if start time and end time is within legal time frame
        // (for phase 2) add warning for double booking
        // add to agenda
        return false;
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
        // edit everything on that time slot, i.e. no option to change one thing
        // check if time is within legal time frame

    /**
     * delete agenda to current planner
     *
     * @return true iff the agenda is correctly deleted from current planner
     */
    @Override
    public boolean Delete(int i) {
        return false;
    }
        // delete everything on that time slot, i.e. no option to delete one thing
        // check if is legal time frame
}
