package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * GUI class for displaying account options for an admin user.
 * Options include: suspend any account by any number of days.
 */
public class AdminAccountOptionUI extends GeneralUI implements KeyListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadAdminAccountOptionUITexts();

    private JScrollPane accountsInfo;
    private final JPanel adminAccount = new JPanel();
    private IForm form;

    private final SuspendUserUI adminSuspendAccount = new SuspendUserUI(this);
    private final AdminCheckPlannerUI adminCheckPlannerUI = new AdminCheckPlannerUI(this);

    public AdminAccountOptionUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
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
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of a key typed event.
     * @param e An event object
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of a key pressed event.
     * @param e An event object
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of a key released event.
     * @param e An event object
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == form.get("accountId")) {
            JTextField text = (JTextField) form.get("accountId");

            adminSuspendAccount.setUserId(text.getText());
        }
    }
}
