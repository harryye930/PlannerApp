package Interface;

public interface IController {
    /**
     * Login the user with given retriever and password.
     * @param retriever A string representing the User ID or Email.
     * @param password A String representing the password.
     * @return A boolean value representing whether the login has success or not.
     */
    public boolean logIn(String retriever, String password);


    /**
     * Return the account Role.
     * @param retriever A String representing the user ID or Email.
     * @return A String indicating the account role, "admin", "regular" or "trial".
     */
    public String accountRole(String retriever);


    /**
     * Return the account Role.
     * @return A String indicating the account role, "admin", "regular" or "trial".
     */
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


    /**
     * Return a String of public planners.
     * @return A String representing the information of public planners.
     */
    public String viewPublicPlanners();


    /**
     * Create an account.
     * @param email A String representing the email of the account.
     * @param userName A String representing the username of the account.
     * @param password A String representing the password of the account.
     * @return A String representing the Account ID of created account.
     */
    public String createAccount(String email, String userName, String password);


    /**
     * Create a Daily Planner.
     * @return A String representing the id of the created planner.
     */
    public String createDailyPlanner();


    /**
     * Create a project Planner.
     * @return A String representing the id of the created planner.
     */
    public String createProjectPlanner();


    /**
     * View the templates' information.
     * @return A String representing the information of the data.
     */
    public String viewTemplates();


    /**
     * Check the template, similar to login so that the controller will
     * remember the planner's id.
     * @param id A String representing the planner id we want to check.
     * @return A boolean value representing whether the planner is available to the current
     * account.
     */
    public boolean checkTemplate(String id);


    /**
     * View the information of specific planner.
     * @param id A String representing the id of the planner we want to check.
     * @return A String representing the information about the planner
     */
    public String viewPlanner(String id);


    /**
     * View the information of specific planner.
     * @return A String representing the information about the planner with
     * current planner id.
     */
    public String viewPlanner();


    /**
     *
     * @return A boolean value representing the status of the planner, true if it
     * is private, false if it is public.
     */
    public boolean getPlannerStatus();


    /**
     *
     * @param id A String representing the planner id we want to check.
     * @return A boolean value representing the status of the planner, true if it
     * is private, false if it is public.
     */
    public boolean getPlannerStatus(String id);


    /**
     * Change the planner status without parameter, from private to public or public
     * to private.
     */
    public void setPlannerStatus();


    /**
     * Delete the planner with given id.
     * @param id A String representing the id of the planner we want to
     * @return A boolean value representing whether the deletion is successful or not.
     */
    public boolean deletePlanner(String id);


    /**
     * Check the planner with given id, similar to the login process.
     * @param id A String representing the if the planner we want to check.
     * @return A boolean value representing whether the planner is available to the
     * user or not.
     */
    public boolean checkPlanner(String id);


    /**
     * Edit the planner with given time and agenda
     * @param time A String representing the timeslot or index of the planner. For daily planner,
     *             a timeslot in form of HH:MM is needed. for project planner, a integer number is
     *             needed.
     * @param agenda A String representing the agenda you want to assign.
     * @return A boolean value representing whether the edit is successful or not.
     */
    public boolean editDailyPlanner(String  time, String agenda);


    /**
     * Get the planner type, Daily planner or Project Planner
     * @return A String representing the planner type.
     */
    public  String getPlannerType();


    /**
     * Save the data in ser. files.
     */
    public void saveProgram();


    /**
     * Delete the account with current retriever.
     */
    public void deleteAccount();


    /**
     * Change the password of the current retriever.
     * @param original A String representing the original password.
     * @param newPassword A String representing the new password.
     * @return A boolean value representing whether the reset is successful or not.
     */
    public boolean changePassword(String original, String newPassword);


    /**
     * Get the account information.
     * @return A String representing the account information.
     */
    public String getAccountInfo();


    /**
     * Change the username with given new name.
     * @param newName A String representing the new name want to assign.
     */
    public void changeUserName(String newName);

}
