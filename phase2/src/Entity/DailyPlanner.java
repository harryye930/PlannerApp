package Entity;


import java.util.ArrayList;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class DailyPlanner extends Planner {
    private HashMap<String, String> dailyPlannerTask;
    private ArrayList<String> timesList; // time array
    private int ID;
    private int interval;  //minutes interval
    private int startHour;
    private int startMins;
    private int endHour;
    private int endMins;
    private int NumAgendas;

    /**
     * initialize DailyPlanner
     * @param plannerName: the name of the planner
     * @param startTime: the start time of the current planner, "HH:MM"
     * @param endTime:   the end time of the current planner, "HH:MM"
     * @param Interval:  time interval between each calendar time, in minutes
     */
    public DailyPlanner(String plannerName, String startTime, String endTime, int Interval) {
        // generate hashmap with given time interval with empty content
        // and a arraylist of time for reference since hashmap don't have order per se
        // https://facingissuesonit.com/2019/05/10/java-generate-15-minute-time-interval-am-pm/
        super();
        this.plannerName = plannerName;
        this.interval = Interval;
        this.startHour = Integer.parseInt(startTime.substring(0, 2));
        this.startMins = Integer.parseInt(startTime.substring(3, 5));
        this.endHour = Integer.parseInt(endTime.substring(0, 2));
        this.endMins = Integer.parseInt(endTime.substring(3, 5));
        this.timesList = new ArrayList<>();
        this.dailyPlannerTask = new HashMap<>();
        this.ID = super.getID();
        this.NumAgendas = 0;
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


//    public DailyPlanner(){
//        super();
//        timesList = new ArrayList<>();
//        dailyPlannerTask = new HashMap<>();
//    }


    /** Show the planner type is daily planner
     *
     * @return a string represent this planner is daily planner
     */
    public String getType(){
        return "daily";
    }


    /** Set the time interval of the daily planner representing by integer
     *
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /** Set a start time for the daily planner in HH:MM.
     *
     */
    public void setStartTime(int startHour, int startMins){
        this.startHour = startHour;
        this.startMins = startMins;
    }


    /** Set a end time for the daily planner in HH:MM.
     *
     */
    public void setEndTime(int endHour, int endMins){
        this.endHour = endHour;
        this.endMins = endMins;
    }


    /** Set a name for the daily planner representing in String.
     *
     */
    public void setPlannerName(String PlannerName){
        this.plannerName = PlannerName;
    }


    /** Set a start time list for the daily planner.
     *
     */
    public void setTimesList(){
        String timeFormat;
        for (int h = this.startHour; h < this.endHour; h++) {
            for (int m = this.startMins; m < 60; ) {
                timeFormat = String.format("%02d:%02d", h, m);
                timesList.add(timeFormat);
                m = m + interval;
            }
        }
    }


    /** Show the current time based on the system time
     *
     * @return a string representation of the current time
     */
    public String CurrentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    /** Get the number of agendas the daily planner has
     *
     * @return a integer representation of the number of agendas
     */
    public int getNumAgendas(){
        return this.NumAgendas;
    }


    /** Show the daily planner tasks
     *
     */
    public void setDailyPlannerTask(ArrayList<String> timesList){
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
        sb.append("Status: ").append(this.privacyStatus).append("\n");

        String timeInfo = String.format("Start time -> %d:%d, End time -> %d:%d. \n",
                this.startHour, this.startMins, this.endHour, this.endMins);
        String plannerInfo = this.plannerName + "\n" + "ID: " + this.ID + "\n" + timeInfo + "\nTasks: \n";
        sb.append(plannerInfo);
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
    public Boolean add(String s) {
        // add task to the available time slot closest to start time and last an hour
        // call overloaded complete add method
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        this.NumAgendas ++;
        return this.add(time, s);
    }


    /**
     * add agenda to current planner, if the user give a time newStartTime
     * @param newStartTime: start time for new agenda item
     * @param agenda:            content of new agenda item
     * @return true iff the new agenda is successfully added
     */
    public Boolean add(String newStartTime, String agenda) {
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
            newStartMins = getClosestMins(newStartMins, this.interval);
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
            String newTime = hourIndex + ":" + minIndex;
            if (this.dailyPlannerTask.get(newTime).equals("N/A")){
                this.dailyPlannerTask.replace(newTime, agenda);
            }
            else {
                String updatedTasks = this.dailyPlannerTask.get(newTime) + ", " + agenda;
                this.dailyPlannerTask.put(newTime, updatedTasks);
            }
            this.NumAgendas ++;
            return true;
        }
    }


    /**
     * edit agenda to current planner (given index)
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean edit(int i, String agenda) {
        if (i > this.dailyPlannerTask.size() - 1){ // if i is over the size limit
            return false;
        }
        else if (agenda.length() == 0){ // if the new agenda is empty
            add(agenda);
            return true;
        }
        else{
            this.dailyPlannerTask.replace(this.timesList.get(i),agenda); // replace the string.
            return true;
        }
    }


    /**
     * edit agenda to current planner (given time)
     *
     * @param time the time for the new agenda
     * @param newAgenda the new agenda
     *
     * @return true iff the agenda is correctly edited on current planner
     */
    public Boolean edit(String time, String newAgenda) {
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
    public boolean delete(String time) {
        // delete everything on that time slot, i.e. no option to delete one thing
        // check if is legal time frame
        return edit(time, "N/A");
    }


    /** for Phase 2
     * take the new agenda start time to the closest minutes based on the interval
     *
     * @param NewStartMins the start time for a agenda
     * @param Interval the time interval set by the user
     * @return the closest time the user may reach
     */
    public int getClosestMins(int NewStartMins, int Interval) {
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
    public String remainTasks(){
        //get the current time from the system.
        String current_time = CurrentTime();
        //make to the current time toString, and find the substring of the current hour and current mins.
        int current_hour = Integer.parseInt(current_time.substring(0, 2));
        int current_min = Integer.parseInt(current_time.substring(3, 5));
        //construct a empty arraylist that will store the remaining tasks today.
        ArrayList<String> remain_tasks = new ArrayList<>();
        //compare the daily tasks time with the current time, if it is later than now, then add to remain_tasks.
        for (String time : timesList) {
            String task_hour = time.substring(0, 2);
            String task_min = time.substring(3, 5);
            //the condition while the hour is the same but min is different.
            if (Integer.parseInt(task_hour) == current_hour) {
                if (Integer.parseInt(task_min) > current_min) {
                    remain_tasks.add(time);
                }
            }
            //the condition while the hour and minn are both different.
            if (Integer.parseInt(task_hour) > current_hour) {
                remain_tasks.add(time);
            }
        }
        //set up a StringBuilder to collect all the information for the remaining tasks.
        StringBuilder RemainTaskSb = new StringBuilder();
        RemainTaskSb.append("Remain tasks: \n");
        for (String task_time : remain_tasks) {
            RemainTaskSb.append(task_time);
            RemainTaskSb.append(":");
            RemainTaskSb.append(this.dailyPlannerTask.get(task_time));
            RemainTaskSb.append("\n");
        }
        return RemainTaskSb.toString();
    }


    public HashMap<String, String> getDailyPlannerTask() {
        return dailyPlannerTask;
    }

    //
//    public int getInterval() {
//        return interval;
//    }
//
//    public int getStartHour() {
//        return startHour;
//    }
//
//    public void setStartHour(int startHour) {
//        this.startHour = startHour;
//    }
//
//    public int getStartMins() {
//        return startMins;
//    }
//
//    public void setStartMins(int startMins) {
//        this.startMins = startMins;
//    }
//
//    public int getEndHour() {
//        return endHour;
//    }
//
//    public void setEndHour(int endHour) {
//        this.endHour = endHour;
//    }
//
//    public int getEndMins() {
//        return endMins;
//    }
//
//    public void setEndMins(int endMins) {
//        this.endMins = endMins;
//    }
}

