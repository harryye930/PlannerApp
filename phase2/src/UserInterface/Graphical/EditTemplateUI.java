package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditTemplateUI extends GeneralPresenter {
    private boolean flag = false;
    private boolean firstScreen = true;
    private String editOption;

    // Panel/Pane
    private JScrollPane template;
    private JPanel editPanel = new JPanel();
    private JScrollPane templateInfo;
    private final CardLayout editCardLayout = new CardLayout();

    // TextArea/Field
    private final JLabel selectionPrompt = new JLabel("<html>Select what you would like to edit:</html>");
    private final JLabel newNamePrompt = new JLabel("<html>Please enter the new name <br/>for the template:</html>");

    private final JTextField newNameField = new JTextField();

    // Button
    private final JButton nameButton = new JButton("Name of the template");
    private final JButton statusButton = new JButton("Published Status of the template");
    private final JButton backToTemplatesButton = new JButton("Go back");
    private final JButton submitNameButton = new JButton("Submit");
    private final JButton submitStatusButton = new JButton("Yes");
    private final JButton backFromNameButton = new JButton("Go back");
    private final JButton backFromStatusButton = new JButton("Go back");

    public EditTemplateUI(String parent){
        this.setParent(parent);
    }

    @Override
    public void run() {
        if (flag){
            template = data.getTemplate(templateController.getCurrTemplateId());
            cl.show(main, "editTemplate");
        } else {
            this.buildEditPanel();
            this.showEditUI();
            cl.show(main, "editTemplate");
            flag = !flag;
        }

    }

    private void showEditUI(){
        JPanel editTemplate = new JPanel();
        editTemplate.setLayout(null);
        main.add(editTemplate, "editTemplate");

        // Show template detail view on the left side of the panel
        template = data.getTemplate(templateController.getCurrTemplateId());
        template.setBounds(25, 25, 400, 500);
        template.setBackground(new Color(213, 212, 212));
        editTemplate.add(template);

        JPanel editPanel = getEditPanel();
        editPanel.setBounds(450, 25, 225, 200);
        editTemplate.add(editPanel);
    }

    private void buildEditPanel(){
        editPanel.setLayout(editCardLayout);

        JPanel editOptionsPanel = getEditOptionsPanel();
        editPanel.add(editOptionsPanel, "editOptions");

        JPanel editNamePanel = getEditNamePanel();
        editPanel.add(editNamePanel, "editName");

        JPanel editStatusPanel = getEditStatusPanel();
        editPanel.add(editStatusPanel, "editStatus");
    }

    private JPanel getEditPanel(){
        if (firstScreen){
            editCardLayout.show(editPanel, "editOptions");
        } else {
            if (editOption.equals("editName")){
                editCardLayout.show(editPanel, "editName");
            } else if (editOption.equals("editStatus")){
                editCardLayout.show(editPanel, "editStatus");
            }
        }

        return editPanel;
    }

    private JPanel getEditOptionsPanel(){
        JPanel editOptionsPanel = new JPanel();
        editOptionsPanel.setLayout(new GridLayout(4, 1));

        editOptionsPanel.add(selectionPrompt);
        editOptionsPanel.add(nameButton);
        editOptionsPanel.add(statusButton);
        editOptionsPanel.add(backToTemplatesButton);

        nameButton.addActionListener(this);
        statusButton.addActionListener(this);
        backToTemplatesButton.addActionListener(this);

//        // Show prompt and buttons on the right side of the panel
//        selectionPrompt.setBounds(450, 50, 225, 40);
//        selectionPrompt.setEditable(false);
//        editOptionsPanel.add(selectionPrompt);
//
//        nameButton.setBounds(450, 150, 225, 40);
//        nameButton.addActionListener(this);
//        editOptionsPanel.add(nameButton);
//        nameButton.addActionListener(this);
//
//        statusButton.setBounds(450, 200, 225, 40);
//        statusButton.addActionListener(this);
//        editOptionsPanel.add(statusButton);
//        statusButton.addActionListener(this);
//
//        backToTemplatesButton.setBounds(450, 250, 225, 40);
//        backToTemplatesButton.addActionListener(this);
//        editOptionsPanel.add(backToTemplatesButton);
//        backToTemplatesButton.addActionListener(this);

        return editOptionsPanel;
    }

    private JPanel getEditNamePanel(){
        JPanel editNamePanel = new JPanel();
        editNamePanel.setLayout(new GridLayout(4, 1));

        editNamePanel.add(newNamePrompt);
        editNamePanel.add(newNameField);
        editNamePanel.add(submitNameButton);
        editNamePanel.add(backFromNameButton);

        submitNameButton.addActionListener(this);
        backFromNameButton.addActionListener(this);

        return editNamePanel;
    }

    private JPanel getEditStatusPanel(){
        JPanel editStatusPanel = new JPanel();
        editStatusPanel.setLayout(new GridLayout(3, 1));

        JLabel setStatusPrompt = new JLabel(
                "<html>Are you sure you would like to <br/>change the published status?</html>");

        editStatusPanel.add(setStatusPrompt);
        editStatusPanel.add(submitStatusButton);
        editStatusPanel.add(backFromStatusButton);

        submitStatusButton.addActionListener(this);
        backFromStatusButton.addActionListener(this);

        return editStatusPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nameButton){
            firstScreen = false;
            editOption = "editName";
            this.showEditUI();
            cl.show(main, "editTemplate");
        } else if (e.getSource() == statusButton) {
            firstScreen = false;
            editOption = "editStatus";
            this.showEditUI();
            cl.show(main, "editTemplate");
        } else if (e.getSource() == submitNameButton) {
            templateController.setTemplateName(Integer.parseInt(templateController.getCurrTemplateId()),
                                                newNameField.getText());
            firstScreen = true;
            this.showEditUI();
            cl.show(main, "editTemplate");
        } else if (e.getSource() == submitStatusButton) {
            templateController.switchPublishedStatus(Integer.parseInt(templateController.getCurrTemplateId()));
            firstScreen = true;
            this.showEditUI();
            cl.show(main, "editTemplate");
        } else if (e.getSource() == backFromNameButton || e.getSource() == backFromStatusButton) {
            firstScreen = true;
            this.showEditUI();
            cl.show(main, "editTemplate");
        } else if (e.getSource() == backToTemplatesButton) {
            templateInfo = data.getTemplates();
            cl.show(main, this.getParent());
        }
    }
}
