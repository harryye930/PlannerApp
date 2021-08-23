package graphical_user_interface;

import gateway.UIGateway;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AdminCheckPlannerUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private Map<String, String> labelToStrings = new UIGateway().loadAdminCheckPlannerUITexts();
    private String  userId;

    // Panel
    JPanel menu = new JPanel();

    // Button
    private JButton delete = new JButton(labelToStrings.get("delete"));
    private JButton back = new JButton(labelToStrings.get("goBack"));
    private JButton submit = new JButton(labelToStrings.get("submit"));

    // List
    private final JList<String> changePrivacy = new JList<>(new String[]{"private", "public", "friends-only"});

    // Text
    private final JTextField text = new JTextField();

    public AdminCheckPlannerUI(GeneralUI parent) {
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
            update();
            cl.show(main, "adminCheckPlanner");
        } else {
            this.showMenu();
            cl.show(main, "adminCheckPlanner");
            flag = !flag;
        }
    }

    private void showMenu() {
        menu.setLayout(null);
        main.add(menu, "adminCheckPlanner");

        data.getPlanners(menu);

        JLabel prompt = new JLabel(labelToStrings.get("prompt"));
        prompt.setBounds(475, 100, 200, 40);
        menu.add(prompt);

        text.setBounds(475, 150, 200, 40);
        menu.add(text);

        delete.setBounds(515, 200, 100, 40);
        menu.add(delete);
        delete.addActionListener(this);

        changePrivacy.setBounds(500, 250, 150, 60);
        menu.add(changePrivacy);

        submit.setBounds(500, 350, 150, 60);
        menu.add(submit);
        submit.addActionListener(this);

        back.setBounds(515, 400, 100, 40);
        menu.add(back);
        back.addActionListener(this);
    }

    private void update() { data.getPlanners(menu);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            if (plannerController.checkPlanner(text.getText())) {
                plannerController.deletePlanner(text.getText());
                text.setText("");
                update();
            } else {
                text.setText(labelToStrings.get("invalidInput"));
            }
        } else if (e.getSource() == submit) {
            if (plannerController.checkPlanner(text.getText())) {
                if (changePrivacy.getSelectedIndex() != -1) {
                    plannerController.changePrivacyStatus(changePrivacy.getSelectedValue());
                    text.setText("");
                    changePrivacy.setSelectedIndex(-1);
                    update();
                }
            } else {
                text.setText(labelToStrings.get("invalidInput"));
            }
        } else if (e.getSource() == back) {
            this.getParent().run();
        }
    }
}
