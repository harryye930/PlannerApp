package Controller;


import Entity.Template;
import Gateway.TemplateGateway;
import Interface.Presenter;
import UseCase.TemplateManager;
import UseCase.PlannerManager;
import UseCase.AccountManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller for Templates.
 */
public class TemplateController{
    private TemplateManager templateManager;
    private TemplateGateway templateGateway;
    private Presenter presenter;
    private UserInputController userInputController;

    Scanner scanner;

    private final String MAIN_MENU = "M";
    private final String[] USER_DECISION = {"YES", "NO"};

    public TemplateController() {
        templateManager = new TemplateManager();
        templateGateway = new TemplateGateway(templateManager);
        userInputController = new UserInputController();
        presenter = new Presenter(this, null, null);
        scanner = new Scanner(System.in);
    }

    /**
     * Preview all Templates stored in the system.
     * This method is used when user wants to see a list of all Templates, before they select which template they want
     * to see in detail.
     * @return String that contains preview of all Template objects stored in the system.
     */
    public String previewAllTemplates(){
        return templateManager.viewTemplateManager("Summary");
    }

    /**
     * View all Templates stored in the system in detail.
     * This method is used when user wants to see a list of all Templates in detail.
     * @return String that contains detailed representation of all Template objects stored in the system.
     */
    public String detailViewAllTemplates(){
        return templateManager.viewTemplateManager("Detail");
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
     * Template Options. The user will remain in this Template Options menu unless they wish to return to the main menu.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    public boolean templateOptions() {
        String userInput;
        String[] templateOptions = {"A", "B", "C", MAIN_MENU};

        presenter.showTemplateMenu();
        userInput = userInputController.validInput(templateOptions);

        switch (userInput) {
            case "A":  // View all templates
                templateViewOptions();
                break;
            case "B":  // Edit template (admin only)
                if (userInputController.isAdmin()) {
                    // First, a user must select a template they would like to edit.
                    // Then, a user can proceed with selecting editing actions they can perform on the selected template.
                    presenter.showDetailViewAllTemplates();
                    presenter.showIDForEditQuestion("template");
                    int templateID = scanner.nextInt();
                    scanner.nextLine(); // Note: required as nextInt() does not read the new line created by enter
                    // Note that a user will remain in editing a given template options until they explicitly wish to
                    // exit from that menu.
                    while (aTemplateEditOptions(templateID)) {
                        presenter.showReturnToTemplateEditMenuMessage();
                    }
                }
                break;
            case "C":  // Create a new template (admin only)
                if (!userInputController.isAdmin()) {
                    break;
                }
                // (phase 2) implement create template
                presenter.showFeatureUnavailableScreen();
                break;
            case MAIN_MENU:
                return false;
        }
        // We know that the user didn't select return to main menu and that the requested action by the user is completed.
        // The user will want to remain in the <templateOptions> menu until they explicitly indicate their interest to
        // return to the main menu.
        return true;
    }

    /**
     * Template options helper method. Allows different options for template viewing.
     */
    private void templateViewOptions() {
        presenter.showTemplateViewMenu();
        String[] viewOptions = {"A", "B", "C"};

        String userInput = userInputController.validInput(viewOptions);
        switch (userInput) {
            case "A": // summary view (preview)
                presenter.showPreviewAllTemplates();
                break;
            case "B": // detailed view
                presenter.showDetailViewAllTemplates();
                break;
            case "C": // exit to template menu
                break;
        }
    }

    /**
     * Template options helper method. Allows different options for editing this template.
     * Returns true if the template exists and the user wants to stay in this menu; otherwise, returns false.
     * @param templateID is the unique id of the template being edited.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean aTemplateEditOptions(int templateID) {
        String userInput;
        String[] editOptions = {"A", "B", "C", "D"};

        presenter.showObjIntroMessage("template", templateID); // intro message for showing what a template currently looks like
        presenter.showDetailViewTemplate(templateManager.detailViewTemplate(templateID));
        presenter.showEditTemplateMenu();// display edit options for user to choose from
        userInput = userInputController.validInput(editOptions);

        switch (userInput) {
            case "A": // edit template name
                presenter.showEditNewNameQuestion("template"); // display message asking user to enter a new name
                String newTemplateName = scanner.nextLine();  // new template name user wants to assign
                presenter.showUpdatingTemplateMessage(); // show message saying template is being updated please wait
                templateManager.editTemplateName(templateID, newTemplateName);
                presenter.showUpdateCompletedMessage(); // show message saying that update is completed
                presenter.showDetailViewTemplate(templateManager.detailViewTemplate(templateID)); // prints out detail view of template
                break;
            case "B": // edit template prompts
                while (editPrompts(templateID)) {
                    presenter.showReturnToEditPromptsMenuMessage(); //show message saying returning to edit prompts menu
                }
                break;
            case "C": // delete template
                presenter.showConfirmDeleteQuestion("template"); // confirm if user wants to delete template
                presenter.showDetailViewTemplate(templateManager.detailViewTemplate(templateID));// visualize the template
                if (userInputController.validInput(USER_DECISION).equals("yes")) {
                    // (phase 2) implement delete template
                    presenter.showFeatureUnavailableScreen();
                }
                return false;
            case "D": // Exit to previous menu
                return false;
        }
        // We know that (1) the user didn't select return to previous menu, (2) the template is not deleted,
        // (3) and the requested action by the user is completed.
        // The user will wants to remain in the <editOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }

    /**
     * Provides different edit options for editing prompts of this template.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @param templateID ID of the template containing the prompts to be edited.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    private boolean editPrompts (int templateID) {
        String userInput;
        String[] editOptions = {"A", "B", "C", "D"};  // options user can choose from

        presenter.showEditTemplatePromptsMenu(); //display options for the user to choose from
        userInput = userInputController.validInput(editOptions);
        switch (userInput) {
            case "A": // rename prompt
                // It is logical that users are allowed to edit prompts consecutively without exiting this menu.
                // At the end of editing each prompt, a user will be asked if they wish to continue editing other prompts.
                // editPrompts feature will only be exited when a user explicitly indicates its interest to stop editing
                // any further prompts.
                String userDecision = "yes";  // Since a user indicated its interest to edit prompt, the default is "yes"
                String[] userOptions = {"yes", "no"};

                while (userDecision.equals("yes")) {
                    presenter.showTemplatePromptsIntroScreen(); // display intro message for showing current template prompts
                    presenter.showDetailViewTemplate(templateManager.detailViewTemplate(templateID)); // display detailed view of template including current prompts
                    presenter.showIDForEditPromptQuestion("rename"); // display message asking user for prompt ID
                    int promptID = scanner.nextInt();
                    scanner.nextLine(); // Note: required as nextInt() does not read the new line created by enter
                    presenter.showEditNewNameQuestion("prompt"); // display message asking user to enter desired new name
                    String newPromptName = scanner.nextLine();

                    presenter.showUpdatingTemplateMessage(); // show message saying that template is being updated
                    // please wait
                    templateManager.renameTemplatePrompt(templateID, promptID, newPromptName);
                    presenter.showUpdateCompletedMessage(); // show message saying that update is completed
                    presenter.showDetailViewTemplate(templateManager.detailViewTemplate(templateID)); // show detail view of template

                    // Ask user if they wish to continue editing other prompts.
                    presenter.showIfContinueEditQuestion();
                    userDecision = userInputController.validInput(userOptions);
                }
                break;
            case "B": // add prompt
                presenter.showIDForEditPromptQuestion("add");
                // display message asking user for ID of prompt they want to add
                int idForNewPrompt = scanner.nextInt();
                presenter.showEditNewNameQuestion("prompt"); // display message asking user to enter name for the new prompt
                String nameForNewPrompt = scanner.nextLine();
                templateManager.addTemplatePrompt(templateID, idForNewPrompt, nameForNewPrompt);
                break;
            case "C": // delete prompt
                presenter.showIDForEditPromptQuestion("delete");
                // display message asking user for ID of prompt they want to delete
                int promptIDToDelete = scanner.nextInt();
                templateManager.removeTemplatePrompt(templateID, promptIDToDelete);
                break;
            case "D":
                return false;
        }
        // We know that the user didn't select return to prev. menu and that the requested action by the user is completed.
        // The user will want to remain in the <editOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }

}
