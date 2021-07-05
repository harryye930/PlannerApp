package Entity;

public class TrialAccount extends Account{
    private final boolean isAdmin;

    public TrialAccount() {
        super();
        this.isAdmin = false;
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    @Override
    public boolean getIsAdmin() {
        return  this.isAdmin;
    }

}
