package graphical_user_interface;

import controller.AccessController;
import controller.PlannerController;
import controller.TemplateController;

import javax.swing.*;
import java.awt.*;

/**
 * Fetches data from controllers and put it inside JScrollPane.
 */
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
    JTextArea trashBinInfo = new JTextArea();

    JScrollPane planners = new JScrollPane(plannerInfo);
    JScrollPane templates = new JScrollPane(templateInfo);
    JScrollPane account = new JScrollPane(accountInfo);
    JScrollPane planner = new JScrollPane(singlePlannerInfo);
    JScrollPane template = new JScrollPane(singleTemplateInfo);
    JScrollPane friends = new JScrollPane(friendsInfo);
    JScrollPane allAccounts = new JScrollPane(accountsInfo);
    JScrollPane trashBin = new JScrollPane(trashBinInfo);

    /**
     * Constructs an instance of ViewData
     * @param accessController An instance of AccessController.
     * @param templateController An instance of TemplateController.
     * @param plannerController An instance of PlannerController.
     */
    public ViewData(AccessController accessController, TemplateController templateController,
                    PlannerController plannerController) {
        this.accessController = accessController;
        this.plannerController = plannerController;
        this.templateController = templateController;
    }

    /**
     * Gets info of all templates stored in the program and puts it inside a JScrollPane inside panel.
     * @param panel Panel where templates data will be added.
     * @return JScrollPane containing templates data.
     */
    public JScrollPane getTemplates(JPanel panel) {
        this.updateTemplates(panel, false);
        return this.templates;
    }

    /**
     * Gets info of published templates stored in the program and puts it inside a JScrollPane inside panel.
     * @param panel Panel where templates data will be added.
     * @return JScrollPane containing templates data.
     */
    public JScrollPane getPublishedTemplates(JPanel panel) {
        this.updateTemplates(panel, true);
        return this.templates;
    }

    /**
     * Gets info of all planners stored in the program and puts it inside a JScrollPane inside panel.
     * @param panel Panel where planners data will be added.
     * @return JScrollPane containing planners data.
     */
    public JScrollPane getPlanners(JPanel panel) {
        this.updatePlanners(panel);
        return this.planners;
    }

    /**
     * Gets info of the planner with id stored in the program and puts it inside a JScrollPane inside panel.
     * @param id ID of the planner.
     * @param panel Panel where the planner's data will be added.
     * @return JScrollPane containing the planner's data.
     */
    public JScrollPane getPlanner(String id, JPanel panel) {
        this.updatePlanner(id, panel);
        return this.planner;
    }

    /**
     * Gets info of the template with id stored in the program and puts it inside a JScrollPane inside panel.
     * @param id ID of the template.
     * @param panel Panel where the template's data will be added.
     * @return JScrollPane containing the template's data.
     */
    public JScrollPane getTemplate(String id, JPanel panel){
        this.updateTemplate(id, panel);
        return this.template;
    }

    /**
     * Gets info of all accounts stored in the program and puts it inside a JScrollPane inside panel.
     * @param panel Panel where accounts data will be added.
     * @return JScrollPane containing accounts data.
     */
    public JScrollPane getAccounts(JPanel panel) {
        this.updateAccountsInfo(panel);
        return this.allAccounts;
    }

    /**
     * Gets info of the account with id stored in the program and puts it inside a JScrollPane inside panel.
     * @param id ID of the account.
     * @param panel Panel where the account's data will be added.
     * @return JScrollPane containing the account's data.
     */
    public JScrollPane getAccount(String id, JPanel panel) {
        this.updateAccountInfo(id, panel);
        return this.account;
    }

    /**
     * Gets info about the current user's friends and puts it inside a JScrollPane inside panel.
     * @param panel Panel where the friends' data will be added.
     * @return JScrollPane containing the friends' data.
     */
    public JScrollPane getFriendsInfo(JPanel panel) {
        this.updateFriendList(accessController.getCurrUserId(), panel);
        return this.friends;
    }

    /**
     * Gets info about the planner trash bin of user with id, and puts it inside a JScrollPane inside panel.
     * @param id ID of the user.
     * @param panel Panel where the planner trash bin info will be added.
     * @return JScrollPane containing the planner trash bin info.
     */
    public JScrollPane getTrashBin(String id, JPanel panel) {
        this.updateTrashBinInfo(id, panel);
        return this.trashBin;
    }

    private void updateTemplates(JPanel panel, boolean publishedTemplatesOnly) {
        this.scrollPaneInit(templates, panel);
        templateInfo.setText(templateController.detailViewAllTemplates(publishedTemplatesOnly));
        this.textAreaInit(templateInfo);
    }

    private void updatePlanners(JPanel panel) {
        String separator = "\n==================\nPublic Planners:\n";
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

    public void updateTrashBinInfo(String id, JPanel panel) {
        this.scrollPaneInit(trashBin, panel);
        trashBinInfo.setText(accessController.getTrashPlanner(id));
        this.textAreaInit(trashBinInfo);
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
