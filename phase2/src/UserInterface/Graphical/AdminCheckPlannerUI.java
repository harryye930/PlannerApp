package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AdminCheckPlannerUI extends GeneralPresenter {
    private boolean flag = false;
    private String  userId;

    // Panel
    private JScrollPane accountInfo;

    // Button
    private JButton delete = new JButton("Delete");
    private JButton back = new JButton("Go back");
    private JButton submit = new JButton("Submit");

    // List
    private final JList<String> changePrivacy = new JList<String>(new String[]{"private", "public", "friends-only"});

    // Text
    private final JTextField text = new JTextField();

    public AdminCheckPlannerUI(String parent) {
        this.setParent(parent);
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            update();
            cl.show(main, "adminCheckPlanner");
        } else {
            this.showMenu();
            cl.show(main, "adminCheckPlanner");
            flag = !flag;
        }
    }

    private void showMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(null);
        main.add(menu, "adminCheckPlanner");

        accountInfo = data.getAccount(userId);
        accountInfo.setBounds(25, 25, 400, 500);
        menu.add(accountInfo);

        JLabel prompt = new JLabel("<html>Please enter the planner ID you<br/> want to operate on:</html>");
        prompt.setBounds(475, 100, 200, 40);
        menu.add(prompt);

        text.setBounds(475, 150, 200, 40);
        menu.add(text);

        delete.setBounds(515, 200, 100, 40);
        menu.add(delete);
        delete.addActionListener(this);

        changePrivacy.setBounds(500, 250, 150, 60);
        menu.add(changePrivacy);

        submit.setBounds(500, 350, 150, 60);
        menu.add(submit);
        submit.addActionListener(this);

        back.setBounds(515, 400, 100, 40);
        menu.add(back);
        back.addActionListener(this);
    }

    private void update() {
        accountInfo = data.getAccount(userId);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            if (plannerController.checkPlanner(text.getText())) {
                plannerController.deletePlanner(text.getText());
                text.setText("");
                update();
            } else {
                text.setText("Invalid input, please try again!");
            }
        } else if (e.getSource() == submit) {
            if (plannerController.checkPlanner(text.getText())) {
                if (changePrivacy.getSelectedIndex() != -1) {
                    plannerController.changePrivacyStatus(changePrivacy.getSelectedValue());
                    text.setText("");
                    changePrivacy.setSelectedIndex(-1);
                    update();
                }
            } else {
                text.setText("Invalid input, please try again!");
            }
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }
}
