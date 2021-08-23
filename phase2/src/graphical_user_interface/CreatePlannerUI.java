package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.FormBuilder;
import graphical_user_interface.builder.IForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

// TODO: To be merged later.
/**
 * GUI class for the first planner creation screen.
 * Displays a list of templates available, and asks the user to enter the ID of the template they would like to use.
 */
public class CreatePlannerUI extends GeneralUI implements ActionListener {
    private boolean menuFlag = false;
    private boolean createPageFlag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadCreatePlannerUITexts();

    private final GeneralUI checkPlanner = new CheckPlannerUI(this);

    IForm selectTemplateForm;
    IForm inputForm;

    private final JPanel createPlanner = new JPanel();
    private final JPanel inputPage = new JPanel();
    private JScrollPane templateInfo;

    public CreatePlannerUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (menuFlag) {
            templateInfo = data.getTemplates(createPlanner);
            cl.show(main, "createPlanner");
        } else {
            this.showMenu();
            cl.show(main, "createPlanner");
            menuFlag = !menuFlag;
        }
    }

    private void showMenu() {
        createPlanner.setLayout(null);
        main.add(createPlanner, "createPlanner");

        templateInfo = data.getTemplates(createPlanner);
        createPlanner.add(templateInfo);

        FormBuilder selectTemplateFormBuilder = new FormBuilder();
        selectTemplateFormBuilder.setBounds(450, 50, 200, 300);

        selectTemplateFormBuilder.addLabel("message", labelToStrings.get("message"));
        selectTemplateFormBuilder.addTextField("id");
        selectTemplateFormBuilder.addSubmitButton("submit", labelToStrings.get("submit"));
        selectTemplateFormBuilder.addSubmitButton("returnToPlannerMenu", labelToStrings.get("returnToPlannerMenu"));

        selectTemplateFormBuilder.addListener(this);
        selectTemplateForm = selectTemplateFormBuilder.getForm();
        createPlanner.add(selectTemplateForm.getPanel());
    }

    private void createInputPage(List<String> prompts) {
        inputPage.setLayout(null);
        main.add(inputPage, "inputPage");

        FormBuilder inputFormBuilder = new FormBuilder();
        inputFormBuilder.setBounds(50, 50, 550, 500);

        inputFormBuilder.addLabel("plannerNamePrompt", prompts.get(0));
        inputFormBuilder.addTextField("name");
        inputFormBuilder.addLabel("firstPlannerPrompt", prompts.get(1));
        inputFormBuilder.addTextField("p1");
        inputFormBuilder.addLabel("secondPlannerPrompt", prompts.get(2));
        inputFormBuilder.addTextField("p2");
        inputFormBuilder.addLabel("thirdPlannerPrompt", prompts.get(3));
        inputFormBuilder.addTextField("p3");
        inputFormBuilder.addSubmitButton("confirm", labelToStrings.get("confirm"));
        inputFormBuilder.addSubmitButton("goBack", labelToStrings.get("goBack"));

        inputFormBuilder.addListener(this);
        inputForm = inputFormBuilder.getForm();
        inputPage.add(inputForm.getPanel());
    }

    private void updateInputPage(List<String> prompts){
        ((JLabel)inputForm.get("plannerNamePrompt")).setText(prompts.get(0));
        ((JLabel)inputForm.get("firstPlannerPrompt")).setText(prompts.get(1));
        ((JLabel)inputForm.get("secondPlannerPrompt")).setText(prompts.get(2));
        ((JLabel)inputForm.get("thirdPlannerPrompt")).setText(prompts.get(3));
    }

    /**
     * Invoked when an action occurs.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectTemplateForm.get("submit")) {
            if (!templateController.checkTemplate(((JTextField)selectTemplateForm.get("id")).getText())) {
                ((JLabel)selectTemplateForm.get("message")).setText(labelToStrings.get("invalidInput"));
            } else {
                List<String> prompts = templateController.getTemplatePrompts(
                        Integer.parseInt(templateController.getCurrTemplateId()));
                if (createPageFlag) {
                    this.updateInputPage(prompts);
                    cl.show(main, "inputPage");
                } else {
                    this.createInputPage(prompts);
                    cl.show(main, "inputPage");
                    createPageFlag = !createPageFlag;
                }
            }
        } else if (e.getSource() == selectTemplateForm.get("returnToPlannerMenu")){
            this.getParent().run();
        } else if (e.getSource() == inputForm.get("goBack")){
            templateInfo = data.getTemplates(createPlanner);
            cl.show(main, "createPlanner");
        } else if (e.getSource() == inputForm.get("confirm")) {
            plannerController.createPlanner(((JTextField)inputForm.get("p1")).getText(),
                                            ((JTextField)inputForm.get("p2")).getText(),
                                            ((JTextField)inputForm.get("p3")).getText(),
                                            ((JTextField)inputForm.get("name")).getText());
            this.checkPlanner.run();
        }
    }
}
