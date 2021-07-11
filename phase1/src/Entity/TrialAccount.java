package Entity;

/**
 * A instance of this class represents a trial account in this application.
 */
public class TrialAccount extends Account{

    public TrialAccount() {
        super();
        this.isAdmin = "trial";
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    /**
     * Return whether this account belongs to an admin.
     * @return Return false.
     */
    @Override
    public String getIsAdmin() {
        return  this.isAdmin;
    }

}
