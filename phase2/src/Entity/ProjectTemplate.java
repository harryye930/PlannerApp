package Entity;

import java.util.ArrayList;

/**
 * Represents one type of Template in the program - a Task Template, which allows users to create a Planner where
 * users can fill in tasks for a particular project based on the status of the tasks.
 */
public class ProjectTemplate extends Template{
    /**
     * Prompts that are unique to this template.
     * firstStatusPrompt: the prompt for the first status heading (e.g., To Do)
     * secondStatusPrompt: the prompt for the second status heading (e.g., Doing)
     * thirdStatusPrompt: the prompt for the third status heading (e.g., Completed)
     */
    private String firstStatusPrompt, secondStatusPrompt, thirdStatusPrompt;
    private int id;

    public ProjectTemplate(String name, String plannerNamePrompt,
                           String firstStatusPrompt, String secondStatusPrompt, String thirdStatusPrompt) {
        super(name, plannerNamePrompt);
        this.id = super.getId();
        this.firstStatusPrompt = firstStatusPrompt;
        this.secondStatusPrompt = secondStatusPrompt;
        this.thirdStatusPrompt = thirdStatusPrompt;
    }

    /**
     * Setter for changing the prompt for the first status heading of the planner that will be created based on
     * this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setFirstStatusPrompt (String newPrompt){
        firstStatusPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the first status heading of the planner that will be created based on
     * this template.
     * @return String that's the prompt for the first status heading of this template.
     */
    public String getFirstStatusPrompt (){
        return firstStatusPrompt;
    }

    /**
     * Setter for changing the prompt for the second status heading of the planner that will be created based on
     * this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setSecondStatusPrompt (String newPrompt){
        secondStatusPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the second status heading of the planner that will be created based on
     * this template.
     * @return String that's the prompt for the second status heading of this template.
     */
    public String getSecondStatusPrompt (){
        return secondStatusPrompt;
    }

    /**
     * Setter for changing the prompt for the third status heading of the planner that will be created based on
     * this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setThirdStatusPrompt (String newPrompt){
        thirdStatusPrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the third status heading of the planner that will be created based on
     * this template.
     * @return String that's the prompt for the third status heading of this template.
     */
    public String getThirdStatusPrompt (){
        return thirdStatusPrompt;
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
     * Returns all the prompts of this template.
     * @return ArrayList<String> that contains prompts of the template.
     */
    @Override
    public ArrayList<String> retrievePrompts() {
        ArrayList<String> prompts = super.retrievePrompts();
        prompts.add(firstStatusPrompt);
        prompts.add(secondStatusPrompt);
        prompts.add(thirdStatusPrompt);
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
        if (oldPrompt.equals(firstStatusPrompt)) {
            setFirstStatusPrompt(newPrompt);
        }
        if (oldPrompt.equals(secondStatusPrompt)) {
            setSecondStatusPrompt(newPrompt);
        }
        if (oldPrompt.equals(thirdStatusPrompt)) {
            setThirdStatusPrompt(newPrompt);
        }
    }
}
