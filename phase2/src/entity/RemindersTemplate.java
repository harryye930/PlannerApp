package entity;

import java.util.List;

/**
 * Represents one type of Template in the program - a Reminders Template, which allows users to create a Planner
 * where users can fill in tasks for a particular project, the required completion dates and their completion status.
 */
public class RemindersTemplate extends Template{
    /**
     * Prompts that are unique to this template.
     * taskHeadingPrompt: the prompt asking for the heading of the tasks column(e.g., Tasks To Be Completed)
     * dateHeadingPrompt: the prompt asking for the heading of column with dates at which each task should be completed by (e.g., Date)
     * completionStatusHeadingPrompt: the prompt asking for the heading of completion status column (e.g., Completed (Y/N))
     */
    private String taskHeadingPrompt, dateHeadingPrompt, completionStatusHeadingPrompt;
    private String type;
    private final int id;

    /**
     * Constructs a RemindersTemplate object.
     * @param name Name of this template.
     * @param plannerNamePrompt Prompt for getting the name of the planner that can be created from this template.
     */
    public RemindersTemplate(String name, String plannerNamePrompt,
                             String taskHeadingPrompt, String dateHeadingPrompt, String completionStatusHeadingPrompt) {
        super(name, plannerNamePrompt);
        this.id = super.getId();
        this.taskHeadingPrompt = taskHeadingPrompt;
        this.dateHeadingPrompt = dateHeadingPrompt;
        this.completionStatusHeadingPrompt = completionStatusHeadingPrompt;
        this.type = "reminders";
    }

    /**
     * Constructs a RemindersTemplate object.
     * @param numTemplatesLoaded Number of Templates already loaded in the program. So the ID of the template will start
     *                            from numTemplatesLoaded + 1.
     */
    public RemindersTemplate(int numTemplatesLoaded, String name, String plannerNamePrompt,
                             String taskHeadingPrompt, String dateHeadingPrompt, String completionStatusHeadingPrompt) {
        super(numTemplatesLoaded, name, plannerNamePrompt);
        this.id = super.getId();
        this.taskHeadingPrompt = taskHeadingPrompt;
        this.dateHeadingPrompt = dateHeadingPrompt;
        this.completionStatusHeadingPrompt = completionStatusHeadingPrompt;
        this.type = "reminders";
    }

    /**
     * Setter for changing the prompt for the task heading of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    private void setTaskHeadingPrompt (String newPrompt){
        taskHeadingPrompt = newPrompt;
    }

    /**
     * Setter for changing the prompt for the date heading of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    private void setDateHeadingPrompt (String newPrompt){
        dateHeadingPrompt = newPrompt;
    }

    /**
     * Setter for changing the prompt for the completion status heading of the planner that will be created based on
     * this template.
     * @param newPrompt A new prompt for the template.
     */
    private void setCompletionStatusHeadingPrompt (String newPrompt){
        completionStatusHeadingPrompt = newPrompt;
    }

    @Override
    public String isType() {
        return this.type;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns all the prompts of this template.
     * @return List<String> that contains prompts of the template.
     */
    @Override
    public List<String> retrievePrompts() {
        List<String> prompts = super.retrievePrompts();
        prompts.add(taskHeadingPrompt);
        prompts.add(dateHeadingPrompt);
        prompts.add(completionStatusHeadingPrompt);
        return prompts;
    }

    /**
     * Replaces the oldPrompt of this template with the newPrompt.
     * If the provided oldPrompt is not one of the prompts in this template, it does nothing.
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
