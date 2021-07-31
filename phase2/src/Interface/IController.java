package Interface;

public interface IController {
    public boolean logIn(String retriever, String password);

    public String accountRole(String retriever);

    public String accountRole();

    /**
     * Return the ID of currently logged in user, null if user did not login
     * @return A String representing the user ID.
     */
    public String getCurrentId();

    /**
     * Return a String of planners owned by current user.
     * @return A String representing the information of planners.
     */
    public String viewUserPlanners();

    public String viewPublicPlanners();

    public String createAccount(String email, String userName, String password);

    public String createDailyPlanner();

    public String createProjectPlanner();

    public String viewTemplates();

    public boolean checkTemplate(String id);

    public String viewPlanner(String id);

    public String viewPlanner();

    public boolean getPlannerStatus();

    public boolean getPlannerStatus(String id);

    public void setPlannerStatus();

    public boolean deletePlanner(String id);

    public boolean checkPlanner(String id);

    public boolean editPlanner(String  time, String agenda);

    public  String getPlannerType();

    public void saveProgram();

}
