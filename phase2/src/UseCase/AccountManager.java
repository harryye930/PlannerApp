package UseCase;

import Entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages Accounts.
 */
public class AccountManager implements Serializable{
    private final HashMap<String, Account> idToAccount;
    private final HashMap<String, Account> emailToAccount;

    /**
     * Create an AccountManager Object.
     */
    public AccountManager(){
        this.emailToAccount = new HashMap<>();
        this.idToAccount = new HashMap<>();
        }

    /**
     * change the userName of an account
     * @param retriever A String representing the user ID or Email.
     * @param userName the userName user enters that they want to change to
     */
    public void setUserName(String retriever, String userName){
        Account account = this.findAccount(retriever);
        account.setUserName(userName);
    }

    /**
     * set the password. User can change their password to something different from the previous one.
     * @param retriever A String representing the user ID or Email.
     * @param newPassword the new password that user want to change to.
     * @return true if successfully changed password, false otherwise.
     */
    public boolean setPassword(String retriever, String newPassword){
        Account account = this.findAccount(retriever);
        if (account.getPassword() == null) {
            account.setPassword(newPassword);
            return true;
        }
        // users need to enter the correct password to set a new password (just like iPhone)
        else if ((!account.getPassword().equals(newPassword))){
            account.setPassword(newPassword);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add an Account object to the HashMaps.
     * @param acc A Account object we want to assign.
     */
    public void addAccount(Account acc) {
        this.idToAccount.put(acc.getUserId(), acc);
        this.emailToAccount.put(acc.getEmail(), acc);
    }

    /**
     * return whether user's entered password is same as the password of the account
     * @param retriever A String representing the user ID or Email.
     * @param enteredPassword the password that user enters
     * @return true if user's entered password is correct, false otherwise
     */
    public boolean checkPassword(String retriever, String enteredPassword){
        Account account = this.findAccount(retriever);
        return account.getPassword().equals(enteredPassword);
    }

    /**
     * find an account by email or userId. Since both email and id are String, we differentiate
     * them by the keyword "@". If the input contains @, then it is email. else, it is userId.
     * @param userInput the input from user. May be an email or an userId.
     * @return the account if the account is found, null if otherwise.
     */
    public Account findAccount(String userInput){
        Account res = emailToAccount.getOrDefault(userInput, null);
        if (res == null) {
            return idToAccount.getOrDefault(userInput, null);
        } else {
            return res;
        }
    }

    /**
     * create a regular account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    private String createRegAcc(String email){
        UserAccount newAccount = new UserAccount(email);
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a admin account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    private String createAdminAcc(String email){
        AdminAccount newAccount = new AdminAccount(email);
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a trial account. The trial account is not added to emailToAccount.
     * @return the userId of the new account.
     */
    private String createTrialAcc(){
        TrialAccount newAccount = new TrialAccount();
        newAccount.setEmail(newAccount.getUserId());
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create an account which type is determined by user's email. if user's email contains "@admin.com",
     * we think that it would be an admin. If the user has no email (email is empty), then we create
     * trial account. Else, it would be a regular account.
     * @param email: the email that user enters
     * @return userId: the userId of the user so they can login in the future.
     */
    public String createAccount(String email){
        String userId;
        if (email.contains("@admin.com")){
            userId = createAdminAcc(email);
        } else if (email.equals("")){
            userId = createTrialAcc();
        } else {
            userId = createRegAcc(email);
        }
        return userId;
    }

    /**
     * remove an account from allAccount, emailToAccount, and idToAccount. If successfully
     * removed, return true, else return false.
     * @param retriever A String representing the user ID or Email.
     * @return true if successfully removed account, false otherwise.
     */
    public boolean removeAccount(String retriever){
        Account account = this.findAccount(retriever);
        if (this.getAllAccount().contains(account)) {
            idToAccount.remove(account.getUserId());
            emailToAccount.remove(account.getEmail());
            return true; //Return true if the account object is in the collection.
        } else {
            return false; //Return false if the account object is not in the collection.
        }
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public ArrayList<Account> getAllAccount() {
        return new ArrayList<>(this.idToAccount.values());
    }

    /**
     * find the role of the account
     * @param retriever A String representing the user ID or Email.
     * @return the String that represents the role of the account ("regular", "admin", or "trial").
     */
    public String checkAccountRole(String retriever){
        Account account = this.findAccount(retriever);
        return account.getAccountType();
    }

    /**
     * Add new planner to a given account. return true if any one of the planners is added.
     * @param retriever A String representing the user ID or Email.
     * @param plannerIds An array list of Planner id that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     */
    public boolean setPlanners(String retriever, ArrayList<String > plannerIds){
        Account account = this.findAccount(retriever);
        if (account.getAccountType().equals("regular")){
            return ((UserAccount) account).setPlanners(plannerIds);
        } else {
            return false;
        }
    }

    /**
     * Add new planner to a given account. return true if the planner is added.
     * @param retriever A String representing the user ID or Email.
     * @param plannerId An Planner id that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     */
    public boolean setPlanners(String retriever, String  plannerId){
        Account account = this.findAccount(retriever);
        if (account.getAccountType().equals("regular")){
            return ((UserAccount) account).setPlanners(plannerId);
        } else {
            return false;
        }
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @return An ArrayList of Planner that owned by this account, if the account is regular. Else, return
     * null.
     */
    public ArrayList<String > getPlanners(String retriever) {
        if (this.findAccount(retriever).getAccountType().equals("regular")){
            return ((UserAccount) findAccount(retriever)).getPlanner();
        } else {
            return null;
        }
    }

    public void removePlanner(String retriever, String plannerId) {
        UserAccount acc =(UserAccount) this.findAccount(retriever);
        acc.removePlanner(plannerId);
    }

    public String toString(String retriever){
        Account acc = findAccount(retriever);
        return acc.toString();
    }

    public void suspendUser(String retriever, int time){
        Account acc = findAccount(retriever);
        acc.setSuspendedTime(time);
    }
}
