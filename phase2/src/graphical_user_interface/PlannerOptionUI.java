package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.util.Map;

/**
 * GUI class for displaying planner options for a regular user.
 * Options include: create a new planner, view all planners available to the user, and return to the main menu.
 */
public class PlannerOptionUI extends GeneralUI {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadPlannerOptionUITexts();

    private IForm form;

    // JComponent
    private final JPanel panel = new JPanel();

    private final GeneralUI createPlanner = new CreatePlannerUI(this);
    private final GeneralUI checkPlanner = new CheckPlannerUI(this);
    private final GeneralUI trashBin = new PlannerTrashBinUI(this);

    public PlannerOptionUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "plannerMenu");
        } else {
            this.showMenu();
            cl.show(main, "plannerMenu");
            flag = !flag;
        }
    }

    private void showMenu() {
        main.add(panel, "plannerMenu");
        panel.setLayout(null);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(50, 100, 600, 300);
        fb.addTitleLabel("prompt", labelToStrings.get("prompt"));
        fb.addSuperButton("newPlanner", labelToStrings.get("createPlannerButton"), createPlanner);
        fb.addSuperButton("checkPlanner", labelToStrings.get("checkPlannerButton"), checkPlanner);
        fb.addSuperButton("trashBin", labelToStrings.get("trashBin"), trashBin);
        fb.addSuperButton("back", labelToStrings.get("back"), this.getParent());

        form = fb.getForm();
        panel.add(form.getPanel());
    }
}

