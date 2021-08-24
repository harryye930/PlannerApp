package use_case;

import entity.DailyTemplate;
import entity.ProjectTemplate;
import entity.RemindersTemplate;
import entity.Template;
import java.io.*;
import java.util.*;

/**
 * Stores and manages Templates.
 */
public class TemplateManager implements Serializable {

    private final Map<Integer, Template> templates;  // a mapping of template ID to Template
    private int numTemplatesLoaded; // number of templates read in from external file at the start of the program
    private boolean hasInitialized = false; // flag indicating if the templates that are read in from external file
                                            // have been added to the TemplateManager

    /**
     * Creates a new empty TemplateManager.
     */
    public TemplateManager(){
        templates = new HashMap<>();
    }

    /**
     * Sets the numTemplatesLoaded attribute.
     * @param numTemplatesLoaded Number of templates read in from external file at the start of the program.
     */
    public void setNumTemplatesLoaded(int numTemplatesLoaded) {
        this.numTemplatesLoaded = numTemplatesLoaded;
    }

    /**
     * Gets the Map of Template objects stored in TemplateManager, keys are Template ID's, values are the Template
     * object corresponding to the ID.
     * @return Map of Template objects stored in TemplateManager.
     */
    public Map<Integer, Template> getTemplates() {
        return templates;
    }

    /**
     * Adds a new Template to TemplateManager.
     * @param t Template object to be added.
     */
    public void addTemplate(Template t){
        // Add template <t> to the collection of templates stored in this TemplateManager object.
        templates.put(t.getId(), t);
    }

    /**
     * Creates a Template object of type templateType, and stores it in the Template Manager.
     * @param templateType Type of the template to create. Must be one of: "daily", "project", "reminders".
     * @param templateName Name for the template.
     * @param plannerNamePrompt Prompt asking for the name of the planner to be created from this template.
     * @param firstPlannerPrompt First planner prompt, varies based on type of template.
     * @param secondPlannerPrompt Second planner prompt, varies based on type of template.
     * @param thirdPlannerPrompt Third planner prompt, varies based on type of template.
     */
    public void createTemplate(String templateType,
                               String templateName,
                               String plannerNamePrompt,
                               String firstPlannerPrompt,
                               String secondPlannerPrompt,
                               String thirdPlannerPrompt){
        Template template = getTemplate(templateType, templateName, plannerNamePrompt, firstPlannerPrompt,
                secondPlannerPrompt, thirdPlannerPrompt);
        if (template == null){
            System.out.printf("Template of type %s cannot be created.", templateType);
        } else {
            this.addTemplate(template);
        }
    }

    /**
     * Factory method for creating a Template.
     * @param templateType Type of the template to create. Must be one of: "daily", "project", "reminders".
     * @param templateName Name for the template.
     * @param plannerNamePrompt Prompt asking for the name of the planner to be created from this template.
     * @param firstPlannerPrompt First planner prompt, varies based on type of template.
     * @param secondPlannerPrompt Second planner prompt, varies based on type of template.
     * @param thirdPlannerPrompt Third planner prompt, varies based on type of template.
     * @return A Template object.
     */
    private Template getTemplate(String templateType,
                                String templateName,
                                String plannerNamePrompt,
                                String firstPlannerPrompt,
                                String secondPlannerPrompt,
                                String thirdPlannerPrompt){
        if (!hasInitialized){
            switch (templateType) {
                case "daily":
                    hasInitialized = true;
                    return new DailyTemplate(numTemplatesLoaded, templateName, plannerNamePrompt, firstPlannerPrompt,
                            secondPlannerPrompt, thirdPlannerPrompt);
                case "project":
                    hasInitialized = true;
                    return new ProjectTemplate(numTemplatesLoaded, templateName, plannerNamePrompt, firstPlannerPrompt,
                            secondPlannerPrompt, thirdPlannerPrompt);
                case "reminders":
                    hasInitialized = true;
                    return new RemindersTemplate(numTemplatesLoaded, templateName, plannerNamePrompt, firstPlannerPrompt,
                            secondPlannerPrompt, thirdPlannerPrompt);
                default:
                    System.out.printf("Template type %s is undefined for this program.", templateType);
                    return null;
            }
        } else {
            switch (templateType) {
                case "daily":
                    return new DailyTemplate(templateName, plannerNamePrompt, firstPlannerPrompt,
                            secondPlannerPrompt, thirdPlannerPrompt);
                case "project":
                    return new ProjectTemplate(templateName, plannerNamePrompt, firstPlannerPrompt,
                            secondPlannerPrompt, thirdPlannerPrompt);
                case "reminders":
                    return new RemindersTemplate(templateName, plannerNamePrompt, firstPlannerPrompt,
                            secondPlannerPrompt, thirdPlannerPrompt);
                default:
                    System.out.printf("Template type %s is undefined for this program.", templateType);
                    return null;
            }
        }
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
     * @param ID ID of template.
     * @return List that contains prompts.
     */
    public List<String> retrievePrompts(int ID) {
        return this.getTemplates().get(ID).retrievePrompts();
    }

    /**
     * Retrieves and returns all published templates stored in this TemplateManager (i.e., templates that are viewable
     * to all users).
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
     * @return Number of templates stored in TemplateManager.
     */
    public int numberOfTemplates() {
        return templates.size();
    }

    /**
     * @return Number of published template stored in the TemplateManager (i.e., templates that are viewable to all users).
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
        StringBuilder stringRep = new StringBuilder("Number of templates stored in the TemplateManager: " + this.numberOfTemplates()
                + "\n");
        stringRep.append("Templates: " + "\n");

        // Traverse through all key-value pairs in templates, and add those templates' string representation
        // to stringRep.
        for (Map.Entry<Integer, Template> items: this.getTemplates().entrySet()){
            Template value = items.getValue();

            if (viewOption.equals("Detail")){
                stringRep.append(value.toString());
            } else {
                stringRep.append(value.getTemplatePreview());
            }

            stringRep.append("\n");
        }
        return stringRep.toString();
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
        StringBuilder stringRep = new StringBuilder("Number of templates stored in the TemplateManager: " + this.numberOfPublishedTemplates()
                + "\n");
        stringRep.append("Templates: " + "\n");

        // Traverse through all key-value pairs in templates, and add those templates' string representation
        // to stringRep.
        for (Map.Entry<Integer, Template> items: retrievePublishedTemplates().entrySet()){
            Template value = items.getValue();

            if (viewOption.equals("Detail")){
                stringRep.append(value.toString());
            } else {
                stringRep.append(value.getTemplatePreview());
            }

            stringRep.append("\n");
        }
        return stringRep.toString();
    }

    /**
     * Returns detailed string representation of a Template with ID that's stored in TemplateManager.
     * @param ID ID of the Template.
     * @return String representation of the Template object corresponding to the ID. String representation contains
     * detailed representation of the Template, including name, type, number of prompts, and what those prompts are.
     */
    public String detailViewTemplate(int ID){
        return this.getTemplates().get(ID).toString();
    }

    /**
     * Views all Templates stored in the system in detail.
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
     * Returns the type of the template with ID being id.
     * @param id An integer representing the id of the template.
     * @return A String representing the Type of the template.
     */
    public String getType(int id) {
        Map<Integer, Template> hm = this.getTemplates();
        return hm.get(id).isType();
    }

    /**
     * Toggles the published status of a given template (i.e., published to unpublished, vice versa).
     * @param id An integer representing the id of the template.
     */
    public void switchPublishedStatus(int id) {
        Map<Integer, Template> hm = this.getTemplates();
        hm.get(id).switchPublishedStatus();
    }
}
