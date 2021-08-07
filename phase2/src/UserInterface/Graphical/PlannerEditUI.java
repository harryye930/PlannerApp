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
    private JPanel projectPanel = new JPanel();
    JPanel currentPanel;
    private JPanel edit;
    private JPanel add;

    //Label
    private final JLabel message = new JLabel();

    //Text
    private final JTextField text0 = new JTextField();
    private final JTextField text1 = new JTextField();

    //Button
    private final JButton submit = new JButton("Submit");
    private final JButton changeButton = new JButton();
    private final JButton back = new JButton("Go back");

    private CardLayout current = new CardLayout();

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
        changeButton.setBounds(500, 250, 150, 30);

        if (plannerController.getType(Integer.parseInt(plannerController.getCurrPlannerId())).equals("project")) {
            editPlanner.add(changeButton);
            changeButton.addActionListener(this);
        }

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
        projectPanel.setLayout(current);

        edit = new JPanel();
        add = new JPanel();
        projectPanel.add(edit, "editProject");
        projectPanel.add(add, "addProject");
//        changeButton.addActionListener(this);

        //Add
        if (firstStatus) {
            add.setLayout(new GridLayout(4, 1));
            add.setPreferredSize(new Dimension(150, 80));

            JLabel columnName = new JLabel("<html>Please enter the column name<br/> you want to add task to</html>");
            JLabel task = new JLabel("<html>Please enter the task you<br/> want to add.</html>");

            add.add(columnName);
            add.add(text0);
            add.add(task);
            add.add(text1);

        }
        else {
            //Edit
            edit.setLayout(new GridLayout(4, 1));
            edit.setPreferredSize(new Dimension(150, 80));

            JLabel taskName = new JLabel("<html>Please enter the task name<br/> you wan to change status:</html>");
            JLabel destinyColumn = new JLabel("<html>Please enter the column name<br/> you want to change to:</html>");

            edit.add(taskName);
            edit.add(text0);
            edit.add(destinyColumn);
            edit.add(text1);

        }

//        projectPanel.add(changeButton);
        //changeButton.setBounds(600, 200, 150, 30);

        if (this.firstStatus) {
            changeButton.setText("Change to edit page");
            current.show(projectPanel, "addProject");
        } else {
            changeButton.setText("Change to add page");
            current.show(projectPanel, "editProject");
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
                projectPanel.remove(edit);
                edit.removeAll();
                changeButton.setText("Change to edit page");
                //projectPanel = this.getProjectPanel();
                current.show(projectPanel, "addProject");
                currentPanel = this.getProjectPanel();
            } else {
                projectPanel.remove(add);
                add.removeAll();
                changeButton.setText("Change to add page");
                //projectPanel = this.getProjectPanel();
                current.show(projectPanel, "editProject");
                currentPanel = this.getProjectPanel();
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
