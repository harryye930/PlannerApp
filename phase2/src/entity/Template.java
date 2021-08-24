package entity;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a Template in the program.
 * Can be a Daily Template, or a Project Template, etc., which are implemented as subclasses.
 */
public abstract class Template implements Serializable {
    /**
     * name: Name of this template.
     * plannerNamePrompt: Prompt for getting the name of the planner that can be created from this template.
     * id: ID of this template.
     * type: Type of this template.
     * publishedStatus: Published status of this template (if true, the template is published).
     */
    protected String name;
    protected String plannerNamePrompt;
    private static int id;
    private String type;
    protected boolean publishedStatus;

    /**
     * Constructs a new Template object named name, and has plannerNamePrompt.
     * @param name Name of this template.
     * @param plannerNamePrompt Prompt for getting the name of the planner that can be created from this template.
     */
    public Template(String name, String plannerNamePrompt) {
        this.name = name;
        this.plannerNamePrompt = plannerNamePrompt;
        publishedStatus = false;  // the default published status of all templates are false (i.e., unpublished)
        type = null;
        id++;
    }

    /**
     * Constructs a new Template object named name, and has plannerNamePrompt.
     * @param numTemplatesLoaded Number of Templates already loaded in the program. So the ID of the template will start
     *                           from numTemplatesLoaded + 1.
     */
    public Template(int numTemplatesLoaded, String name, String plannerNamePrompt) {
        this.name = name;
        this.plannerNamePrompt = plannerNamePrompt;
        publishedStatus = false;  // the default published status of all templates are false (i.e., unpublished)
        type = null;
        id = numTemplatesLoaded;
        id++;
    }

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
     * Getter for retrieving the id of this template.
     * @return Integer that represents the id of this template.
     */
    public int getId() {
        return id;
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
     * @return boolean indicating whether this template is published (i.e., visible to regular users) or not.
     */
    public boolean getPublishedStatus() {
        return publishedStatus;
    }

    /**
     * Toggle publishedStatus of this template. Switched to unpublished if current status is published, and vice versa.
     */
    public void switchPublishedStatus() {
        publishedStatus = !publishedStatus;
    }

    /**
     * Returns a string representation of the Template object.
     * @return String that represents the Template object in detail.
     */
    public String toString() {
        StringBuilder stringRep = new StringBuilder("---------------------------------------------------------\n");
        stringRep.append("ID: ").append(this.getId()).append("\n");
        stringRep.append("Name: ").append(this.getName()).append("\n");
        stringRep.append("Type: ").append(this.isType()).append("\n");
        stringRep.append("Published: ").append(this.getPublishedStatus()).append("\n\n");
        stringRep.append("Prompts: " + "\n");
        for (Map.Entry<Integer, String> entry: assignPromptNumberToPrompts().entrySet()){
            stringRep.append("Prompt ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        stringRep.append("---------------------------------------------------------\n");
        return stringRep.toString();
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
     * Returns the number of prompts in this template.
     * @return int that represents the number of prompts.
     */
    public int numPrompts() {
        return retrievePrompts().size();
    }

    /**
     * Returns the type of this template.
     * @return String that's the type of this template.
     */
    public abstract String isType();

    /**
     * Returns all the prompts of this template.
     * @return List<String> that contains all the prompts of the template.
     */
    public List<String> retrievePrompts() {
        List<String> prompts = new ArrayList<>();
        prompts.add(plannerNamePrompt);
        return prompts;
    }

    /**
     * Creates and returns map containing prompt numbers and prompts of this template.
     * Assigns prompt number to each prompt starting from 0, increments by 1.
     * @return Map with unique integers as keys (i.e., prompt numbers) and prompts as values.
     */
    private Map<Integer, String> assignPromptNumberToPrompts() {
        // Numbers assigned to the prompts correspond to the index of the prompt in the ArrayList prompts.
        Map<Integer, String> promptsToNumbers = new HashMap<>();
        int i = 0;
        for (String prompt: retrievePrompts()) {
            promptsToNumbers.put(i, prompt);
            i++;
        }
        return promptsToNumbers;
    }
}
