package UseCase;

import Entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountManager implements Serializable {
    private HashMap<String, Account> idToAccount;
    private ArrayList<Account> allAccount;
    private HashMap<String, Account> emailToAccount;

    public AccountManager(){
        idToAccount = new HashMap<>();
        allAccount = new ArrayList<>();
        emailToAccount = new HashMap<>();
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
     * set the password. User can change their password to something different from the previous one.
     * @param account the account that the password is changed
     * @param newPassword the new password that user want to change to.
     * @return true if successfully changed password, false otherwise.
     */
    public boolean setPassword(Account account, String newPassword){
        // users need to enter the correct password to set a new password (just like iPhone)
        if ((!account.getPassword().equals(newPassword))){
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
     * find an account by email or userId. Since both email and id are String, we differentiate
     * them by the keyword "@". If the input contains @, then it is email. else, it is userId.
     * @param userInput the input from user. May be an email or an userId.
     * @return the account if the account is found, null if otherwise.
     */
    public Account findAccount(String userInput){
        if (userInput.contains("@")){
            return emailToAccount.getOrDefault(userInput, null);
        } else {
            return idToAccount.getOrDefault(userInput, null);
        }
    }

    /**
     * create a regular account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    private String createRegAcc(String email){
        UserAccount newAccount = new UserAccount(email);
        emailToAccount.put(email, newAccount);
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a admin account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    private String createAdminAcc(String email){
        AdminAccount newAccount = new AdminAccount(email);
        emailToAccount.put(email, newAccount);
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a trial account. The trial account is not added to emailToAccount.
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
     * remove an account from allAccount, emailToAccount, and idToAccount. If successfully
     * removed, return true, else return false.
     * @param account: the account want to be removed
     * @return true if successfully removed account, false otherwise.
     */
    public boolean removeAccount(Account account){
        if (allAccount.contains(account)) {
            allAccount.remove(account);
            idToAccount.remove(account.getUserId());
            if (emailToAccount.containsKey(account.getEmail())){
                emailToAccount.remove(account.getEmail());
            }
            return true; //Return true if the account object is in the collection.
        } else {
            return false; //Return false if the account object is not in the collection.
        }
    }

    /**
     * log the user in. If the user uses email to login and the email is not found, create
     * new account for them and return false. If they use userid and the id is not found, return false. If the
     * account exists but the user's entered password is not correct, return false. else, return true.
     * @param userInput user's input to login. may be an email or an userId.
     * @param password user's entered password for their account.
     * @return true if successfully logged in, false other wise.
     */
    public boolean login(String userInput, String password){
        Account acc = findAccount(userInput);
        if (acc == null && (userInput.contains("@") || userInput.equals(""))) {
            createAccount(userInput);
            return false;
        } else if (acc == null){
            return false;
        } else {
            return checkPassword(acc, password);
        }
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public ArrayList<Account> getAllAccount() {
        return allAccount;
    }

    /**
     * find the role of the account
     * @param account the account that is to be checked
     * @return the String that represents the role of the account ("regular", "admin", or "trial").
     */
    public String checkAccountRole(Account account){
        return account.getIsAdmin();
    }
}
