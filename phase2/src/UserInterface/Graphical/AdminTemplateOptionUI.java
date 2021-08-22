package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import builder.IForm;
import builder.formBuilder.FormBuilder;

import javax.swing.*;
import java.util.Map;

/**
 * GUI class for displaying template options for an admin user.
 * Options include: create template, edit template, return to main menu.
 */
public class AdminTemplateOptionUI extends GeneralPresenter{
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadAdminTemplateOptionUITexts();

    private IForm form;

    private final GeneralPresenter createTemplateUI = new CreateTemplateUI(this);
    private final GeneralPresenter checkTemplateUI = new CheckTemplateUI(this);

    // JComponents
    private final JPanel panel = new JPanel();

    public AdminTemplateOptionUI(GeneralPresenter parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
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

        buttons.addLabel("prompt", labelToStrings.get("prompt"));
        buttons.addSuperButton("createTemplate", labelToStrings.get("createTemplate"), createTemplateUI);
        buttons.addSuperButton("editTemplate", labelToStrings.get("editTemplate"), checkTemplateUI);
        buttons.addSuperButton("returnToMainMenuButton", labelToStrings.get("returnToMainMenuButton"), this.getParent());
        buttons.setBounds(150, 100, 400, 250);

        form = buttons.getForm();
        panel.add(form.getPanel());
    }
}

