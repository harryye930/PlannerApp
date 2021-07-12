package Controller;


import UseCase.TemplateManager;

public class TemplateController {
    private TemplateManager tm = new TemplateManager();

    /**
     * Preview all Templates stored in the system. Preview includes Template ID, name, and type.
     * This method is used when user wants to see a list of all Templates, before they select which template they want
     * to see in detail.
     * @return String that contains preview of all Template objects stored in the system.
     */
    public String getPreviewAllTemplates(){
        return tm.previewAllTemplates();
    }

    /**
     * View detailed information about one particular Template. Detailed view includes Template name, type,
     * number of prompts, and what those prompts are.
     * This method is used when user wants to view a particular Template and see what it contains.
     * @param ID
     * @return String representation of the Template object corresponding to the ID.
     */
    public String getViewDetailedTemplate(int ID){
        return tm.viewDetailedTemplate(ID);
    }



}
