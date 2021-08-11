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
    private boolean createdDailyInputPanel = false;
    private boolean createdProjectInputPanel = false;
    private boolean createdRemindersInputPanel = false;

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
    private JPanel dailyInputPanel;
    private JPanel projectInputPanel;
    private JPanel remindersInputPanel;

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
//    private final JButton submitButton = new JButton("Submit");
    private final JButton dailySubmitButton = new JButton("Submit");
    private final JButton projectSubmitButton = new JButton("Submit");
    private final JButton remindersSubmitButton = new JButton("Submit");
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
            this.showCreateMenu();
            cl.show(main, "createTemplate");
            flag = !flag;
        }
    }

    private void showCreateMenu(){
        createTemplatePanel.setLayout(null);
        main.add(createTemplatePanel, "createTemplate");

        // Add existingTemplatesLabel
        existingTemplatesLabel.setBounds(25, 25, 400, 25);
        createTemplatePanel.add(existingTemplatesLabel);

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

    private JPanel createInputGrid(String firstPlannerPromptStr, String secondPlannerPromptStr, String thirdPlannerPromptStr){
        firstPlannerPrompt.setText(promptPrefix + " " + firstPlannerPromptStr + " " + promptSuffix);
        secondPlannerPrompt.setText(promptPrefix + " " + secondPlannerPromptStr + " " + promptSuffix);
        thirdPlannerPrompt.setText(promptPrefix + " " + thirdPlannerPromptStr + " " + promptSuffix);

        JPanel inputGrid = new JPanel();
        inputGrid.setLayout(new GridLayout(12, 1));

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
        inputGrid.add(backToOptionsButton);

        backToOptionsButton.addActionListener(this);

        return inputGrid;
    }

    private void showDailyInputPanel(){
        dailyInputPanel = new JPanel();
        JPanel dailyInputGrid = createInputGrid(startTimePromptStr, endTimePromptStr, timeIncrementPromptStr);
        dailyInputGrid.add(dailySubmitButton);
        dailySubmitButton.addActionListener(this);
        dailyInputGrid.setBounds(50, 50, 400, 500);

        dailyInputPanel.add(dailyInputGrid);
        main.add(dailyInputPanel, "daily");
    }

    private void showProjectInputPanel(){
        projectInputPanel = new JPanel();
        JPanel projectInputGrid = createInputGrid(firstStatusPromptStr, secondStatusPromptStr, thirdStatusPromptStr);
        projectInputGrid.add(projectSubmitButton);
        projectSubmitButton.addActionListener(this);
        projectInputGrid.setBounds(50, 50, 400, 500);

        projectInputPanel.add(projectInputGrid);
        main.add(projectInputPanel, "project");
    }

    private void showRemindersInputPanel(){
        remindersInputPanel = new JPanel();
        JPanel remindersInputGrid = createInputGrid(taskHeadingPromptStr, dateHeadingPromptStr, completionHeadingPromptStr);
        remindersInputGrid.add(remindersSubmitButton);
        remindersSubmitButton.addActionListener(this);
        remindersInputGrid.setBounds(50, 50, 400, 500);

        remindersInputPanel.add(remindersInputGrid);
        main.add(remindersInputPanel, "reminders");
    }

//    private void buildInputPanel(){
//        inputPanel = new JPanel();
//        inputPanel.setLayout(inputPanelLayout);
//
//        JPanel dailyTemplateInputPanel = createInputGrid(startTimePromptStr, endTimePromptStr, timeIncrementPromptStr);
//        dailyTemplateInputPanel.setBounds(50, 50, 400, 500);
//        inputPanel.add(dailyTemplateInputPanel, "daily");
//
//        JPanel projectTemplateInputPanel = createInputGrid(firstStatusPromptStr, secondStatusPromptStr, thirdStatusPromptStr);
//        dailyTemplateInputPanel.setBounds(50, 50, 400, 500);
//        inputPanel.add(projectTemplateInputPanel, "project");
//
//        JPanel remindersTemplateInputPanel = createInputGrid(taskHeadingPromptStr, dateHeadingPromptStr, completionHeadingPromptStr);
//        dailyTemplateInputPanel.setBounds(50, 50, 400, 500);
//        inputPanel.add(remindersTemplateInputPanel, "reminders");
//    }
//
//    private void getInputPanel(){
//        if (plannerType.equals("daily")){
//            inputPanelLayout.show(inputPanel, "daily");
//        } else if (plannerType.equals("project")){
//            inputPanelLayout.show(inputPanel, "project");
//        } else if (plannerType.equals("reminders")){
//            inputPanelLayout.show(inputPanel, "reminders");
//        }
////        return inputPanel;
//    }

//    public void showInputPanel(){
//        JPanel inputScreen = new JPanel();
//        inputScreen.setLayout(null);
//        main.add(inputScreen, "inputScreen");
//        inputPanel.setBounds(50, 50, 400, 500);
//        inputScreen.add(inputPanel);
//
//        if (!firstScreen){
//
//        }
//    }



//    private void showCreatePage(String templateType){
//        switch (templateType) {
//            case "daily":
//                firstPlannerPromptStr = startTimePromptStr;
//                secondPlannerPromptStr = endTimePromptStr;
//                thirdPlannerPromptStr = timeIncrementPromptStr;
//                break;
//            case "project":
//                firstPlannerPromptStr = firstStatusPromptStr;
//                secondPlannerPromptStr = secondStatusPromptStr;
//                thirdPlannerPromptStr = thirdStatusPromptStr;
//                break;
//            case "reminders":
//                firstPlannerPromptStr = taskHeadingPromptStr;
//                secondPlannerPromptStr = dateHeadingPromptStr;
//                thirdPlannerPromptStr = completionHeadingPromptStr;
//                break;
//        }
//        firstPlannerPrompt.setText(promptPrefix + " " + firstPlannerPromptStr + " " + promptSuffix);
//        secondPlannerPrompt.setText(promptPrefix + " " + secondPlannerPromptStr + " " + promptSuffix);
//        thirdPlannerPrompt.setText(promptPrefix + " " + thirdPlannerPromptStr + " " + promptSuffix);
//
//        JPanel inputPagePanel = new JPanel();
//        JPanel inputGrid = new JPanel();
//        inputGrid.setLayout(new GridLayout(12, 1));
//        inputGrid.setBounds(50, 50, 400, 500);
//        inputPagePanel.add(inputGrid);
//        main.add(inputPagePanel, "inputPage");
//
//        inputGrid.add(templateNamePrompt);
//        inputGrid.add(templateNamePromptField);
//        inputGrid.add(plannerNamePrompt);
//        inputGrid.add(plannerNamePromptField);
//        inputGrid.add(firstPlannerPrompt);
//        inputGrid.add(firstPlannerPromptField);
//        inputGrid.add(secondPlannerPrompt);
//        inputGrid.add(secondPlannerPromptField);
//        inputGrid.add(thirdPlannerPrompt);
//        inputGrid.add(thirdPlannerPromptField);
//        inputGrid.add(submitButton);
//        inputGrid.add(backToOptionsButton);
//
//        submitButton.addActionListener(this);
//        backToOptionsButton.addActionListener(this);
//    }


//TODO: these if statements are a bit repetitive, would we lose mark for this
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dailyTemplateButton){
            if (!createdDailyInputPanel){
                this.showDailyInputPanel();
                createdDailyInputPanel = true;
            }
            cl.show(main, "daily");
        } else if (e.getSource() == projectTemplateButton){
            if (!createdProjectInputPanel){
                this.showProjectInputPanel();
                createdProjectInputPanel = true;
            }
            cl.show(main, "project");
        } else if (e.getSource() == remindersTemplateButton){
            if (!createdRemindersInputPanel){
                this.showRemindersInputPanel();
                createdRemindersInputPanel = true;
            }
            cl.show(main, "reminders");
        } else if (e.getSource() == dailySubmitButton){
            templateController.createTemplate("daily", templateNamePromptField.getText(),
                    plannerNamePromptField.getText(), firstPlannerPromptField.getText(),
                    secondPlannerPromptField.getText(), secondPlannerPromptField.getText());
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else if (e.getSource() == projectSubmitButton){
            templateController.createTemplate("project", templateNamePromptField.getText(),
                    plannerNamePromptField.getText(), firstPlannerPromptField.getText(),
                    secondPlannerPromptField.getText(), secondPlannerPromptField.getText());
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else if (e.getSource() == remindersSubmitButton){
            templateController.createTemplate("reminders", templateNamePromptField.getText(),
                    plannerNamePromptField.getText(), firstPlannerPromptField.getText(),
                    secondPlannerPromptField.getText(), secondPlannerPromptField.getText());
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else if (e.getSource() == backToOptionsButton){
            templateInfo = data.getTemplates();
            cl.show(main, "createTemplate");
        } else if (e.getSource() == backToTemplateMenuButton){
            cl.show(main, this.getParent());
        }
    }
}

