package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EditTemplateUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    private boolean firstScreen = true;
    private String editOption;
    private Map<String, String> labelToStrings = new UIGateway().loadEditTemplateUITexts();

    // Panel/Pane
    private final JPanel editTemplate = new JPanel();
    private JScrollPane template;
    private JPanel editPanel = new JPanel();
    private JScrollPane templateInfo;
    private final CardLayout editCardLayout = new CardLayout();

    // TextArea/Field
    private final JLabel selectionPrompt = new JLabel(labelToStrings.get("selectionPrompt"));
    private final JLabel newNamePrompt = new JLabel(labelToStrings.get("newNamePrompt"));

    private final JTextField newNameField = new JTextField();

    // Button
    private final JButton nameButton = new JButton(labelToStrings.get("nameButton"));
    private final JButton statusButton = new JButton(labelToStrings.get("statusButton"));
    private final JButton backToTemplatesButton = new JButton(labelToStrings.get("goBack"));
    private final JButton submitNameButton = new JButton(labelToStrings.get("submit"));
    private final JButton submitStatusButton = new JButton(labelToStrings.get("submitStatusButton"));
    private final JButton backFromNameButton = new JButton(labelToStrings.get("goBack"));
    private final JButton backFromStatusButton = new JButton(labelToStrings.get("goBack"));

    public EditTemplateUI(GeneralPresenter parent){
        this.setParent(parent);
    }

    @Override
    public void run() {
        if (flag){
            template = data.getTemplate(templateController.getCurrTemplateId(), editPanel);
            cl.show(main, "editTemplate");
        } else {
            this.buildEditPanel();
            this.showEditUI();
            cl.show(main, "editTemplate");
            flag = !flag;
        }

    }

    private void showEditUI(){
        editTemplate.setLayout(null);
        main.add(editTemplate, "editTemplate");

        // Show template detail view on the left side of the panel
        template = data.getTemplate(templateController.getCurrTemplateId(), editPanel);

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
                labelToStrings.get("setStatusPrompt"));

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
            templateInfo = data.getTemplates(editPanel);
            this.getParent().run();
        }
    }
}
