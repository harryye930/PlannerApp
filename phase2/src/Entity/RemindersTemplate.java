package Entity;

import java.util.ArrayList;

/**
 * Represents one type of Template in the program - a Reminder Template, which allows users to create a Planner
 * where users can fill in tasks for a particular project and the date of completion and its completion status.
 */
public class RemindersTemplate extends Template{
//    /**
//     * Prompts that are unique to this template.
//     * taskHeadingPrompt: the prompt for the heading of the tasks (e.g., Tasks To Be Completed)
//     * dateHeadingPrompt: the prompt for the date at which each task should be completed by (e.g., Date)
//     * completionStatusHeadingPrompt: the prompt for the completion status heading (e.g., Completed (Y/N))
//     */
    private String taskHeadingPrompt, dateHeadingPrompt, completionStatusHeadingPrompt;
    private int id;

    /**
     *
     * @param name the name of the template
     * @param plannerNamePrompt the planner name that corresponds to the template
     * @param taskHeadingPrompt the prompt for the heading of the tasks (e.g., Tasks To Be Completed)
     * @param dateHeadingPrompt the prompt for the date at which each task should be completed by (e.g., Date)
     * @param completionStatusHeadingPrompt the prompt for the completion status heading (e.g., Completed (Y/N))
     */
    public RemindersTemplate(String name, String plannerNamePrompt,
                             String taskHeadingPrompt, String dateHeadingPrompt, String completionStatusHeadingPrompt) {
        super(name, plannerNamePrompt);
        this.id = super.getId();
        this.taskHeadingPrompt = taskHeadingPrompt;
        this.dateHeadingPrompt = dateHeadingPrompt;
        this.completionStatusHeadingPrompt = completionStatusHeadingPrompt;
        this.setTemplateType("reminders");
    }

    /**
     * Setter for changing the prompt for the task heading of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setTaskHeadingPrompt (String newPrompt){
        taskHeadingPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the task heading of the planner that will be created based on this template.
     * @return String that's the prompt for the task heading of this template.
     */
    public String getTaskHeadingPrompt (){
        return taskHeadingPrompt;
    }

    /**
     * Setter for changing the prompt for the date heading of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setDateHeadingPrompt (String newPrompt){
        dateHeadingPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the date heading of the planner that will be created based on this template.
     * @return String that's the prompt for the date heading of this template.
     */
    public String getDateHeadingPrompt (){
        return dateHeadingPrompt;
    }

    /**
     * Setter for changing the prompt for the completion status heading of the planner that will be created based on
     * this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setCompletionStatusHeadingPrompt (String newPrompt){
        completionStatusHeadingPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the completion status heading of the planner that will be created based on
     * this template.
     * @return String that's the prompt for the completion status heading of this template.
     */
    public String getCompletionStatusHeadingPrompt (){
        return completionStatusHeadingPrompt;
    }

    @Override
    public String isType() {
        return "reminders";
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns all the prompts of this template.
     * @return ArrayList<String> that contains prompts of the template.
     */
    @Override
    public ArrayList<String> retrievePrompts() {
        ArrayList<String> prompts = super.retrievePrompts();
        prompts.add(taskHeadingPrompt);
        prompts.add(dateHeadingPrompt);
        prompts.add(completionStatusHeadingPrompt);
        return prompts;
    }

    /**
     * Replace the old prompt of this template with the new prompt.
     * If the provided old prompt is not one of the prompts in this template, it does nothing.
     * @param oldPrompt is the provided prompt to be replaced.
     * @param newPrompt is the new prompt provided to replace the old prompt.
     */
    @Override
    public void replacePrompt(String oldPrompt, String newPrompt) {
        super.replacePrompt(oldPrompt, newPrompt);
        if (oldPrompt.equals(taskHeadingPrompt)) {
            setTaskHeadingPrompt(newPrompt);
        }
        if (oldPrompt.equals(dateHeadingPrompt)) {
            setDateHeadingPrompt(newPrompt);
        }
        if (oldPrompt.equals(completionStatusHeadingPrompt)) {
            setCompletionStatusHeadingPrompt(newPrompt);
        }
    }
}
