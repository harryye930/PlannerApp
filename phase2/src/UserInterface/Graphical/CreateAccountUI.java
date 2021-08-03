package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class CreateAccountUI extends GeneralPresenter {
    private boolean flag = false;
    private final GeneralPresenter adminUI = new AdminUI("createAccount");
    private final GeneralPresenter regularAccUI = new RegularAccountUI("createAccount");

    private final JLabel createAccount = new JLabel("Register your account!");
    private final JTextField email = new JTextField();
    private final JTextField userName = new JTextField();
    private final JPasswordField password0 = new JPasswordField();
    private final JPasswordField password1 = new JPasswordField();
    private final JButton register = new JButton("Register");
    private final JButton back = new JButton("Go back");
    private final JPanel create = new JPanel();
    private JPanel grids;


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

        grids = new JPanel();
        grids.setLayout(new GridLayout(5, 2));
        grids.setBounds(70, 100, 500, 250);
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
        if (!Objects.equals(password0.getText(), password0.getText())) {
            this.createAccount.setText("Incorrect password, please try again");
        } else {
            String id = controller.createAccount(email.getText(), userName.getText(), password0.getText());
            grids.setVisible(false);
            this.createAccount.setText("Please remember your ID:(You can also login by email)\n" + id);
            controller.logIn(id, password0.getText());
            if (controller.accountRole().equals("admin")) {
                this.adminUI.run();
            } else {
                this.regularAccUI.run();
            }
        }
    }
}
