package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GUI implements ActionListener {

    private JFrame frame = new JFrame();
    JLabel usernameLabel;
    JLabel passwordLabel;
    JLabel loginStatus;
    JTextField usernameText;
    JTextField passwordText;
    JButton loginButton;
    JButton newAccountButton;
    JButton guestButton;


    public GUI() {
        frame.setSize(350, 200);
        loginPage();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login Page");
        frame.pack();
        frame.setVisible(true);
    }


    private void loginPage(){
        JPanel loginPage = new JPanel();
        frame.add(loginPage, BorderLayout.CENTER);
        loginPage.setLayout(null);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10,20,80,25);
        loginPage.add(usernameLabel);

        usernameText = new JTextField(20);
        usernameText.setBounds(100,20,165,25);
        loginPage.add(usernameText);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        loginPage.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        loginPage.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(40, 100, 160, 50);
        loginPage.add(loginButton);

        newAccountButton = new JButton("Create account");
        newAccountButton.setBounds(40, 160, 160, 50);
        loginPage.add(newAccountButton);

        guestButton = new JButton("Continue as guest");
        guestButton.setBounds(40, 220, 160, 50);
        loginPage.add(guestButton);

        loginStatus = new JLabel("");
        loginStatus.setBounds(10, 80, 300, 25);
        loginPage.add(loginStatus);


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
                loginStatus.setText("Login successful!");
            } else {
                loginStatus.setText("Invalid username or password!");
            }
        }






    }
    public static void main (String[]args){
        new GUI();
    }
}


