package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IButton;
import strategy.buttonGenerator.GridStyleButtons;
import sun.jvm.hotspot.gc_interface.GCWhen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * GUI class for displaying the main menu for an admin user.
 * The main menu includes: planner options, edit template, suspend account
 * - Edit Template
 * - Planner options
 *  - change privacy setting
 *  - interact with all planners
 *  - delete planner
 * - Suspend account
 */
public class AdminAccountUI extends GeneralPresenter {
    private boolean flag = false;

    private Map<String, String> labelToStrings = new UIGateway().loadAdminAccountUITexts();

    // GeneralPresenters
    private final GeneralPresenter plannerOptionUI = new AdminCheckPlannerUI(this);
    private final GeneralPresenter templateOptionUI = new AdminTemplateOptionUI(this);
    private final GeneralPresenter accountOptionUI = new AdminAccountOptionUI(this);

    // Components
    private final JPanel panel = new JPanel();
    private JLabel prompt = new JLabel();

    private final IButton buttons = new GridStyleButtons();

    public AdminAccountUI(GeneralPresenter parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
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

        prompt.setText(labelToStrings.get("prompt"));
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        panel.add(prompt);

        buttons.add("plannerButton", labelToStrings.get("plannerButton"), plannerOptionUI);
        buttons.add("templateButton", labelToStrings.get("templateButton"), templateOptionUI);
        buttons.add("accountButton", labelToStrings.get("accountButton"), accountOptionUI);
        buttons.add("logOutButton", labelToStrings.get("logOutButton"), this.getParent());
        buttons.setBounds(150, 150, 400, 200);

        panel.add(buttons.getPanel());
    }
}
//    private boolean flag = false;
//    private Map<String, String> labelToStrings = new UIGateway().loadAdminAccountUITexts();
//
//    private final JPanel adminUserMainMenu = new JPanel();
//    private JLabel prompt;
//    private final JButton plannerButton = new JButton(labelToStrings.get("plannerButton"));
//    private final JButton templateButton = new JButton(labelToStrings.get("templateButton"));
//    private final JButton accountButton = new JButton(labelToStrings.get("accountButton"));
//    private final JButton logOutButton = new JButton(labelToStrings.get("logOutButton"));
//
//    private final GeneralPresenter plannerOptionUI = new AdminPlannerOptionUI("this");
//    private final GeneralPresenter templateOptionUI = new AdminTemplateOptionUI(this);
//    private final GeneralPresenter accountOptionUI = new AdminAccountOptionUI(this);
//
//    public AdminAccountUI(GeneralPresenter parent) {
//        this.setParent(parent);
//    }
//    /**
//     * run the presenter from the beginning.
//     */
//    @Override
//    public void run() {
//        if (flag) {
//            cl.show(main, "adminUserMainMenu");
//        } else {
//            this.showMenu();
//            cl.show(main, "adminUserMainMenu");
//            frame.setVisible(true);
//            flag = !flag;
//        }
//    }
//
//    private void showMenu() {
//        main.add(adminUserMainMenu, "adminUserMainMenu");
//
//        adminUserMainMenu.setLayout(null);
//
//        prompt = new JLabel(labelToStrings.get("prompt"));
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        prompt.setVerticalAlignment(JLabel.TOP);
//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setBounds(0, 100, 700, 50);
//        prompt.setOpaque(true);
//        adminUserMainMenu.add(prompt);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(150, 150, 400, 200);
//        panel.setLayout(new GridLayout(4, 1));
//        adminUserMainMenu.add(panel);
//
//        panel.add(plannerButton);
//        panel.add(templateButton);
//        panel.add(accountButton);
//        panel.add(logOutButton);
//
//        plannerButton.addActionListener(this);
//        templateButton.addActionListener(this);
//        accountButton.addActionListener(this);
//        logOutButton.addActionListener(this);
//
//
//    }
//
//    /**
//     * Invoked when an action occurs.
//     * @param e
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == plannerButton){
//            //this.plannerOptionUI.run();
//        }else if (e.getSource() == templateButton){
//            this.templateOptionUI.run();
//        } else if (e.getSource() == logOutButton){
//            accessController.logOut();
//            this.getParent().run();
//        } else if (e.getSource() == accountButton) {
//            this.accountOptionUI.run();
//        }
//
//    }

