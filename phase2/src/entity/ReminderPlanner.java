package entity;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Represents one type of Planner in the program - a Reminder Planner, it corresponds to the Reminder Template.
 */
public class ReminderPlanner extends Planner{

    private Map<String, List<String>> reminderPlannerTask;
    private List<String> TaskPromptTasks, DatePromptTasks, CompletionPromptTasks;
    private String taskHeading, dateHeading, completionStatusHeading;
    private int NumAgendas;
    private final int id;

    /**
     * @param taskHeading the first column name of the reminder planner
     * @param dateHeading the second column name of the reminder planner
     * @param completionStatusHeading the third column name of the reminder planner
     */
    public ReminderPlanner(String plannerName, String taskHeading, String dateHeading,
                           String completionStatusHeading){
        super();
        this.id = super.getID();
        initializePlannerVars(plannerName, taskHeading, dateHeading, completionStatusHeading);
    }

    public ReminderPlanner(int numPlannersLoaded, String plannerName, String taskHeading, String dateHeading,
                           String completionStatusHeading){
        super(numPlannersLoaded);
        this.id = super.getID();
        initializePlannerVars(plannerName, taskHeading, dateHeading, completionStatusHeading);
    }

    private void initializePlannerVars(String plannerName, String taskHeading, String dateHeading, String completionStatusHeading) {
        this.plannerName = plannerName;
        this.taskHeading = taskHeading;
        this.dateHeading = dateHeading;
        this.completionStatusHeading = completionStatusHeading;
        this.TaskPromptTasks = new ArrayList<>();
        this.DatePromptTasks = new ArrayList<>();
        this.CompletionPromptTasks = new ArrayList<>();
        this.reminderPlannerTask = new HashMap<>();
        this.reminderPlannerTask.put(this.taskHeading, this.TaskPromptTasks);
        this.reminderPlannerTask.put(this.dateHeading, this.DatePromptTasks);
        this.reminderPlannerTask.put(this.completionStatusHeading, this.CompletionPromptTasks);
        this.NumAgendas = 0;
    }

    /** Show the current planner
     *
     * @return a string represent reminder planner's content
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("\n");
        int taskNums = this.TaskPromptTasks.size();

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
     *
     * @return the String type of the planner (which is Reminder).
     */
    @Override
    public String getType(){
        return "reminders";
    }

    /**
     *
     * @return the number of agendas in the reminder planner.
     */
    @Override
    public int getNumAgendas(){
        return this.NumAgendas;
    }

    /**
     *
     * @param dateStr the date string the user enters
     * @return true iff the input time is a valid time format
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
     *
     * @param s1 the task heading it wants to add.
     * @param s2 the task date it wants to add.
     * @return true iff the agenda is correctly added to current planner.
     */
    @Override
    public Boolean add (String s1, String s2){
        if (!isValid(s2)){
            return false;
        } else {
            this.reminderPlannerTask.get(this.taskHeading).add(s1);
            this.reminderPlannerTask.get(this.dateHeading).add(s2);
            this.reminderPlannerTask.get(this.completionStatusHeading).add("incomplete");
            this.NumAgendas ++;
            return true;
        }
    }


    /**
     *
     * @param OldAgenda the original agenda the user wants to change
     * @param NewAgenda the new content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
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
     *
     * @param taskName the task name the user wants to change status
     * @param taskStatus the status the user wants to change
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

    /*** Show the id for the planner
     *
     * @return a int id for the planner.
     */
    @Override
    public int getID(){
        return id;
    }
}
