package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.FormBuilder;
import graphical_user_interface.builder.IForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * GUI class for displaying planner trash bin UI for a regular user.
 * Options include: recover a deleted planner, permanently delete a planner, and return to the planner menu.
 */
public class PlannerTrashBinUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadPlannerTrashBinUITexts();

    private final JPanel panel = new JPanel();
    private IForm form;

    public PlannerTrashBinUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            data.getTrashBin(accessController.getCurrUserId(), panel);
            cl.show(main, "trashBin");
        } else {
            this.showMenu();
            cl.show(main, "trashBin");
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(panel, "trashBin");
        panel.setLayout(null);
        data.getTrashBin(accessController.getCurrUserId(), panel);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(425, 50, 300, 250);
        fb.addLabel("prompt", labelToStrings.get("prompt"));
        fb.addTextField("plannerId");
        fb.addSubmitButton("unTrash", labelToStrings.get("unTrash"));
        fb.addSubmitButton("delete", labelToStrings.get("delete"));
        fb.addSuperButton("goBack", labelToStrings.get("goBack"), this.getParent());
        fb.addListener(this);

        form = fb.getForm();
        panel.add(form.getPanel());
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.get("unTrash")) {
            if (accessController.unTrashPlanner(accessController.getCurrUserId(),
                    ((JTextField)form.get("plannerId")).getText())) {
                ((JLabel)form.get("prompt")).setText(labelToStrings.get("unTrashSuccessPrompt"));
                data.getTrashBin(accessController.getCurrUserId(), panel);
            } else {
                ((JLabel)form.get("prompt")).setText(labelToStrings.get("unTrashFailPrompt"));
            }
        } else if (e.getSource() == form.get("delete")) {
            if (accessController.permanentTrashPlanner(accessController.getCurrUserId(),
                    ((JTextField)form.get("plannerId")).getText())) {
                ((JLabel)form.get("prompt")).setText(labelToStrings.get("deleteSuccessPrompt"));
            } else {
                ((JLabel)form.get("prompt")).setText(labelToStrings.get("deleteFailPrompt"));
            }
        }
    }
}
