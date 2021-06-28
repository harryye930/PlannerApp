package Entity;

public class AdminAccount extends Account{
    private boolean isAdmin;

    public AdminAccount() {
        super();
        this.isAdmin = true;
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
    }

    @Override
    public boolean getIsAdmin() {
        return this.isAdmin;
    }
}
