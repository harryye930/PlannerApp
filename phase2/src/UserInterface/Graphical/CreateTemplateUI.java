package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *  GUI class for displaying the options for creating a template, and displaying fields for user to fill out in order
 *  to create a template.
 */
public class CreateTemplateUI extends GeneralPresenter {
    private boolean flag = false;
    private boolean hasBuiltCreatePanel = false;
    private String plannerType;
    private String firstPlannerPromptStr;
    private String secondPlannerPromptStr;
    private String thirdPlannerPromptStr;

    // Strings
    private final String promptPrefix = "<html>Please enter a template prompt message that asks for<br>";
    private final String plannerNamePromptStr = "the name of the planner";
    // daily planner prompt strings
    private final String startTimePromptStr = "the start time of the planner";
    private final String endTimePromptStr = "the end time of the planner";
    private final String timeIncrementPromptStr = "the time increment of the planner";
    // project planner prompt strings
    private final String firstStatusPromptStr = "the first status column of the planner";
    private final String secondStatusPromptStr = "the second status column of the planner";
    private final String thirdStatusPromptStr = "the third status column of the planner";
    // reminder planner prompt strings
    private final String taskHeadingPromptStr = "name of the column representing tasks in the planner";
    private final String dateHeadingPromptStr = "name of the column representing deadlines in the planner";
    private final String completionHeadingPromptStr = "name of the column representing completion status in the planner";
    // suffix
    private final String promptSuffix = "<br>that's created from this template:</html>";

    // Panel/Pane
    private final JPanel createTemplatePanel = new JPanel();
    private final JPanel optionsPanel = new JPanel();
    private JScrollPane templateInfo;

    // TextArea/Field
    private final JLabel existingTemplatesLabel = new JLabel("Here are the existing templates:");
    private final JLabel createTemplatePrompt = new JLabel(
            "<html>Select the type of template you would like to create:</html>");
    private final JLabel templateNamePrompt = new JLabel("<html>Please enter the name of the template:</html>");
    private JLabel plannerNamePrompt = new JLabel(promptPrefix + " " + plannerNamePromptStr + " " + promptSuffix);
    private JLabel firstPlannerPrompt = new JLabel();
    private JLabel secondPlannerPrompt = new JLabel();
    private JLabel thirdPlannerPrompt = new JLabel();
    private final JTextField templateNamePromptField = new JTextField();
    private final JTextField plannerNamePromptField = new JTextField();
    private final JTextField firstPlannerPromptField = new JTextField();
    private final JTextField secondPlannerPromptField = new JTextField();
    private final JTextField thirdPlannerPromptField = new JTextField();

    // Button
    private final JButton dailyTemplateButton = new JButton("Daily Template");
    private final JButton projectTemplateButton = new JButton("Project Template");
    private final JButton remindersTemplateButton = new JButton("Reminders Template");
    private final JButton submitButton = new JButton("Submit");
    private final JButton backToOptionsButton = new JButton("Go back");
    private final JButton backToTemplateMenuButton = new JButton("Return to Template Menu");


    public CreateTemplateUI(String parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag){
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else {
            this.showMenu();
            cl.show(main, "createTemplate");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void showMenu(){
        createTemplatePanel.setLayout(null);
        main.add(createTemplatePanel, "createTemplate");

        // Add existingTemplatesLabel
        existingTemplatesLabel.setBounds(25, 25, 400, 25);

        // Add JScrollPane showing templateInfo below the existingTemplatesLabel
        templateInfo = data.getTemplates();
        templateInfo.setBounds(25, 50, 400, 500);
        templateInfo.setBackground(new Color(143, 141, 141));
        createTemplatePanel.add(templateInfo);

        // Add prompt asking user to select the type of template to create
        createTemplatePrompt.setBounds(450, 75, 200, 50);
        createTemplatePanel.add(createTemplatePrompt);

        // Create options panel consisting of types of template user can create
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionsPanel.add(dailyTemplateButton);
        optionsPanel.add(projectTemplateButton);
        optionsPanel.add(remindersTemplateButton);
        optionsPanel.add(backToTemplateMenuButton);

        dailyTemplateButton.addActionListener(this);
        projectTemplateButton.addActionListener(this);
        remindersTemplateButton.addActionListener(this);
        backToTemplateMenuButton.addActionListener(this);

        // Add optionsPanel to the createPagePanel
        optionsPanel.setBounds(450, 150, 200, 200);
        createTemplatePanel.add(optionsPanel);
    }

    private void showCreatePage(String templateType){
        switch (templateType) {
            case "daily":
                firstPlannerPromptStr = startTimePromptStr;
                secondPlannerPromptStr = endTimePromptStr;
                thirdPlannerPromptStr = timeIncrementPromptStr;
                break;
            case "project":
                firstPlannerPromptStr = firstStatusPromptStr;
                secondPlannerPromptStr = secondStatusPromptStr;
                thirdPlannerPromptStr = thirdStatusPromptStr;
                break;
            case "reminders":
                firstPlannerPromptStr = taskHeadingPromptStr;
                secondPlannerPromptStr = dateHeadingPromptStr;
                thirdPlannerPromptStr = completionHeadingPromptStr;
                break;
        }
        firstPlannerPrompt.setText(promptPrefix + " " + firstPlannerPromptStr + " " + promptSuffix);
        secondPlannerPrompt.setText(promptPrefix + " " + secondPlannerPromptStr + " " + promptSuffix);
        thirdPlannerPrompt.setText(promptPrefix + " " + thirdPlannerPromptStr + " " + promptSuffix);

        JPanel inputPagePanel = new JPanel();
        JPanel inputGrid = new JPanel();
        inputGrid.setLayout(new GridLayout(12, 1));
        inputGrid.setBounds(50, 50, 400, 500);
        inputPagePanel.add(inputGrid);
        main.add(inputPagePanel, "inputPage");

        inputGrid.add(templateNamePrompt);
        inputGrid.add(templateNamePromptField);
        inputGrid.add(plannerNamePrompt);
        inputGrid.add(plannerNamePromptField);
        inputGrid.add(firstPlannerPrompt);
        inputGrid.add(firstPlannerPromptField);
        inputGrid.add(secondPlannerPrompt);
        inputGrid.add(secondPlannerPromptField);
        inputGrid.add(thirdPlannerPrompt);
        inputGrid.add(thirdPlannerPromptField);
        inputGrid.add(submitButton);
        inputGrid.add(backToOptionsButton);

        submitButton.addActionListener(this);
        backToOptionsButton.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dailyTemplateButton){
            showCreatePage("daily");
            cl.show(main, "inputPage");
            plannerType = "daily";
            hasBuiltCreatePanel = true;
        } else if (e.getSource() == projectTemplateButton){
            showCreatePage("project");
            cl.show(main, "inputPage");
            plannerType = "project";
            hasBuiltCreatePanel = true;
        } else if (e.getSource() == remindersTemplateButton){
            showCreatePage("reminders");
            cl.show(main, "inputPage");
            plannerType = "reminders";
            hasBuiltCreatePanel = true;
        } else if (e.getSource() == submitButton){
            System.out.println("submit");
            templateController.createTemplate(plannerType, templateNamePromptField.getText(),
                    plannerNamePromptField.getText(), firstPlannerPromptField.getText(),
                    secondPlannerPromptField.getText(), secondPlannerPromptField.getText());
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else if (e.getSource() == backToOptionsButton){
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else if (e.getSource() == backToTemplateMenuButton){
            System.out.println("test");
            System.out.println(this.getParent());
            cl.show(main, this.getParent());
        }
    }
}

