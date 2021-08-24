package graphical_user_interface;

import gateway.UIGateway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.Objects;

/**
 * GUI class for creating a new account by entering email, username, password, and confirmed password.
 * Allows user to create a temporary account by checking a box for creating temporary account.
 * If the temporary account checkbox is not selected, then either a regular account or an admin account will be
 * automatically created, based on the email domain that the user enters.
 */
public class CreateAccountUI extends GeneralUI implements KeyListener, ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadCreateAccountUITexts();

    private final GeneralUI adminAccUI;
    private final GeneralUI regularAccUI;

    private final JLabel createAccount = new JLabel(labelToStrings.get("createAccount"));
    private final JLabel passwordPrompt0 = new JLabel(labelToStrings.get("passwordPrompt"));
    private final JTextField email = new JTextField();
    private final JTextField userName = new JTextField();
    private final JPasswordField password0 = new JPasswordField();
    private final JPasswordField password1 = new JPasswordField();
    private final JButton register = new JButton(labelToStrings.get("register"));
    private final JButton back = new JButton(labelToStrings.get("goBack"));
    private final JButton generatePassword = new JButton(labelToStrings.get("generatePassword"));
    private final JPanel create = new JPanel();
    private final JCheckBox registerTempAccount = new JCheckBox(labelToStrings.get("registerTempAccount"));
    private final JButton goNext = new JButton(labelToStrings.get("goNext"));
    JPanel messagePanel = new JPanel();


    public CreateAccountUI(GeneralUI parent) {
        this.setParent(parent);
        this.adminAccUI = new AdminAccountUI(this.getParent());
        this.regularAccUI = new RegularAccountUI(this.getParent());
    }

    /**
     * Runs the UI from the beginning.
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

        generatePassword.setBounds(580, 190, 100, 30);
        create.add(generatePassword);
        generatePassword.addActionListener(this);

        JPanel grids = new JPanel();
        grids.setLayout(new GridLayout(6, 2));
        grids.setBounds(70, 100, 500, 250);
        create.add(grids);

        JLabel emailPrompt = new JLabel(labelToStrings.get("emailPrompt"));
        emailPrompt.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(emailPrompt);

        grids.add(email);

        JLabel userNamePrompt = new JLabel(labelToStrings.get("userNamePrompt"));
        userNamePrompt.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(userNamePrompt);

        grids.add(userName);

        passwordPrompt0.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(passwordPrompt0);

        grids.add(password0);

        JLabel confirmPasswordPrompt = new JLabel(labelToStrings.get("confirmPasswordPrompt"));
        confirmPasswordPrompt.setHorizontalAlignment(JLabel.RIGHT);
        grids.add(confirmPasswordPrompt);

        grids.add(password1);
        
        grids.add(register);
        register.addActionListener(this);

        grids.add(registerTempAccount);

        grids.add(back);
        back.addActionListener(this);
        password0.addKeyListener(this);
    }

    private void createNewAccount(String type) {
        if (!Objects.equals(password0.getText(), password1.getText())) {
            this.createAccount.setText(labelToStrings.get("incorrectPassword"));
        } else if (accessController.getPasswordStrength(password0.getText()).equals("Too Weak")) {
            this.createAccount.setText(labelToStrings.get("weakPassword"));
        } else {
            String id;
            if (type.equals("regular")) {
                id = accessController.createAccount(email.getText(), userName.getText(), password0.getText());
            } else{
                id = accessController.createTemporaryAccount(email.getText(), userName.getText(), password0.getText());
            }

            messagePanel.setLayout(null);

            JLabel message = new JLabel(labelToStrings.get("rememberID") + id);
            message.setBounds(50, 0, 600, 300);
            message.setFont(new Font("MV Boli", Font.PLAIN, 15));
            message.setHorizontalAlignment(JLabel.CENTER);
            messagePanel.add(message);

            goNext.setBounds(300, 200, 100, 50);
            goNext.addActionListener(this);
            messagePanel.add(goNext);

            main.add(messagePanel, "createMessage");
            accessController.logIn(id, password0.getText());
            cl.show(main, "createMessage");
        }
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            if (registerTempAccount.isSelected()) {
                createNewAccount("temporary");
            } else {
                createNewAccount("regular");
            }
        } else if (e.getSource() == back) {
            cl.show(main, "LoginPage");
        } else if (e.getSource() == goNext) {
            main.remove(messagePanel);
            messagePanel.removeAll();
            if (accessController.isAdmin(accessController.getCurrUserId()).equals("admin")) {
                this.adminAccUI.run();
            } else {
                this.regularAccUI.run();
            }
        } else if (e.getSource() == generatePassword) {
            password0.setEchoChar((char)0);
            password1.setEchoChar((char)0);
            String tempPassword = accessController.generateTempPassword();
            password0.setText(tempPassword);
            password1.setText(tempPassword);
        }
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of a key typed event.
     * @param e An event object
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of a key pressed event.
     * @param e An event object
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of a key released event.
     * @param e An event object
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == password0) {
            passwordPrompt0.setText(labelToStrings.get("complexityLevel") + accessController.getPasswordStrength(password0.getText()));
        }
    }
}
