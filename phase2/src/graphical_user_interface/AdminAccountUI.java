package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.util.Map;

/**
 * GUI class for displaying the main menu for an admin user.
 * The main menu includes: planner options, template options, account options
 */
public class AdminAccountUI extends GeneralUI {
    private boolean flag = false;

    private final Map<String, String> labelToStrings = new UIGateway().loadAdminAccountUITexts();

    // GeneralPresenters
    private final GeneralUI plannerOptionUI = new AdminCheckPlannerUI(this);
    private final GeneralUI templateOptionUI = new AdminTemplateOptionUI(this);
    private final GeneralUI accountOptionUI = new AdminAccountOptionUI(this);

    // Components
    private final JPanel panel = new JPanel();

    public AdminAccountUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "adminUserMainMenu");
        } else {
            this.showMenu();
            cl.show(main, "adminUserMainMenu");
            flag = !flag;
        }
    }

    private void showMenu() {
        panel.setLayout(null);
        main.add(panel, "adminUserMainMenu");

        FormBuilder buttons = new FormBuilder();
        buttons.addTitleLabel("prompt", labelToStrings.get("prompt"));
        buttons.addSuperButton("plannerButton", labelToStrings.get("plannerButton"), plannerOptionUI);
        buttons.addSuperButton("templateButton", labelToStrings.get("templateButton"), templateOptionUI);
        buttons.addSuperButton("accountButton", labelToStrings.get("accountButton"), accountOptionUI);
        buttons.addSuperButton("logOutButton", labelToStrings.get("logOutButton"), this.getParent());
        buttons.setBounds(150, 150, 400, 200);

        IForm form = buttons.getForm();
        panel.add(form.getPanel());

    }
}


