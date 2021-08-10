package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SuspendUserUI extends GeneralPresenter {
    private boolean flag = false;
    private String userId;

    // Panel
    private JScrollPane accountInfo;
    private JPanel menu = new JPanel();

    // Button
    private final JButton suspend = new JButton();
    private final JButton back = new JButton("Go back");

    // Text
    private final JTextField text = new JTextField();

    public SuspendUserUI(String parent) {
        this.setParent(parent);
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            menu.removeAll();
            this.showMenu();
            cl.show(main, "suspendAccount");
        } else {
            this.showMenu();
            cl.show(main, "suspendAccount");
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(menu, "suspendAccount");

        accountInfo = data.getAccount(userId);
        accountInfo.setBounds(25, 25, 400, 500);
        menu.add(accountInfo);

        if (accessController.getSuspensionStatus(userId)) {
            suspend.setText("Unsuspend");
        } else {
            JLabel prompt = new JLabel("<html>Please enter the number of days<br/>" +
                    " you want to suspend this user:</html>");
            prompt.setBounds(450, 50, 200, 50);
            menu.add(prompt);

            text.setBounds(450, 100, 250, 50);
            menu.add(prompt);

            suspend.setText("Suspend");
        }
        menu.add(suspend);

        back.setBounds(515, 250, 100, 40);
        menu.add(back);
        back.addActionListener(this);
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
