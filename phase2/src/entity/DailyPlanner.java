package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A type of Planner - Daily Planner. Displays a planner that starts at a specified hour, ends at a specified hour, 
 * with a specified time interval. Allows users to fill in any activities planned for a particular hour. 
 */
public class DailyPlanner extends Planner {
    private Map<String, String> dailyPlannerTask;
    private List<String> timesList; // time array
    private int interval;  //minutes interval
    private int startHour;
    private int endHour;
    private int numAgendas;
    private final int ID;

    /**
     * Initializes a DailyPlanner.
     * @param plannerName: The name of the planner
     * @param startTime: The start time of the current planner, "HH:MM"
     * @param endTime: The end time of the current planner, "HH:MM"
     * @param Interval: Time interval between each calendar time, in hours
     */
    public DailyPlanner(String plannerName, String startTime, String endTime, int Interval) {
        super(plannerName);
        this.ID = super.getID();
        initializePlannerVars(startTime, endTime, Interval);
    }

    /**
     * Initializes a DailyPlanner.
     * @param numPlannersLoaded Number of planners already loaded in the program. So the ID of the planner will start
     *                            from numPlannersLoaded + 1.
     */
    public DailyPlanner(int numPlannersLoaded, String plannerName, String startTime, String endTime, int Interval) {
        super(numPlannersLoaded, plannerName);
        this.ID = super.getID();
        initializePlannerVars(startTime, endTime, Interval);
    }

    private void initializePlannerVars(String startTime, String endTime, int Interval) {
        this.interval = Interval;
        this.startHour = Integer.parseInt(startTime.substring(0, 2));
        this.endHour = Integer.parseInt(endTime.substring(0, 2));
        this.timesList = new ArrayList<>();
        this.dailyPlannerTask = new HashMap<>();
        this.numAgendas = 0;
        String timeFormat;
        int m = 0;
        for (int h = this.startHour; h < this.endHour; h+=interval) {
            timeFormat = String.format("%02d:%02d", h, m);
            timesList.add(timeFormat);
        }
        //add all time to Hashmap with empty agenda
        for (String time : timesList) {
            dailyPlannerTask.put(time, "N/A");
        }
    }

    /**
     * @return A string representing that this planner is daily planner.
     */
    public String getType() {
        return "daily";
    }

    /**
     * @return An integer representing the ID of the planner.
     */
    @Override
    public int getID(){
        return ID;
    }

    /**
     * Gets the number of agendas the daily planner has.
     * @return An integer representation of the number of agendas.
     */
    public int getNumAgendas() {
        return this.numAgendas;
    }

    /**
     * @return A string representing this DailyPlanner.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Status: ").append(this.privacyStatus).append("\n");

        String timeInfo = String.format("Start time -> %d:%d, End time -> %d:%d. \n",
                this.startHour, 0, this.endHour, 0);
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
     * Adds agenda to the timeslot corresponding to time in this planner.
     * @param time: The timeslot of the new agenda.
     * @param agenda: The agenda to be added.
     * @return true iff the agenda is correctly added to this planner.
     */
    @Override
    public Boolean add(String time, String agenda) {
        this.numAgendas++;
        int newStartHour = Integer.parseInt(time.substring(0, 2));

        if (newStartHour < this.startHour) {
            int gap = startHour - newStartHour;
            for (int i = 0; i < gap; i++) {
                int currH = startHour - i - 1;
                String hour = String.format("%02d", currH);
                this.dailyPlannerTask.put(hour + ":00", "N/A");
                this.timesList.add(0, hour + ":00");
            }
            this.dailyPlannerTask.put(String.format("%02d", newStartHour) + ":00", agenda);
            this.startHour = newStartHour;
        } else if (newStartHour > this.endHour) {
            int gap = newStartHour - endHour;
            for (int i = 0; i < gap; i++) {
                int currH = endHour + i + 1;
                String hour = String.format("%02d", currH);
                this.dailyPlannerTask.put(hour + ":00", "N/A");
                this.timesList.add(hour + ":00");
            }
            this.dailyPlannerTask.put(String.format("%02d", newStartHour) + ":00", agenda);
            this.endHour = newStartHour;
        } else {
            String hour = String.format("%02d", newStartHour);
            this.dailyPlannerTask.put(hour + ":00", agenda);
        }

        this.numAgendas++;
        return true;
    }


    /**
     * Changes the agenda in the timeslot time to agenda in this planner.
     * @param time: The timeslot to be edited.
     * @param agenda: The new agenda for time.
     * @return true iff the agenda is correctly edited on this planner.
     */
    @Override
    public Boolean edit(String time, String agenda) {
        return this.add(time, agenda);
    }

    /**
     * @param TaskName: The name of the task that the user wants to change status for
     * @param TaskStatus: The status that the user wants to change to
     * @return true iff the task is correctly changed to the right status
     */
    @Override
    public Boolean ChangeTaskStatus(String TaskName, String TaskStatus) {
        return false; // This is a daily planner which does not need to change status.
    }

    /**
     * Deletes agenda in the timeslot corresponding to time in this planner.
     * @param time Timeslot where the user wants to delete agenda.
     * @return true iff the agenda is correctly deleted from time in this planner.
     */
    public boolean delete(String time) {
        // delete everything on that time slot, i.e. no option to delete one thing
        // check if is legal time frame
        return edit(time, "N/A");
    }
}