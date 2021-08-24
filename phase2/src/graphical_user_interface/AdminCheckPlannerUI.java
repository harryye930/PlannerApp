package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.FormBuilder;
import graphical_user_interface.builder.IForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AdminCheckPlannerUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadAdminCheckPlannerUITexts();

    IForm editInputForm;

    // Panel
    JPanel editPlanner = new JPanel();
    JScrollPane allPlanners;

    public AdminCheckPlannerUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            update();
            cl.show(main, "adminCheckPlanner");
        } else {
            this.showMenu();
            cl.show(main, "adminCheckPlanner");
            flag = !flag;
        }
    }

    private void showMenu() {
        editPlanner.setLayout(null);
        main.add(editPlanner, "adminCheckPlanner");

        allPlanners = data.getPlanners(editPlanner);

        FormBuilder editInputBuilder = new FormBuilder();

        editInputBuilder.setBounds(460, 0, 230, 550);
        editInputBuilder.addLabel("prompt", labelToStrings.get("prompt"));
        editInputBuilder.addTextField("idTextField");
        editInputBuilder.addSubmitButton("delete", labelToStrings.get("delete"));
        editInputBuilder.addLabel("blankRow1", " ");
        editInputBuilder.addLabel("changeStatusPrompt", labelToStrings.get("changeStatusPrompt"));
        editInputBuilder.addList("statusOptions", new String[]{labelToStrings.get("statusOption1"),
                                                                    labelToStrings.get("statusOption2"),
                                                                    labelToStrings.get("statusOption3")});
        editInputBuilder.addSubmitButton("submit", labelToStrings.get("submit"));
        editInputBuilder.addLabel("blankRow2", " ");
        editInputBuilder.addSuperButton("back", labelToStrings.get("goBack"), this.getParent());

        editInputBuilder.addListener(this);
        editInputForm = editInputBuilder.getForm();
        editPlanner.add(editInputForm.getPanel());
    }

    private void update() {
        allPlanners = data.getPlanners(editPlanner);
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editInputForm.get("delete")){
            if (plannerController.checkPlanner(((JTextField)editInputForm.get("idTextField")).getText())) {
                plannerController.deletePlanner(((JTextField)editInputForm.get("idTextField")).getText());
                ((JTextField)editInputForm.get("idTextField")).setText("");
                update();
            } else {
                ((JTextField)editInputForm.get("idTextField")).setText(labelToStrings.get("invalidInput"));
            }
        } else if (e.getSource() == editInputForm.get("submit")) {
            if (plannerController.checkPlanner(((JTextField)editInputForm.get("idTextField")).getText())) {
                if (((JList<String>)editInputForm.get("statusOptions")).getSelectedIndex() != -1) {
                    plannerController.changePrivacyStatus(((JList<String>)editInputForm.get("statusOptions")).getSelectedValue());
                    ((JTextField)editInputForm.get("idTextField")).setText("");
                    ((JList<String>)editInputForm.get("statusOptions")).setSelectedIndex(-1);
                    update();
                }
            } else {
                ((JTextField)editInputForm.get("idTextField")).setText(labelToStrings.get("invalidInput"));
            }
        }
    }
}
