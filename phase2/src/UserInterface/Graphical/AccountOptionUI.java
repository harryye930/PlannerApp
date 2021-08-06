package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccountOptionUI extends GeneralPresenter {
    private boolean flag = false;

    //Panel
    private final JPanel accountMenu = new JPanel();
    private JPanel changeMenu;
    JScrollPane accounts;

    //Button
    private final JButton changName = new JButton("Change User name");
    private final JButton changePassword = new JButton("Change Password");
    private final JButton back = new JButton("Go back");
    private JButton submit0;
    private JButton submit1;
    private JButton goBack;

    //Text
    private JTextField text0;
    private JPasswordField text1;

    //Label
    private JLabel message;

    public AccountOptionUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (this.flag) {
            accounts = data.getAccounts();
            cl.show(main, "accountMenu");
        } else {
            this.showAccountMenu();
            cl.show(main, "accountMenu");
            flag = !flag;
        }
    }

    private void showAccountMenu() {
        accountMenu.setLayout(null);
        main.add(accountMenu, "accountMenu");

        accounts = data.getAccounts();
        accounts.setBounds(75, 25, 450, 100);
        accountMenu.add(accounts);

        changName.setBounds(515, 150, 150, 40);
        changePassword.setBounds(515, 200, 150, 40);
        back.setBounds(515, 250, 70, 40);
        accountMenu.add(changName);
        accountMenu.add(changePassword);
        accountMenu.add(back);
        changName.addActionListener(this);
        changePassword.addActionListener(this);
        back.addActionListener(this);
    }

    private void changeName() {
        changeMenu = new JPanel();
        changeMenu.setLayout(null);
        main.add(changeMenu, "changeMenu");

        message = new JLabel("Please enter your new user name:");
        message.setBounds(125, 100, 500, 50);
        message.setFont(new Font("MV Boli", Font.PLAIN, 20));
        changeMenu.add(message);

        text0 = new JTextField();
        text0.setBounds(125, 175, 400, 40);
        changeMenu.add(text0);

        submit0 = new JButton("Submit");
        submit0.setBounds(275, 275, 100, 40);
        submit0.addActionListener(this);
        changeMenu.add(submit0);

        goBack = new JButton("Go back");
        goBack.setBounds(275, 325, 100, 40);
        goBack.addActionListener(this);
        changeMenu.add(goBack);

        cl.show(main, "changeMenu");
    }

    private void changePassword() {
        changeMenu = new JPanel();
        changeMenu.setLayout(null);
        main.add(changeMenu, "changeMenu");

        message = new JLabel("Please enter your original password:");
        message.setBounds(125, 50, 500, 50);
        message.setFont(new Font("MV Boli", Font.PLAIN, 20));
        changeMenu.add(message);

        text0 = new JTextField();
        text0.setBounds(125, 100, 400, 40);
        changeMenu.add(text0);

        JLabel message0 = new JLabel("Please enter your new password:");
        message0.setBounds(125, 150, 500, 50);
        message0.setFont(new Font("MV Boli", Font.PLAIN, 20));
        changeMenu.add(message0);

        text1 = new JPasswordField();
        text1.setBounds(125, 200, 400, 40);
        changeMenu.add(text1);

        submit1 = new JButton("Submit");
        submit1.setBounds(275, 300, 100, 40);
        submit1.addActionListener(this);
        changeMenu.add(submit1);

        goBack = new JButton("Go back");
        goBack.setBounds(275, 350, 100, 40);
        goBack.addActionListener(this);
        changeMenu.add(goBack);

        cl.show(main, "changeMenu");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            cl.show(main, this.getParent());
        } else if (e.getSource() == changName) {
            this.changeName();
        } else if (e.getSource() == changePassword) {
            this.changePassword();
        } else if (e.getSource() == submit0) {
            accessController.changeUserName(accessController.getCurrUserId(), text0.getText());
            main.remove(changeMenu);
            this.run();
            //cl.show(main, "accountMenu");
        } else if (e.getSource() == submit1) {
            String message = accessController.changePassword(accessController.getCurrUserId(), text0.getText(), text1.getText());
            main.remove(changeMenu);
            //cl.show(main, "accountMenu");
            text0.setText("");
            text0.setText(message);

        } else if (e.getSource() == goBack) {
            //cl.show(main, "accountMenu");
            main.remove(changeMenu);
            this.run();
        }
    }
}
