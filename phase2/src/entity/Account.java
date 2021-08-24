package entity;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * An instance of this class represents an account in this application.
 */
public abstract class Account implements Serializable {
    /**
     * accountType: Type of Account.
     * userName: Username associated with the Account.
     * userId: User ID associated with the Account.
     * email: Email associated with the Account.
     * password: Password associated with the Account.
     * suspendedTime: Time this Account is suspended for.
     * friends: List of Accounts that are this Account's friends.
     */
    protected String accountType;
    protected String userName;
    protected String userId;
    protected String email;
    protected String password;
    protected LocalDateTime suspendedTime;
    protected List<Account> friends;

    /**
     * Constructs an Account object.
     */
    public Account() {
        this.accountType = "regular";
        this.userId = ((Integer)Math.abs(this.hashCode())).toString();
        suspendedTime = LocalDateTime.now();
        friends = new ArrayList<>();
    }

    /**
     * suspendedTime is the variable that determines how long (in days) the user needs to wait
     * to be able to login again.
     * Set the suspended time to time.
     * @param time The time that was set for a user to be suspended (in days).
     */
    public void setSuspendedTime(long time){
        suspendedTime = suspendedTime.plusDays(time);
    }

    /**
     * Resets the suspendedTime.
     */
    public void removeSuspendTime(){
        suspendedTime = LocalDateTime.now();
    }

    /**
     * @return The suspendedTime.
     */
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
     * @return a List of Account representing the friends of the user.
     */
    public List<Account> getFriends() {
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
     * add the given Account to the List of friends
     * @param acc the account to be added
     */
    public void addFriend(Account acc){
        friends.add(acc);
    }

    /**
     * delete the friend from the List of friends
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
     * @return A String that represents the email of this account.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email A String representing the email of this account.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return A String that represents the user ID.
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @return A String that represents the username.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName A String representing the username of this account.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return A String that represents the password of this account.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password A String representing the password of this account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return A String representing the type of this account.
     */
    public String getAccountType() {
        return this.accountType;
    }
}

