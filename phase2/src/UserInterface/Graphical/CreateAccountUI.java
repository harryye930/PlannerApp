package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateAccountUI extends GeneralPresenter {
    private boolean flag = false;

    private JLabel createAccount = new JLabel("Register your account!");
    private JTextField email = new JTextField();
    private JTextField userName = new JTextField();
    private JPasswordField password0 = new JPasswordField();
    private JPasswordField password1 = new JPasswordField();
    private JButton register = new JButton("Register");
    private JButton back = new JButton("Go back");
    private JPanel create = new JPanel();


    public CreateAccountUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "createAccount");
        } else {
            this.createAccount();
            cl.show(main, "createAccount");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void createAccount() {
        create.setLayout(null);
        main.add(create, "createAccount");

        createAccount.setBounds(0, 50, 700, 50);
        createAccount.setHorizontalAlignment(JLabel.CENTER);
        createAccount.setOpaque(true);
        create.add(createAccount);

        JPanel grids = new JPanel();
        grids.setLayout(new GridLayout(5, 2));
        grids.setBounds(0, 100, 700, 250);
        create.add(grids);

        JLabel emailPrompt = new JLabel("Please enter your email");
        emailPrompt.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(emailPrompt);

        grids.add(email);

        JLabel userNamePrompt = new JLabel("User name:");
        userNamePrompt.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(userNamePrompt);

        grids.add(userName);

        JLabel passwordPrompt0 = new JLabel("Password");
        passwordPrompt0.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(passwordPrompt0);

        grids.add(password0);

        JLabel passwordPrompt1 = new JLabel("Reconfirm your Password");
        passwordPrompt1.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(passwordPrompt1);

        grids.add(password1);
        
        grids.add(register);
        register.addActionListener(this);
        grids.add(back);
        back.addActionListener(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            createNewAccount();
        } else if (e.getSource() == back) {
            cl.show(main, "LoginPage");
        }
    }

    private void createNewAccount() {
    }
}
