package use_case;

import entity.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;


/**
 * Stores and manages Accounts.
 */
public class AccountManager implements Serializable{
    private final Map<String, Account> idToAccount;
    private final Map<String, Account> emailToAccount;

    private final PasswordCalculator passwordCalculator = new PasswordCalculator();

    /**
     * Create an AccountManager Object.
     */
    public AccountManager(){
        this.emailToAccount = new HashMap<>();
        this.idToAccount = new HashMap<>();
        }

    /**
     * Change the userName of an account
     * @param retriever A String representing the user ID or Email.
     * @param userName the userName user enters that they want to change to
     */
    public void setUserName(String retriever, String userName){
        Account account = this.findAccount(retriever);
        account.setUserName(userName);
    }

    /***
     * Sets the password if the password meets the complexity criteria.
     * @param retriever A string representing the user ID or email.
     * @param password The password user wants to set for the account.
     * @return Boolean value indicating if the password is successfully set. If false, then the password provided is
     * not complex enough and therefore not set; if true, the password is complex enough and is set successfully.
     */
    public boolean setPassword(String retriever, String password){
        Account account = this.findAccount(retriever);
        if (!passwordCalculator.getPasswordComplexityLevel(password).equals("Too Weak")){
            account.setPassword(password);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add an Account object to the Maps.
     * @param acc An Account object we want to assign.
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
     * create a regular account, add it to all accounts and the Maps.
     * @return the userId of the new account.
     */
    private String createRegAcc(String email){
        UserAccount newAccount = new UserAccount(email);
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create an admin account, add it to all accounts and the Maps.
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
     * create a temporary account, add it to all accounts and the Maps.
     * @return the userId of the new account.
     */
    public String createTempAcc(String email){
        TemporaryAccount newAccount = new TemporaryAccount(email);
        LocalDateTime startDate = newAccount.getStartDate();
        long days = 30;

        newAccount.setEndDate(startDate.plusDays(days));

        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create an account which type is determined by user's email. if user's email contains "@admin.com",
     * we think that it would be an admin. If the user has no email (email is empty), then we create
     * trial account. Else, it would be a regular account.
     * @param email: the email that user enters
     * @return userId: the userId of the user so they can log in the future.
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
     * delete a temporary account from allAccount, emailToAccount, and idToAccount
     * when the end date has passed.
     * @param retriever A String representing the user ID or Email.
     * @return true if successfully removed account, false otherwise.
     */
    public boolean deleteTempAccount(String retriever){
        TemporaryAccount account = (TemporaryAccount) this.findAccount(retriever);
        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime endDate = account.getEndDate();

        boolean isAfter = todayDate.isAfter(endDate);

        if (this.getAllAccount().contains(account) && isAfter) {
            idToAccount.remove(account.getUserId());
            emailToAccount.remove(account.getEmail());
            return true; //Return true if the account object is deleted.
        } else {
            return false; //Return false if the account object is not deleted.
        }
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public List<Account> getAllAccount() {
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
     * Get the String representation of an account
     * @param retriever A String representing the User ID or Email.
     * @return The String representation of the account
     */
    public String toString(String retriever){
        Account acc = findAccount(retriever);
        return acc.toString();
    }

    /**
     * Suspend the given user for x amount of days
     * @param retriever A String representing the User ID or Email.
     */
    public void suspendUser(String retriever, long days){
        Account acc = findAccount(retriever);
        acc.setSuspendedTime(days);
    }

    /**
     * Reverse the suspension of a user
     * @param retriever A String representing the User ID or Email.
     */
    public void unSuspendUser(String retriever){
        Account acc = findAccount(retriever);
        acc.removeSuspendTime();
    }

    /**
     * Return whether a user is suspended or not
     * @param retriever A String representing the User ID or Email.
     */
    public boolean suspendedStatus(String retriever){
        Account acc = findAccount(retriever);
        return acc.getSuspendedTime().isAfter(LocalDateTime.now());
    }
}
