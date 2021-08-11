package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditRemainder extends GeneralPresenter {
    private boolean flag = false;
    private boolean isAdd = true;

    //Panel
    private JScrollPane plannerInfo;
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

    public EditRemainder(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "editRemainder");
        } else {
            this.showEditPanel();
            cl.show(main, "editRemainder");
            flag = !flag;
        }
    }

    private void showEditPanel() {
        main.add(remainderEdit, "editRemainder");

        plannerInfo = data.getPlanner(plannerController.getCurrPlannerId(), remainderEdit);

        prompt1.setBounds(472, 25, 200, 30);
        prompt0.setBounds(472, 125, 200, 30);
        text0.setBounds(472, 75, 200, 30);
        text1.setBounds(472, 175, 200, 30);
        changeButton.setBounds(500, 225, 150, 30);
        submit.setBounds(500, 275, 150, 30);
        back.setBounds(472, 325, 150, 30);

        remainderEdit.add(changeButton);
        remainderEdit.add(text0);
        remainderEdit.add(text1);

        changeButton.addActionListener(this);
        submit.addActionListener(this);
        back.addActionListener(this);
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
        plannerInfo = data.getPlanner(plannerController.getCurrPlannerId(), remainderEdit);
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
                plannerController.add(text0.getText(), text1.getText());
            } else {
                plannerController.changeTaskStatus(text0.getText(), text1.getText());
            }
        }  else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }
}
