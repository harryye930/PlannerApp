package graphical_user_interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditReminder extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private boolean isAdd = true;

    //Panel
    JPanel remainderEdit = new JPanel();

    //Button
    private final JButton changeButton = new JButton();
    private final JButton submit = new JButton("Submit");
    private final JButton back = new JButton("Go back");

    //Text
    private final JTextField text0 = new JTextField();
    private final JTextField text1 = new JTextField();

    //Label
    JLabel prompt0 = new JLabel();
    JLabel prompt1 = new JLabel();

    public EditReminder(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
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
        main.add(remainderEdit, "editReminder");
        remainderEdit.setLayout(null);

        data.getPlanner(plannerController.getCurrPlannerId(), remainderEdit);

        prompt1.setBounds(472, 25, 200, 40);
        prompt0.setBounds(472, 125, 200, 40);
        text0.setBounds(472, 75, 200, 40);
        text1.setBounds(472, 175, 200, 40);
        changeButton.setBounds(475, 225, 200, 40);
        submit.setBounds(500, 275, 150, 40);
        back.setBounds(500, 325, 150, 40);

        remainderEdit.add(changeButton);
        remainderEdit.add(text0);
        remainderEdit.add(text1);
        remainderEdit.add(prompt0);
        remainderEdit.add(prompt1);
        remainderEdit.add(text0);
        remainderEdit.add(text1);
        remainderEdit.add(submit);
        remainderEdit.add(back);

        changeButton.addActionListener(this);
        submit.addActionListener(this);
        back.addActionListener(this);
        this.updateLabel();
    }

    private void updateLabel() {
        if (isAdd) {
            prompt0.setText("Please enter the task heading it wants to add:");
            prompt1.setText("Please enter the task date it wants to add:");
            changeButton.setText("Change to change status menu");
        } else {
            prompt0.setText("Please enter the task name the user wants to change status");
            prompt1.setText("Please enter the status the user wants to change");
            changeButton.setText("Change to add task menu");
        }
        data.getPlanner(plannerController.getCurrPlannerId(), remainderEdit);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeButton) {
            isAdd = !isAdd;
            this.updateLabel();
        } else if (e.getSource() == submit) {
            if (isAdd) {
                System.out.println(plannerController.add(text0.getText(), text1.getText()));
            } else {
                plannerController.changeTaskStatus(text0.getText(), text1.getText());
            }
            data.getPlanner(plannerController.getCurrPlannerId(), remainderEdit);
        }  else if (e.getSource() == back) {
            this.getParent().run();
        }
    }
}
