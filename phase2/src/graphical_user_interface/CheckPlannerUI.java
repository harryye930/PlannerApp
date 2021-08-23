package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * GUI class for viewing all planners available to a regular user (all personal planners, all public planners,
 * any friends-only planners if they are added to the friends list of another user)
 */

public class CheckPlannerUI extends GeneralUI implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadCheckPlannerUITexts();

    private IForm form;

    private final GeneralUI plannerEdit = new EditPlannerUI(this);

    //JPanel
    JPanel checkPlanner = new JPanel();

    //TextArea/Field
    JScrollPane plannerInfo;

    public CheckPlannerUI(GeneralUI parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            plannerInfo = data.getPlanners(checkPlanner);
            cl.show(main, "checkPlanner");
        } else {
            this.showPlanners();
            cl.show(main, "checkPlanner");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    public void showPlanners() {
        checkPlanner.setLayout(null);
        main.add(checkPlanner, "checkPlanner");

        plannerInfo = data.getPlanners(checkPlanner);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(450, 50, 200, 200);
        fb.addLabel("prompt", labelToStrings.get("prompt"));
        fb.addTextField("plannerId");
        fb.addSubmitButton("submit", labelToStrings.get("submit"));
        fb.addSuperButton("goBack", labelToStrings.get("goBack"), this.getParent());
        fb.addListener(this);

        form = fb.getForm();
        checkPlanner.add(form.getPanel());
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e An event object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.get("submit")) {
            if (plannerController.checkPlanner(((JTextField) form.get("plannerId")).getText())) {
                this.plannerEdit.run();
            } else {
                ((JLabel)(this.form.get("prompt"))).setText(labelToStrings.get("invalidInput"));
            }
        }
    }
}
