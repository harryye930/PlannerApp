package Entity;

import java.util.ArrayList;

/**
 * Represents one type of Template in the program - a Task Reminder Template, which allows users to create a Planner
 * where users can fill in tasks for a particular project and the date of completion and its completion status.
 */
public class RemindersTemplate extends Template{
    /**
     * Indexes of of specific setting required for all ProjectTemplates (i.e., 3 status headings should be specified),
     * and an index indicating where the set of prompts asked for every events starts.
     */
    private final int TASK_HEADING = 0; // e.g., Tasks
    private final int DATE_HEADING = 1; // e.g., Date
    private final int COMPLETION_STATUS_HEADING = 2; // e.g., Completed (yes/no)
    private int id;

    public RemindersTemplate(String name, ArrayList<String> prompts) {
        super(name, prompts);
        this.id = super.getId();
    }

    @Override
    public String isType() {
        return "Reminders";
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the setting prompt for the task heading (i.e., Tasks)
     */
    public String taskHeadingPrompt() {
        return this.getPrompts().get(TASK_HEADING);
    }

    /**
     * @return the setting prompt for the date heading (i.e., Date)
     */
    public String dateHeadingPrompt() {
        return this.getPrompts().get(DATE_HEADING);
    }

    /**
     * @return the setting prompt for the completion status heading (i.e., Completed)
     */
    public String completionStatusPrompt() {
        return this.getPrompts().get(COMPLETION_STATUS_HEADING);
    }
}
