package graphical_user_interface;

import gateway.UIGateway;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EditReminderPlannerUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private boolean isAdd = true;

    private final Map<String, String> labelToStrings = new UIGateway().loadEditReminderUITexts();

    //Panel
    JPanel reminderEdit = new JPanel();

    //Button
    private final JButton changeButton = new JButton();
    private final JButton submit = new JButton(labelToStrings.get("submit"));
    private final JButton delete = new JButton(labelToStrings.get("delete"));
    private final JButton back = new JButton(labelToStrings.get("goBack"));

    //Text
    private final JTextField text0 = new JTextField();
    private final JTextField text1 = new JTextField();

    //Label
    JLabel prompt0 = new JLabel();
    JLabel prompt1 = new JLabel();

    public EditReminderPlannerUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "editReminder");
        } else {
            this.showEditPanel();
            cl.show(main, "editReminder");
            flag = !flag;
        }
    }

    private void showEditPanel() {
        main.add(reminderEdit, "editReminder");
        reminderEdit.setLayout(null);

        data.getPlanner(plannerController.getCurrPlannerId(), reminderEdit);

        prompt1.setBounds(472, 25, 200, 50);
        prompt0.setBounds(472, 125, 200, 40);
        text0.setBounds(472, 75, 200, 40);
        text1.setBounds(472, 175, 200, 40);
        changeButton.setBounds(475, 225, 200, 40);
        submit.setBounds(500, 275, 150, 40);
        delete.setBounds(500, 325, 150, 40);
        back.setBounds(500, 375, 150, 40);

        reminderEdit.add(changeButton);
        reminderEdit.add(text0);
        reminderEdit.add(text1);
        reminderEdit.add(prompt0);
        reminderEdit.add(prompt1);
        reminderEdit.add(text0);
        reminderEdit.add(text1);
        reminderEdit.add(submit);
        reminderEdit.add(delete);
        reminderEdit.add(back);

        changeButton.addActionListener(this);
        submit.addActionListener(this);
        delete.addActionListener(this);
        back.addActionListener(this);
        this.updateLabel();
    }

    private void updateLabel() {
        if (isAdd) {
            prompt0.setText(labelToStrings.get("prompt0f"));
            prompt1.setText(labelToStrings.get("prompt1f"));
            changeButton.setText(labelToStrings.get("changeButtonF"));
        } else {
            prompt0.setText(labelToStrings.get("prompt0s"));
            prompt1.setText(labelToStrings.get("prompt1s"));
            changeButton.setText(labelToStrings.get("changeButtonS"));
        }
        data.getPlanner(plannerController.getCurrPlannerId(), reminderEdit);
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeButton) {
            isAdd = !isAdd;
            this.updateLabel();
        } else if (e.getSource() == submit) {
            if (isAdd) {
                System.out.println(plannerController.add(text1.getText(), text0.getText()));
            } else {
                System.out.println(plannerController.changeTaskStatus(text1.getText(), text0.getText()));
            }
            data.getPlanner(plannerController.getCurrPlannerId(), reminderEdit);
        }  else if (e.getSource() == back) {
            this.getParent().run();
        } else if (e.getSource() == delete) {
            accessController.removePlanner(accessController.getCurrUserId(), plannerController.getCurrPlannerId());
            this.getParent().run();
        }
    }
}
