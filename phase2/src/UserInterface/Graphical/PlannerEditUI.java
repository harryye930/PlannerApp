package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlannerEditUI extends GeneralPresenter {
//    private final String dailyMessage = "Please enter the time zone you \n" +
//            "want to edit/add agenda to\n(in form of HH:MM)";
//    private final String projectMessage = "Please enter the column you want to"

    private boolean flag = false;
    private boolean firstStatus;

    // Pane/Panel
    private JScrollPane planner;
    private final JPanel editPlanner = new JPanel();
    private JPanel projectPanel;

    //Label
    private final JLabel message = new JLabel();

    //Text
    private final JTextField text0 = new JTextField();
    private final JTextField text1 = new JTextField();

    //Button
    private final JButton submit = new JButton("Submit");
    private final JButton changeButton = new JButton();
    private final JButton back = new JButton("Go back");

    private CardLayout current;

    public PlannerEditUI(String parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            planner = data.getPlanner(plannerController.getCurrPlannerId());
            cl.show(main, "editPlanner");
        } else {
            this.showEditUI();
            cl.show(main, "editPlanner");
            flag = !flag;
        }
    }

    private void showEditUI() {
        main.add(editPlanner, "editPlanner");
        planner = data.getPlanner(plannerController.getCurrPlannerId());
        planner.setBounds(20, 20, 450, 500);
        editPlanner.setLayout(null);
        editPlanner.add(planner);

        JPanel currentPanel;
        if (plannerController.getType(Integer.parseInt(plannerController.getCurrPlannerId())).equals("daily")) {
            currentPanel = this.getDailyPanel();
            currentPanel.setBounds(475, 25, 200, 200);
        } else { //Project
            currentPanel = this.getProjectPanel();
            currentPanel.setBounds(472, 25, 200, 200);
        }
        editPlanner.add(currentPanel);

        submit.setBounds(500, 350, 150, 30);
        back.setBounds(500, 400, 150, 30);
        editPlanner.add(submit);
        submit.addActionListener(this);
        editPlanner.add(back);
        back.addActionListener(this);
    }

    private JPanel getDailyPanel() {
        JPanel dailyPanel = new JPanel();
        dailyPanel.setLayout(new GridLayout(4, 1));

        JLabel timeSlot = new JLabel("Please enter the time slot you want to add agenda to (in form of HH:MM)");
        JLabel agenda = new JLabel("Please enter the agenda you wan to add");

        dailyPanel.add(timeSlot);
        dailyPanel.add(text0);
        dailyPanel.add(agenda);
        dailyPanel.add(text1);

        return dailyPanel;
    }

    private JPanel getProjectPanel() {
        projectPanel = new JPanel();
        current = new CardLayout();
        projectPanel.setLayout(current);

        JPanel edit = new JPanel();
        JPanel add = new JPanel();
        projectPanel.add(edit, "editProject");
        projectPanel.add(add, "addProject");

        //Add
        add.setLayout(new GridLayout(4, 1));
        add.setPreferredSize(new Dimension(300, 200));

        JLabel columnName = new JLabel("Please enter the column name you want to add task to");
        JLabel task = new JLabel("Please enter the task you want to add.");

        add.add(columnName);
        add.add(text0);
        add.add(task);
        add.add(text1);

        //Edit
        edit.setLayout(new GridLayout(5,1));
        edit.setPreferredSize(new Dimension(300, 200));

        JLabel taskName = new JLabel("Please enter the task name you wan to change status:");
        JLabel destinyColumn = new JLabel("Please enter the column name you want to change to:");

        edit.add(taskName);
        edit.add(text0);
        edit.add(destinyColumn);
        edit.add(text1);

        changeButton.addActionListener(this);
        edit.add(changeButton);

        if (this.firstStatus) {
            changeButton.setText("Change to edit page");
            current.show(projectPanel, "add");
        } else {
            changeButton.setText("change to add page");
            current.show(projectPanel, "edit");
        }

        return projectPanel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeButton) {
            firstStatus = !firstStatus;
            if (this.firstStatus) {
                changeButton.setText("Change to edit page");
                current.show(projectPanel, "add");
            } else {
                changeButton.setText("change to add page");
                current.show(projectPanel, "edit");
            }
        } else if (e.getSource() == submit) {
            plannerController.edit(text0.getText(), text1.getText());
            text0.setText("");
            text1.setText("");
            this.run();
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }
}
