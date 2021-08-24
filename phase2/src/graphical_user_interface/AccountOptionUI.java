package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/***
 * GUI class for the account setting screen: displays account information and allows user to change account username
 * and password.
 */
public class AccountOptionUI extends GeneralUI implements ActionListener {
    private boolean flag = false;

    private final GeneralUI friendUI = new FriendUI(this);
    private final Map<String, String> labelToStrings = new UIGateway().loadAccountOptionUITexts();

    private IForm menuForm;
    private IForm passwordForm;
    private IForm nameForm;

    //Panel
    private final JPanel accountMenu = new JPanel();
    private JPanel changeMenu;
    JScrollPane accounts;

    public AccountOptionUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (this.flag) {
            accounts = data.getAccount(accessController.getCurrUserId(), accountMenu);
            cl.show(main, "accountMenu");
        } else {
            this.showAccountMenu();
            cl.show(main, "accountMenu");
            flag = !flag;
        }
    }

    private void showAccountMenu() {
        FormBuilder menuButtons = new FormBuilder();

        this.changeName();
        this.changePassword();

        accountMenu.setLayout(null);
        main.add(accountMenu, "accountMenu");

        accounts = data.getAccount(accessController.getCurrUserId(), accountMenu);

        menuButtons.setBounds(515, 190, 150, 150);
        menuButtons.addSubmitButton("changeUserName", labelToStrings.get("changeUsername"));
        menuButtons.addSubmitButton("changePassword", labelToStrings.get("changePassword"));
        menuButtons.addSubmitButton("addFriend", labelToStrings.get("addFriend"));
        menuButtons.addSuperButton("back", labelToStrings.get("back"), this.getParent());

        menuButtons.addListener(this);
        menuForm = menuButtons.getForm();
        accountMenu.add(menuForm.getPanel());
    }

    private void changeName() {
        FormBuilder changeNameForm = new FormBuilder();

        changeMenu = new JPanel();
        changeMenu.setLayout(null);
        main.add(changeMenu, "changeNameMenu");

        changeNameForm.setBounds(125, 100, 500, 250);
        changeNameForm.addLabel("changeUsernameMessage", labelToStrings.get("changeUsernameMessage"));
        changeNameForm.addTextField("name");
        changeNameForm.addSubmitButton("submit", labelToStrings.get("submit"));
        changeNameForm.addSubmitButton("back", labelToStrings.get("back"));

        nameForm = changeNameForm.getForm();
        changeNameForm.addListener(this);
        changeMenu.add(nameForm.getPanel());
    }

    private void changePassword() {
        FormBuilder changePasswordForm = new FormBuilder();

        changeMenu = new JPanel();
        changeMenu.setLayout(null);
        main.add(changeMenu, "changePasswordMenu");

        changePasswordForm.addLabel("enterOriginalPassword", labelToStrings.get("enterOriginalPassword"));
        changePasswordForm.addTextField("originalPassword");
        changePasswordForm.addLabel("enterNewPassword", labelToStrings.get("enterNewPassword"));
        changePasswordForm.addTextField("newPassword");
        changePasswordForm.addSubmitButton("submit", labelToStrings.get("submit"));
        changePasswordForm.addSubmitButton("back", labelToStrings.get("back"));
        changePasswordForm.setBounds(125, 50, 500, 300);

        passwordForm = changePasswordForm.getForm();
        changePasswordForm.addListener(this);
        changeMenu.add(passwordForm.getPanel());
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuForm.get("changeUserName")) {
            cl.show(main, "changeNameMenu");

        } else if (e.getSource() == menuForm.get("changePassword")) {
            cl.show(main, "changePasswordMenu");

        } else if (e.getSource() == nameForm.get("submit")) {
            accessController.changeUserName(accessController.getCurrUserId(),
                    ((JTextField)nameForm.get("name")).getText());
            main.remove(changeMenu);
            this.run();

        } else if (e.getSource() == passwordForm.get("submit")) {
            String message = accessController.changePassword(accessController.getCurrUserId(),
                    ((JTextField)passwordForm.get("originalPassword")).getText(),
                    ((JTextField) passwordForm.get("newPassword")).getText());
            ((JTextField)passwordForm.get("originalPassword")).setText("");
            ((JTextField) passwordForm.get("newPassword")).setText("");
            ((JLabel)passwordForm.get("enterOriginalPassword")).setText(message);

        } else if (e.getSource() == nameForm.get("back") ||
                e.getSource() == passwordForm.get("back")) {
            this.run();

        } else if (e.getSource() == menuForm.get("addFriend")) {
            friendUI.run();
        }
    }
}
