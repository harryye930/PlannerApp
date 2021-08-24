package controller;

import gateway.PlannerGateway;
import use_case.PlannerManager;

import java.util.List;

/**
 * The Planner controller.
 */
public class PlannerController {
    private final PlannerManager plannerManager;
    private final PlannerGateway plannerGateway;

    private AccessController accessController;
    private TemplateController templateController;

    private String currPlannerId;

    /**
     * Sets the access Controller.
     * @param accessController An AccessController object.
     */
    public void setAccessController(AccessController accessController) {
        this.accessController = accessController;
    }

    /**
     * Sets the template Controller.
     * @param templateController A TemplateController object.
     */
    public void setTemplateController(TemplateController templateController) {
        this.templateController = templateController;
    }

    /**
     * Initializes the PlannerController. Creates a new PlannerManager.
     */
    public PlannerController(){
        this.plannerManager = new PlannerManager();
        this.plannerGateway = new PlannerGateway(plannerManager);
        this.load();
    }

    /**
     * Gets the current planner ID.
     * @return The current planner id.
     */
    public String getCurrPlannerId() {
        return currPlannerId;
    }

    /**
     * Saves the planners.
     * Call this function when a saving is needed. Must be called when exit the application.
     * @return A boolean value representing whether the saving process is successful.
     */
    public boolean save() {
        return this.plannerGateway.save();
    }

    /**
     * Loads in the planners to PlannerManager.
     * @return A boolean value representing whether the loading process is successful.
     */
    public boolean load() {
        return this.plannerGateway.load();
    }

    /**
     * Creates a new planner based on the chosen template.
     * @return A String representing the planner id.
     */
    public String createPlanner(String firstInput, String secondInput, String thirdInput, String name) {
        String type = this.templateController.getTemplateType(
                Integer.parseInt(templateController.getCurrTemplateId()));
        int id;
        Integer createdPlannerID = this.plannerManager.createPlanner(type, name, firstInput, secondInput, thirdInput);
        if (createdPlannerID == null){
            System.out.printf("Planner of type %s cannot be created.", type);
            return null;
        } else {
            id = createdPlannerID;
            this.currPlannerId = Integer.toString(id);
            accessController.setPlanner(accessController.getCurrUserId(), this.currPlannerId);
            this.save();
            return this.currPlannerId;
        }
    }

    /**
     * Gets a string representation of the planner with id.
     * @return A string representing the planner.
     */
    public String toString(int id){
        return plannerManager.findPlanner(id).toString();
    }

    /**
     * Returns a String of planners owned by current user.
     * @return A String representing the information of planners.
     */
    public String viewUserPlanners() {
        StringBuilder res = new StringBuilder();
        List<String> plannerIds = this.accessController.getPlanners(this.accessController.getCurrUserId());
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
     * Returns a String representation of all public planners.
     * @return A String representing the information of public planners.
     */
    public String viewPublicPlanners() {
        StringBuilder res = new StringBuilder();
        List<Integer> publicPlanners = this.getPublicPlanners();
        for (int plannerId: publicPlanners) {
            res.append(this.toString(plannerId));
            res.append("==================================\n");
        }
        return res.toString();
    }

    /**
     * Checks if the planner with id is available to the current user.
     * @param id A string representing the ID of the planner.
     * @return A boolean value representing whether the planner is available to the current user.
     */
    public boolean checkPlanner(String id) {
        List<String> plannerIds;
        if (this.accessController.isAdmin(this.accessController.getCurrUserId()).equals("admin")) {
            plannerIds = this.plannerManager.getAllPlannerId();
        } else {
            plannerIds = this.accessController.getPlanners(accessController.getCurrUserId());
        }
        List<Integer> publicIds = this.getPublicPlanners();
        if (plannerIds.contains(id) || publicIds.contains(Integer.parseInt(id))) {
            this.currPlannerId = id;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Passes on request to edit a planner.
     * @param item: The item for which the agenda will be changed. The item varies based on the type of the planner,
     *            e.g., item is timeslot for daily planner, and is status column for project planner.
     * @param newAgenda: New agenda item.
     * @return true iff is edit is successful.
     */
    public boolean edit(String item, String newAgenda){
        boolean flag = plannerManager.edit(Integer.parseInt(currPlannerId), item, newAgenda);
        this.save();
        return flag;
    }

    /**
     * Passes on request to change the privacy status of the current planner.
     * @param status New status to set the planner to. Must be "private" or "public" or "friends-only"
     * @return true iff the planner's privacy status has been successfully set to status.
     */
    public boolean changePrivacyStatus(String status){
        boolean flag = plannerManager.changePrivacyStatus(Integer.parseInt(currPlannerId), status);
        this.save();
        return flag;
    }

    /**
     * Deletes the planner corresponding to the given plannerId.
     * @return true if successfully deleted, false if otherwise.
     */
    public boolean deletePlanner(String plannerId){
        accessController.removePlanner(accessController.getCurrUserId(), plannerId);
        boolean flag = this.plannerManager.deletePlanner(Integer.parseInt(plannerId));
        this.save();
        return flag;
    }

    /**
     * Returns a list of id's of all planners that are public.
     * @return List of all public planner's id.
     */
    public List<Integer> getPublicPlanners(){
        return plannerManager.getPublicPlanners();
    }

    /**
     * Gets the type of the planner with id.
     * @param id The id of the planner.
     * @return String representation of the type of the planner.
     */
    public String getType(int id){
        return plannerManager.plannerType(id);
    }

    /**
     * Returns the privacy status of the planner corresponding to the given id.
     * @param id The id of the planner.
     * @return String representing the privacy status of the planner.
     */
    public String  getPrivacyStatus(int id) {
        return this.plannerManager.getPrivacyStatus(id);
    }

    /**
     * Adds new agenda or task to the planner.
     * @param i The first String input depending on the planner type.
     * @param j The second String input depending on the planner type.
     * @return A boolean value representing whether the change is successful.
     */
    public boolean add(String i, String j) {
        boolean flag = this.plannerManager.add(Integer.parseInt(this.getCurrPlannerId()), i, j);
        this.save();
        return flag;
    }

    /**
     * Changes the status of the given task.
     * @param taskName A String representing the task name.
     * @param status A String representing the task status.
     * @return A boolean value representing whether the change is successful.
     */
    public boolean changeTaskStatus(String taskName, String status) {
        boolean flag = plannerManager.changeTaskStatus(Integer.parseInt(getCurrPlannerId()), taskName, status);
        this.save();
        return flag;
    }
}
