package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Template in the program. Can be a Daily Template, or a Project Template, etc., which are
 * implemented as subclasses.
 */
public abstract class Template implements Serializable {
    /**
     * name: Name of this template.
     * plannerNamePrompt: Prompt for the planner name that can be created based on this template.
     * id: ID of this template.
     * publishedStatus: Published status of this template (if true, the template is published).
     * templateType: Type of this template.
     */
    protected String name;
    protected String plannerNamePrompt;
    private static int id;
    protected boolean publishedStatus;
    private String type;

    public Template(String name, String plannerNamePrompt) {
        this.name = name;
        this.plannerNamePrompt = plannerNamePrompt;
        publishedStatus = false;  // the default published status of all templates are false (i.e., unpublished)
        type = null;
        id++;
    }

//    /**
//     * Set the type of the template
//     * @param type A String representing the type of the template.
//     */
//    public void setType(String type) {
//        this.type = type;
//    }

    /**
     * Setter for changing the name of this template.
     * @param newName A new name for this template.
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Getter for retrieving the name of this template.
     * @return String that's the name of this template.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for changing the prompt for the name of the planner that will be created based on this template.
     * @param newPrompt A new prompt for the template.
     */
    public void setPlannerNamePrompt(String newPrompt){
        plannerNamePrompt = newPrompt;
    }

    /**
     * Getter for retrieving the prompt for the name of the planner that will be created based on this template.
     * @return String that's the prompt for the name of this template.
     */
    public String getPlannerNamePrompt(){
        return plannerNamePrompt;
    }

    /**
     * Getter for retrieving the id of this template.
     * @return int that represent the id of this template.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for changing the ID of this template.
     * @param id New ID for this template.
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * Setter for changing the published status of this template.
     * @param newStatus A new published status for this template.
     */
    public void setPublishedStatus(boolean newStatus) {
        publishedStatus = newStatus;
    }

    /**
     * Getter for retrieving the published status of this template.
     * @return boolean indicating whether this template is published (i.e., visible to users) or not.
     */
    public boolean getPublishedStatus() {
        return publishedStatus;
    }

    /**
     * Switch publishedStatus of this template. Switched to unpublished if current status is published, vice versa.
     */
    public void switchPublishStatus() {
        publishedStatus = !publishedStatus;
    }

    /**
     * Returns a string representation of the Template object.
     * @return String that represents the Template object in detail.
     */
    public String toString() {
        String stringRep = "---------------------------------------------------------\n";
        stringRep += "ID: " + this.getId() + "\n";
        stringRep += "Name: " + this.getName() + "\n";
        stringRep += "Type: " + this.isType() + "\n";
        stringRep += "Published: " + this.getPublishedStatus() + "\n\n";
        stringRep += "Prompts: " + "\n";
        int i = 0;
        for (String p: retrievePrompts()){
            stringRep += "Prompt " + i + ": " + p + "\n";
            i++;
        }
        stringRep += "---------------------------------------------------------\n";
        return stringRep;
    }

    /**
     * Returns a preview of Template object that just includes the basic and identifying information of a Template,
     * in String format.
     * Different from the toString method in that the preview only includes the basic information, and does nto include
     * the detailed prompts.
     * @return String preview of Template that includes basic information.
     */
    public String getTemplatePreview(){
        String preview = "---------------------------------------------------------\n";
        preview += "ID: " + this.getId() + "\n";
        preview += "Name: " + this.getName() + "\n";
        preview += "Type: " + this.isType() + "\n";
        preview += "Number of Prompts: " + this.numPrompts() + "\n";
        preview += "---------------------------------------------------------\n";
        return preview;
    }

    /**
     * Returns the number of prompts of the template.
     * @return int that represents the number of prompts.
     */
    public int numPrompts() {
        return retrievePrompts().size();
    }

    /**
     * Returns true if the provided prompt is one of the prompts of this template.
     * @param prompt is the given prompt being checked
     * @return a boolean that represents the presence or absence of the given prompt.
     */
    protected boolean hasPrompt(String prompt) {
        List<String> templatePrompts = retrievePrompts();
        return templatePrompts.contains(prompt);
    }

    /**
     * Returns the type of the template.
     * @return String that's the type of the template.
     */
    public abstract String isType();

    /**
     * Returns all the prompts of this template.
     * @return List<String> that contains prompts of the template.
     */
    public List<String> retrievePrompts() {
        List<String> prompts = new ArrayList<>();
        prompts.add(plannerNamePrompt);
        return prompts;
    }

    /**
     * Replace the old prompt of this template with the new prompt.
     * If the provided old prompt is not one of the prompts in this template, it does nothing.
     * @param oldPrompt is the provided prompt to be replaced.
     * @param newPrompt is the new prompt provided to replace the old prompt.
     */
    public void replacePrompt(String oldPrompt, String newPrompt) {
        if (oldPrompt.equals(plannerNamePrompt)) {
            setPlannerNamePrompt(newPrompt);
        }
    }

    /**
     * Creates and returns map containing prompt numbers and prompts of this template.
     * Assigns prompt number to each prompt starting from 0, increments by 1.
     * @return Map with unique integers as keys (i.e., prompt numbers) and prompts as values.
     */
    private Map<Integer, String> assignPromptNumberToPrompts() {
        // Numbers assigned to the prompts correspond to the index of the prompt in the ArrayList prompts.
        List<String> templatePrompts = retrievePrompts();
        Map<Integer, String> promptsToNumbers = new HashMap<>();
        int i = 0;
        for (String prompt: templatePrompts) {
            promptsToNumbers.put(i, prompt);
            i++;
        }
        return promptsToNumbers;
    }

    /**
     * Returns the prompt at a given prompt number.
     * @param promptNumber The number of the prompt to retrieve.
     * @return String that's the prompt of the template with the provided prompt number.
     */
    private String findPrompt(int promptNumber) {
        Map<Integer, String> templatePromptsToNumber = assignPromptNumberToPrompts();
        return templatePromptsToNumber.get(promptNumber);
    }

    /**
     * Renames the prompt with the corresponding promptNumber to newName. Note that the prompt numbers start from 0.
     * @param promptNumber The number of the prompt to rename.
     * @param newName The new name to give to the prompt.
     */
    public void renamePrompt(int promptNumber, String newName){
        replacePrompt(findPrompt(promptNumber), newName);
    }

//    /**
//     * Adds a new prompt named promptName to the list of prompts and number it with promptNumber (i.e., all existing
//     * prompts that have number equal to or greater than promptNumber will have their number increased by 1).
//     * Note that the prompt numbers start from 0.
//     * @param promptNumber The number to give to the new prompt.
//     * @param promptName The name to give to the new prompt.
//     */
//    public void addPrompt(int promptNumber, String promptName){
//        this.prompts.add(promptNumber, promptName);
//    }
//
//    /**
//     * Adds a new prompt named promptName to the end of the existing list of prompts.
//     * @param promptName The name to give to the new prompt.
//     */
//    public void addPrompt(String promptName){
//        addPrompt(this.numPrompts(), promptName);
//    }
//
//    /**
//     * Removes an existing prompt with promptNumber.
//     * @param promptNumber The number that corresponds to the prompt to remove.
//     */
//    public void removePrompt(int promptNumber){
//        this.prompts.remove(promptNumber);
//    }

}
