package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 *  GUI class for displaying the options for creating a template, and displaying fields for user to fill out in order
 *  to create a template.
 */
public class CreateTemplateUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadCreateTemplateUITexts();

    //Forms
    IForm optionsForm;
    IForm dailyInputForm;
    IForm projectInputForm;
    IForm remindersInputForm;

    // Strings
    // general strings: prefix, suffix, and planner name prompt
    private final String promptPrefix = labelToStrings.get("promptPrefix");
    private final String promptSuffix = labelToStrings.get("promptSuffix");
    private final String plannerNamePromptStr = labelToStrings.get("plannerNamePromptStr");
    // daily planner prompt strings
    private final String startTimePromptStr = labelToStrings.get("startTimePromptStr");
    private final String endTimePromptStr = labelToStrings.get("endTimePromptStr");
    private final String timeIncrementPromptStr = labelToStrings.get("timeIncrementPromptStr");
    // project planner prompt strings
    private final String firstStatusPromptStr = labelToStrings.get("firstStatusPromptStr");
    private final String secondStatusPromptStr = labelToStrings.get("secondStatusPromptStr");
    private final String thirdStatusPromptStr = labelToStrings.get("thirdStatusPromptStr");
    // reminder planner prompt strings
    private final String taskHeadingPromptStr = labelToStrings.get("taskHeadingPromptStr");
    private final String dateHeadingPromptStr = labelToStrings.get("dateHeadingPromptStr");
    private final String completionHeadingPromptStr = labelToStrings.get("completionHeadingPromptStr");

    // Panel/Pane
    private final JPanel createTemplatePanel = new JPanel();
    private JScrollPane templateInfo;

    public CreateTemplateUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run(){
        if (flag){
            templateInfo = data.getTemplates(createTemplatePanel);
            cl.show(main, "createTemplate");
        } else {
            showCreateMenu();
            showDailyInputPanel();
            showProjectInputPanel();
            showRemindersInputPanel();
            cl.show(main, "createTemplate");
            flag = !flag;
        }
    }

    private void showCreateMenu(){
        createTemplatePanel.setLayout(null);
        main.add(createTemplatePanel, "createTemplate");

        // Add existingTemplatesLabel
        JLabel existingTemplatesLabel = new JLabel(labelToStrings.get("existingTemplatesLabel"));
        existingTemplatesLabel.setBounds(25, 0, 400, 25);
        createTemplatePanel.add(existingTemplatesLabel);

        // Add JScrollPane showing templateInfo below the existingTemplatesLabel
        templateInfo = data.getTemplates(createTemplatePanel);
        templateInfo.setBounds(25, 75, 400, 450);
        templateInfo.setBackground(new Color(143, 141, 141));

        FormBuilder optionsFormBuilder = new FormBuilder();

        optionsFormBuilder.setBounds(450, 75, 200, 300);
        optionsFormBuilder.addLabel("createTemplatePrompt", labelToStrings.get("createTemplatePrompt"));
        optionsFormBuilder.addSubmitButton("dailyTemplateButton", labelToStrings.get("dailyTemplateButton"));
        optionsFormBuilder.addSubmitButton("projectTemplateButton", labelToStrings.get("projectTemplateButton"));
        optionsFormBuilder.addSubmitButton("remindersTemplateButton", labelToStrings.get("remindersTemplateButton"));
        optionsFormBuilder.addSubmitButton("backToTemplateMenuButton", labelToStrings.get("backToTemplateMenuButton"));

        optionsFormBuilder.addListener(this);
        optionsForm = optionsFormBuilder.getForm();
        createTemplatePanel.add(optionsForm.getPanel());
    }

    private IForm createInputForm(String submitButtonName, String firstPlannerPromptStr, String secondPlannerPromptStr,
                                  String thirdPlannerPromptStr){

        FormBuilder inputFormBuilder = new FormBuilder();
        inputFormBuilder.setBounds(50, 50, 400, 500);

        inputFormBuilder.addLabel("templateNamePrompt", labelToStrings.get("templateNamePrompt"));
        inputFormBuilder.addTextField("templateNamePromptField");
        inputFormBuilder.addLabel("plannerNamePrompt",
                promptPrefix + " " + plannerNamePromptStr + " " + promptSuffix);
        inputFormBuilder.addTextField("plannerNamePromptField");
        inputFormBuilder.addLabel("firstPlannerPrompt",
                promptPrefix + " " + firstPlannerPromptStr + " " + promptSuffix);
        inputFormBuilder.addTextField("firstPlannerPromptField");
        inputFormBuilder.addLabel("secondPlannerPrompt",
                promptPrefix + " " + secondPlannerPromptStr + " " + promptSuffix);
        inputFormBuilder.addTextField("secondPlannerPromptField");
        inputFormBuilder.addLabel("thirdPlannerPrompt",
                promptPrefix + " " + thirdPlannerPromptStr + " " + promptSuffix);
        inputFormBuilder.addTextField("thirdPlannerPromptField");

        inputFormBuilder.addSubmitButton(submitButtonName, labelToStrings.get("submit"));
        inputFormBuilder.addSubmitButton("backToOptionsButton", labelToStrings.get("goBack"));

        inputFormBuilder.addListener(this);

        return inputFormBuilder.getForm();
    }

    private void showDailyInputPanel(){
        JPanel dailyInputPanel = new JPanel();

        dailyInputForm = createInputForm("dailySubmitButton",
                startTimePromptStr, endTimePromptStr, timeIncrementPromptStr);

        dailyInputPanel.add(dailyInputForm.getPanel());
        main.add(dailyInputPanel, "daily");
    }

    private void showProjectInputPanel(){
        JPanel projectInputPanel = new JPanel();

        projectInputForm = createInputForm("projectSubmitButton",
                firstStatusPromptStr, secondStatusPromptStr, thirdStatusPromptStr);

        projectInputPanel.add(projectInputForm.getPanel());
        main.add(projectInputPanel, "project");
    }

    private void showRemindersInputPanel(){
        JPanel remindersInputPanel = new JPanel();

        remindersInputForm = createInputForm("remindersSubmitButton",
                taskHeadingPromptStr, dateHeadingPromptStr, completionHeadingPromptStr);

        remindersInputPanel.add(remindersInputForm.getPanel());
        main.add(remindersInputPanel, "reminders");
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == optionsForm.get("dailyTemplateButton")){
            cl.show(main, "daily");
        } else if (e.getSource() == optionsForm.get("projectTemplateButton")){
            cl.show(main, "project");
        } else if (e.getSource() == optionsForm.get("remindersTemplateButton")){
            cl.show(main, "reminders");
        } else if (e.getSource() == dailyInputForm.get("dailySubmitButton")){
            templateController.createTemplate("daily",
                    ((JTextField)dailyInputForm.get("templateNamePromptField")).getText(),
                    ((JTextField)dailyInputForm.get("plannerNamePromptField")).getText(),
                    ((JTextField)dailyInputForm.get("firstPlannerPromptField")).getText(),
                    ((JTextField)dailyInputForm.get("secondPlannerPromptField")).getText(),
                    ((JTextField)dailyInputForm.get("thirdPlannerPromptField")).getText());
            templateInfo = data.getTemplates(createTemplatePanel);
            cl.show(main, "createTemplate");
        } else if (e.getSource() == projectInputForm.get("projectSubmitButton")){
            templateController.createTemplate("project",
                    ((JTextField)projectInputForm.get("templateNamePromptField")).getText(),
                    ((JTextField)projectInputForm.get("plannerNamePromptField")).getText(),
                    ((JTextField)projectInputForm.get("firstPlannerPromptField")).getText(),
                    ((JTextField)projectInputForm.get("secondPlannerPromptField")).getText(),
                    ((JTextField)projectInputForm.get("thirdPlannerPromptField")).getText());
            templateInfo = data.getTemplates(createTemplatePanel);
            cl.show(main, "createTemplate");
        } else if (e.getSource() == remindersInputForm.get("remindersSubmitButton")){
            templateController.createTemplate("reminders",
                    ((JTextField)remindersInputForm.get("templateNamePromptField")).getText(),
                    ((JTextField)remindersInputForm.get("plannerNamePromptField")).getText(),
                    ((JTextField)remindersInputForm.get("firstPlannerPromptField")).getText(),
                    ((JTextField)remindersInputForm.get("secondPlannerPromptField")).getText(),
                    ((JTextField)remindersInputForm.get("thirdPlannerPromptField")).getText());
            templateInfo = data.getTemplates(createTemplatePanel);
            cl.show(main, "createTemplate");
        } else if (e.getSource() == dailyInputForm.get("backToOptionsButton") ||
                e.getSource() == projectInputForm.get("backToOptionsButton") ||
                e.getSource() == remindersInputForm.get("backToOptionsButton")){
            templateInfo = data.getTemplates(createTemplatePanel);
            cl.show(main, "createTemplate");
        } else if (e.getSource() == optionsForm.get("backToTemplateMenuButton")){
            this.getParent().run();
        }
    }
}

