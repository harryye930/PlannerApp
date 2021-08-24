package entity;

import java.util.List;

/**
 * Represents one type of Template in the program - a Project Template, which allows users to create a Planner where
 * users can fill in tasks for a particular project based on the status of the tasks.
 */
public class ProjectTemplate extends Template{
    /**
     * Prompts that are unique to this template:
     * firstStatusPrompt: Prompt asking for the first status column heading (e.g., To Do).
     * secondStatusPrompt: Prompt asking for the second status column heading (e.g., Doing).
     * thirdStatusPrompt: Prompt asking for the third status column heading (e.g., Completed).
     */
    private String firstStatusPrompt, secondStatusPrompt, thirdStatusPrompt;
    private String type;
    private final int id;

    /**
     * Constructs a ProjectTemplate object.
     * @param name Name of this template.
     * @param plannerNamePrompt Prompt for getting the name of the planner that can be created from this template.
     */
    public ProjectTemplate(String name, String plannerNamePrompt,
                           String firstStatusPrompt, String secondStatusPrompt, String thirdStatusPrompt) {
        super(name, plannerNamePrompt);
        this.id = super.getId();
        this.firstStatusPrompt = firstStatusPrompt;
        this.secondStatusPrompt = secondStatusPrompt;
        this.thirdStatusPrompt = thirdStatusPrompt;
        this.type = "project";
    }

    /**
     * Constructs a ProjectTemplate object.
     * @param numTemplatesLoaded Number of Templates already loaded in the program. So the ID of the template will start
     *                            from numTemplatesLoaded + 1.
     */
    public ProjectTemplate(int numTemplatesLoaded, String name, String plannerNamePrompt,
                           String firstStatusPrompt, String secondStatusPrompt, String thirdStatusPrompt) {
        super(numTemplatesLoaded, name, plannerNamePrompt);
        this.id = super.getId();
        this.firstStatusPrompt = firstStatusPrompt;
        this.secondStatusPrompt = secondStatusPrompt;
        this.thirdStatusPrompt = thirdStatusPrompt;
        this.type = "project";
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
        prompts.add(firstStatusPrompt);
        prompts.add(secondStatusPrompt);
        prompts.add(thirdStatusPrompt);
        return prompts;
    }
}
