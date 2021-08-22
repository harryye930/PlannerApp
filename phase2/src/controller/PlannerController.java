package controller;

import gateway.PlannerGateway;
import use_case.PlannerManager;

import java.util.ArrayList;

/**
 *  the Planner controller.
 */
public class PlannerController {
    private final PlannerManager plannerManager;
    private final PlannerGateway accGateway;

    private AccessController accessController;
    private TemplateController templateController;

    private String currPlannerId;

    /**
     * Set the access Controller.
     * @param accessController A access Controller Controller.
     */
    public void setAccessController(AccessController accessController) {
        this.accessController = accessController;
    }

    /**
     * Set the template Controller.
     * @param templateController A template controller object
     */
    public void setTemplateController(TemplateController templateController) {
        this.templateController = templateController;
    }

    /**
     * Initialize the PlannerController. Create a new PlannerManager.
     */
    public PlannerController(){
        this.plannerManager = new PlannerManager();
        this.accGateway = new PlannerGateway(plannerManager);
        this.load();
    }

    /**
     * Get the current planner ID.
     * @return the planner id
     */
    public String getCurrPlannerId() {
        return currPlannerId;
    }

    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.accGateway.save();
    }

    /**
     * Load in the data from database to AccountManager.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean load() {
        return this.accGateway.load();
    }

    /**
     * Create a new planner based on the chosen template.
     * @return A String representing the planner id.
     */
    public String createPlanner(String prompt0, String prompt1, String prompt2, String name) {
        String type = this.templateController.getTemplateType(Integer.parseInt(templateController.getCurrTemplateId()));
        int id = 0;
        if (type.equals("Daily")) {
            id = this.plannerManager.newDailyPlanner(name, prompt0, prompt1, prompt2);
            this.currPlannerId = Integer.toString(id);
        } else if (type.equals("Project")) {
            //TODO: Add planner name.
            id = this.plannerManager.newProjectPlanner(name, prompt0, prompt1, prompt2);
            this.currPlannerId = Integer.toString(id);
        }
        accessController.setPlanner(accessController.getCurrUserId(), Integer.toString(id));
        this.save();
        return Integer.toString(id);
    }


    /** Pass on request to get a string representation of a planner
     *
     * @return a string of the planner tasks
     */
    public String toString(int id){
        return plannerManager.findPlanner(id).toString();
    }

    /**
     * Return a String of planners owned by current user.
     * @return A String representing the information of planners.
     */
    public String viewUserPlanners() {
        StringBuilder res = new StringBuilder();
        ArrayList<String> plannerIds = this.accessController.getPlanners(this.accessController.getCurrUserId());
        System.out.println(plannerIds.toString());
        if (plannerIds.size() == 0) {
            return "No personal planners available yet.";
        } else {
            for (String plannerId : plannerIds) {
                res.append(this.toString(Integer.parseInt(plannerId)));
                res.append("==================================\n");
            }
            return res.toString();
        }
    }

    /**
     * Return a String of public planners.
     * @return A String representing the information of public planners.
     */
    public String viewPublicPlanners() {
        StringBuilder res = new StringBuilder();
        ArrayList<Integer> publicPlanners = this.getPublicPlanners();
        for (int plannerId: publicPlanners) {
            res.append(this.toString(plannerId));
            res.append("==================================");
        }
        return res.toString();
    }

    /**
     * Check the planner with given id, similar to the login process.
     * @param id A String representing the if the planner we want to check.
     * @return A boolean value representing whether the planner is available to the
     * user or not.
     */
    public boolean checkPlanner(String id) {
        ArrayList<String> plannerIds;
        if (this.accessController.isAdmin(this.accessController.getCurrUserId()).equals("admin")) {
            plannerIds = this.plannerManager.getAllPlannerId();
        } else {
            plannerIds = this.accessController.getPlanners(accessController.getCurrUserId());
        }
        ArrayList<Integer> publicIds = this.getPublicPlanners();
        if (plannerIds.contains(id) || publicIds.contains(Integer.parseInt(id))) {
            this.currPlannerId = id;
            return true;
        } else {
            return false;
        }
    }


    /** Pass on request to edit daily planner.
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly request to change.
     */
    public boolean edit(String time, String newAgenda){
        return plannerManager.edit(Integer.parseInt(currPlannerId), time, newAgenda);
    }

    /** Pass on request to change their own planner
     *
     * @param status "private" or "public" or "friends-only"
     * @return true iff the status is correctly requested to change. (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(String status){
        return plannerManager.changePrivacyStatus(Integer.parseInt(currPlannerId), status);
    }

    /**
     * delete the planner corresponding to the given id.
     * @return true if successfully deleted, false if otherwise.
     */
    public boolean deletePlanner(String plannerId){
        accessController.removePlanner(accessController.getCurrUserId(), plannerId);
        return this.plannerManager.deletePlanner(Integer.parseInt(plannerId));
    }


    /**
     * Show all the planners id of one author.
     * @param author the userId of the author.
     * @return the ArrayList of integer id of planners.
     */
    public ArrayList<Integer> getPlannerByAuthor(String author){
        return plannerManager.getPlannersByAuthor(author);
    }

    /**
     * return an ArrayList of all integer id of all planners made public by all authors.
     * @return the ArrayList of all public planner's id
     */
    public ArrayList<Integer> getPublicPlanners(){
        return plannerManager.getPublicPlanners();
    }

    /**
     * get the type of the planner. could be "daily" or "project" planner.
     * @param id the id of the planner
     * @return the String representation of the type of planner.
     */
    public String getType(int id){
        return plannerManager.plannerType(id);
    }

    /**
     * Get the number of agendas of a planner corresponding to given integer id.
     * @param id the integer id of the planner
     * @return the number of agendas of the planner
     */
    public int getNumAgendas(int id) {
        return plannerManager.getNumAgendas(id);
    }

    /**
     * Return the privacy status of the planner corresponding to the given id.
     * @param id the integer id of the planner
     * @return the String "private" or "public".
     */
    public String  getPrivacyStatus(int id) {
        return this.plannerManager.getPrivacyStatus(id);
    }

    //TODO the ChangeTaskStatus method, based on project type; daily always return false; project planner detail see
    //TODO in project planner entity class; reminder planner the second input parameter should be a default value(e.g. "")

    /**
     * Add new agenda or task to the planner
     * @param i the first String input depending on the planner type
     * @param j the second String input depending on the planner type.
     * @return a boolean value representing whether the change is successful or not.
     */
    public boolean add(String i, String j) {
        return this.plannerManager.add(Integer.parseInt(this.getCurrPlannerId()), i, j);
    }

    /**
     * Change the status of the given task.
     * @param taskName A String representing the task name.
     * @param status A String representing the task status.
     * @return a boolean value representing whether the change is successful or not.
     */
    public boolean changeTaskStatus(String taskName, String status) {
        return plannerManager.changTaskStatus(Integer.parseInt(getCurrPlannerId()), taskName, status);
    }
}
