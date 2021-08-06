package Entity;


import javafx.concurrent.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * Represents one type of Planner in the program - a Reminder Planner, it corresponds to the Reminder Template.
 */
public class ReminderPlanner extends Planner{

    private HashMap<String, ArrayList<String>> reminderPlannerTask;
    private ArrayList<String> TaskPromptTasks, DatePromptTasks, CompletionPromptTasks;
    private String taskHeadingPrompt, dateHeadingPrompt, completionStatusHeadingPrompt;
    private int NumAgendas;
    private int ID;

    /**
     *
     * @param taskHeadingPrompt the first column name of the reminder planner
     * @param dateHeadingPrompt the second column name of the reminder planner
     * @param completionStatusHeadingPrompt the third column name of the reminder planner
     */
    public ReminderPlanner(String taskHeadingPrompt,
                           String dateHeadingPrompt,
                           String completionStatusHeadingPrompt){
        super();
        this.taskHeadingPrompt = taskHeadingPrompt;
        this.dateHeadingPrompt = dateHeadingPrompt;
        this.completionStatusHeadingPrompt = completionStatusHeadingPrompt;
        this.TaskPromptTasks = new ArrayList<>();
        this.DatePromptTasks = new ArrayList<>();
        this.CompletionPromptTasks = new ArrayList<>();
        this.reminderPlannerTask = new HashMap<>();
        this.reminderPlannerTask.put(this.taskHeadingPrompt, this.TaskPromptTasks);
        this.reminderPlannerTask.put(this.dateHeadingPrompt, this.DatePromptTasks);
        this.reminderPlannerTask.put(this.completionStatusHeadingPrompt, this.CompletionPromptTasks);
        this.ID = super.getID();
        this.NumAgendas = 0;
    }


    /** Show the current planner
     *
     * @return a string represent reminder planner's content
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int TaskNums = this.TaskPromptTasks.size();
        for(int i = 0; i < TaskNums - 1; i++){
            // get the task name of the reminder
            sb.append(this.taskHeadingPrompt).append(": ");
            sb.append(this.reminderPlannerTask.get(this.taskHeadingPrompt).get(i)).append("\n");

            // get the task deadline date of the reminder
            sb.append(this.dateHeadingPrompt).append(": ");
            sb.append(this.reminderPlannerTask.get(this.dateHeadingPrompt).get(i)).append("\n");

            // get the task completion status of the reminder
            sb.append(this.completionStatusHeadingPrompt).append(": ");
            sb.append(this.reminderPlannerTask.get(this.completionStatusHeadingPrompt).get(i)).append("\n");

            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     *
     * @return the String type of the planner (which is Reminder).
     */
    @Override
    public String getType(){
        return "Reminder";
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
        DateFormat sdf = new SimpleDateFormat(dateStr);
        sdf.setLenient(false);
        try {
            sdf.parse("MM/dd/yyyy");
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
            this.reminderPlannerTask.get(this.taskHeadingPrompt).add(s1);
            this.reminderPlannerTask.get(this.dateHeadingPrompt).add(s2);
            this.reminderPlannerTask.get(this.completionStatusHeadingPrompt).add("incomplete");
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
        if (this.reminderPlannerTask.get(this.taskHeadingPrompt).contains(OldAgenda)){
            int TaskIndex = this.reminderPlannerTask.get(this.taskHeadingPrompt).indexOf(OldAgenda);
            this.reminderPlannerTask.get(this.taskHeadingPrompt).remove(OldAgenda);
            this.reminderPlannerTask.get(this.taskHeadingPrompt).add(TaskIndex, NewAgenda);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param TaskName the task name the user wants to change status
     * @param TaskStatus the status the user wants to change
     * @return true iff the task status is corrected changed.
     */
    @Override
    public Boolean ChangeTaskStatus(String TaskName, String TaskStatus){
        if (this.reminderPlannerTask.get(this.taskHeadingPrompt).contains(TaskName)){
            int TaskIndex = this.reminderPlannerTask.get(this.taskHeadingPrompt).indexOf(TaskName);
            if (Objects.equals(this.reminderPlannerTask.
                    get(this.completionStatusHeadingPrompt).get(TaskIndex), "incomplete")){
                this.reminderPlannerTask.get(completionStatusHeadingPrompt).remove(TaskIndex);
                this.reminderPlannerTask.get(this.taskHeadingPrompt).add(TaskIndex, "completed");
            } else if (Objects.equals(this.reminderPlannerTask.
                    get(this.completionStatusHeadingPrompt).get(TaskIndex), "completed")){
                this.reminderPlannerTask.get(completionStatusHeadingPrompt).remove(TaskIndex);
                this.reminderPlannerTask.get(this.taskHeadingPrompt).add(TaskIndex, "incomplete");
            }
            return true;
            } else{
            return false;
        }
    }
}
