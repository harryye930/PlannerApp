package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GUI implements ActionListener {

    private JFrame frame = new JFrame();
    JPanel page;
    JPanel loginPage;

    JLabel usernameLabel;
    JLabel passwordLabel;
    JLabel loginStatus;

    JTextField usernameText;
    JTextField passwordText;

    JButton loginButton;
    JButton newAccountButton;
    JButton guestButton;



    public GUI() {
        loginPage();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login Page");
        frame.pack();
        frame.setVisible(true);
    }
    private void newPage(){


        JPanel page = new JPanel();
        frame.add(page, BorderLayout.CENTER);
        page.setLayout(null);
        JLabel test = new JLabel("New page!");
        page.add(test);
    }

    private void loginPage(){
        loginPage = new JPanel(new GridLayout(4,2));
        frame.add(loginPage, BorderLayout.CENTER);

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


