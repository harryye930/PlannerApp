package Entity;

import java.util.ArrayList;
import java.util.List;

public class UserAccount extends Account {
    private final boolean isAdmin;
    private ArrayList<Planner> planners = new ArrayList<>();

    public UserAccount() {
        super();
        this.isAdmin = false;
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    public UserAccount(List<Planner> planners) {
        super();
        this.isAdmin = false;
        this.planners = (ArrayList<Planner>) planners;
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    @Override
    public boolean getIsUser() {
        return  !this.isAdmin;
    }

}
