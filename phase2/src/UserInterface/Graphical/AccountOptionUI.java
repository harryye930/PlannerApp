package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IButton;
import strategy.IForm;
import strategy.buttonGenerator.GridStyleButtons;
import strategy.formGenerator.GridStyleForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/***
 * GUI class for the account setting screen: displays account information and allows user to change account username
 * and password.
 */
public class AccountOptionUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;

    private final GeneralPresenter friendUI = new FriendUI(this);
    private final Map<String, String> labelToStrings = new UIGateway().loadAccountOptionUITexts();

    private final IButton menuButtons = new GridStyleButtons();
    private final IForm changeNameForm = new GridStyleForm();
    private final IForm changePasswordForm = new GridStyleForm();

    //Panel
    private final JPanel accountMenu = new JPanel();
    private JPanel changeMenu;
    JScrollPane accounts;

    public AccountOptionUI(GeneralPresenter parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
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
        accountMenu.setLayout(null);
        main.add(accountMenu, "accountMenu");

        accounts = data.getAccount(accessController.getCurrUserId(), accountMenu);

        menuButtons.setBounds(515, 190, 150, 150);
        menuButtons.add("changeUserName", labelToStrings.get("changeUsername"), null);
        menuButtons.add("changePassword", labelToStrings.get("changePassword"), null);
        menuButtons.add("addFriend", labelToStrings.get("addFriend"), null);
        menuButtons.add("back", labelToStrings.get("back"), null);

        accountMenu.add(menuButtons.getPanel());
        for (JButton button: menuButtons.getButtons().values()) {
            button.addActionListener(this);
        }

    }

    private void changeName() {
        changeMenu = new JPanel();
        changeMenu.setLayout(null);
        main.add(changeMenu, "changeNameMenu");

        changeNameForm.setBounds(125, 100, 500, 250);
        changeNameForm.addLabel("changeUsernameMessage", labelToStrings.get("changeUsernameMessage"));
        changeNameForm.addTextField("name");
        changeNameForm.addSubmitButton("submit", labelToStrings.get("submit"));
        changeNameForm.addSubmitButton("back", labelToStrings.get("back"));
        changeNameForm.setLayout(4, 1);
        changeMenu.add(changeNameForm.getPanel());
        changeNameForm.addListener(this);

        cl.show(main, "changeNameMenu");

    }

    private void changePassword() {
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
        changePasswordForm.addListener(this);
        changePasswordForm.setLayout(6, 1);
        changeMenu.add(changePasswordForm.getPanel());

        cl.show(main, "changePasswordMenu");

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButtons.getButtons().get("back")) {
            this.getParent().run();
        } else if (e.getSource() == menuButtons.getButtons().get("changeUserName")) {
            this.changeName();
        } else if (e.getSource() == menuButtons.getButtons().get("changePassword")) {
            this.changePassword();
        } else if (e.getSource() == changeNameForm.getComponents().get("submit")) {
            accessController.changeUserName(accessController.getCurrUserId(),
                    ((JTextField)changeNameForm.getComponents().get("name")).getText());
            main.remove(changeMenu);
            this.run();
            //cl.show(main, "accountMenu");
        } else if (e.getSource() == changePasswordForm.getComponents().get("submit")) {
            String message = accessController.changePassword(accessController.getCurrUserId(),
                    ((JTextField)changePasswordForm.getComponents().get("originalPassword")).getText(),
                    ((JTextField) changePasswordForm.getComponents().get("newPassword")).getText());
            main.remove(changeMenu);
            //cl.show(main, "accountMenu");
            ((JTextField)changePasswordForm.getComponents().get("originalPassword")).setText("");
            ((JTextField) changePasswordForm.getComponents().get("newPassword")).setText(message);
            this.run();

        } else if (e.getSource() == changeNameForm.getComponents().get("back") ||
                e.getSource() == changePasswordForm.getComponents().get("back")) {
            //cl.show(main, "accountMenu");
            //main.remove(changeMenu);
            this.run();
        } else if (e.getSource() == menuButtons.getButtons().get("addFriend")) {
            friendUI.run();
        }
    }
}
