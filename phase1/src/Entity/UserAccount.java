package Entity;

public class UserAccount extends Account {

    public UserAccount() {
        super(false);
    }

    @Override
    public Boolean isUser() {
        return true;
    }

    public String getInterfaceInfo(Integer stage) {

    }

}
