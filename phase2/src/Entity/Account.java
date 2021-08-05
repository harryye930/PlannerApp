package Entity;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SplittableRandom;
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

    public ArrayList<Account> getFriends() {
        return friends;
    }

    public boolean findFriend(Account acc){
        return friends.contains(acc);
    }

    public void addFriend(Account acc){
        friends.add(acc);
    }

    public boolean removeFriend(Account acc){
        if (friends.contains(acc)){
            friends.remove(acc);
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, ArrayList<String>> getMailbox() {
        return mailbox;
    }

    public void receiveMail(String id, String mail){
        mailbox.get(id).add(mail);
    }

    public String seeAllMail(){
        return mailbox.toString();
    }

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

