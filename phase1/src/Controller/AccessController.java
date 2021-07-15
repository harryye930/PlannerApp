package Controller;

import Entity.Account;
import Exceptions.WrongAccTypeException;
import UseCase.*;
import Entity.*;

import java.util.ArrayList;

/**
 * Account accessibility controller.
 */
public class AccessController {
    private AccountManager accManager;

    public AccessController(){
        accManager = new AccountManager();
    }

    /**
     * Check whether the given password is correct to to account.
     * @param retriever A String representing User ID or Email.
     * @param passWord A String representing password.
     * @return A boolean value representing whether the password is correct or not.
     */
    public boolean logIn(String retriever, String passWord) {
        return accManager.findAccount(retriever).getPassword().equals(passWord);
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
            return accManager.checkAccountRole(acc);
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
        accManager.setPassword(accManager.findAccount(email), passWord);
        accManager.setUserName(accManager.findAccount(email), userName);
        return id;
    }

    /**
     * Reset the password of given account, user need to enter the correct original password to proceed.
     * @param retriever A String representing the User ID or Email.
     * @param oldPassWord A String representing the original password.
     * @param newPassWord A String representing the new password.
     * @return A boolean representing whether the password is successfully changed or not.
     */
    public boolean changePassword(String retriever, String oldPassWord, String newPassWord) {
        Account account = accManager.findAccount(retriever);
        if (accManager.checkPassword(account, oldPassWord)) {
            return accManager.setPassword(account, newPassWord);
        } else {
            return false;
        }
    }

    /**
     * Reset the user name.
     * @param retriever A String representing the User ID or Email.
     * @param userName A String representing the new user name.
     */
    public void changeUserName(String retriever, String userName) {
        Account account = accManager.findAccount(retriever);
        accManager.setUserName(account, userName);
    }

    /**
     * Remove an account. User can only remove an account after they logged in or when a trial account logged out.
     * @param retriever A String representing the User ID or Email.
     * @return A boolean value representing whether the remove operation is successful or not.
     */
    public boolean removeAccount(String retriever) {
        return accManager.removeAccount(accManager.findAccount(retriever));
    }

    /**
     * Log out the account, delete the account if it is a trial account.
     * @param retriever A String representing the User ID or Email.
     * @return A boolean representing whether the log out is success.
     */
    public boolean logOut(String retriever){
        if(accManager.findAccount(retriever).getAccountType().equals("trial")){
            this.removeAccount(retriever);
        }
        return true;
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @param planner A planner object that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not..
     */
    public boolean setPlanner(String retriever, Planner planner){
        return this.accManager.setPlanners(accManager.findAccount(retriever), planner);
    }

    /**
     * Add new planner to a given account. return true if any one of the planners is added.
     * @param retriever A String representing the User ID or Email.
     * @param planner A planner object that need to be added to the account.
     * @return A boolean value representing whether the adding is successful or not..
     */
    public boolean setPlanner(String retriever, ArrayList<Planner> planner){
        return this.accManager.setPlanners(accManager.findAccount(retriever), planner);
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public ArrayList<Account> getAllAccount() {
        return this.accManager.getAllAccount();
    }

    /**
     *
     * @param retriever A String representing the User ID or Email.
     * @return An ArrayList of Planner that owned by this account.
     */
    public ArrayList<Planner> getPlanners(String retriever){
        return accManager.getPlanners(retriever);
    }

}
