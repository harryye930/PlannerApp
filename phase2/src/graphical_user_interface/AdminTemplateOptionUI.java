package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.util.Map;

/**
 * GUI class for displaying template options for an admin user.
 * Options include: create template, edit template, return to main menu.
 */
public class AdminTemplateOptionUI extends GeneralUI {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadAdminTemplateOptionUITexts();

    private final GeneralUI createTemplateUI = new CreateTemplateUI(this);
    private final GeneralUI checkTemplateUI = new CheckTemplateUI(this);

    // JComponents
    private final JPanel panel = new JPanel();

    public AdminTemplateOptionUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "adminTemplateMenu");
        } else {
            this.showMenu();
            cl.show(main, "adminTemplateMenu");
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(panel, "adminTemplateMenu");
        panel.setLayout(null);

        FormBuilder buttons = new FormBuilder();

        buttons.addTitleLabel("prompt", labelToStrings.get("prompt"));
        buttons.addSuperButton("createTemplate", labelToStrings.get("createTemplate"), createTemplateUI);
        buttons.addSuperButton("editTemplate", labelToStrings.get("editTemplate"), checkTemplateUI);
        buttons.addSuperButton("returnToMainMenuButton", labelToStrings.get("returnToMainMenuButton"), this.getParent());
        buttons.setBounds(150, 100, 400, 250);

        IForm form = buttons.getForm();
        panel.add(form.getPanel());
    }
}

