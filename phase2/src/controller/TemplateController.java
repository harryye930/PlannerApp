package controller;


import gateway.TemplateGateway;
import use_case.TemplateManager;
import java.util.ArrayList;
import java.util.List;
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
     * Sets the access controller.
     * @param accessController An AccessController object.
     */
    public void setAccessController(AccessController accessController) {
        this.accessController = accessController;
    }

    /**
     * Sets the planner controller.
     * @param plannerController A PlannerController object.
     */
    public void setPlannerController(PlannerController plannerController) {
        this.plannerController = plannerController;
    }

    /**
     * Gets the current template ID.
     * @return A String representing the current template ID.
     */
    public String getCurrTemplateId() {
        return currTemplateId;
    }

    /**
     * Views all Templates stored in the system in detail.
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

    /**
     * Saves all templates stored in TemplateManager to an external file.
     * @return A boolean value representing whether the saving process is successful.
     */
    public boolean save() {
        return this.templateGateway.save();
    }

    /**
     * Loads all templates from an external file into TemplateManager.
     * @return A boolean value representing whether the loading process is successful.
     */
    public boolean load() {
        return this.templateGateway.load();
    }

    /**
     * Checks if there is a template with ID being id stored in the program.
     * If so, then the program remembers the template id.
     * @param id A String representing the template id we want to check.
     * @return A boolean value representing whether a template with ID being id is stored in the program.
     */
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
     * Returns a collection of all template id's in String.
     * @param publishedTemplatesOnly Boolean indicating whether to retrieve ids of only the published templates.
     * @return A List that contains the id's of all templates.
     */
    public List<String> getAllTemplateIds(boolean publishedTemplatesOnly) {
        List<String> output = new ArrayList<>();
        List<Integer> allIntIDs;
        if (publishedTemplatesOnly) {
            allIntIDs = new ArrayList<>(this.templateManager.retrievePublishedTemplates().keySet());
        } else {
            allIntIDs = new ArrayList<>(this.templateManager.getTemplates().keySet());
        }
        for (Integer id: allIntIDs) {
            output.add(id.toString());
        }
        return output;
    }

    /**
     * Retrieves a collection of all prompts in the template with the given template ID.
     * @param ID of the template.
     * @return A List that contains the prompts of template with the given ID.
     */
    public List<String> getTemplatePrompts(int ID) {
        return this.templateManager.retrievePrompts(ID);
    }

    /**
     * Returns the type of template with give id.
     * @param id An int representing the template id.
     * @return A String representing the type of the template.
     */
    public String getTemplateType(int id) {
        return this.templateManager.getType(id);
    }

    /**
     * Toggles the published status of template with id (i.e., published to unpublished, vice versa).
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
        this.save();
    }

    /**
     * Creates a template of Type templateType.
     * @param templateType Type of the template to be created.
     * @param templateName Name of the template to be created.
     * @param plannerNamePrompt String representing the prompt asking for planner name in the template to be created.
     * @param firstPlannerPrompt String representing the first planner prompt required for the corresponding
     *                           template type.
     * @param secondPlannerPrompt String representing the second planner prompt required for the corresponding
     *                            template type.
     * @param thirdPlannerPrompt String representing the third planner prompt required for the corresponding
     *                           template type.
     */
    public void createTemplate(String templateType, String templateName, String plannerNamePrompt,
                               String firstPlannerPrompt, String secondPlannerPrompt, String thirdPlannerPrompt){
        this.templateManager.createTemplate(templateType, templateName, plannerNamePrompt, firstPlannerPrompt,
                secondPlannerPrompt, thirdPlannerPrompt);
        this.save();
    }

}
