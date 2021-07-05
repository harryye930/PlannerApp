package UseCase;

import Entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountManager implements Serializable {
    private HashMap<String, Account> idToAccount;
    private ArrayList<Account> allAccount;

    public AccountManager(){
        idToAccount = new HashMap<>();
        allAccount = new ArrayList<>();
    }

    /**
     * change the userName of an account
     * @param account the target account to change userName
     * @param userName the userName user enters that they want to change to
     */
    public void setUserName(Account account, String userName){
        account.setUserName(userName);
    }

    /**
     * set the password. User need to enter a password that is correct first, then they can change their
     * password to something different from the previous one.
     * @param account the account that the password is changed
     * @param enteredPassword the user's entered password, should be the same as the original password
     * @param newPassword the new password that user want to change to.
     * @return true if successfully changed password, false otherwise.
     */
    public boolean setPassword(Account account, String enteredPassword, String newPassword){
        // users need to enter the correct password to set a new password (just like iPhone)
        if (enteredPassword.equals(account.getPassword()) && (!account.getPassword().equals(newPassword))){
            account.setPassword(newPassword);
            return true;
        } else {
            return false;
        }
    }

    /**
     * return whether user's entered password is same as the password of the account
     * @param account the account that user wants to login to
     * @param enteredPassword the password that user enters
     * @return true if user's entered password is correct, false otherwise
     */
    public boolean checkPassword(Account account, String enteredPassword){
        return account.getPassword().equals(enteredPassword);
    }

    /**
     * Find the corresponding account of the given userId. If the userId is not recorded in idToAccount,
     * the method will return null.
     * @param userId: the userId as key to find the account
     * @return account: the account corresponding to the userId
     */
    public Account findAccount(String userId){
        return idToAccount.getOrDefault(userId, null);
    }

    /**
     * create a regular account
     * @return the userId of the new account.
     */
    private String createRegAcc(String email){
        UserAccount newAccount = new UserAccount(email);
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a admin account
     * @return the userId of the new account.
     */
    private String createAdminAcc(String email){
        AdminAccount newAccount = new AdminAccount(email);
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a trial account
     * @return the userId of the new account.
     */
    private String createTrialAcc(){
        TrialAccount newAccount = new TrialAccount();
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
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
     * remove an account from allAccount and idToAccount. If successfully removed, return true, else
     * return false.
     * @param account: the account want to be removed
     * @return true if successfully removed account, false otherwise.
     */
    public boolean removeAccount(Account account){
        if (this.allAccount.contains(account)) {
            this.allAccount.remove(account);
            this.idToAccount.remove(account.getUserId());
            return true; //Return true if the account object is in the collection.
        } else {
            return false; //Return false if the account object is not in the collection.
        }
    }

    /**
     * return whether the account is admin or not.
     * @param account the target account to check role
     * @return true if the account is admin, false otherwise.
     */
    public boolean accountRole(Account account){
        return account.getIsAdmin();
    }

}
