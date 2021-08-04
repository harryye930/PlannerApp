package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlannerOptionUI extends GeneralPresenter {
    private boolean flag = false;
    // all buttons
    private final JButton newPlannerButton = new JButton("Create New Planner");
    private final JButton editPlannerButton = new JButton("Edit Your Planner");
    private final JButton deletePlannerButton = new JButton("Delete Your Planner");
    private final JButton showPlannerButton = new JButton("Show Planners");
    private final JButton returnToMainMenuButton= new JButton("Return to Main Menu");

    //planner panel
    private final JPanel plannerMenu = new JPanel();

    //planner menu text
    private JLabel prompt;

    public PlannerOptionUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        this.showPlannerMenu();
        cl.show(main, "plannerMenu");
        frame.setVisible(true);
    }
    private void showPlannerMenu(){
        main.add(plannerMenu, "plannerMenu");
        plannerMenu.setLayout(null);

        prompt = new JLabel("Planner Menu");
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        prompt.setOpaque(true);
        plannerMenu.add(prompt);

        JPanel panel = new JPanel();
        panel.setBounds(150, 150, 400, 200);
        panel.setLayout(new GridLayout(4, 1));
        plannerMenu.add(panel);

        panel.add(newPlannerButton);
        panel.add(editPlannerButton);
        panel.add(deletePlannerButton);
        panel.add(showPlannerButton);
        panel.add(returnToMainMenuButton);

        newPlannerButton.addActionListener(this);
        editPlannerButton.addActionListener(this);
        deletePlannerButton.addActionListener(this);
        showPlannerButton.addActionListener(this);
        returnToMainMenuButton.addActionListener(this);


    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnToMainMenuButton){
            cl.show(main, "regularUserMainMenu");


        }


    }
}
