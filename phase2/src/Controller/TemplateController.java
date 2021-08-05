package Controller;


import Entity.Template;
import Gateway.TemplateGateway;
import UseCase.TemplateManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller for Templates.
 */
public class TemplateController{
    private TemplateManager templateManager;
    private TemplateGateway templateGateway;

    Scanner scanner;

    private final String MAIN_MENU = "M";
    private final String[] USER_DECISION = {"YES", "NO"};

    public TemplateController() {
        templateManager = new TemplateManager();
        templateGateway = new TemplateGateway(templateManager);
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
        return templateManager.previewAllTemplates(publishedTemplatesOnly);
    }

    /**
     * Add Template t into TemplateManager.
     * @param t Template to be added to TemplateManager.
     */
    public void addTemplate(Template t){
        templateManager.addTemplate(t);
    }

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
     * Return a collection of all template id in String.
     * @return An ArrayList that contain the id of all template
     */
    public ArrayList<String> getAllTemplateIds() {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<Integer> arr = new ArrayList<>(this.templateManager.getTemplates().keySet());
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
}
