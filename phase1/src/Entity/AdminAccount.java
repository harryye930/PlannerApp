package Entity;

/**
 * A instance of this class represents a admin account in this application.
 */
public class AdminAccount  extends Account {

    public AdminAccount() {
        super();
        super.isAdmin = "admin";
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
    }

    /**
     * @param email represent the email of this admin account.
     */
    public AdminAccount(String email) {
        super();
        this.isAdmin = "admin";
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
        super.email = email;
        super.userId = ((Integer) (email).hashCode()).toString();
    }

    /**
     * Return the role of this account: admin, regular or trial.
     * @return Return false.
     */
    @Override
    public String getIsAdmin() {
        return  this.isAdmin;
    }

    /**
     * Return the available information of this account including username, id, and email.
     * @return A String that contains the user name, id and email of this account.
     */
    @Override
    public String toString() {
        String result;

        result = "This is an Admin Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email" + this.email + "\n";
        return result;
    }
}
