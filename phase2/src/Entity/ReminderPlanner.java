package Entity;


import java.util.ArrayList;
import java.util.HashMap;

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
     * @param s1 the task heading it wants to add.
     * @param s2 the task date it wants to add.
     * @return true iff the agenda is correctly added to current planner.
     */
    @Override
    public Boolean add (String s1, String s2){
        this.reminderPlannerTask.get(this.taskHeadingPrompt).add(s1);
        this.reminderPlannerTask.get(this.dateHeadingPrompt).add(s2);
        this.NumAgendas ++;
        return true;
    }


    @Override
    public Boolean edit(String OldAgenda, String NewAgenda){
        return true;
    }

    @Override
    public Boolean ChangeTaskStatus(String TaskName, String TaskStatus){
        return true;
    }
}
