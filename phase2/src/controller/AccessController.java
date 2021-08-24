package controller;

import entity.Account;
import gateway.AccountGateway;
import use_case.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Account accessibility controller.
 */
public class AccessController{
    private final AccountManager accManager;
    private final AccountGateway accGateway;

    private TemplateController templateController;
    private PlannerController plannerController;

    private final PasswordCalculator passwordCalculator = new PasswordCalculator();
    private final AccountPlannerOptionManager accPlanner;
    private final AccountFriendManager accFriendManager;

    private String currUserId;

    /**
     * Constructs an AccessController.
     */
    public AccessController(){
        accManager = new AccountManager();
        accGateway = new AccountGateway(accManager);
        this.accPlanner = new AccountPlannerOptionManager(accManager);
        this.accFriendManager = new AccountFriendManager(accManager);
        this.accGateway.load();
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
     * Set the template Controller.
     * @param templateController A template controller object
     */
    public void setTemplateController(TemplateController templateController) {
        this.templateController = templateController;
    }

    /**
     * Get current user id.
     * @return A string representing user id.
     */
    public String getCurrUserId() {
        return currUserId;
    }

    /**
     * Set the plannerController.
     * @param plannerController A Planner Controller.
     */
    public void setPlannerController(PlannerController plannerController) {
        this.plannerController = plannerController;
    }

    /**
     * Check whether the given password is correct to account.
     * @param retriever A String representing User ID or Email.
     * @param passWord A String representing password.
     * @return A boolean value representing whether the password is correct or not.
     */
    public boolean logIn(String retriever, String passWord) {
        if (accManager.findAccount(retriever) == null ||
        accManager.findAccount(retriever).getSuspendedTime().isAfter(LocalDateTime.now())) {
            return false;
        } else if (accManager.findAccount(retriever).getAccountType().equals("temporary")) {
            boolean isDeleted = accManager.deleteTempAccount(retriever);
            if (isDeleted){
                return false;
            }
        }
        if (accManager.findAccount(retriever).getPassword().equals(passWord)) {
            currUserId = this.accManager.findAccount(retriever).getUserId();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether an account is an admin account or not.
     * @param retriever A String representing the User ID or Email.
     * @return A String value representing whether the account is an admin account, regular
     * account or a trial account.
     */
    public String isAdmin(String retriever) {
        Account acc = accManager.findAccount(retriever);
        if (acc != null) {
            return accManager.checkAccountRole(retriever);
        }
        return null;
    }

    /**
     * Create a new account.
     * @param email A String representing the email of this account.
     * @param userName A String representing the username of this account.
     * @param passWord A String representing the password of this account.
     * @return A String representing the unique user ID of this account.
     */
    public String createAccount(String email, String userName, String passWord) {
        String id = accManager.createAccount(email);
        accManager.setPassword(id, passWord);
        accManager.setUserName(id, userName);
        this.currUserId = id;
        this.save();
        return id;
    }

    /**
     * Create a temporary account that due 30 days later.
     * @param email A String representing the email of the account.
     * @param userName A String representing the username.
     * @param password A String representing the password.
     * @return ID of the temporary account.
     */
    public String createTemporaryAccount(String email, String userName, String password) {
        String id = accManager.createTempAcc(email);
        accManager.setPassword(id, password);
        accManager.setUserName(id, userName);
        this.currUserId = id;
        return id;
    }


    /**
     * Reset the password of given account, user needs to enter the correct original password to proceed.
     * @param retriever A String representing the User ID or Email.
     * @param oldPassWord A String representing the original password.
     * @param newPassWord A String representing the new password.
     * @return A string representing the status of the change. It is not to be displayed to users.
     *         It must be one of the following:
     *          - "oldPasswordIncorrect": The current password user entered is not correct, must re-enter.
     *          - "repeatingPassword": The new password is the same as the current password, must choose another
     *                                 new password.
     *          - "passwordTooWeak": The new password does not meet the complexity requirement, must choose another
     *                               new password.
     *          - "changeSuccessful": Password has been successfully set to newPassWord.
     *          - "changeUnsuccessful": Change was unsuccessful but not due to any of the above reasons, needs
     *                                  investigation.
     */
    public String changePassword(String retriever, String oldPassWord, String newPassWord) {
        if (!accManager.checkPassword(retriever, oldPassWord)){
            return "oldPasswordIncorrect"; // old password incorrect
        }

        if (accManager.checkPassword(retriever, newPassWord)){
            return "repeatingPassword"; // new password cannot be the same as the old password
        }

        if (passwordCalculator.getPasswordComplexityLevel(newPassWord).equals("Too Weak")){
            return "passwordTooWeak"; // new password is not complex enough
        }

        if (accManager.setPassword(retriever, newPassWord)){
            return "changeSuccessful";
        } else {
            return "changeUnsuccessful";
        }
        // if none of the above is satisfied, then able to successfully change password
    }

    /***
     * Creates randomly generated password that satisfies the complexity requirement of a "good" password.
     * @return A string representing a randomly generated password.
     */
    public String generateTempPassword(){
        return passwordCalculator.randomPasswordGenerator(true);
    }

    /**
     * Saves temporary password to a text file. This should be called when user requests to have a temporary password
     * stored in a text file for them to access.
     * @param retriever A String representing the User ID or Email.
     * @return A boolean value representing whether the saving process is successful.
     */
    public boolean updateAndSaveTempPassword(String retriever){
        String tempPassword = this.generateTempPassword();
        return accManager.setPassword(retriever, tempPassword) && accGateway.saveTempPassword(tempPassword);
    }

    /**
     * Reset the username.
     * @param retriever A String representing the User ID or Email.
     * @param userName A String representing the new username.
     */
    public void changeUserName(String retriever, String userName) {
        accManager.setUserName(retriever, userName);
        this.save();
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @param plannerId A planner id that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not.
     */
    public boolean setPlanner(String retriever, String  plannerId){
        boolean flag = this.accPlanner.setPlanners(retriever, plannerId);
        this.save();
        return flag;
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public List<Account> getAllAccount() {
        return this.accManager.getAllAccount();
    }

    /**
     * Return the account information of every account.
     * @return A string representation of all accounts stored in the program.
     */
    public String viewAllAccount(){
        List<Account> acc = getAllAccount();
        StringBuilder res = new StringBuilder();
        String sep = "\n=====================\n";
        for (Account i : acc){
            if (!isAdmin(i.getUserId()).equals("admin")) {
                res.append(i).append(sep);
            }
        }
        return res.toString();
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @return A List of Planner that owned by this account.
     */
    public List<String> getPlanners(String retriever){
        return accPlanner.getPlanners(retriever);
    }

    /**
     * Return the information of the Account with given id.
     * @param retriever A String representing information of account
     * @return A String representing the information of the account.
     */
    public String getInfo(String retriever) {
        return this.accManager.findAccount(retriever).toString();
    }

    /**
     * remove planner from the user's planners, add it to the trashcan
     * @param retriever A String representing information of account
     * @param plannerId A String that identifies the planner
     */
    public boolean removePlanner(String retriever, String plannerId) {
        boolean flag = this.accPlanner.removePlanner(retriever, plannerId);
        this.save();
        plannerController.save();
        return flag;
    }

    /**
     * suspend a user for x amount of days
     * @param retriever A String representing information of account
     * @param days The number of days user is suspended for
     */
    public void suspendUser(String retriever, long days){
        accManager.suspendUser(retriever, days);
        this.save();
    }

    /**
     * reverse the suspension of a user
     * @param retriever A String representing information of account
     */
    public void unSuspendUser(String retriever){
        accManager.unSuspendUser(retriever);
        this.save();
    }

    /**
     * get the suspension status of a user
     * @param retriever A String representing information of account
     */
    public boolean getSuspensionStatus(String retriever){
        return accManager.suspendedStatus(retriever);
    }

    /**
     * Return the strength of the password, Too Weak, Weak or Good.
     * @param password A String representing the password.
     * @return A String representing the complexity level of the password.
     */
    public String getPasswordStrength(String password) {
        return passwordCalculator.getPasswordComplexityLevel(password);
    }

    /**
     * let 2 users add friends
     * @param selfId id of the first user
     * @param friendId id of the second user
     * @return whether it's successful for 2 users to add friend
     */
    public boolean addFriend(String selfId, String friendId){
        if (selfId.equals(friendId)) {
            return false;
        } else {
            return accFriendManager.addFriend(selfId, friendId);
        }
    }

    /**
     * let 2 users stop being friends
     * @param selfId id of the first user
     * @param friendId id of the second user
     * @return whether it's successful for 2 users to delete friend
     */
    public boolean deleteFriend(String selfId, String friendId){
        return accFriendManager.deleteFriend(selfId, friendId);
    }

    /**
     * return the friends's info and their planners of a user
     * @param selfId the id of the user
     * @return String representation of friends' info and planners
     */
    public String getFriendsInfo(String selfId){
        List<String> friends = accFriendManager.getFriends(selfId);
        StringBuilder strFriends = new StringBuilder();
        strFriends.append("Friend List:\n").append("===============\n");
        for (String i : friends){
            strFriends.append(getInfo(i)).append("\n");
            strFriends.append("Available friends' planners:\n");
            for (String plannerId: this.getPlanners(i)) {
                if (!plannerController.getPrivacyStatus(Integer.parseInt(plannerId)).equals("private")) {
                    strFriends.append(plannerController.toString(Integer.parseInt(plannerId)));
                    strFriends.append("\n");
                }
            }
            strFriends.append("\n\n");
        }
        return strFriends.toString();
    }

    /**
     * return the trashed planners of a user
     * @param userId the user id of the user
     * @return A string representing the trashed planners of a user
     */
    public String getTrashPlanner(String userId){
        StringBuilder sb = new StringBuilder();
        for (String plannerId: accPlanner.getTrashPlanner(userId)) {
            sb.append(plannerController.toString(Integer.parseInt(plannerId))).append("\n");
        }
        return sb.toString();
    }

    /**
     * remove a planner from trashcan, add it back to all planners the user has
     * @param userId the id of the user
     * @param plannerId the String id of the planner
     * @return whether the removing and adding were successful
     */
    public boolean unTrashPlanner(String userId, String plannerId){
        return accPlanner.unTrashPlanner(userId, plannerId);
    }

    /**
     * Delete a planner permanently.
     * @param userId A String representing the user id.
     * @param plannerId A String representing the planner id.
     * @return whether the removing is successful
     */
    public boolean permanentTrashPlanner(String userId, String plannerId) {
        return accPlanner.permanentTrashPlanner(userId, plannerId);
    }
}
