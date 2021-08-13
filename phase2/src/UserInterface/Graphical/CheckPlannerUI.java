package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

//TODO: combine with AdminCheckPlannerUI
// TODO: Extract the left panel (the JScrollPane)
/**
 * GUI class for viewing all planners available to a regular user (all personal planners, all public planners,
 * any friends-only planners if they are added to the friends list of another user)
 */

//TODO: show friends-only planners
public class CheckPlannerUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    private Map<String, String> labelToStrings = new UIGateway().loadCheckPlannerUITexts();

    private GeneralPresenter plannerEdit = new EditPlannerUI(this);

    //JPanel
    JPanel checkPlanner = new JPanel();

    //TextArea/Field

    JScrollPane plannerInfo;
    JLabel prompt = new JLabel(labelToStrings.get("prompt"));
    JTextField plannerId = new JTextField();

    //Button
    JButton back = new JButton(labelToStrings.get("goBack"));
    JButton submit = new JButton(labelToStrings.get("submit"));

    public CheckPlannerUI(GeneralPresenter parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
//            plannerInfo.setText(plannerController.viewUserPlanners() +
//                    separator + plannerController.viewPublicPlanners());
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

        prompt.setBounds(450, 50, 200, 50);
        checkPlanner.add(prompt);

        plannerId.setBounds(500, 130, 100, 50);
        checkPlanner.add(plannerId);

        submit.setBounds(515, 200, 70, 40);
        submit.addActionListener(this);
        checkPlanner.add(submit);

        back.setBounds(515, 250, 70, 40);
        back.addActionListener(this);
        checkPlanner.add(back);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if (plannerController.checkPlanner(plannerId.getText())) {
                this.plannerEdit.run();
            } else {
                this.prompt.setText(labelToStrings.get("invalidInput"));
            }
        } else if (e.getSource() == back) {
            this.getParent().run();
        }
    }
}
