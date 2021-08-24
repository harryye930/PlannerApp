package graphical_user_interface;

import gateway.UIGateway;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * GUI class for suspending a user.
 */
public class SuspendUserUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private String userId;
    private final Map<String, String> labelToStrings = new UIGateway().loadSuspendUserUITexts();

    // Panel
    private final JPanel menu = new JPanel();

    // Button
    private final JButton suspend = new JButton();
    private final JButton back = new JButton(labelToStrings.get("goBack"));

    // Text
    private final JTextField text = new JTextField();

    // Label
    private final JLabel prompt = new JLabel(labelToStrings.get("prompt"));

    public SuspendUserUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Sets the ID or email of the user to be suspended.
     * @param id Identifier of the user to be suspended. Must be User ID or User Email.
     */
    public void setUserId(String id) {
        this.userId = id;
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            this.update();
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

        data.getAccount(userId, menu);

        prompt.setBounds(450, 50, 200, 50);
        menu.add(prompt);

        text.setBounds(450, 100, 250, 50);
        menu.add(text);

        if (accessController.getSuspensionStatus(userId)) {
            suspend.setText(labelToStrings.get("unsuspendButton"));
        } else {
            suspend.setText(labelToStrings.get("suspendButton"));
        }

        suspend.setBounds(450, 150, 250, 50);
        menu.add(suspend);
        suspend.addActionListener(this);

        back.setBounds(515, 250, 100, 40);
        menu.add(back);
        back.addActionListener(this);

        this.update();
    }

    private void update() {
        data.getAccount(userId, menu);
        if (accessController.getSuspensionStatus(userId)) {
            prompt.setVisible(false);
            text.setVisible(false);
            suspend.setText(labelToStrings.get("unsuspendButton"));
        } else {
            prompt.setVisible(true);
            text.setVisible(true);
            suspend.setText(labelToStrings.get("suspendButton"));
        }
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == suspend) {
            if (accessController.getSuspensionStatus(userId)) {
                accessController.unSuspendUser(userId);
            } else {
                accessController.suspendUser(userId, Integer.parseInt(text.getText()));
            }
            update();
        } else if (e.getSource() == back) {
            this.getParent().run();
        }
    }
}
