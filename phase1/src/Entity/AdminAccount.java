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
    }

    /**
     * Return whether this account belongs to an admin.
     * @return Return true.
     */
    @Override
    public String getIsAdmin() {
        return  this.isAdmin;
    }

    @Override
    public String toString() {
        String result;
        String temp;

        temp = "Admin";

        result = "This is an " + temp + " Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email" + this.email + "\n";
        return result;
    }
}
