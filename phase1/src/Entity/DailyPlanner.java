package Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
        super();
        this.interval = Interval;
        this.startHour = Integer.parseInt(startTime.substring(0, 2));
        this.startMins = Integer.parseInt(startTime.substring(3, 5));
        this.endHour = Integer.parseInt(endTime.substring(0, 2));
        this.endMins = Integer.parseInt(endTime.substring(3, 5));
        this.timesList = new ArrayList<>();
        this.dailyPlannerTask = new HashMap<>();
        String timeFormat;
        for (int h = this.startHour; h < this.endHour; h++) {
            for (int m = this.startMins; m < 60; ) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("Daily tasks: \n");
        String timeInfo = String.format("Start time -> %d:%d, End time -> %d:%d. \n",
                this.startHour, this.startMins, this.endHour, this.endMins);
        sb.append(timeInfo);
        for (String time : timesList) {
            sb.append(time);
            sb.append(":");
            sb.append(this.dailyPlannerTask.get(time));
            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     * add agenda to current planner, if the user does not give a time.
     * @param s: the content of new agenda item
     * @return true iff the agenda is correctly added to current planner
     */
    @Override
    public Boolean Add(String s) {
        // add task to the available time slot closest to start time and last an hour
        // call overloaded complete add method
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        return this.Add(time, s);
    }


    /**
     * add agenda to current planner, if the user give a time newStartTime
     * @param newStartTime: start time for new agenda item
     * @param s:            content of new agenda item
     * @return true iff the new agenda is successfully added
     */
    public Boolean Add(String newStartTime, String s) {
        // assume start time and duration on whole clock (10:00, 10:15 ... if interval for daily planner is 15 mins)
        // check if time slots already occupied, check if start time and end time is within legal time frame
        // (for phase 2) add warning for double booking
        // add to agenda
        int newStartHour = Integer.parseInt(newStartTime.substring(0, 2));
        int newStartMins = Integer.parseInt(newStartTime.substring(3, 5));
        String hourIndex;
        String minIndex;
        if (newStartHour < this.startHour || newStartHour > this.endHour || newStartMins < 0 || newStartMins > 60) {
            return false;
        } else {
            newStartMins = GetClosestMins(newStartMins, this.interval);
            if (newStartHour<10){
                 hourIndex = String.format("0%d", newStartHour);
            }
            else{
                 hourIndex = String.format("%d", newStartHour);
            }
            if (newStartMins<10){
                 minIndex = String.format("0%d", newStartMins);
            }
            else{
                 minIndex = String.format("%d", newStartMins);
            }
            String newTime = new String(hourIndex + ":" + minIndex);
            this.dailyPlannerTask.replace(newTime, s);
            return true;
        }
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
        if (i > this.dailyPlannerTask.size() - 1){ // if i is over the size limit
            return false;
        }
        else if (s.length() == 0){ // if the new agenda is empty
            return false;
        }
        else{
            this.dailyPlannerTask.replace(this.timesList.get(i),s); // replace the string.
            return true;
        }
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

    /**
     * delete agenda to current planner
     *
     * @param time index of the agenda user wish to delete
     * @return true iff the agenda is correctly deleted from current planner
     */
    public boolean Delete(String time) {
        // delete everything on that time slot, i.e. no option to delete one thing
        // check if is legal time frame
        return Edit(time, "N/A");
    }


    /**
     * take the new agenda start time to the closest minutes based on the interval
     *
     * @param NewStartMins the start time for a agenda
     * @param Interval the time interval set by the user
     * @return the closest time the user may reach
     */
    public int GetClosestMins(int NewStartMins, int Interval) {
        //new list of all possible mins given ineterval, ie. 0, 5, 10, 15... for interval=5
        ArrayList<Integer> numbers = new ArrayList<>(0);
        numbers.add(0);
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
     * get the remaining tasks the user need to do
     *
     * @return a int iff the agenda is not passed.
     *
     */
    public String Remaintasks(){
        //get the current time from the system.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        dtf.format(now);
        //make to the current time toString, and find the substring of the current hour and current mins.
        String current_time = dtf.toString();
        int current_hour = Integer.parseInt(current_time.substring(0,1));
        int current_min = Integer.parseInt(current_time.substring(3,4));
        //construct a empty arraylist that will store the remaining tasks today.
        ArrayList<String> remain_tasks = new ArrayList<>();
        //compare the daily tasks time with the current time, if it is later than now, then add to remain_tasks.
        for (String time : timesList) {
            if (Integer.parseInt(time.substring(0,1)) > current_hour){
                if (Integer.parseInt(time.substring(3, 4)) > current_min){
                    remain_tasks.add(time);
                }
            }
        }
        //set up a StringBuilder to collect all the information for the remaining tasks.
        StringBuilder sb = new StringBuilder();
        sb.append("Remain tasks: \n");
        for (String time : remain_tasks) {
            sb.append(time);
            sb.append(":");
            sb.append(this.dailyPlannerTask.get(time));
            sb.append("\n");
        }
        return sb.toString();
    }


}

