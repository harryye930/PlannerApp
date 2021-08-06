package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginUI extends GeneralPresenter {

    private final JPanel menu = new JPanel();
    private JLabel prompt;
    private JTextField retriever;
    private JTextField password;
    private JButton login;
    private JButton createAccount;

    private final GeneralPresenter adminUI = new AdminUI("LoginPage");
    private final GeneralPresenter regularUserUI = new RegularAccountUI("LoginPage");
    private final GeneralPresenter createAccountUI = new CreateAccountUI("LoginPage");


    public LoginUI() {}

    public LoginUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        this.showLoginPage();
        cl.show(main, "LoginPage");
        frame.setVisible(true);
    }

    private void showLoginPage() {

        menu.setLayout(null);
        main.add(menu, "LoginPage");

        prompt = new JLabel("Welcome!");
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        prompt.setOpaque(true);
        menu.add(prompt);

        JPanel panel = new JPanel();
        panel.setBounds(100, 150, 400, 200);
        panel.setLayout(new GridLayout(3, 2));
        menu.add(panel);

        JLabel retrieverPrompt = new JLabel("User ID or Email:");
        retrieverPrompt.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(retrieverPrompt);

        retriever = new JTextField();
        //retriever.setVisible(false);
        panel.add(retriever);

        JLabel passwordPrompt = new JLabel("Password:");
        passwordPrompt.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(passwordPrompt);

        password = new JPasswordField();
        panel.add(password);

        login = new JButton("Login");
        login.addActionListener(this);
        panel.add(login);

        createAccount = new JButton("Create new Account");
        createAccount.addActionListener(this);
        panel.add(createAccount);

        //exit = new JButton("Exit");
        //exit.addActionListener(this);
        //panel.add(exit);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            this.loginIdentifier();
            this.password.setText("");
        } else if (e.getSource() == createAccount) {
            this.createAccountUI.run();
        }
    }

    private void loginIdentifier() {
        if (accessController.logIn(retriever.getText(), password.getText())) {
            if (accessController.isAdmin(accessController.getCurrUserId()).equals("admin")) {
                this.adminUI.run();
            } else {
                this.regularUserUI.run();
            }
        } else {
            this.prompt.setText("Invalid input, please try again!");
        }
    }

    public static void main(String[] args) {
        LoginUI x = new LoginUI();
        x.run();
    }
}
