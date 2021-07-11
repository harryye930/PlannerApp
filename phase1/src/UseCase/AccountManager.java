package UseCase;

import Entity.*;
import Exceptions.WrongAccTypeException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class AccountManager implements Serializable{
    private HashMap<String, Account> idToAccount;
    private ArrayList<Account> allAccount;
    private HashMap<String, Account> emailToAccount;

    private final String filePath = "phase1/src/UserData/";
    private String idMapPath = "phase1/src/UserData/idMap.ser";
    private String emailMapPath = "phase1/src/UserData/emailMap.ser";

    public AccountManager(){
        this.allAccount = new ArrayList<>();

        try {
            this.idToAccount = this.readFile(idMapPath);
            this.emailToAccount = this.readFile(emailMapPath);

            if (this.idToAccount == null) {
                System.out.println(11);
                this.idToAccount = new HashMap<>();
            }
            if (this.emailToAccount == null) {
                this.emailToAccount = new HashMap<>();
            }
            for (String id: this.idToAccount.keySet()) {
                this.readAccount(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.writeAccount(email);
        this.writeObject(this.idToAccount, this.idMapPath);
        this.writeObject(this.emailToAccount, this.emailMapPath);
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
            this.deleteAccount(account.getUserId()); // Delete the .ser file of this account.
            allAccount.remove(account);
            idToAccount.remove(account.getUserId());
            emailToAccount.remove(account.getEmail());
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
        return this.allAccount;
    }

    /**
     * find the role of the account
     * @param account the account that is to be checked
     * @return the String that represents the role of the account ("regular", "admin", or "trial").
     */
    public String checkAccountRole(Account account){
        return account.getIsAdmin();
    }

    /**
     * Add new planner to a given account. return true if any one of the planners is added.
     * @param account An Account object that need to be read.
     * @param planner An array list of Planners that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     * @throws WrongAccTypeException
     */
    public boolean setPlanners(Account account, ArrayList<Planner> planner) throws WrongAccTypeException{
        try{
            ((UserAccount)account).setPlanners(planner);
            return true;
        }catch (Exception e){
            throw new WrongAccTypeException();
        }
    }

    /**
     * Add new planner to a given account. return true if the planner is added.
     * @param account An Account object that need to be read.
     * @param planner An array list of Planners that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     * @throws WrongAccTypeException Wrong type of Account subclass.
     */
    public boolean setPlanners(Account account, Planner planner) throws WrongAccTypeException{
        try{
            ((UserAccount)account).setPlanners(planner);
            return true;
        }catch (Exception e){
            throw new WrongAccTypeException();
        }
    }

    /**
     * Write an object into a .ser file.
     * @param retriever A String representing User ID or email.
     */
    private void writeAccount(String retriever) {
        Account acc = this.findAccount(retriever);
        String id = acc.getUserId();
        String thisPath = this.filePath + id + ".ser";

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(thisPath));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(acc);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Return a Account object by reading the file corresponding to the given retriever.
     * @param retriever A String representing the user ID or Email of which account that we want.
     * @return An Account object with given email or ID.
     */
    private Object readAccount(String retriever) {
        Account acc = this.findAccount(retriever);
        String id = acc.getUserId();
        String fileName = id + ".ser";
        String fp = this.filePath + fileName;
        try {
            FileInputStream fileIn = new FileInputStream(fp);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Delete an account by its ID ot Email
     * @param retriever A string representing Usr ID or email to find the account.
     * @return A boolean value representing whether the deleting is successful or not.
     */
    public boolean deleteAccount(String retriever) {
        Account acc = this.findAccount(retriever);
        String id = acc.getUserId();
        String fileName = id + ".ser";
        String fp = this.filePath + fileName;

        File accFile = new File(fp);
        return accFile.delete();
    }

    private HashMap<String, Account> readFile(String filePath) throws IOException {
        File f = new File(filePath);
        return this.readObject(filePath);
    }

    private HashMap<String, Account> readObject(String filepath) throws IOException {
        try {
            InputStream file = new FileInputStream(filepath);
            InputStream buffer = new BufferedInputStream(file);
            if (buffer.available() == 0){
                return null;
            }
            ObjectInput input = new ObjectInputStream(buffer);
            HashMap<String, Account> res = (HashMap<String, Account>) input.readObject();
            input.close();

            return res;

        } catch (FileNotFoundException | ClassNotFoundException e) {
            try {
                File newFile = new File(filepath);
                newFile.createNewFile();
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    private void writeObject(HashMap<String, Account> obj, String fp) {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(fp));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AccountManager am = new AccountManager();
        System.out.println(am.idToAccount.toString());
    }
}
