package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
// TODO: Extract the left panel (the JScrollPane)
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

    // Label
    private JLabel prompt = new JLabel("<html>Please enter the number of days<br/>" +
                                                 " you want to suspend this user:</html>");

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
            cl.show(main, "suspendAccount");
        } else {
            this.showMenu();
            cl.show(main, "suspendAccount");
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(menu, "suspendAccount");
        menu.setLayout(null);

        accountInfo = data.getAccount(userId, menu);

        if (accessController.getSuspensionStatus(userId)) {
            suspend.setText("Unsuspend");
        } else {
            prompt.setBounds(450, 50, 200, 50);
            menu.add(prompt);

            text.setBounds(450, 100, 250, 50);
            menu.add(text);

            suspend.setText("Suspend");
        }

        suspend.setBounds(450, 150, 250, 50);
        menu.add(suspend);
        suspend.addActionListener(this);

        back.setBounds(515, 250, 100, 40);
        menu.add(back);
        back.addActionListener(this);
    }

    private void update() {
        this.accountInfo = data.getAccount(userId, menu);
        if (accessController.getSuspensionStatus(userId)) {
            prompt.setVisible(false);
            text.setVisible(false);
            suspend.setText("Unsuspend");
        } else {
            prompt.setVisible(true);
            text.setVisible(true);
            suspend.setText("Suspend");
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == suspend) {
            if (accessController.getSuspensionStatus(userId)) {
                accessController.unSuspendUser(userId);
                update();
            } else {
                accessController.suspendUser(userId, Integer.parseInt(text.getText()));
                update();
            }
        } else if (e.getSource() == back) {
            menu.removeAll();
            cl.show(main, this.getParent());
        }
    }
}
