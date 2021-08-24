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
}
