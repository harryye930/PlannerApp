package UserInterface.Graphical;

import Interface.IController;

import javax.swing.*;
import java.awt.*;

public class ViewData {
    private IController controller;

    JTextArea templateInfo = new JTextArea();
    JTextArea plannerInfo = new JTextArea();
    JTextArea accountInfo = new JTextArea();

    JScrollPane planners = new JScrollPane(plannerInfo);
    JScrollPane templates = new JScrollPane(templateInfo);
    JScrollPane accounts = new JScrollPane(accountInfo);

    public ViewData(IController controller) {
        this.controller = controller;
    }

    public JScrollPane getTemplates() {
        this.update();
        return this.templates;
    }

    public JScrollPane getPlanners() {
        this.update();
        return this.planners;
    }

    public JScrollPane getAccounts() {
        this.update();
        return this.accounts;
    }

    private void update() {

        //templates.setBounds(25, 25, 400, 500);
        templates.setBackground(new Color(143, 141, 141));
        templateInfo.setText(controller.viewTemplates());
        templateInfo.removeAll();
        templateInfo.setLayout(null);
        templateInfo.setEditable(false);

        //plannerInfo.setBounds(25, 25, 400, 500);
        String separator = "\n===============\n";
        planners.setBackground(new Color(143, 141, 141));
        plannerInfo.setText(controller.viewUserPlanners() + separator + controller.viewPublicPlanners());
        plannerInfo.removeAll();
        plannerInfo.setEditable(false);
        plannerInfo.setLayout(null);

        accountInfo.setBackground(new Color(255, 255, 255));
        accountInfo.setText(controller.getAccountInfo());
        accountInfo.removeAll();
        accountInfo.setEditable(false);
        accountInfo.setLayout(null);
    }

}
