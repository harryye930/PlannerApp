package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IForm;
import strategy.formGenerator.FormBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * GUI class for displaying account options for an admin user.
 * Options include: suspend any account by any number of days.
 */
public class AdminAccountOptionUI extends GeneralPresenter implements KeyListener {
    private String userId;
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadAdminAccountOptionUITexts();

    private JScrollPane accountsInfo;
    private final JPanel adminAccount = new JPanel();
    private IForm form;

    private final SuspendUserUI adminSuspendAccount = new SuspendUserUI(this);
    private final AdminCheckPlannerUI adminCheckPlannerUI = new AdminCheckPlannerUI(this);

    public AdminAccountOptionUI(GeneralPresenter parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag) {
            accountsInfo = data.getAccounts(adminAccount);
            cl.show(main, "adminAccountOption");
        } else {
            this.showMenu();
            cl.show(main, "adminAccountOption");
            flag = !flag;
        }
    }

    private void showMenu() {
        adminAccount.setLayout(null);
        main.add(adminAccount, "adminAccountOption");

        accountsInfo = data.getAccounts(adminAccount);
        adminAccount.add(accountsInfo);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(450, 50, 200, 200);
        fb.addLabel("prompt", labelToStrings.get("prompt"));
        fb.addTextField("accountId");
        fb.addSuperButton("suspend", labelToStrings.get("suspend"), adminSuspendAccount);
        fb.addSuperButton("checkPlanner", labelToStrings.get("checkPlanner"), adminCheckPlannerUI);
        fb.addSuperButton("goBack", labelToStrings.get("goBack"), this.getParent());

        this.form = fb.getForm();
        adminAccount.add(form.getPanel());

        form.get("accountId").addKeyListener(this);

//        prompt.setBounds(450, 50, 200, 50);
//        adminAccount.add(prompt);
//
//        text.setBounds(450, 100, 200, 30);
//        text.addKeyListener(this);
//        adminAccount.add(text);
//
//        suspend.setBounds(515, 150, 100, 40);
//        checkPlanner.setBounds(515, 200, 100, 40);
//        back.setBounds(515, 250, 100, 40);
//
//        adminAccount.add(suspend);
//        adminAccount.add(checkPlanner);
//        adminAccount.add(back);
//
//        suspend.addActionListener(this);
//        checkPlanner.addActionListener(this);
//        back.addActionListener(this);
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == form.get("accountId")) {
            JTextField text = (JTextField) form.get("accountId");

            adminSuspendAccount.setUserId(text.getText());
            adminCheckPlannerUI.setUserId(text.getText());
        }
    }
}
