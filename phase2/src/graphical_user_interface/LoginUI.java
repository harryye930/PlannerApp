package graphical_user_interface;

import gateway.UIGateway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * GUI class for the start of the program.
 * Displays three options for the user to either log in, create a new account, or login as guest.
 */
public class LoginUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadLoginUITexts();

    private final JPanel menu = new JPanel();
    private JLabel prompt;
    private JTextField retriever;
    private JTextField password;

    private JButton login;
    private JButton createAccount;
    private JButton guest;
    private JButton forgetPassword;

    private final GeneralUI adminUI = new AdminAccountUI(this);
    private final GeneralUI regularUserUI = new RegularAccountUI(this);
    private final GeneralUI createAccountUI = new CreateAccountUI(this);


    public LoginUI() {}

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "LoginPage");
        } else {
            this.showLoginPage();
            cl.show(main, "LoginPage");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void showLoginPage() {

        menu.setLayout(null);
        main.add(menu, "LoginPage");

        prompt = new JLabel(labelToStrings.get("prompt"));
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        menu.add(prompt);

        JPanel panel = new JPanel();
        panel.setBounds(100, 150, 400, 200);
        panel.setLayout(new GridLayout(4, 2));
        menu.add(panel);

        JLabel retrieverPrompt = new JLabel(labelToStrings.get("retrieverPrompt"));
        retrieverPrompt.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(retrieverPrompt);

        retriever = new JTextField();
        panel.add(retriever);

        JLabel passwordPrompt = new JLabel(labelToStrings.get("passwordPrompt"));
        passwordPrompt.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(passwordPrompt);

        password = new JPasswordField();
        panel.add(password);

        login = new JButton(labelToStrings.get("login"));
        login.addActionListener(this);
        panel.add(login);

        createAccount = new JButton(labelToStrings.get("createAccount"));
        createAccount.addActionListener(this);
        panel.add(createAccount);

        guest = new JButton(labelToStrings.get("guest"));
        guest.addActionListener(this);
        panel.add(guest);

        forgetPassword = new JButton(labelToStrings.get("forgetPassword"));
        forgetPassword.addActionListener(this);
        panel.add(forgetPassword);

    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            this.loginIdentifier();
            this.password.setText("");
        } else if (e.getSource() == createAccount) {
            this.createAccountUI.run();
        } else if (e.getSource() == guest) {
            accessController.createAccount("", "", "");
            this.regularUserUI.run();
        } else if (e.getSource() == forgetPassword) {
            if (accessController.updateAndSaveTempPassword(this.retriever.getText())) {
                prompt.setText("A Text file has been created which contains the temporary password");
            } else {
                prompt.setText("Invalid ID or email!");
            }
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
            this.prompt.setText(labelToStrings.get("invalidInput"));
        }
    }
}
