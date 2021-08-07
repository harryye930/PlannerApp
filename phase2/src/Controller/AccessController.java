package Controller;

import Entity.Account;
import Gateway.AccountGateway;
import UseCase.*;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Account accessibility controller.
 */
public class AccessController{
    private final AccountManager accManager;
    private final AccountGateway accGateway;

    private TemplateController templateController;
    private PlannerController plannerController;

    private String currUserId;

    public AccessController(){
        accManager = new AccountManager();
        accGateway = new AccountGateway(accManager);
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
     * Check whether a account is an admin account or not.
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
     * @param userName A String representing the user name of this account.
     * @param passWord A String representing the password of this account.
     * @return A String representing the unique user ID of this account.
     */
    public String createAccount(String email, String userName, String passWord) {
        String id = accManager.createAccount(email);
        accManager.setPassword(id, passWord);
        accManager.setUserName(id, userName);
        return id;
    }

    /**
     * Create a new temporary account.
     * @param email A String representing the email of this account.
     * @param userName A String representing the user name of this account.
     * @param passWord A String representing the password of this account.
     * @return A String representing the unique user ID of this account.
     */
    public String createTemporaryAccount(String email, String userName, String passWord) {
        String id = accManager.createTempAcc(email);
        accManager.setPassword(id, passWord);
        accManager.setUserName(id, userName);
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

        if (!accManager.checkPasswordComplexity(newPassWord)){
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
        return accManager.generatePassword("good");
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
     * Reset the user name.
     * @param retriever A String representing the User ID or Email.
     * @param userName A String representing the new user name.
     */
    public void changeUserName(String retriever, String userName) {
        accManager.setUserName(retriever, userName);
    }


    /**
     * Remove an account. User can only remove an account after they logged in or when a trial account logged out.
     * @param retriever A String representing the User ID or Email.
     * @return A boolean value representing whether the remove operation is successful or not.
     */
    public boolean removeAccount(String retriever) {
        String accountType = accManager.findAccount(retriever).getAccountType();
        if(accountType.equals("temporary")){
            return accManager.deleteTempAccount(retriever);
        }
        else {
            return accManager.removeAccount(retriever);
        }
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @param plannerId A planner id that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not..
     */
    public boolean setPlanner(String retriever, String  plannerId){
        return this.accManager.setPlanners(retriever, plannerId);
    }

    /**
     * Add new planner to a given account. return true if any one of the planners is added.
     * @param retriever A String representing the User ID or Email.
     * @param planner A planner id that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not..
     */
    public boolean setPlanner(String retriever, ArrayList<String > planner){
        return this.accManager.setPlanners(retriever, planner);
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public ArrayList<Account> getAllAccount() {
        return this.accManager.getAllAccount();
    }

    /**
     * Return the account information of every account.
     * @return An ArrayList of
     */
    public ArrayList<String> viewAllAccount(){
        ArrayList<Account> acc = getAllAccount();
        ArrayList<String> info = new ArrayList<>();
        for (Account i : acc){
            info.add(i.toString());
        }
        return info;
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @return An ArrayList of Planner that owned by this account.
     */
    public ArrayList<String> getPlanners(String retriever){
        return accManager.getPlanners(retriever);
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
     * remove planner from the user's planners
     * @param retriever A String representing information of account
     * @param plannerId A String that identifies the planner
     */
    public void removePlanner(String retriever, String plannerId) {
        this.accManager.removePlanner(retriever, plannerId);
    }

    /**
     * suspend a user for x amount of days
     * @param retriever A String representing information of account
     * @param days The number of days user is suspended for
     */
    public void suspendUser(String retriever, long days){
        accManager.suspendUser(retriever, days);
    }

    /**
     * reverse the suspension of a user
     * @param retriever A String representing information of account
     */
    public void unSuspendUser(String retriever){
        accManager.unSuspendUser(retriever);
    }

    /**
     * get the suspension status of a user
     * @param retriever A String representing information of account
     */
    public boolean getSuspensionStatus(String retriever){
        return accManager.suspendedStatus(retriever);
    }

    public boolean addFriend(String selfId, String friendId){
        return accManager.addFriend(selfId, friendId);
    }

    public boolean deleteFriend(String selfId, String friendId){
        return accManager.deleteFriend(selfId, friendId);
    }

    public String getFriends(String selfId){
        ArrayList<String> friends = accManager.getFriends(selfId);
        StringBuilder strFriends = new StringBuilder();
        for (String i : friends){
            strFriends.append("\n").append(i);
        }
        return strFriends.toString();
    }

    public void sendMail(String senderId, String revieveId, String mail){
        accManager.sendMail(senderId, revieveId,mail);
    }

    public HashMap<String, ArrayList<String>> getMailbox(String userId){
        return accManager.getMailbox(userId);
    }

    public String seeOnesMail(String selfId, String senderId){
        return accManager.seeOnesMail(selfId, senderId);
    }

    public String seeAllMail(String userId){
        return accManager.seeAllMail(userId);
    }

    public ArrayList<String> getTrashPlanner(String userId){
        return accManager.getTrashPlanner(userId);
    }

    public boolean unTrashPlanner(String userId, String plannerId){
        return accManager.unTrashPlanner(userId, plannerId);
    }
}
