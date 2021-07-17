package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: Add save template method.

/**
 * Represents a Template in the program. Can be a Daily Template, or a Project Template, etc., which are
 * implemented as subclasses.
 */
public abstract class Template implements Serializable {
    private String name; // name of this template
    private ArrayList<String> prompts; // prompts of this template
    private static int id; // id of this template

    public Template(String name, ArrayList<String> prompts) {
        this.name = name;
        this.prompts = prompts;
        id++;
    }

    /**
     * Setter for changing the name of the template.
     * @param newName The new name for template.
     */
    public void setName(String newName){
        name = newName;
    }

    /**
     * Getter for retrieving the name of the template.
     * @return String that's the name of the template.
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for retrieving the prompts of the template.
     * @return ArrayList<String> that contains prompts of the template.
     */
    public ArrayList<String> getPrompts() {
        return prompts;
    }

    /**
     * Getter for retrieving the id of this template.
     * @return int that represent the id of this template.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the Template object.
     * @return String that represents the Template object in detail.
     */
    public String toString() {
        String stringRep = "ID: " + this.getId() + "\n";
        stringRep += "Name: " + this.getName() + "\n";
        stringRep += "Type: " + this.isType() + "\n\n";
        stringRep += "Prompts: " + "\n";
        int i = 0;
        for (String p: this.getPrompts()){
            stringRep += "Prompt" + i + ": " + p + "\n";
            i++;
        }
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
        String preview = "ID: " + this.getId() + "\n";
        preview += "Name: " + this.getName() + "\n";
        preview += "Type: " + this.isType() + "\n";
        preview += "Number of Prompts: " + this.numPrompts() + "\n";
        return preview;
    }

    /**
     * Returns the number of prompts of the template.
     * @return int that represents the number of prompts.
     */
    public int numPrompts() {
        return prompts.size();
    }

    /**
     * Returns the type of the template.
     * @return String that's the type of the template.
     */
    public abstract String isType();

    /**
     * Renames the prompt with the corresponding promptNumber to newName. Note that the prompt numbers start from 0.
     * @param promptNumber The number of the prompt to rename.
     * @param newName The new name to give to the prompt.
     */
    public void renamePrompt(int promptNumber, String newName){
        // promptNumber corresponds to the index of the prompt in the ArrayList prompts.
        this.prompts.set(promptNumber, newName);
    }

    /**
     * Adds a new prompt named promptName to the list of prompts and number it with promptNumber (i.e., all existing
     * prompts that have number equal to or greater than promptNumber will have their number increased by 1).
     * Note that the prompt numbers start from 0.
     * @param promptNumber The number to give to the new prompt.
     * @param promptName The name to give to the new prompt.
     */
    public void addPrompt(int promptNumber, String promptName){
        this.prompts.add(promptNumber, promptName);
    }

    /**
     * Adds a new prompt named promptName to the end of the existing list of prompts.
     * @param promptName The name to give to the new prompt.
     */
    public void addPrompt(String promptName){
        addPrompt(this.numPrompts(), promptName);
    }

    /**
     * Removes an existing prompt with promptNumber.
     * @param promptNumber The number that corresponds to the prompt to remove.
     */
    public void removePrompt(int promptNumber){
        this.prompts.remove(promptNumber);
    }

}
