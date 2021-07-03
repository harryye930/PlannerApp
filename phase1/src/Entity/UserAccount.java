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

    public List<Planner> getPlanner() {
        return this.planners;
    }

    /**
     * Add single plan to planners.
     * @param plan The plan that need to be added.
     * @return Return true if the plan is successfully added and false if not.
     */
    public boolean setPlanners(Planner plan) {
        if (!this.planners.contains(plan)) {
            this.planners.add(plan);
            return true;
        }
        return false;
    }

    /**
     *  Add a list of planners to planners.
     * @param planners the planners that need to be added.
     * @return Return true if any one of the planner is successfully added.
     */
    public boolean setPlanners(ArrayList<Planner> planners) {
        boolean flag = false;
        for (Planner planner : planners) {
            if (!this.planners.contains(planner)) {
                this.planners.add(planner);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean getIsAdmin() {
        return  !this.isAdmin;
    }

}
