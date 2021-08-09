package UseCase;

import Entity.DailyTemplate;
import Entity.ProjectTemplate;
import Entity.RemindersTemplate;
import Entity.Template;
import java.io.*;
import java.util.*;

/**
 * Manages templates.
 */
public class TemplateManager implements Serializable {

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

    public void createDailyTemplate(String name, String plannerNamePrompt, String startTimePrompt,
                                    String endTimePrompt, String incrementPrompt){
        DailyTemplate dailyTemplate = new DailyTemplate(name, plannerNamePrompt, startTimePrompt,
                endTimePrompt, incrementPrompt);
        this.addTemplate(dailyTemplate);
    }

    public void createProjectTemplate(String name, String plannerNamePrompt, String firstStatusPrompt,
                                      String secondStatusPrompt, String thirdStatusPrompt){
        ProjectTemplate projectTemplate = new ProjectTemplate(name, plannerNamePrompt, firstStatusPrompt,
                                                                secondStatusPrompt, thirdStatusPrompt);
        this.addTemplate(projectTemplate);
    }

    public void createRemindersTemplate(String name, String plannerNamePrompt, String taskHeadingPrompt,
                                        String dateHeadingPrompt, String completionStatusHeadingPrompt){
        RemindersTemplate remindersTemplate = new RemindersTemplate(name, plannerNamePrompt, taskHeadingPrompt,
                                                                    dateHeadingPrompt, completionStatusHeadingPrompt);
        this.addTemplate(remindersTemplate);
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
    public void setTemplateName(int ID, String newName) {
        this.getTemplates().get(ID).setName(newName);
    }

    /**
     * Retrieves prompts of the Template with ID.
     * @param ID ID of template being edited.
     * @return An ArrayList that's contains prompts.
     */
    public ArrayList<String> retrievePrompts(int ID) {
        return this.getTemplates().get(ID).retrievePrompts();
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

//    /**
//     * For Template with ID, adds a new prompt named promptName to the list of prompts and numbers it with promptNumber
//     * (i.e., all existing prompts that have number equal to or greater than promptNumber will have their number
//     * increased by 1).
//     * Note that the prompt numbers start from 0.
//     * @param ID ID of template being edited.
//     * @param promptNumber The number to give to the new prompt.
//     * @param promptName The name to give to the new prompt.
//     */
//    public void addTemplatePrompt(int ID, int promptNumber, String promptName){
//        this.getTemplates().get(ID).addPrompt(promptNumber, promptName);
//    }
//
//    /**
//     * For Template with ID, adds a new prompt named promptName to the end of the existing list of prompts.
//     * @param ID ID of template being edited.
//     * @param promptName The name to give to the new prompt.
//     */
//    public void addTemplatePrompt(int ID, String promptName){
//        this.getTemplates().get(ID).addPrompt(promptName);
//    }
//
//    /**
//     * For Template with ID,  removes an existing prompt with promptNumber.
//     * @param ID ID of template being edited.
//     * @param promptNumber The number that corresponds to the prompt to remove.
//     */
//    public void removeTemplatePrompt(int ID, int promptNumber){
//        this.getTemplates().get(ID).removePrompt(promptNumber);
//    }
    /**
     * Retrieves and returns all published templates stored in this TemplateManager (i.e., templates that are viewable
     * by all users).
     * A template is published if their publishedStatus is true.
     * @return Map<Integer, Template> which contains all published templates.
     */
    public Map<Integer, Template> retrievePublishedTemplates() {
        Map<Integer, Template> publishedTemplates = new HashMap<>();
        for (Map.Entry<Integer, Template> items: this.getTemplates().entrySet()) {
            Template value = items.getValue();
            if (value.getPublishedStatus()) {
                publishedTemplates.put(value.getId(), value);
            }
        }
        return publishedTemplates;
    }

    /**
     * @return Number of templates in TemplateManager.
     */
    public int numberOfTemplates() {
        return templates.size();
    }

    /**
     * Returns number of published templates in the TemplateManager (i.e., templates that are viewable by all users).
     * @return Number of published template in TemplateManager.
     */
    public int numberOfPublishedTemplates() {
        return retrievePublishedTemplates().size();
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
     * Returns a string representation of the published templates stored in this TemplateManager , with the option of:
     * - either including full details of each Template stored in it;
     * - or including only the summary of each Template stored in it.
     * @param viewOption Can only have one of these two values: "Detail", "Summary".
     * @return String that contains a representation of all Template objects stored in the TemplateManager.
     */
    public String viewPublishedInTemplateManager (String viewOption) throws IllegalArgumentException{
        if ((!viewOption.equals("Detail")) & (!viewOption.equals("Summary"))){
            throw new IllegalArgumentException(
                    String.format("Invalid user input %s. Please enter either \"Detail\" or \"Summary\".", viewOption));
        }
        String stringRep = "Number of templates stored in the TemplateManager: " + this.numberOfPublishedTemplates()
                + "\n";
        stringRep += "Templates: " + "\n";

        // Traverse through all key-value pairs in templates, and add those templates' string representation
        // to stringRep.
        for (Map.Entry<Integer, Template> items: retrievePublishedTemplates().entrySet()){
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

    /**
     * Preview all Templates stored in the system.
     * This method is used when user wants to see a list of all Templates, before they select which template they want
     * to see in detail.
     * @param publishedTemplatesOnly Boolean indicating whether to show only published templates or not.
     * @return String that contains preview of all Template objects stored in the system.
     */
    public String previewAllTemplates(boolean publishedTemplatesOnly){
        if (publishedTemplatesOnly) {
            return this.viewPublishedInTemplateManager("Summary");
        } else {
            return this.viewTemplateManager("Summary");
        }
    }

    /**
     * View all Templates stored in the system in detail.
     * This method is used when user wants to see a list of all Templates in detail.
     * @param publishedTemplatesOnly Boolean indicating whether to show only published templates or not.
     * @return String that contains detailed representation of all Template objects stored in the system.
     */
    public String detailViewAllTemplates(boolean publishedTemplatesOnly){
        if (publishedTemplatesOnly) {
            return this.viewPublishedInTemplateManager("Detail");
        } else {
            return this.viewTemplateManager("Detail");
        }
    }

    /**
     * Return the type of the template
     * @param id A int representing the id of the template.
     * @return A String representing the Type of the template.
     */
    public String getType(int id) {
        Map<Integer, Template> hm = this.getTemplates();
        return hm.get(id).isType();
    }

    /**
     * Returns the published status of a given template.
     * @param id A int representing the id of the template.
     * @return A boolean representing the Type of the template.
     */
    public boolean getPublishedStatus(int id) {
        Map<Integer, Template> hm = this.getTemplates();
        return hm.get(id).getPublishedStatus();
    }

    /**
     * Changes the published status of a given template (i.e., published to unpublished, vice versa).
     * @param id A int representing the id of the template.
     */
    public void switchPublishedStatus(int id) {
        Map<Integer, Template> hm = this.getTemplates();
        hm.get(id).switchPublishStatus();
    }
}
