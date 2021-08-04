package UserInterface.Graphical;

import Entity.Template;
import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegularAccountUI extends GeneralPresenter {
    private boolean flag = false;

    private final JPanel regularUserMainMenu = new JPanel();
    private JLabel prompt;
    private final JButton plannerButton = new JButton("Planner Option");
    private final JButton templateButton = new JButton("Template Option");
    private final JButton accountButton = new JButton("Account Setting");
    private final JButton logOutButton = new JButton("Logout");

    private final GeneralPresenter plannerOptionUI = new PlannerOptionUI("LoginPage");
    private final GeneralPresenter templateOptionUI = new TemplateOptionUI("LoginPage");


    public RegularAccountUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "regularUserMainMenu");
        } else {
            this.showMenu();
            cl.show(main, "regularUserMainMenu");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(regularUserMainMenu, "regularUserMainMenu");

        regularUserMainMenu.setLayout(null);

        prompt = new JLabel("Main Menu for Regular User");
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        prompt.setOpaque(true);
        regularUserMainMenu.add(prompt);

        JPanel panel = new JPanel();
        panel.setBounds(150, 150, 400, 200);
        panel.setLayout(new GridLayout(4, 1));
        regularUserMainMenu.add(panel);

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
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plannerButton){
            this.plannerOptionUI.run();
//            JOptionPane.showMessageDialog(frame, "Planner Button Clicked");
        }

        if (e.getSource() == templateButton){
            this.templateOptionUI.run();
//            JOptionPane.showMessageDialog(frame, "Planner Button Clicked");
        }

        else if (e.getSource() == logOutButton){
            cl.show(main, "LoginPage");
        }
    }

}
