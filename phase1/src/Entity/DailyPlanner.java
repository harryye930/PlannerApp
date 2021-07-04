package Entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class DailyPlanner extends Planner {
    HashMap<String, String> dailyPlannerTask;
    ArrayList<String> timesList; // time array
    int interval;  //minutes interval
    int startHour;
    int startMins;
    int endHour;
    int endMins;

    /**
     * initialize DailyPlanner
     *
     * @param startTime: the start time of the current planner, "HH:MM"
     * @param endTime:   the end time of the current planner, "HH:MM"
     * @param Interval:  time interval between each calendar time, in minutes
     */
    public DailyPlanner(String startTime, String endTime, int Interval) {
        // generate hashmap with given time interval with empty content
        // and a arraylist of time for reference since hashmap don't have order per se
        // https://facingissuesonit.com/2019/05/10/java-generate-15-minute-time-interval-am-pm/
        this.interval = Interval;
        this.startHour = Integer.parseInt(startTime.substring(0, 1));
        this.startMins = Integer.parseInt(startTime.substring(3, 4));
        this.endHour = Integer.parseInt(endTime.substring(0, 1));
        this.endMins = Integer.parseInt(endTime.substring(3, 4));
        this.timesList = new ArrayList<>();
        this.dailyPlannerTask = new HashMap<>();
        String timeFormat;
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; ) {
                timeFormat = String.format("%02d:%02d", h, m);
                timesList.add(timeFormat);
                m = m + interval;
            }
        }

        //add all time to Hashmap with empty agenda
        for (String time : timesList) {
            dailyPlannerTask.put(time, "N/A");
        }
    }


    /**
     * Show the current planner
     *
     * @return a string represent this planner's content
     */
    @Override
    public String toString() {
        String result;
        result = "Daily tasks: \n";
        return result;
    }


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
     * edit agenda to current planner
     *
     * @param i index of the agenda user wish to edit
     * @param s content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean Edit(int i, String s) {
        return null;
        // TODO
    }

    /**
     * delete agenda to current planner
     *
     * @param i index of the agenda user wish to delete
     * @return true iff the agenda is correctly deleted from current planner
     */
    @Override
    public boolean Delete(int i) {
        return false;
    }


    /**
     * @param newStartTime: start time for new agenda item
     * @param s:            content of new agenda item
     * @return true iff the new agenda is successfully added
     */
    public Boolean Add(String newStartTime, String s) {
        // assume start time and duration on whole clock (10:00, 10:15 ... if interval for daily planner is 15 mins)
        // check if time slots already occupied, check if start time and end time is within legal time frame
        // (for phase 2) add warning for double booking
        // add to agenda
        int newStartHour = Integer.parseInt(newStartTime.substring(0, 1));
        int newStartMins = Integer.parseInt(newStartTime.substring(3, 4));
        if (newStartHour < this.startHour || newStartHour > this.endHour || newStartMins < 0 || newStartMins > 60) {
            return false;
        } else {
            newStartMins = GetCloestMins(newStartMins, this.interval);
            String newTime = String.format("%d:%d", newStartHour, newStartMins);
            this.dailyPlannerTask.replace(newTime, s);
            return true;
        }
    }

    public int GetCloestMins(int NewStartMins, int Interval) {
        //new list of all possible mins given ineterval, ie. 0, 5, 10, 15... for interval=5
        ArrayList<Integer> numbers = new ArrayList<Integer>(0);
        for (int i = 0; i < (60 - Interval); i = i + Interval) {
            numbers.add(i);
        }
        int distance = Math.abs(numbers.get(0) - NewStartMins);
        int idx = 0;
        for (int c = 1; c < numbers.size(); c++) {
            int cdistance = Math.abs(numbers.get(c) - NewStartMins);
            if (cdistance < distance) {
                idx = c;
                distance = cdistance;
            }
        }
        return numbers.get(idx);

    }

    /**
     * edit agenda to current planner
     *
     * @return true iff the agenda is correctly edited on current planner
     */

    public Boolean Edit(String time, String newAgenda) {
        if (this.dailyPlannerTask.containsKey(time)) {
            this.dailyPlannerTask.replace(time, newAgenda);
            return true;
        } else {
            return false;
        }
    }
    // edit everything on that time slot, i.e. no option to change one thing
    // check if time is within legal time frame

    /**
     * delete agenda to current planner
     *
     * @return true iff the agenda is correctly deleted from current planner
     */
    public boolean Delete(String time) {
        // delete everything on that time slot, i.e. no option to delete one thing
        // check if is legal time frame
        return Edit(time, "N/A");
    }
}

