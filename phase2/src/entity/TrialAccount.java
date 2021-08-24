package entity;

/**
 * An instance of this class represents a trial account in this application.
 */
public class TrialAccount extends UserAccount{

    /**
     * Constructs a TrialAccount.
     */
    public TrialAccount() {
        super("");
        super.accountType = "trial";
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
     * @return A String that contains id of this account.
     */
    @Override public String toString() {
        return "This is a trial account with user id:" + this.userId;
    }

}
