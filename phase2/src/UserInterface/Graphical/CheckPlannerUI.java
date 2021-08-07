package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CheckPlannerUI extends GeneralPresenter {
    private final String separator = "\n======================";
    private boolean flag = false;

    private GeneralPresenter plannerEdit = new PlannerEditUI("checkPlanner");

    //JPanel
    JPanel checkPlanner = new JPanel();

    //TextArea/Field

    JScrollPane plannerInfo;
    JTextArea prompt = new JTextArea("Please enter the planner ID\n you want to operate on:");
    JTextField plannerId = new JTextField();

    //Button
    JButton back = new JButton("Go back");
    JButton submit = new JButton("Submit");

    public CheckPlannerUI(String parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
//            plannerInfo.setText(plannerController.viewUserPlanners() +
//                    separator + plannerController.viewPublicPlanners());
            plannerInfo = data.getPlanners();
            cl.show(main, "checkPlanner");
        } else {
            this.showPlanners();
            cl.show(main, "checkPlanner");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    public void showPlanners() {
        checkPlanner.setLayout(null);
        main.add(checkPlanner, "checkPlanner");

        plannerInfo = data.getPlanners();
        plannerInfo.setBounds(25, 25, 400, 500);

        plannerInfo.setBackground(new Color(213, 212, 212));
        checkPlanner.add(plannerInfo);

        prompt.setBounds(450, 50, 200, 50);
        prompt.setEditable(false);
        checkPlanner.add(prompt);

        plannerId.setBounds(500, 130, 100, 50);
        checkPlanner.add(plannerId);

        submit.setBounds(515, 200, 70, 40);
        submit.addActionListener(this);
        checkPlanner.add(submit);

        back.setBounds(515, 250, 70, 40);
        back.addActionListener(this);
        checkPlanner.add(back);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if (plannerController.checkPlanner(plannerId.getText())) {
                this.plannerEdit.run();
            } else {
                this.prompt.setText("Invalid ID, please try again!");
            }
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }
}
