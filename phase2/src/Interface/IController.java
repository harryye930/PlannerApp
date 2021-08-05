package Interface;

import java.util.ArrayList;

public interface IController {
    /**
     * Login the user with given retriever and password.
     * @param retriever A string representing the User ID or Email.
     * @param password A String representing the password.
     * @return A boolean value representing whether the login is successful or not.
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
     * Return the ID of the user currently logged in, null if there is no user currently logged in.
     * @return A String representing the user ID.
     */
    public String getCurrentId();

    /**
     * Return a String of planners owned by current user.
     * @return A String representing the information about planners.
     */
    public String viewUserPlanners();

    /**
     * Return a String of planners owned by current user.
     * @param retriever A String representing user id or email.
     * @return A String representing the information about planners.
     */
    public String viewUserPlanners(String retriever);

    /**
     * Return a String of public planners.
     * @return A String representing the information about public planners.
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
     * Create a new planner based on existing template.
     * @return A String representing the planner ID.
     */
    public String createPlanner();

    /**
     * View the templates' information.
     * @return A String representing the information about the templates.
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
     * View information about a specific planner.
     * @param id A String representing the id of the planner we want to check.
     * @return A String representing the information about the planner.
     */
    public String viewPlanner(String id);

    /**
     * View information about a specific planner.
     * @return A String representing the information about the planner with the
     * current planner id.
     */
    public String viewPlanner();

    /**
     * @return A boolean value representing the status of the planner, true if it
     * is private, false if it is public.
     */
    public boolean getPlannerStatus();

    /**
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
     * @param id A String representing the id of the planner we want to delete.
     * @return A boolean value representing whether the deletion is successful or not.
     */
    public boolean deletePlanner(String id);

    /**
     * Check the planner with given id, similar to the login process.
     * @param id A String representing the ID of the planner we want to check.
     * @return A boolean value representing whether the planner is available to the
     * user or not.
     */
    public boolean checkPlanner(String id);

    /**
     * Edit the planner with given time and agenda
     * @param time A String representing the timeslot or index of the planner.
     *             For daily planner, a timeslot in the form of HH:MM is needed.
     *             For project planner, an integer is needed.
     * @param agenda A String representing the agenda you want to assign.
     * @return A boolean value representing whether the edit is successful or not.
     */
    public boolean editDailyPlanner(String  time, String agenda);

    /**
     * Get the planner type, Daily planner or Project Planner.
     * @return A String representing the planner type.
     */
    public  String getPlannerType();

    /**
     * Save the data in .ser files.
     */
    public void saveProgram();

    /**
     * Delete the account with the current retriever.
     */
    public void deleteAccount();

    /**
     * Change the password of the current retriever.
     * @param original A String representing the original password.
     * @param newPassword A String representing the new password.
     * @return A boolean value representing whether the update is successful or not.
     */
    public boolean changePassword(String original, String newPassword);

    /**
     * Get account information.
     * @return A String representing the account information.
     */
    public String getAccountInfo();

    /**
     * Change the username to newName.
     * @param newName A String representing the new name we want to assign.
     */
    public void changeUserName(String newName);

    /**
     * Return a collection of IDs of the planners that are owned by the current user.
     * @return An ArrayList representing planner ids.
     */
    public ArrayList<String> getPlannerIds();

    /**
     * Return all the user information in a collection.
     * @return An ArrayList representing all the user info.
     */
    public ArrayList<String> allUserInfo();

    void setSuspension(String userId, long days);

    void unSuspend(String userId);

    boolean suspendStatus(String userId);

}
