package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IForm;
import strategy.formGenerator.FormBuilder;

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

//        prompt.setText(labelToStrings.get("prompt"));
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setBounds(0, 100, 700, 50);
//        panel.add(prompt);

        buttons.addLabel("prompt", labelToStrings.get("prompt"));
        buttons.addSuperButton("createTemplate", labelToStrings.get("createTemplate"), createTemplateUI);
        buttons.addSuperButton("editTemplate", labelToStrings.get("editTemplate"), checkTemplateUI);
        buttons.addSuperButton("returnToMainMenuButton", labelToStrings.get("returnToMainMenuButton"), this.getParent());
        buttons.setBounds(150, 100, 400, 250);

        form = buttons.getForm();
        panel.add(form.getPanel());
    }
}

//    // all buttons
//    JButton createTemplate = new JButton(labelToStrings.get("createTemplate"));
//    JButton editTemplate = new JButton(labelToStrings.get("editTemplate"));
//    JButton returnToMainMenuButton = new JButton(labelToStrings.get("returnToMainMenuButton"));
//
//    // panel
//    private final JPanel templateMenu = new JPanel();
//
//    // menu text
//    private JLabel prompt;
//
//    private GeneralPresenter createTemplateUI = new CreateTemplateUI(this);
//    private GeneralPresenter checkTemplateUI = new CheckTemplateUI(this);
//
//    public AdminTemplateOptionUI(GeneralPresenter parent) {
//        this.setParent(parent);
//    }
//
//    @Override
//    public void run(){
//        if (flag) {
//            cl.show(main, "adminTemplateMenu");
//        } else {
//            this.buildTemplateMenu();
//            cl.show(main, "adminTemplateMenu");
//            frame.setVisible(true);
//            flag = !flag;
//        }
//    }
//
//    private void buildTemplateMenu(){
//        main.add(templateMenu, "adminTemplateMenu");
//        templateMenu.setLayout(null);
//
//        prompt = new JLabel(labelToStrings.get("prompt"));
//        prompt.setHorizontalAlignment(JLabel.CENTER);
//        prompt.setVerticalAlignment(JLabel.TOP);
//        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
//        prompt.setBounds(0, 100, 700, 50);
//        prompt.setOpaque(true);
//        templateMenu.add(prompt);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(150, 150, 400, 200);
//        panel.setLayout(new GridLayout(3, 1));
//        templateMenu.add(panel);
//
//        panel.add(createTemplate);
//        panel.add(editTemplate);
//        panel.add(returnToMainMenuButton);
//
//        createTemplate.addActionListener(this);
//        editTemplate.addActionListener(this);
//        returnToMainMenuButton.addActionListener(this);
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == returnToMainMenuButton){
//            cl.show(main, "adminUserMainMenu");
//        } else if (e.getSource() == editTemplate){
//            this.checkTemplateUI.run();
//        } else if (e.getSource() == createTemplate){
//            this.createTemplateUI.run();
//        }
//    }

