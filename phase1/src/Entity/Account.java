package entity;

import UseCase.AccountManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SplittableRandom;
import java.io.Serializable;

/**
 * A instance of this class represent an account in this application.
 */
public abstract class Account implements Serializable {
    protected String accountType;
    protected String userName;
    protected String userId;
    protected String email;
    protected String password;

    public Account() {
        this.accountType = "regular";
        this.userId = ((Integer) this.hashCode()).toString();
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
     * @return A boolean value indicating that whether this account id admin.
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

