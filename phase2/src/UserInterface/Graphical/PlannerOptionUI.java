package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IForm;
import strategy.formGenerator.FormBuilder;

import javax.swing.*;
import java.util.Map;

/**
 * GUI class for displaying planner options for a regular user.
 * Options include: create a new planner, view all planners available to the user, and return to the main menu.
 */
public class PlannerOptionUI extends GeneralPresenter {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadPlannerOptionUITexts();

    private IForm form;

    // JComponent
    private final JPanel panel = new JPanel();

    private final GeneralPresenter createPlanner = new CreatePlannerUI(this);
    private final GeneralPresenter checkPlanner = new CheckPlannerUI(this);

    public PlannerOptionUI(GeneralPresenter parent) {
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
        fb.setBounds(150, 100, 600, 250);
        fb.addLabel("prompt", labelToStrings.get("prompt"));
        fb.addSuperButton("newPlanner", labelToStrings.get("createPlannerButton"), createPlanner);
        fb.addSuperButton("checkPlanner", labelToStrings.get("checkPlannerButton"), checkPlanner);
        fb.addSuperButton("back", labelToStrings.get("back"), this.getParent());

        form = fb.getForm();
        panel.add(form.getPanel());


//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        prompt.setBounds(0, 100, 700, 50);
//        panel.add(prompt);
//
//        buttons.add("newPlanner", labelToStrings.get("createPlannerButton"), createPlanner);
//        buttons.add("checkPlanner", labelToStrings.get("checkPlannerButton"), checkPlanner);
//        buttons.add("back", labelToStrings.get("back"), this.getParent());
//        buttons.setBounds(150, 150, 400, 200);
//
//        panel.add(buttons.getPanel());
    }
}
//    private boolean flag = false;
//    // all buttons
//    private final JButton newPlannerButton = new JButton("Create New Planner");
//    private final JButton checkPlannerButton = new JButton("Check my Planners");
//    private final JButton returnToMainMenuButton= new JButton("Return to Main Menu");
//    private final JButton back = new JButton("Go back");
//
//    //planner panel
//    private final JPanel plannerMenu = new JPanel();
//    private final JPanel temp = new JPanel();
//
//    //planner menu text
//    private JLabel prompt;
//
//    private final GeneralPresenter createPlanner = new CreatePlannerUI(this);
//    private final GeneralPresenter checkPlanner = new CheckPlannerUI(this);
//
//    public PlannerOptionUI(GeneralPresenter parent) {
//        this.setParent(parent);
//    }
//
//    /**
//     * run the presenter from the beginning.
//     */
//    @Override
//    public void run() {
//        if (flag) {
//            cl.show(main, "plannerMenu");
//        } else {
//            this.buildPlannerMenu();
//            cl.show(main, "plannerMenu");
//            frame.setVisible(true);
//            flag = !flag; // flag = false?
//        }
//    }
//    private void buildPlannerMenu(){
//        main.add(plannerMenu, "plannerMenu");
//        plannerMenu.setLayout(null);
//
//        prompt = new JLabel("Planner Menu");
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        prompt.setVerticalAlignment(JLabel.TOP);
//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setBounds(0, 100, 700, 50);
//        prompt.setOpaque(true);
//        plannerMenu.add(prompt);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(150, 150, 400, 200);
//        panel.setLayout(new GridLayout(3, 1));
//        plannerMenu.add(panel);
//
//        panel.add(newPlannerButton);
//        panel.add(checkPlannerButton);
//        panel.add(returnToMainMenuButton);
//
//        newPlannerButton.addActionListener(this);
//        checkPlannerButton.addActionListener(this);
//        returnToMainMenuButton.addActionListener(this);
//    }
//
//    /**
//     * Invoked when an action occurs.
//     *
//     * @param e
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == returnToMainMenuButton){
//            cl.show(main, "regularUserMainMenu");
//        } else if (e.getSource() == newPlannerButton) {
//            this.createPlanner.run();
//        } else if (e.getSource() == checkPlannerButton) {
//            this.checkPlanner.run();
//        } else if (e.getSource() == back) {
//            cl.show(main, "plannerMenu");
//            main.remove(temp);
//        }
//    }

