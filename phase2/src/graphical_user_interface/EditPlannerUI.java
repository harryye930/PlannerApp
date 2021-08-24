package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.FormBuilder;
import graphical_user_interface.builder.IForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EditPlannerUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private boolean isAdd = true;

    private final JPanel panel = new JPanel();
    private final JList<String> list = new JList<>();

    private final GeneralUI editRemainder;
    private final Map<String, String> labelToStrings = new UIGateway().loadEditPlannerUITexts();

    private IForm dailyEditForm;
    private IForm projectEditForm;
    private IForm projectAddForm;
    private IForm menuForm;

    public EditPlannerUI(GeneralUI parent) {
        this.setParent(parent);
        this.editRemainder = new EditReminderPlannerUI(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (plannerController.getType(Integer.parseInt(plannerController.getCurrPlannerId())).equals("reminders")) {
            editRemainder.run();
            return;
        }
        if (flag) {
            update();
            cl.show(main, "editPlanner");
        } else {
            this.initDailyEditForm();
            this.initProjectAddForm();
            this.initProjectEditForm();
            this.showMenu();
            cl.show(main, "editPlanner");
            flag = !flag;
        }
    }

    private void showMenu() {
        panel.setLayout(null);
        main.add(panel, "editPlanner");

        data.getPlanner(plannerController.getCurrPlannerId(), panel);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(475, 370, 100, 150);
        fb.addSubmitButton("delete", "Delete");
        fb.addSubmitButton("submit", "Submit");
        fb.addSuperButton("goBack", "Go Back", this.getParent());
        fb.addListener(this);
        menuForm = fb.getForm();
        panel.add(menuForm.getPanel());
        list.setListData(new String[]{"", "private", "public", "friends-only"});
        list.setBounds(475, 300, 150, 60);
        this.update();
    }

    private void update() {
        String type = plannerController.getType(Integer.parseInt(plannerController.getCurrPlannerId()));
        if (type.equals("daily")) {
            panel.removeAll();
            panel.add(menuForm.getPanel());
            panel.add(dailyEditForm.getPanel());
        } else if (type.equals("project") && isAdd) {
            panel.removeAll();
            panel.add(menuForm.getPanel());
            panel.add(projectAddForm.getPanel());
        } else if (type.equals("project")) {
            panel.removeAll();
            panel.add(menuForm.getPanel());
            panel.add(projectEditForm.getPanel());
        } else if (type.equals("reminders")){
            panel.removeAll();
            editRemainder.run();
            return;
        }
        data.getPlanner(plannerController.getCurrPlannerId(), panel);
        panel.add(list);
        this.getParent().run();
        cl.show(main, "editPlanner");
    }

    private void initDailyEditForm() {
        FormBuilder fb = new FormBuilder();
        fb.setBounds(450, 25, 200, 250);
        fb.addLabel("timeSlotPrompt",
                labelToStrings.get("timeSlotPrompt"));
        fb.addTextField("timeSlot");
        fb.addLabel("agendaPrompt",
                labelToStrings.get("agendaPrompt"));
        fb.addTextField("agenda");
        this.dailyEditForm = fb.getForm();
    }

    private void initProjectEditForm() {
        FormBuilder fb = new FormBuilder();
        fb.setBounds(450, 25, 200, 250);
        fb.addLabel("statusPrompt", labelToStrings.get("statusPrompt"));
        fb.addTextField("status");
        fb.addLabel("columnPrompt", labelToStrings.get("columnPrompt"));
        fb.addTextField("column");
        fb.addSubmitButton("changeToAdd", labelToStrings.get("changeToAdd"));
        fb.addListener(this);
        this.projectEditForm = fb.getForm();
    }

    private void initProjectAddForm() {
        FormBuilder fb = new FormBuilder();
        fb.setBounds(450, 25, 200, 250);
        fb.addLabel("addNamePrompt", labelToStrings.get("addNamePrompt"));
        fb.addTextField("addColumnName");
        fb.addLabel("taskNamePrompt", labelToStrings.get("taskNamePrompt"));
        fb.addTextField("taskName");
        fb.addSubmitButton("changeToEdit", labelToStrings.get("changeToEdit"));
        fb.addListener(this);
        this.projectAddForm = fb.getForm();
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == projectEditForm.get("changeToAdd") || e.getSource() == projectAddForm.get("changeToEdit")){
            isAdd = !isAdd;
            this.update();
        } if (e.getSource() == menuForm.get("submit")) {
            if (list.getSelectedIndex() != -1) {
                plannerController.changePrivacyStatus(list.getSelectedValue());
                this.update();
            }
            String plannerId = plannerController.getCurrPlannerId();
            String type = plannerController.getType(Integer.parseInt(plannerId));
            if (type.equals("daily")) {
                if (plannerController.edit(((JTextField)dailyEditForm.get("timeSlot")).getText(),
                        ((JTextField)dailyEditForm.get("agenda")).getText())) {
                    this.update();
                } else {
                    ((JLabel)dailyEditForm.get("timeSlotPrompt")).setText(labelToStrings.get("invalidPrompt"));
                    ((JTextField)dailyEditForm.get("agenda")).setText("");
                    ((JTextField)dailyEditForm.get("timeSlot")).setText("");
                }
            } else if (type.equals("project") && isAdd) {
                if (plannerController.add(((JTextField)projectAddForm.get("addColumnName")).getText(),
                        ((JTextField)projectAddForm.get("taskName")).getText())) {
                    this.update();
                } else {
                    ((JLabel)projectAddForm.get("addNamePrompt")).setText(labelToStrings.get("invalidPrompt"));
                }
            } else if (type.equals("project")) {
                if (plannerController.edit(((JTextField) projectEditForm.get("status")).getText(),
                        ((JTextField)projectEditForm.get("column")).getText())) {
                    this.update();
                } else {
                    ((JLabel)projectEditForm.get("statusPrompt")).setText(labelToStrings.get("invalidPrompt"));
                }
            }
        } else if (e.getSource() == menuForm.get("delete")) {
            if (accessController.removePlanner(accessController.getCurrUserId(), plannerController.getCurrPlannerId())){
                this.getParent().run();
            }
        }
    }
}
