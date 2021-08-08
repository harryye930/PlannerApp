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
    private final JButton submitButton = new JButton("Submit");
    private final JButton backButton = new JButton("Go back");

    public EditTemplateUI(String parent){
        this.setParent(parent);
    }

    @Override
    public void run() {

    }

    private void showEditUI(){
        editTemplate.setLayout(null);
        main.add(editTemplate, "editTemplate");

        template = data.getTemplate(templateController.getCurrTemplateId());
        template.setBounds(20, 20, 450, 500);
        template.setBackground(new Color(213, 212, 212));
        editTemplate.add(template);

        selectionPrompt.setBounds(450, 50, 200, 50);
        selectionPrompt.setEditable(false);
        editTemplate.add(selectionPrompt);

        nameButton.setBounds(500, 200, 70, 40);
        nameButton.addActionListener(this);
        editTemplate.add(nameButton);

        statusButton.setBounds(500, 280, 70, 40);
        statusButton.addActionListener(this);
        editTemplate.add(statusButton);
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
        String currStatus = null;
        String newStatus = null;
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
        editTemplate.remove(selectionPrompt);
        editTemplate.remove(nameButton);
        editTemplate.remove(statusButton);
        if (e.getSource() == nameButton){
            currentPanel = this.getEditNamePanel();
            currentPanel.setBounds(450, 50, 200, 200);
        } else if (e.getSource() == statusButton) {
            currentPanel = this.getEditStatusPanel();
            currentPanel.setBounds(450, 50, 200, 200);
        }
    }
}
