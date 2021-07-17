package Controller;


import UseCase.TemplateManager;

import java.util.ArrayList;

/**
 * Controller for Templates.
 */
public class TemplateController {
    private TemplateManager tm;

    public TemplateController() {
        tm = new TemplateManager();
    }

    /**
     * Preview all Templates stored in the system.
     * This method is used when user wants to see a list of all Templates, before they select which template they want
     * to see in detail.
     * @return String that contains preview of all Template objects stored in the system.
     */
    public String previewAllTemplates(){
        return tm.viewTemplateManager("Summary");
    }

    /**
     * View all Templates stored in the system in detail.
     * This method is used when user wants to see a list of all Templates in detail.
     * @return String that contains detailed representation of all Template objects stored in the system.
     */
    public String detailViewAllTemplates(){
        return tm.viewTemplateManager("Detail");
    }

    //TODO: Are the methods below redundant? We need user input to better decide which methods to call
    //          - but we only get user input in the UserActionController

    /**
     * Get preview (a summary that only includes basic info) of a specified Template that's stored in the system.
     * @param ID ID of the Template.
     * @return String preview of Template corresponding to ID that includes basic information.
     */
    public String previewTemplate(int ID){
        return tm.previewTemplate(ID);
    }

    /**
     * View detailed information about one particular Template. Detailed view includes Template name, type,
     * number of prompts, and what those prompts are.
     * This method is used when user wants to view a particular Template and see what it contains.
     * @param ID
     * @return String representation of the Template object corresponding to the ID.
     */
    public String detailViewTemplate(int ID){
        return tm.detailViewTemplate(ID);
    }

    /**
     * Changes the name of the Template with ID to newValue.
     * @param ID ID of template being edited.
     * @param newName New value to set the name of the Template to.
     */
    public void editTemplateName(int ID, String newName){
        tm.editTemplateName(ID, newName);
    }

    /**
     * Edit a prompt with promptNumber in the Template with ID.
     * @param ID ID of the template being edited.
     * @param promptNumber The number of the prompt to rename.
     * @param newName The new name to give to the prompt.
     */
    public void renameTemplatePrompt(int ID, int promptNumber, String newName){
        tm.renameTemplatePrompt(ID, promptNumber, newName);
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
        tm.addTemplatePrompt(ID, promptNumber, promptName);
    }

    /**
     * For Template with ID, adds a new prompt named promptName to the end of the existing list of prompts.
     * @param ID ID of template being edited.
     * @param promptName The name to give to the new prompt.
     */
    public void addTemplatePrompt(int ID, String promptName){
        tm.addTemplatePrompt(ID, promptName);
    }

    /**
     * For Template with ID,  removes an existing prompt with promptNumber.
     * @param ID ID of template being edited.
     * @param promptNumber The number that corresponds to the prompt to remove.
     */
    public void removeTemplatePrompt(int ID, int promptNumber){
        tm.removeTemplatePrompt(ID, promptNumber);
    }

}
