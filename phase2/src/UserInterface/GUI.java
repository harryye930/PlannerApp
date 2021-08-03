package UserInterface;

import Entity.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GUI implements ActionListener {
    //
    JFrame frame = new JFrame("Productivity App GUI");

    //initialize Panels (pages)
    JPanel panelCont = new JPanel();
    JPanel loginPage;
    JPanel mainMenuPage = new JPanel();

    //initialize buttons
    JButton logOut = new JButton("Log out");
    JButton newAccountButton;
    JButton guestButton;
    JButton loginButton;
    JButton plannerButton = new JButton("Planners");
    JButton templateButton = new JButton("Templates");
    JButton accountButton = new JButton("Account");

    CardLayout cl = new CardLayout();

    //initialize labels (fix texts in GUI)
    JLabel usernameLabel;
    JLabel passwordLabel;

    //initialize text field (place where user can type)
    JTextField usernameText;
    JTextField passwordText;




    public GUI() {
        panelCont.setLayout(cl);

       loginPage = loginPage();
       mainMenuPage = mainMenuPage();



        panelCont.add(loginPage, "loginPage");
        panelCont.add(mainMenuPage, "mainMenuPage");
        cl.show(panelCont, "loginPage");




        // GUI setup
        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public JPanel mainMenuPage() {
        mainMenuPage = new JPanel();
        mainMenuPage.add(plannerButton);
        mainMenuPage.add(templateButton);
        mainMenuPage.add(accountButton);
        mainMenuPage.add(logOut);

        // redefine logout button functionality
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelCont, "loginPage");
            }
        });
        return mainMenuPage;
    }

    /**
     * @return Login panel
     */
    public JPanel loginPage() {
        loginPage = new JPanel(new GridLayout(4, 2));
        loginPage.setPreferredSize(new Dimension(400, 200));

        //username and place to enter
        usernameLabel = new JLabel("Username:");
        loginPage.add(usernameLabel);
        usernameText = new JTextField(20);
        loginPage.add(usernameText);

        // password and place to enter
        passwordLabel = new JLabel("Password:");
        loginPage.add(passwordLabel);
        passwordText = new JPasswordField(20);
        loginPage.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this); // we can now interact with button click using addActionListener
        loginPage.add(loginButton);

        newAccountButton = new JButton("Create account");
        loginPage.add(newAccountButton);
        guestButton = new JButton("Continue as guest");
        loginPage.add(guestButton);
        return loginPage;
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
     * Perform login process after user entered username and password
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