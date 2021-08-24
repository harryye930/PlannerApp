package graphical_user_interface;

import gateway.UIGateway;
import graphical_user_interface.builder.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * GUI class for displaying template options for a regular user.
 * Options include: view all templates, return to main menu.
 */
public class TemplateOptionUI extends GeneralUI implements ActionListener{
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadTemplateOptionUITexts();

    private IForm form;

    private final JButton back = new JButton(labelToStrings.get("goBack"));
    private final JPanel templateMenu = new JPanel();

    public TemplateOptionUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * Runs the UI from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "templateMenu");
        } else {
            this.buildTemplateMenu();
            cl.show(main, "templateMenu");
            frame.setVisible(true);
            flag = !flag; // flag = false?
        }
    }

    private void buildTemplateMenu(){
        main.add(templateMenu, "templateMenu");
        templateMenu.setLayout(null);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(100, 100, 500, 250);
        fb.addTitleLabel("prompt", labelToStrings.get("prompt"));
        fb.addSubmitButton("viewAllTemplateButton", labelToStrings.get("viewAllTemplateButton"));
        fb.addSubmitButton("returnToMainMenuButton", labelToStrings.get("returnToMainMenuButton"));
        fb.addListener(this);

        form = fb.getForm();
        templateMenu.add(form.getPanel());
    }

    /**
     * Invoked when an action occurs.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.get("returnToMainMenuButton")){
            cl.show(main, "regularUserMainMenu");
        } else if (e.getSource() == form.get("viewAllTemplateButton")) {
            JPanel temp = new JPanel();
            temp.setLayout(null);

            JScrollPane templates = data.getPublishedTemplates(temp);
            templates.setBounds(25, 25, 400, 500);
            temp.add(templates);
            main.add(temp, "templateInfo");

            back.addActionListener(this);
            back.setBounds(475, 225, 200, 50);
            temp.add(back);

            cl.show(main, "templateInfo");
        } else if (e.getSource() == back) {
            this.run();
        }
    }
}
