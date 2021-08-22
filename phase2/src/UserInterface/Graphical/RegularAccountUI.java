package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IForm;
import strategy.formGenerator.FormBuilder;

import javax.swing.*;
import java.awt.*;
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

//        JLabel prompt = new JLabel(labelToStrings.get("prompt"));
//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setBounds(0, 100, 700, 50);
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        panel.add(prompt);

        buttons.addLabel("prompt", labelToStrings.get("prompt"));
        buttons.addSuperButton("plannerOption", labelToStrings.get("plannerOptionButton"), plannerOptionUI);
        buttons.addSuperButton("templateOption", labelToStrings.get("templateOptionButton"), templateOptionUI);
        buttons.addSuperButton("accountOption", labelToStrings.get("accountOptionButton"), accountOptionUI);
        buttons.addSuperButton("logOut", labelToStrings.get("logOutButton"), this.getParent());
        buttons.setBounds(150, 150, 400, 200);

        form = buttons.getForm();
        panel.add(form.getPanel());
    }
}

//    private final JPanel regularUserMainMenu = new JPanel();
//    private JLabel prompt;
//    private final JButton plannerButton = new JButton("Planner Option");
//    private final JButton templateButton = new JButton("Template Option");
//    private final JButton accountButton = new JButton("Account Setting");
//    private final JButton logOutButton = new JButton("Logout");
//
//    private final GeneralPresenter plannerOptionUI = new PlannerOptionUI("regularUserMainMenu");
//    private final GeneralPresenter templateOptionUI = new TemplateOptionUI("regularUserMainMenu");
//    private final GeneralPresenter accountOptionUI = new AccountOptionUI("regularUserMainMenu");
//
//
//    public RegularAccountUI(String parent) {
//        this.setParent(parent);
//    }
//
//    /**
//     * run the presenter from the beginning.
//     */
//    @Override
//    public void run() {
//        if (flag) {
//            cl.show(main, "regularUserMainMenu");
//        } else {
//            this.showMenu();
//            cl.show(main, "regularUserMainMenu");
//            frame.setVisible(true);
//            flag = !flag;
//        }
//    }
//
//    private void showMenu() {
//        main.add(regularUserMainMenu, "regularUserMainMenu");
//
//        regularUserMainMenu.setLayout(null);
//
//        prompt = new JLabel("Main Menu for Regular User");
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        prompt.setVerticalAlignment(JLabel.TOP);
//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setBounds(0, 100, 700, 50);
//        prompt.setOpaque(true);
//        regularUserMainMenu.add(prompt);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(150, 150, 400, 200);
//        panel.setLayout(new GridLayout(4, 1));
//        regularUserMainMenu.add(panel);
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
//     *
//     * @param e
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == plannerButton){
//            this.plannerOptionUI.run();
//        }else if (e.getSource() == templateButton){
//            this.templateOptionUI.run();
//        } else if (e.getSource() == logOutButton){
//            accessController.logOut();
//            cl.show(main, this.getParent());
//        } else if (e.getSource() == accountButton) {
//            this.accountOptionUI.run();
//        }
//    }

