package Entity;

/**
 * A instance of this class represents a trial account in this application.
 */
public class TrialAccount extends Account{
    private final boolean isAdmin;

    public TrialAccount() {
        super();
        this.isAdmin = false;
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    /**
     * Return whether this account belongs to an admin.
     * @return Return false.
     */
    @Override
    public boolean getIsAdmin() {
        return  this.isAdmin;
    }

}
