package entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents one type of Planner in the program - a Reminder Planner, it corresponds to the Reminder Template.
 * Allows users to add reminders for tasks that still need to be completed, their required completion date,
 * and completion status.
 */
public class ReminderPlanner extends Planner{
    private Map<String, List<String>> reminderPlannerTask;
    private List<String> taskPromptTasks, datePromptTasks, completionPromptTasks;
    private String taskHeading, dateHeading, completionStatusHeading;
    private int numAgendas;
    private final int ID;

    /**
     * Constructs a ReminderPlanner.
     * @param taskHeading The first column name of the reminder planner.
     * @param dateHeading The second column name of the reminder planner.
     * @param completionStatusHeading The third column name of the reminder planner.
     */
    public ReminderPlanner(String plannerName, String taskHeading, String dateHeading,
                           String completionStatusHeading){
        super(plannerName);
        this.ID = super.getID();
        initializePlannerVars(taskHeading, dateHeading, completionStatusHeading);
    }

    /**
     * Constructs a ReminderPlanner.
     * @param numPlannersLoaded Number of planners already loaded in the program. So the ID of the planner will start
     *                            from numPlannersLoaded + 1.
     */
    public ReminderPlanner(int numPlannersLoaded, String plannerName, String taskHeading, String dateHeading,
                           String completionStatusHeading){
        super(numPlannersLoaded, plannerName);
        this.ID = super.getID();
        initializePlannerVars(taskHeading, dateHeading, completionStatusHeading);
    }

    private void initializePlannerVars(String taskHeading, String dateHeading, String completionStatusHeading) {
        this.taskHeading = taskHeading;
        this.dateHeading = dateHeading;
        this.completionStatusHeading = completionStatusHeading;
        this.taskPromptTasks = new ArrayList<>();
        this.datePromptTasks = new ArrayList<>();
        this.completionPromptTasks = new ArrayList<>();
        this.reminderPlannerTask = new HashMap<>();
        this.reminderPlannerTask.put(this.taskHeading, this.taskPromptTasks);
        this.reminderPlannerTask.put(this.dateHeading, this.datePromptTasks);
        this.reminderPlannerTask.put(this.completionStatusHeading, this.completionPromptTasks);
        this.numAgendas = 0;
    }

    /**
     * @return A string representation of this reminder planner.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("\n");
        int taskNums = this.taskPromptTasks.size();

        sb.append("This is a Reminder Planner\n");
        sb.append("Privacy Status: ").append(this.privacyStatus).append("\n");
        sb.append("Planner Name: ").append(this.plannerName).append("\n");
        sb.append("Planner ID: ").append(this.getID()).append("\n");
        sb.append("Tasks: ").append(this.getNumAgendas()).append("\n");

        for (int i = 0; i < taskNums; i++) {
            sb.append("\n").append(this.taskHeading).append(": ").
                    append(this.reminderPlannerTask.get(this.taskHeading).get(i)).append("\n");
            sb.append("\n").append(this.dateHeading).append(": ").
                    append(this.reminderPlannerTask.get(this.dateHeading).get(i)).append("\n");
            sb.append("\n").append(this.completionStatusHeading).append(": ").
                    append(this.reminderPlannerTask.get(this.completionStatusHeading).get(i)).append("\n");
            sb.append("-----------------\n");
        }
        if (taskNums == 0) {
            sb.append("\n").append(this.taskHeading).append(": ");
            sb.append("\n").append(this.dateHeading).append(": ");
            sb.append("\n").append(this.completionStatusHeading).append(": \n");
            sb.append("-----------------\n");
        }
        return sb.toString();
    }


    /**
     * @return A String representing the type of the planner.
     */
    @Override
    public String getType(){
        return "reminders";
    }

    /**
     * Returns the total number of agendas stored in this reminder planner object.
     * @return An integer representing the total number of agendas.
     */
    @Override
    public int getNumAgendas(){
        return this.numAgendas;
    }

    /**
     * Checks if the input dateStr is of valid time format.
     * @param dateStr The date string the user enters.
     * @return true iff the input time is of valid time format.
     */
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Adds a task to this reminder planner. Defaults the completion status to "incomplete".
     * @param s1 the task heading it wants to add.
     * @param s2 the task date it wants to add.
     * @return true iff the task is correctly added to current planner.
     */
    @Override
    public Boolean add (String s1, String s2){
        if (!isValid(s2)){
            return false;
        } else {
            this.reminderPlannerTask.get(this.taskHeading).add(s1);
            this.reminderPlannerTask.get(this.dateHeading).add(s2);
            this.reminderPlannerTask.get(this.completionStatusHeading).add("incomplete");
            this.numAgendas++;
            return true;
        }
    }

    /**
     * Replaces OldAgenda with NewAgenda.
     * @param OldAgenda The original agenda the user wants to replace.
     * @param NewAgenda The new agenda the user wants to change to,
     * @return true iff the agenda is correctly edited in this planner.
     */
    @Override
    public Boolean edit(String OldAgenda, String NewAgenda){
        if (this.reminderPlannerTask.get(this.taskHeading).contains(OldAgenda)){
            int TaskIndex = this.reminderPlannerTask.get(this.taskHeading).indexOf(OldAgenda);
            this.reminderPlannerTask.get(this.taskHeading).remove(OldAgenda);
            this.reminderPlannerTask.get(this.taskHeading).add(TaskIndex, NewAgenda);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param taskName The name of the task that the user wants to change status for.
     * @param taskStatus The status that the user wants to change to.
     * @return true iff the task status is corrected changed.
     */
    @Override
    public Boolean ChangeTaskStatus(String taskName, String taskStatus){
        if (this.reminderPlannerTask.get(this.taskHeading).contains(taskName)){
            int TaskIndex = this.reminderPlannerTask.get(this.taskHeading).indexOf(taskName);
            if (Objects.equals(this.reminderPlannerTask.
                    get(this.completionStatusHeading).get(TaskIndex), "incomplete")){
                this.reminderPlannerTask.get(completionStatusHeading).remove(TaskIndex);
                this.reminderPlannerTask.get(completionStatusHeading).add(TaskIndex, "completed");
            } else if (Objects.equals(this.reminderPlannerTask.
                    get(this.completionStatusHeading).get(TaskIndex), "completed")){
                this.reminderPlannerTask.get(completionStatusHeading).remove(TaskIndex);
                this.reminderPlannerTask.get(completionStatusHeading).add(TaskIndex, "incomplete");
            }
            return true;
            } else{
            return false;
        }
    }

    /**
     * @return An integer representing id of the planner.
     */
    @Override
    public int getID(){
        return ID;
    }
}
