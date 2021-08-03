package UserInterface.Graphical;

import Entity.Template;
import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegularAccountUI extends GeneralPresenter {
    private final JPanel regularUserMainMenu = new JPanel();
    private JLabel prompt;
    private JButton plannerButton = new JButton("Planners");
    private JButton templateButton = new JButton("Templates");
    private JButton accountButton = new JButton("Account");
    private JButton logOutButton = new JButton("Logout");

    public RegularAccountUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        this.showMenu();
        cl.show(main, "regularUserPage");
        frame.setVisible(true);



    }

    private void showMenu() {
        prompt = new JLabel("Main Menu for Regular User");
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        prompt.setOpaque(true);
        regularUserMainMenu.add(prompt);

        main.add(regularUserMainMenu, "regularUserMenu");

        JPanel panel = new JPanel();
        panel.setBounds(100, 150, 400, 200);
        panel.setLayout(new GridLayout(3, 2));
        regularUserMainMenu.add(panel);

        regularUserMainMenu.add(plannerButton);
        regularUserMainMenu.add(templateButton);
        regularUserMainMenu.add(accountButton);
        regularUserMainMenu.add(logOutButton);
        logOutButton.addActionListener(this);




    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOutButton){
            cl.show(main, "loginPage");
        }


    }

}
