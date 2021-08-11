package UserInterface.Graphical;

import Controller.AccessController;
import Controller.PlannerController;
import Controller.TemplateController;

import javax.swing.*;
import java.awt.*;

public class ViewData {
    private final AccessController accessController;
    private final TemplateController templateController;
    private final PlannerController plannerController;

    JTextArea templateInfo = new JTextArea();
    JTextArea plannerInfo = new JTextArea();
    JTextArea accountInfo = new JTextArea();
    JTextArea singlePlannerInfo = new JTextArea();
    JTextArea singleTemplateInfo = new JTextArea();
    JTextArea friendsInfo = new JTextArea();
    JTextArea accountsInfo = new JTextArea();

    JScrollPane planners = new JScrollPane(plannerInfo);
    JScrollPane templates = new JScrollPane(templateInfo);
    JScrollPane account = new JScrollPane(accountInfo);
    JScrollPane planner = new JScrollPane(singlePlannerInfo);
    JScrollPane template = new JScrollPane(singleTemplateInfo);
    JScrollPane friends = new JScrollPane(friendsInfo);
    JScrollPane allAccounts = new JScrollPane(accountsInfo);

    public ViewData(AccessController accessController, TemplateController templateController,
                    PlannerController plannerController) {
        this.accessController = accessController;
        this.plannerController = plannerController;
        this.templateController = templateController;
    }

    public JScrollPane getTemplates(JPanel panel) {
        this.updateTemplates(panel);
        return this.templates;
    }

    public JScrollPane getPlanners(JPanel panel) {
        this.updatePlanners(panel);
        return this.planners;
    }

    public JScrollPane getPlanner(String id, JPanel panel) {
        this.updatePlanner(id, panel);
        return this.planner;
    }

    public JScrollPane getTemplate(String id, JPanel panel){
        this.updateTemplate(id, panel);
        return this.template;
    }

    public JScrollPane getAccounts(JPanel panel) {
        this.updateAccountsInfo(panel);
        return this.allAccounts;
    }

    public JScrollPane getAccount(String id, JPanel panel) {
        this.updateAccountInfo(id, panel);
        return this.account;
    }

    public JScrollPane getFriendsInfo(JPanel panel) {
        this.updateFriendList(accessController.getCurrUserId(), panel);
        return this.friends;
    }

    private void updateTemplates(JPanel panel) {
        this.scrollPaneInit(templates, panel);
        templateInfo.setText(templateController.detailViewAllTemplates(false));
        this.textAreaInit(templateInfo);
    }

    private void updatePlanners(JPanel panel) {
        String separator = "\n===============\n";
        this.scrollPaneInit(planners, panel);
        plannerInfo.setText(plannerController.viewUserPlanners() + separator + plannerController.viewPublicPlanners());
        this.textAreaInit(plannerInfo);
    }

    private void updatePlanner(String id, JPanel panel) {
        this.scrollPaneInit(planner, panel);
        singlePlannerInfo.setText(plannerController.toString(Integer.parseInt(id)));
        this.textAreaInit(singlePlannerInfo);
    }

    private void updateTemplate(String id, JPanel panel) {
        this.scrollPaneInit(template, panel);
        singleTemplateInfo.setText(templateController.detailViewTemplate(Integer.parseInt(id)));
        this.textAreaInit(singleTemplateInfo);
    }

    private void updateFriendList(String id, JPanel panel) {
        this.scrollPaneInit(friends, panel);
        friendsInfo.setText(accessController.getFriendsInfo(accessController.getCurrUserId()));
        this.textAreaInit(friendsInfo);
    }

    private void updateAccountsInfo(JPanel panel) {
        this.scrollPaneInit(allAccounts, panel);
        accountsInfo.setText(accessController.viewAllAccount());
        this.textAreaInit(accountsInfo);
    }

    private void updateAccountInfo(String id, JPanel panel) {
        this.scrollPaneInit(account, panel);
        accountInfo.setText(accessController.getInfo(id));
        this.textAreaInit(accountInfo);
    }

    private void scrollPaneInit(JScrollPane js, JPanel jp) {
        js.setBounds(25, 25, 400, 500);
        js.setBackground(new Color(143, 141, 141));
        jp.add(js);
    }

    private void textAreaInit(JTextArea ta) {
        ta.removeAll();
        ta.setEditable(false);
        ta.setLayout(null);
    }

}
