package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import builder.IForm;
import builder.formBuilder.FormBuilder;

import javax.swing.*;
import java.util.Map;

/**
 * GUI class for displaying the main menu for a regular user.
 * The main menu includes 4 options: planner option, template option, account setting, log out.
 */
public class RegularAccountUI extends GeneralPresenter {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadRegularAccountUITexts();

    private final GeneralPresenter plannerOptionUI = new PlannerOptionUI(this);
    private final GeneralPresenter templateOptionUI = new TemplateOptionUI(this);
    private final GeneralPresenter accountOptionUI = new AccountOptionUI(this);

    private IForm form;

    public RegularAccountUI(GeneralPresenter parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "regularUserMainMenu");
        } else {
            this.showMenu();
            cl.show(main, "regularUserMainMenu");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    /**
     * Initialize the UI with buttons and Label using GridStyleButtons strategy.
     */
    private void showMenu() {
        JPanel panel = new JPanel();
        main.add(panel, "regularUserMainMenu");
        panel.setLayout(null);

        FormBuilder buttons = new FormBuilder();

        buttons.addTitleLabel("prompt", labelToStrings.get("prompt"));
        buttons.addSuperButton("plannerOption", labelToStrings.get("plannerOptionButton"), plannerOptionUI);
        buttons.addSuperButton("templateOption", labelToStrings.get("templateOptionButton"), templateOptionUI);
        buttons.addSuperButton("accountOption", labelToStrings.get("accountOptionButton"), accountOptionUI);
        buttons.addSuperButton("logOut", labelToStrings.get("logOutButton"), this.getParent());
        buttons.setBounds(150, 150, 400, 200);

        form = buttons.getForm();
        panel.add(form.getPanel());
    }
}

