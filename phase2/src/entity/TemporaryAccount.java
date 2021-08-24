package entity;

import java.time.LocalDateTime;

/**
 * An instance of this class represents a temporary account in this application.
 */
public class TemporaryAccount extends UserAccount {

    private final LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs a TemporaryAccount.
     * @param email representing the email of this account.
     */
    public TemporaryAccount(String email) {
        super(email);
        super.accountType = "temporary";
        this.startDate = LocalDateTime.now();
    }

    /**
     * Return the role of this account: admin, regular, trial or temporary.
     * @return A string.
     */
    @Override
    public String getAccountType() {
        return  this.accountType;
    }

    /**
     * Return the available information of this account including username, id, and email.
     * @return A String that contains the user name, id, end date and email of this account.
     */
    @Override public String toString() {
        String result;

        result = "This is an Temporary Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email:" + this.email + "\n" +
                "This account will be automatically deleted after " + this.endDate.toString() + "\n";
        return result;
    }

    /**
     * @return A LocalDateTime represent the start time of this account.
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * @return A LocalDateTime represent the end time of this account.
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * Set the end time of this account.
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
