package Entity;

public class AdminAccount  extends Account {
    private final boolean isAdmin;

    public AdminAccount() {
        super();
        this.isAdmin = true;
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
    }

    public AdminAccount(String email) {
        super();
        this.isAdmin = true;
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
        super.email = email;
    }

    @Override
    public boolean getIsAdmin() {
        return  this.isAdmin;
    }

}
