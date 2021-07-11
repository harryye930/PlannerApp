package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A instance of this class represents a user account in this application.
 */
public class UserAccount extends Account {

    private String email;
    private ArrayList<Planner> planners = new ArrayList<>();

    public UserAccount() {
        super();
        this.isAdmin = "regular";
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    /**
     * @param email represent the email of this user account.
     */
    public UserAccount(String email) {
        super();
        this.isAdmin = "regular";
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
        super.email = email;
    }

    /**
     * @param planners represent a list of planners that belong to this user.
     */
    public UserAccount(List<Planner> planners) {
        super();
        this.isAdmin = "regular";
        this.planners = (ArrayList<Planner>) planners;
        super.file_path = "phase1/src/Entity/regularInfo.csv";
        super.read_csv(super.file_path);
    }

    /**
     * @return A List containing all planners of this user.
     */
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

    /**
     * Return whether this account belongs to an admin.
     * @return Return false.
     */
    @Override
    public String getIsAdmin() {
        return  this.isAdmin;
    }

    @Override public String toString() {
        String result;
        String temp;

        temp = "Regular";

        result = "This is an " + temp + " Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email" + this.email + "\n";
        return result;
    }
}
