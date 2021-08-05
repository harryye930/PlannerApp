package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AccountOptionUI extends GeneralPresenter {
    private boolean flag = false;

    //JPanel
    JPanel accountMenu = new JPanel();

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
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
