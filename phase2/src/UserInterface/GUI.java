package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GUI implements ActionListener {
    JFrame frame = new JFrame("CardLayout demo");
    JPanel panelCont = new JPanel();
    JPanel loginPage = new JPanel();
    JPanel mainMenuPage = new JPanel();
    JButton buttonOne = new JButton("to main menu");
    JButton buttonSecond = new JButton("Log out");
    CardLayout cl = new CardLayout();
    JButton newAccountButton;
    JButton guestButton;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField usernameText;
    JTextField passwordText;
    JButton loginButton;
    JLabel loginStatus;


    public GUI() {
        panelCont.setLayout(cl);

        loginPage.add(buttonOne);
        loginPage = new JPanel(new GridLayout(4, 2));
        loginPage.setPreferredSize(new Dimension(400, 200));

        usernameLabel = new JLabel("Username:");
        loginPage.add(usernameLabel);
        usernameText = new JTextField(20);
        loginPage.add(usernameText);

        passwordLabel = new JLabel("Password:");
        loginPage.add(passwordLabel);
        passwordText = new JPasswordField(20);
        loginPage.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginPage.add(loginButton);
        newAccountButton = new JButton("Create account");
        loginPage.add(newAccountButton);
        guestButton = new JButton("Continue as guest");
        loginPage.add(guestButton);

        loginStatus = new JLabel("");
        loginPage.add(loginStatus);
        mainMenuPage.add(buttonSecond);

        panelCont.add(loginPage, "loginPage");
        panelCont.add(mainMenuPage, "mainMenuPage");
        cl.show(panelCont, "loginPage");

        buttonOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelCont, "mainMenuPage");
            }
        });

        buttonSecond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelCont, "loginPage");
            }
        });

        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }



    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) { // clicked login
            String username = usernameText.getText();
            String password = passwordText.getText();

            Boolean authStatus = Objects.equals(username, "ABCD"); //todo check username and password match
            System.out.println(username + ", " + password + ", " + String.valueOf(authStatus));
            if (authStatus) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                cl.show(panelCont, "mainMenuPage");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        }
    }


}