package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccountOptionUI extends GeneralPresenter {
    private boolean flag = false;

    //JPanel
    JPanel accountMenu = new JPanel();

    //Buttons
    private final JButton changeUserEmailButton = new JButton("Change Your Associated Email");
    private final JButton changeUserPasswordButton = new JButton("Change Your Password");
    private final JButton deleteAccountButton = new JButton("Delete your account");
    private final JButton returnToMainButton = new JButton("Return to Main Menu");



    public AccountOptionUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (this.flag) {
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

        JScrollPane accounts = data.getAccounts();
        accounts.setBounds(75, 25, 450, 100);
        accountMenu.add(accounts);


        accountMenu.add(changeUserEmailButton);
        accountMenu.add(changeUserPasswordButton);
        accountMenu.add(deleteAccountButton);
        accountMenu.add(returnToMainButton);

        changeUserEmailButton.addActionListener(this);
        changeUserPasswordButton.addActionListener(this);
        deleteAccountButton.addActionListener(this);
        returnToMainButton.addActionListener(this);



    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeUserEmailButton){
            System.out.println("remove after implement");
        }
        else if (e.getSource() == changeUserPasswordButton){
            System.out.println("remove after implement");
        }
        else if (e.getSource() == returnToMainButton){
            System.out.println("remove after implement");
        }
        else if (e.getSource() == returnToMainButton){
            cl.show(main, this.getParent());
        }

    }
}
