package UserInterface.Graphical;

import UserInterface.GeneralPresenter;
import sun.text.resources.et.JavaTimeSupplementary_et;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditTemplateUI extends GeneralPresenter {
    private boolean flag = false;

    // Panel/Pane
    private JScrollPane template;
    private final JPanel editTemplate = new JPanel();
    JPanel currentPanel;

    // TextArea/Field
    private final JTextArea selectionPrompt = new JTextArea("Select what you would like to edit:");
    private final JTextArea newNamePrompt = new JTextArea("Please enter the new name for the template:");
    private final JTextArea setToPublishedMsg = new JTextArea("Template has been set to published!");
    private final JTextArea setToUnpublishedMsg = new JTextArea("Template has been set to unpublished!");

    private final JTextField newNameField = new JTextField();

    // Button
    private final JButton nameButton = new JButton("Name of the template");
    private final JButton statusButton = new JButton("Published Status of the template");
    private final JButton backToTemplatesButton = new JButton("Go back");
    private final JButton submitButton = new JButton("Submit");
    private final JButton backButton = new JButton("Go back");

    public EditTemplateUI(String parent){
        this.setParent(parent);
    }

    @Override
    public void run() {
        if (flag){
            template = data.getTemplate(templateController.getCurrTemplateId());
            cl.show(main, "editTemplate");
        } else {
            this.showEditUI();
            cl.show(main, "editTemplate");
            flag = !flag;
        }

    }

    private void showEditUI(){
        editTemplate.setLayout(null);
        main.add(editTemplate, "editTemplate");

        template = data.getTemplate(templateController.getCurrTemplateId());
        template.setBounds(25, 25, 400, 500);
        template.setBackground(new Color(213, 212, 212));
        editTemplate.add(template);

        selectionPrompt.setBounds(450, 50, 225, 40);
        selectionPrompt.setEditable(false);
        editTemplate.add(selectionPrompt);

        nameButton.setBounds(450, 150, 225, 40);
        nameButton.addActionListener(this);
        editTemplate.add(nameButton);
        nameButton.addActionListener(this);

        statusButton.setBounds(450, 200, 225, 40);
        statusButton.addActionListener(this);
        editTemplate.add(statusButton);
        statusButton.addActionListener(this);

        backToTemplatesButton.setBounds(450, 250, 225, 40);
        backToTemplatesButton.addActionListener(this);
        editTemplate.add(backToTemplatesButton);
        backToTemplatesButton.addActionListener(this);
    }

    private JPanel getEditNamePanel(){
        JPanel editNamePanel = new JPanel();
        editNamePanel.setLayout(new GridLayout(4, 1));

        editNamePanel.add(newNamePrompt);
        editNamePanel.add(newNameField);
        editNamePanel.add(submitButton);
        editNamePanel.add(backButton);

        submitButton.addActionListener(this);
        backButton.addActionListener(this);

        return editNamePanel;
    }

    private JPanel getEditStatusPanel(){
        JPanel editStatusPanel = new JPanel();

        // get the current published status of the template
        boolean isPublished = templateController.getTemplatePublishedStatus(
                Integer.parseInt(templateController.getCurrTemplateId()));
        String currStatus;
        String newStatus;
        if (isPublished){
            currStatus = "published";
            newStatus = "unpublished";
        } else {
            currStatus = "unpublished";
            newStatus = "published";
        }

        JTextArea setStatusPrompt = new JTextArea(String.format(
                "The current status is %s. Are you sure you want to change it to %s?", currStatus, newStatus));

        editStatusPanel.setLayout(new GridLayout(3, 1));

        editStatusPanel.add(setStatusPrompt);
        editStatusPanel.add(submitButton);
        editStatusPanel.add(backButton);

        submitButton.addActionListener(this);
        backButton.addActionListener(this);

        return editStatusPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nameButton){
            editTemplate.remove(selectionPrompt);
            editTemplate.remove(nameButton);
            editTemplate.remove(statusButton);
            currentPanel = this.getEditNamePanel();
            currentPanel.setBounds(450, 50, 200, 200);
        } else if (e.getSource() == statusButton) {
            editTemplate.remove(selectionPrompt);
            editTemplate.remove(nameButton);
            editTemplate.remove(statusButton);
            currentPanel = this.getEditStatusPanel();
            currentPanel.setBounds(450, 50, 200, 200);
        } else if (e.getSource() == backToTemplatesButton) {
            cl.show(main, this.getParent());
        }
    }
}
