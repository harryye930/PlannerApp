package entity;

import java.util.List;

/**
 * Represents one type of Template in the program - a Daily Template, which allows users to create a Planner where
 * users can fill in tasks planned for hours in a particular day.
 */
public class DailyTemplate extends Template{
    /**
     * Prompts that are unique to this template:
     * startTimePrompt: Prompt for the start time of the planner that will be created based on this template.
     * endTimePrompt: Prompt for the end time of the planner that will be created based on this template.
     * incrementPrompt: Prompt for the time increment of the planner that will be created based on this template.
     */
    private String startTimePrompt, endTimePrompt, incrementPrompt;
    private String type;
    private final int id;

    /**
     * Constructs a DailyTemplate object.
     * @param name Name of this template.
     * @param plannerNamePrompt Prompt for getting the name of the planner that can be created from this template.
     */
    public DailyTemplate(String name, String plannerNamePrompt,
                         String startTimePrompt, String endTimePrompt, String incrementPrompt) {
        super(name, plannerNamePrompt);
        this.id = super.getId();
        this.startTimePrompt = startTimePrompt;
        this.endTimePrompt = endTimePrompt;
        this.incrementPrompt = incrementPrompt;
        this.type = "daily";
    }

    /**
     * Constructs a DailyTemplate object.
     * @param numTemplatesLoaded Number of Templates already loaded in the program. So the ID of the template will start
     *                           from numTemplatesLoaded + 1.
     */
    public DailyTemplate(int numTemplatesLoaded, String name, String plannerNamePrompt,
                         String startTimePrompt, String endTimePrompt, String incrementPrompt) {
        super(numTemplatesLoaded, name, plannerNamePrompt);
        this.id = super.getId();
        this.startTimePrompt = startTimePrompt;
        this.endTimePrompt = endTimePrompt;
        this.incrementPrompt = incrementPrompt;
        this.type = "daily";
    }

    /**
     * Setter for changing the prompt for the start time of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    private void setStartTimePrompt (String newPrompt){
        startTimePrompt = newPrompt;
    }

    /**
     * Setter for changing the prompt for the end time of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    private void setEndTimePrompt (String newPrompt){
        endTimePrompt = newPrompt;
    }

    /**
     * Setter for changing the prompt for the time increment of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    private void setIncrementPrompt (String newPrompt){
        incrementPrompt = newPrompt;
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
     * @return List<String> that contains all the prompts of this template.
     */
    @Override
    public List<String> retrievePrompts() {
        List<String> prompts = super.retrievePrompts();
        prompts.add(startTimePrompt);
        prompts.add(endTimePrompt);
        prompts.add(incrementPrompt);
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
        if (oldPrompt.equals(startTimePrompt)) {
            setStartTimePrompt(newPrompt);
        }
        if (oldPrompt.equals(endTimePrompt)) {
            setEndTimePrompt(newPrompt);
        }
        if (oldPrompt.equals(incrementPrompt)) {
            setIncrementPrompt(newPrompt);
        }
    }
}
