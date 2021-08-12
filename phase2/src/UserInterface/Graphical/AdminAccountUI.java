package UserInterface.Graphical;

import UserInterface.GeneralPresenter;
import sun.jvm.hotspot.gc_interface.GCWhen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//TODO: combine with AccountOptionUI
/**
 * GUI class for displaying the main menu for an admin user.
 * The main menu includes: planner options, edit template, suspend account
 * - Edit Template
 * - Planner options
 *  - change privacy setting
 *  - interact with all planners
 *  - delete planner
 * - Suspend account
 */
public class AdminAccountUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;

    private final JPanel adminUserMainMenu = new JPanel();
    private JLabel prompt;
    private final JButton plannerButton = new JButton("Planner Options");
    private final JButton templateButton = new JButton("Template Options");
    private final JButton accountButton = new JButton("Account Options");
    private final JButton logOutButton = new JButton("Log out");

    //private final GeneralPresenter plannerOptionUI = new AdminPlannerOptionUI("adminUserMainMenu");
    private final GeneralPresenter templateOptionUI = new AdminTemplateOptionUI(this);
    private final GeneralPresenter accountOptionUI = new AdminAccountOptionUI(this);

    public AdminAccountUI(GeneralPresenter parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "adminUserMainMenu");
        } else {
            this.showMenu();
            cl.show(main, "adminUserMainMenu");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(adminUserMainMenu, "adminUserMainMenu");

        adminUserMainMenu.setLayout(null);

        prompt = new JLabel("Main Menu for Admin User");
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        prompt.setOpaque(true);
        adminUserMainMenu.add(prompt);

        JPanel panel = new JPanel();
        panel.setBounds(150, 150, 400, 200);
        panel.setLayout(new GridLayout(4, 1));
        adminUserMainMenu.add(panel);

        panel.add(plannerButton);
        panel.add(templateButton);
        panel.add(accountButton);
        panel.add(logOutButton);

        plannerButton.addActionListener(this);
        templateButton.addActionListener(this);
        accountButton.addActionListener(this);
        logOutButton.addActionListener(this);


    }

    /**
     * Invoked when an action occurs.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plannerButton){
            //this.plannerOptionUI.run();
        }else if (e.getSource() == templateButton){
            this.templateOptionUI.run();
        } else if (e.getSource() == logOutButton){
            accessController.logOut();
            this.getParent().run();
        } else if (e.getSource() == accountButton) {
            this.accountOptionUI.run();
        }

    }
}
