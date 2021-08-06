package UserInterface.Graphical;

import Controller.AccessController;
import Controller.PlannerController;
import Controller.TemplateController;
import Interface.IController;
import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;

public class ViewData {
    private final AccessController accessController;
    private final TemplateController templateController;
    private final PlannerController plannerController;

    JTextArea templateInfo = new JTextArea();
    JTextArea plannerInfo = new JTextArea();
    JTextArea accountInfo = new JTextArea();

    JScrollPane planners = new JScrollPane(plannerInfo);
    JScrollPane templates = new JScrollPane(templateInfo);
    JScrollPane accounts = new JScrollPane(accountInfo);

    public ViewData(AccessController accessController, TemplateController templateController,
                    PlannerController plannerController) {
        this.accessController = accessController;
        this.plannerController = plannerController;
        this.templateController = templateController;
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
        templateInfo.setText(templateController.detailViewAllTemplates(false));
        templateInfo.removeAll();
        templateInfo.setLayout(null);
        templateInfo.setEditable(false);

        //plannerInfo.setBounds(25, 25, 400, 500);
        String separator = "\n===============\n";
        planners.setBackground(new Color(143, 141, 141));
        plannerInfo.setText(plannerController.viewUserPlanners() + separator + plannerController.viewPublicPlanners());
        plannerInfo.removeAll();
        plannerInfo.setEditable(false);
        plannerInfo.setLayout(null);

        accountInfo.setBackground(new Color(241, 241, 241));
        accountInfo.setText(accessController.getInfo(accessController.getCurrUserId()));
        accountInfo.removeAll();
        accountInfo.setEditable(false);
        accountInfo.setLayout(null);
    }

}
