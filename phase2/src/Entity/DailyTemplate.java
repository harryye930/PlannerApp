package Entity;

import java.util.ArrayList;

/**
 * Represents one type of Template in the program - a Daily Template, which allows users to create a Planner where
 * users can fill in tasks planned for hours in a particular day.
 */
public class DailyTemplate extends Template{
    /**
     * Prompts that are unique to this template.
     * startTimePrompt: the prompt for the start time of the planner that will be created based on this template.
     * endTimePrompt: the prompt for the end time of the planner that will be created based on this template.
     * incrementPrompt: the prompt for the time increment of the planner that will be created based on this template.
     */
    private String startTimePrompt, endTimePrompt, incrementPrompt;
    private int id;

    public DailyTemplate(String name, String plannerNamePrompt,
                         String startTimePrompt, String endTimePrompt, String incrementPrompt) {
        super(name, plannerNamePrompt);
        this.id = super.getId();
        this.startTimePrompt = startTimePrompt;
        this.endTimePrompt = endTimePrompt;
        this.incrementPrompt = incrementPrompt;
    }

    /**
     * Setter for changing the prompt for the start time of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setStartTimePrompt (String newPrompt){
        startTimePrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the start time of the planner that will be created based on this template.
     * @return String that's the prompt for the start time of this template.
     */
    public String getStartTimePrompt (){
        return startTimePrompt;
    }

    /**
     * Setter for changing the prompt for the end time of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setEndTimePrompt (String newPrompt){
        endTimePrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the end time of the planner that will be created based on this template.
     * @return String that's the prompt for the end time of this template.
     */
    public String getEndTimePrompt (){
        return endTimePrompt;
    }

    /**
     * Setter for changing the prompt for the time increment of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setIncrementPrompt (String newPrompt){
        incrementPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the time increment of the planner that will be created
     * based on this template.
     * @return String that's the prompt for the time increment of this template.
     */
    public String getIncrementPrompt (){
        return incrementPrompt;
    }

    @Override
    public String isType() {
        return "Daily";
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
        prompts.add(startTimePrompt);
        prompts.add(endTimePrompt);
        prompts.add(incrementPrompt);
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
