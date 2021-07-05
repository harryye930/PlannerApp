package Entity;

public class AdminAccount  extends Account {
    private final boolean isAdmin;
    private final String email;

    public AdminAccount(String email) {
        super();
        this.isAdmin = true;
        this.email = email;
        super.file_path = "phase1/src/Entity/adminInfo.csv";
        super.read_csv(super.file_path);
    }

    @Override
    public boolean getIsAdmin() {
        return  this.isAdmin;
    }

}
