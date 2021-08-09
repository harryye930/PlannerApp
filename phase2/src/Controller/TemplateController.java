package Controller;


import Entity.Template;
import Gateway.TemplateGateway;
import UseCase.TemplateManager;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller for Templates.
 */
public class TemplateController{
    private final TemplateManager templateManager;
    private final TemplateGateway templateGateway;

    private AccessController accessController;
    private PlannerController plannerController;

    private String currTemplateId;

    public TemplateController() {
        templateManager = new TemplateManager();
        templateGateway = new TemplateGateway(templateManager);
        this.load();
    }

    /**
     * Set the access controller
     * @param accessController An access controller object
     */
    public void setAccessController(AccessController accessController) {
        this.accessController = accessController;
    }

    /**
     * Set the planner controller.
     * @param plannerController A planner Controller object.
     */
    public void setPlannerController(PlannerController plannerController) {
        this.plannerController = plannerController;
    }

    /**
     * Get the current template ID.
     * @return A String representing the current template ID.
     */
    public String getCurrTemplateId() {
        return currTemplateId;
    }

    /**
     * Preview all Templates stored in the system.
     * This method is used when user wants to see a list of all Templates, before they select which template they want
     * to see in detail.
     * @param publishedTemplatesOnly Boolean indicating whether to show only published templates or not.
     * @return String that contains preview of all Template objects stored in the system.
     */
    public String previewAllTemplates(boolean publishedTemplatesOnly){
        return templateManager.previewAllTemplates(publishedTemplatesOnly);
    }

    /**
     * View all Templates stored in the system in detail.
     * This method is used when user wants to see a list of all Templates in detail.
     * @param publishedTemplatesOnly Boolean indicating whether to show only published templates or not.
     * @return String that contains detailed representation of all Template objects stored in the system.
     */
    public String detailViewAllTemplates(boolean publishedTemplatesOnly){
        return templateManager.detailViewAllTemplates(publishedTemplatesOnly);
    }

    /**
     * Returns a detailed string representation of a template with id.
     * @param id ID of the template.
     * @return String representation of the Template object corresponding to the id. String representation contains
     * detailed representation of the Template, including name, type, number of prompts, and what those prompts are.
     */
    public String detailViewTemplate(int id){
        return templateManager.detailViewTemplate(id);
    }

//    /**
//     * Add Template t into TemplateManager.
//     * @param t Template to be added to TemplateManager.
//     */
//    public void addTemplate(Template t){
//        templateManager.importTemplate(t);
//    }

    /**
     * Saves all templates stored in TemplateManager.
     * @return A boolean value representing whether the saving process is successful.
     */
    public boolean save() {
        return this.templateGateway.save();
    }

    /**
     * Loads all templates into TemplateManager.
     * @return A boolean value representing whether the loading process is successful.
     */
    public boolean load() {
        return this.templateGateway.load();
    }

    /**
     * Check the template, similar to login so that the controller will
     * remember the template's id.
     * @param id A String representing the template id we want to check.
     * @return A boolean value representing whether the template is stored in the system.
     */
    // TODO: Same TODO as the one for viewTemplates() above. Please indicate whether you want to get ID of just published
    // TODO: templates (i.e., publishedTemplatesOnly = true) or IDs of all templates regardless of its published status
    // TODO: (i.e., publishedTemplatesOnly = false). I put it as false for now.
    public boolean checkTemplate(String id) {
        for (String tempId: this.getAllTemplateIds(false)) {
            if (Objects.equals(id, tempId)) {
                this.currTemplateId = id;
                return true;
            }
        }
        return false;
    }

    /**
     * Return a collection of all template id in String.
     * @param publishedTemplatesOnly Boolean indicating whether to retrieve ids of only published templates or not.
     * @return An ArrayList that contain the id of all template
     */
    public ArrayList<String> getAllTemplateIds(boolean publishedTemplatesOnly) {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<Integer> arr;
        if (publishedTemplatesOnly) {
            arr = new ArrayList<>(this.templateManager.retrievePublishedTemplates().keySet());
        } else {
            arr = new ArrayList<>(this.templateManager.getTemplates().keySet());
        }
        for (Integer id: arr) {
            res.add(id.toString());
        }
        return res;
    }

    /**
     * Retrieves a collection of all prompts in the template with a given template ID.
     * @param ID of the the template.
     * @return An ArrayList that contains the prompts of template with a given ID.
     */
    public ArrayList<String> getTemplatePrompts(int ID) {
        return this.templateManager.retrievePrompts(ID);
    }

    /**
     * Return the template type with give id.
     * @param id An int representing the template id.
     * @return A String representing the type of the template.
     */
    public String getTemplateType(int id) {
        return this.templateManager.getType(id);
    }

    /**
     * Return the template published status with a given id.
     * @param id An int representing the template id.
     * @return A boolean representing the published status of the template.
     */
    public boolean getTemplatePublishedStatus(int id) {
        return this.templateManager.getPublishedStatus(id);
    }

    /**
     * Changes the published status of a given template (i.e., published to unpublished, vice versa).
     * @param id An int representing the id of the template.
     */
    public void switchPublishedStatus(int id) {
        this.templateManager.switchPublishedStatus(id);
    }

    /**
     * Changes the name of the Template with ID to newName.
     * @param id ID of template being edited.
     * @param newName New value to set the name of the Template to.
     */
    public void setTemplateName(int id, String newName){
        this.templateManager.setTemplateName(id, newName);
    }

    /**
     * Creates a template of Type = type.
     * @param type Type of the template to be created.
     * @param templateName Name of the template to be created.
     * @param plannerNamePrompt String representing the prompt asking for planner name in the template to be created.
     * @param firstPlannerPrompt String representing the first planner prompt required for the corresponding
     *                           template type.
     * @param secondPlannerPrompt String representing the second planner prompt required for the corresponding
     *                            template type.
     * @param thirdPlannerPrompt String representing the third planner prompt required for the corresponding
     *                           template type.
     */
    public void createTemplate(String type, String templateName, String plannerNamePrompt,
                               String firstPlannerPrompt, String secondPlannerPrompt, String thirdPlannerPrompt){
        switch (type) {
            case "daily":
                this.templateManager.createDailyTemplate(templateName, plannerNamePrompt, firstPlannerPrompt,
                        secondPlannerPrompt, thirdPlannerPrompt);
                break;
            case "project":
                this.templateManager.createProjectTemplate(templateName, plannerNamePrompt, firstPlannerPrompt,
                        secondPlannerPrompt, thirdPlannerPrompt);
                break;
            case "reminders":
                this.templateManager.createRemindersTemplate(templateName, plannerNamePrompt, firstPlannerPrompt,
                        secondPlannerPrompt, thirdPlannerPrompt);
                break;
        }
    }

}
