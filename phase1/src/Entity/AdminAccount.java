package Entity;

public class AdminAccount  extends Account {

    public AdminAccount() {
        super(true);
    }

    @Override
    public Boolean isAdmin() {
        return true;
    }

    public String getInterfaceInfo(Integer stage) {

    }

}
