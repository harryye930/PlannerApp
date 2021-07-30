package Entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents one type of Template in the program - a Task Template, which allows users to create a Planner where
 * users can fill in tasks for a particular project based on the status of the tasks.
 */
public class ProjectTemplate extends Template{
    /**
     * Indexes of of specific setting required for all ProjectTemplates (i.e., 3 status headings should be specified),
     * and an index indicating where the set of prompts asked for every events starts.
     */
    private final int STATUS_START = 0; // e.g., to do
    private final int STATUS_MIDDLE = 1; // e.g., doing
    private final int STATUS_END = 2; // e.g., done
    private int id;

    public ProjectTemplate(String name, ArrayList<String> prompts) {
        super(name, prompts);
        this.id = super.getId();
    }

    @Override
    public String isType() {
        return "Project";
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the setting prompt for the first heading (i.e., start)
     */
    public String firstStatusPrompt() {
        return this.getPrompts().get(STATUS_START);
    }

    /**
     * @return the setting prompt for the second heading (i.e., middle)
     */
    public String secondStatusPrompt() {
        return this.getPrompts().get(STATUS_MIDDLE);
    }

    /**
     * @return the setting prompt for the third heading (i.e., end)
     */
    public String thirdStatusPrompt() {
        return this.getPrompts().get(STATUS_END);
    }

}
