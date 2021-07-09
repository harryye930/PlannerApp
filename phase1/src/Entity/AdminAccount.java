package Entity;

/**
 * A instance of this class represents a admin account in this application.
 */
public class AdminAccount  extends Account {
    private final boolean isAdmin;

    public AdminAccount() {
        super();
        this.isAdmin = true;
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
    }

    /**
     * @param email represent the email of this admin account.
     */
    public AdminAccount(String email) {
        super();
        this.isAdmin = true;
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
        super.email = email;
    }

    /**
     * Return whether this account belongs to an admin.
     * @return Return true.
     */
    @Override
    public boolean getIsAdmin() {
        return  this.isAdmin;
    }

}
