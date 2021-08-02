package Controller;

import Interface.IController;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A Class that control AccessController, PlannerController and TemplateController.
 */
public class ActionController implements IController{
    private final PlannerController plannerController;
    private final AccessController accessController;
    private final TemplateController templateController;

    private String currRetriever;
    private String currPlannerId;
    private String currTemplateId;


    /**
     * Initialize the object with new managers and load the data at the same time.
     */
    public ActionController() {
        this.accessController = new AccessController();
        this.templateController = new TemplateController();
        this.plannerController = new PlannerController();

        accessController.load();
        templateController.load();
        plannerController.load();
    }


    /**
     * Login the user, return true if the login is success.
     * @param retriever A string representing the User ID or Email.
     * @param password A String representing the password.
     * @return A boolean value that representing whether the login is success or not.
     */
    @Override
    public boolean logIn(String retriever, String password) {
        if (this.accessController.logIn(retriever, password)) {
            this.currRetriever = retriever;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Return the account Role.
     * @param retriever A String representing the user ID or Email.
     * @return A String indicating the account role, "admin", "regular" or "trial".
     */
    @Override
    public String accountRole(String retriever) {
        return accessController.isAdmin(retriever);
    }


    /**
     * Return the account Role.
     * @return A String indicating the account role, "admin", "regular" or "trial".
     */
    @Override
    public String accountRole() {
        return this.accessController.isAdmin(this.currRetriever);
    }


    /**
     * Return the ID of currently logged in user, null if user did not login
     * @return A String representing the user ID.
     */
    @Override
    public String getCurrentId() {
        return this.currRetriever;
    }


    /**
     * Return a String of planners owned by current user.
     * @return A String representing the information of planners.
     */
    @Override
    public String viewUserPlanners() {
        StringBuilder res = new StringBuilder();
        ArrayList<String> plannerIds = this.accessController.getPlanners(this.currRetriever);
        System.out.println(plannerIds.toString());
        if (plannerIds.size() == 0) {
            return "No personal planners available yet.";
        } else {
            for (String plannerId : plannerIds) {
                res.append(this.plannerController.toString(Integer.parseInt(plannerId)));
                res.append("==================================");
            }
            return res.toString();
        }
    }


    /**
     * Return a String of public planners.
     * @return A String representing the information of public planners.
     */
    @Override
    public String viewPublicPlanners() {
        StringBuilder res = new StringBuilder();
        ArrayList<Integer> publicPlanners = plannerController.getPublicPlanners();
        for (int plannerId: publicPlanners) {
            res.append(plannerController.toString(plannerId));
            res.append("==================================");
        }
        return res.toString();
    }


    /**
     * Create an account.
     * @param email A String representing the email of the account.
     * @param userName A String representing the username of the account.
     * @param password A String representing the password of the account.
     * @return A String representing the Account ID of created account.
     */
    @Override
    public String createAccount(String email, String userName, String password) {
        String  retriever =  accessController.createAccount(email, userName, password);
        this.saveProgram();
        return retriever;
    }


    /**
     * Create a Daily Planner.
     * @return A String representing the id of the created planner.
     */
    @Override
    public String createDailyPlanner() {
        String id =  ((Integer)plannerController.createNewDailyPlanner()).toString();
        this.plannerController.setPlannerAuthor(Integer.parseInt(id), this.currRetriever);
        this.accessController.setPlanner(this.currRetriever, id);
        this.saveProgram();
        return id;
    }


    /**
     * Create a project Planner.
     * @return A String representing the id of the created planner.
     */
    @Override
    public String createProjectPlanner() {
        String id =  ((Integer)plannerController.createNewProjectPlanner()).toString();
        this.plannerController.setPlannerAuthor(Integer.parseInt(id), this.currRetriever);
        this.accessController.setPlanner(this.currRetriever, id);
        this.saveProgram();
        return id;
    }


    /**
     * View the templates' information.
     * @return A String representing the information of the data.
     */
    @Override
    public String viewTemplates() {
        return templateController.detailViewAllTemplates();
    }


    /**
     * Check the template, similar to login so that the controller will
     * remember the planner's id.
     * @param id A String representing the planner id we want to check.
     * @return A boolean value representing whether the planner is available to the current
     * account.
     */
    @Override
    public boolean checkTemplate(String id) {
        for (String tempId: templateController.getAllTemplateIds()) {
            if (Objects.equals(id, tempId)) {
                this.currTemplateId = id;
                return true;
            }
        }
        return false;
    }


    /**
     * View the information of specific planner.
     * @param id A String representing the id of the planner we want to check.
     * @return A String representing the information about the planner
     */
    @Override
    public String viewPlanner(String id) {
        return plannerController.toString(Integer.parseInt(id));
    }


    /**
     * View the information of specific planner.
     * @return A String representing the information about the planner with
     * current planner id.
     */
    @Override
    public String viewPlanner() {
        return plannerController.toString(Integer.parseInt(this.currPlannerId));
    }


    /**
     *
     * @return A boolean value representing the status of the planner, true if it
     * is private, false if it is public.
     */
    @Override
    public boolean getPlannerStatus() {
        return this.plannerController.getPrivacyStatus(Integer.parseInt(this.currPlannerId)).equals("private");
    }


    /**
     *
     * @param id A String representing the planner id we want to check.
     * @return A boolean value representing the status of the planner, true if it
     * is private, false if it is public.
     */
    @Override
    public boolean getPlannerStatus(String id) {
        return this.plannerController.getPrivacyStatus(Integer.parseInt(id)).equals("private");
    }


    /**
     * Change the planner status without parameter, from private to public or public
     * to private.
     */
    @Override
    public void setPlannerStatus() {
        if (getPlannerStatus()) {
            this.plannerController.changePrivacyStatus(Integer.parseInt(this.currPlannerId), "public");
        } else {
            this.plannerController.changePrivacyStatus(Integer.parseInt(this.currPlannerId), "private");
        }
    }


    /**
     * Delete the planner with given id.
     * @param id A String representing the id of the planner we want to
     * @return A boolean value representing whether the deletion is successful or not.
     */
    @Override
    public boolean deletePlanner(String id) {
        boolean flag = plannerController.deletePlanner(Integer.parseInt(id));
        if (flag) {
            this.accessController.removePlanner(currRetriever, id);
            this.accessController.save();
            this.saveProgram();
            return true;
        } else {
            return false;
        }
    }


    /**
     * Check the planner with given id, similar to the login process.
     * @param id A String representing the if the planner we want to check.
     * @return A boolean value representing whether the planner is available to the
     * user or not.
     */
    @Override
    public boolean checkPlanner(String id) {
        ArrayList<String> plannerIds = this.accessController.getPlanners(this.currRetriever);
        ArrayList<Integer> publicIds = this.plannerController.getPublicPlanners();
        if (plannerIds.contains(id) || publicIds.contains(Integer.parseInt(id))) {
            this.currPlannerId = id;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Edit the planner with given time and agenda
     * @param timeSlot A String representing the timeslot or index of the planner. For daily planner,
     *             a timeslot in form of HH:MM is needed. for project planner, a integer number is
     *             needed.
     * @param agenda A String representing the agenda you want to assign.
     * @return A boolean value representing whether the edit is successful or not.
     */
    @Override
    public boolean editDailyPlanner(String timeSlot, String agenda) {
        boolean res = this.plannerController.edit(Integer.parseInt(this.currPlannerId), timeSlot, agenda);
        this.saveProgram();
        return res;
    }


    /**
     * Get the planner type, Daily planner or Project Planner
     * @return A String representing the planner type.
     */
    @Override
    public String getPlannerType() {
        return this.plannerController.getType(Integer.parseInt(this.currPlannerId));
    }


    /**
     * Save the data in ser. files.
     */
    @Override
    public void saveProgram() {
        this.accessController.save();
        this.plannerController.save();
        this.templateController.save();
    }


    /**
     * Delete the account with current retriever.
     */
    @Override
    public void deleteAccount() {
        for (String plannerId: this.accessController.getPlanners(currRetriever)) {
            if (this.plannerController.getPrivacyStatus(Integer.parseInt(plannerId)).equals("private")) {
                this.plannerController.deletePlanner(Integer.parseInt(plannerId));
            }
        }
        this.accessController.removeAccount(this.currRetriever);
    }


    /**
     * Change the password of the current retriever.
     * @param original A String representing the original password.
     * @param newPassword A String representing the new password.
     * @return A boolean value representing whether the reset is successful or not.
     */
    @Override
    public boolean changePassword(String original, String newPassword) {
        return this.accessController.changePassword(currRetriever, original, newPassword);
    }


    /**
     * Get the account information.
     * @return A String representing the account information.
     */
    @Override
    public String getAccountInfo() {
        return this.accessController.getInfo(currRetriever);
    }


    /**
     * Change the username with given new name.
     * @param newName A String representing the new name want to assign.
     */
    @Override
    public void changeUserName(String newName) {
        this.accessController.changeUserName(currRetriever, newName);
    }
}
