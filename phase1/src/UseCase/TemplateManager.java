package UseCase;

import Entity.Template;
import java.io.*;
import java.util.*;

/**
 * Manages templates.
 */
public class TemplateManager implements Serializable {

    // TODO: *** note from office hour ***
    // TODO: (1) Make template gateway - use an interface (week7) to not violate dependency rule while using gateway to
    // TODO: read the information

    //TODO: Idea for Gateway:
    // Gateway reader should return a HashMap, with key being name of the file,
    // and value being ArrayList<String> - each row in the csv file should be a String in ArrayList<String>

    private Map<Integer, Template> templates;  // a mapping of template ID to Template

    /**
     * Creates a new empty TemplateManager.
     */

    public TemplateManager(){
        templates = new HashMap<>();
    }

    /**
     * Getter for HashMap of Template objects stored in TemplateManager, keys are Template ID's, values are the Template
     * object corresponding to the ID.
     * @return HashMap of Template objects stored in TemplateManager.
     */
    public Map<Integer, Template> getTemplates() {
        return templates;
    }

    /**
     * Adds a Template to TemplateManager.
     * @param t Template being added to TemplateManager.
     */
    public void addTemplate(Template t) {
        // Add template <t> to the collection of templates stored in this TemplateManager object.
        templates.put(t.getId(), t);
    }

    /**
     * Removes a Template from TemplateManager.
     * @param t Template being removed from TemplateManager.
     */
    public void removeTemplate(Template t) {
        // Remove template <t> from the collection of templates stored in this TemplateManager object.
        templates.remove(t.getId(), t);
    }

    /**
     * Changes the name of the Template with ID to newName.
     * @param ID ID of template being edited.
     * @param newName New value to set the name of the Template to.
     */
    public void editTemplateName(int ID, String newName) {
        this.getTemplates().get(ID).setName(newName);
    }

    /**
     * For Template with ID, renames the prompt with the corresponding promptNumber to newName.
     * Note that the prompt numbers start from 0.
     * @param ID ID of template being edited.
     * @param promptNumber The number of the prompt to rename.
     * @param newName The new name to give to the prompt.
     */
    public void renameTemplatePrompt(int ID, int promptNumber, String newName) {
        this.getTemplates().get(ID).renamePrompt(promptNumber, newName);
    }

    /**
     * For Template with ID, adds a new prompt named promptName to the list of prompts and numbers it with promptNumber
     * (i.e., all existing prompts that have number equal to or greater than promptNumber will have their number
     * increased by 1).
     * Note that the prompt numbers start from 0.
     * @param ID ID of template being edited.
     * @param promptNumber The number to give to the new prompt.
     * @param promptName The name to give to the new prompt.
     */
    public void addTemplatePrompt(int ID, int promptNumber, String promptName){
        this.getTemplates().get(ID).addPrompt(promptNumber, promptName);
    }

    /**
     * For Template with ID, adds a new prompt named promptName to the end of the existing list of prompts.
     * @param ID ID of template being edited.
     * @param promptName The name to give to the new prompt.
     */
    public void addTemplatePrompt(int ID, String promptName){
        this.getTemplates().get(ID).addPrompt(promptName);
    }

    /**
     * For Template with ID,  removes an existing prompt with promptNumber.
     * @param ID ID of template being edited.
     * @param promptNumber The number that corresponds to the prompt to remove.
     */
    public void removeTemplatePrompt(int ID, int promptNumber){
        this.getTemplates().get(ID).removePrompt(promptNumber);
    }

    /**
     * @return Number of templates in TemplateManager.
     */
    public int numberOfTemplates() {
        return templates.size();
    }

    /** Returns a string representation of the TemplateManager object, with the option of:
     * - either including full details of each Template stored in it;
     * - or including only the summary of each Template stored in it.
     * @param viewOption Can only have one of these two values: "Detail", "Summary".
     * @return String that contains a representation of all Template objects stored in the TemplateManager.
     */
    public String viewTemplateManager (String viewOption) throws IllegalArgumentException{
        if ((!viewOption.equals("Detail")) & (!viewOption.equals("Summary"))){
            throw new IllegalArgumentException(
                    String.format("Invalid user input %s. Please enter either \"Detail\" or \"Summary\".", viewOption));
        }
        String stringRep = "Number of templates stored in the TemplateManager: " + this.numberOfTemplates()
                            + "\n";
        stringRep += "Templates: " + "\n";

        // Traverse through all key-value pairs in templates, and add those templates' string representation
        // to stringRep.
        for (Map.Entry<Integer, Template> items: this.getTemplates().entrySet()){
            Template value = items.getValue();

            if (viewOption.equals("Detail")){
                stringRep += value.toString();
            } else {
                stringRep += value.getTemplatePreview();
            }

            stringRep += "\n";
        }

        return stringRep;
    }

    /**
     * Get detailed string representation of a specified Template that's stored in TemplateManager.
     * @param ID ID of the Template.
     * @return String representation of the Template object corresponding to the ID. String representation contains
     * detailed representation of the Template, including name, type, number of prompts, and what those prompts are.
     */
    public String detailViewTemplate(int ID){
        return this.getTemplates().get(ID).toString();
    }

    /**
     * Get preview (a summary that only includes basic info) of a specified Template that's stored in TemplateManager.
     * @param ID ID of the Template.
     * @return String preview of Template corresponding to ID that includes basic information.
     */
    public String previewTemplate(int ID){
        return this.getTemplates().get(ID).getTemplatePreview();
    }

}
