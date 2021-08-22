package entity;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A instance of this class represent an account in this application.
 */
public abstract class Account implements Serializable {
    protected String accountType;
    protected String userName;
    protected String userId;
    protected String email;
    protected String password;
    protected LocalDateTime suspendedTime;
    protected ArrayList<Account> friends;
    protected HashMap<String, ArrayList<String>> mailbox;

    public Account() {
        this.accountType = "regular";
        this.userId = ((Integer) this.hashCode()).toString();
        suspendedTime = LocalDateTime.now();
        friends = new ArrayList<>();
        mailbox = new HashMap<>();
    }

    /**
     * suspendedTime is the variable that determines how long (in days) the user need to wait for them
     * to be able to login again. Set the suspended time with given integer
     * @param time the time that set to be suspended (in days).
     */
    public void setSuspendedTime(long time){
        suspendedTime = suspendedTime.plusDays(time);
    }

    public void removeSuspendTime(){
        suspendedTime = LocalDateTime.now();
    }

    public LocalDateTime getSuspendedTime(){
        return suspendedTime;
    }

    /**
     * Return the available information of this account including username, id, and email.
     * @return A String that contains the user name, id and email of this account.
     */
    @Override
    public String toString() {
        throw new NotImplementedException();
    }

    /**
     * return the friends of the user.
     * @return an ArrayList of Account representing the friends of the user.
     */
    public ArrayList<Account> getFriends() {
        return friends;
    }

    /**
     * return whether the given account is a friend of the user or not
     * @param acc Account of the friend
     * @return boolean, whether the given account is a friend of the user or not
     */
    public boolean findFriend(Account acc){
        return friends.contains(acc);
    }

    /**
     * add the given Account to the ArrayList of friends
     * @param acc the account to be added
     */
    public void addFriend(Account acc){
        friends.add(acc);
    }

    /**
     * delete the friend from the ArrayList
     * @param acc the given Account, representing the friend
     * @return whether the change is made in 'friends' or not
     */
    public boolean removeFriend(Account acc){
        if (findFriend(acc)){
            friends.remove(acc);
            return true;
        } else {
            return false;
        }
    }

    /**
     * return the mailbox of the user
     * @return HashMap<String, ArrayList<String>>, representing the mailbox
     */
    public HashMap<String, ArrayList<String>> getMailbox() {
        return mailbox;
    }

    /**
     * add the message to mailbox
     * @param id the String of userId representing the sender
     * @param mail the String representing thr message
     */
    public void receiveMail(String id, String mail){
        mailbox.get(id).add(mail);
    }

    /**
     * see all mail in the mailbox.
     * @return the String representation of the mailbox
     */
    public String seeAllMail(){
        return mailbox.toString();
    }

    /**
     * see all mails of one specific sender
     * @param userId the sender's user id
     * @return the string representation of all mails of the sender.
     */
    public String seeOnesMail(String userId){
        return mailbox.get(userId).toString();
    }

    /**
     * @return A String that represent the email of this account.
     */
    public String  getEmail() {
        return this.email;
    }

    /**
     * @param email representing the email of this account.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return A String that represent the user ID.
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @return A String that represent the user name.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName represent the user name of this account.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return A String represent the password of this account.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password Represent the password of this account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the type of this account.
     */
    public String getAccountType() {
        return this.accountType;
    }

    private int find(String[] lst, String item) {
        // return the index of the item in the list, -1 if not found.
        for (int i = 0; i < lst.length; i++) {
            if (lst[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }
}

