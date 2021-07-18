package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A instance of this class represents a user account in this application.
 */
public class UserAccount extends Account {

    private ArrayList<String > planners = new ArrayList<>();

    /**
     * @param email represent the email of this user account.
     */
    public UserAccount(String email) {
        super();
        this.accountType = "regular";
        super.email = email;
        super.userId = ((Integer) (email).hashCode()).toString();
    }

    /**
     * @param planners represent a list of planners that belong to this user.
     */
    public UserAccount(List<String> planners) {
        super();
        this.accountType = "regular";
        this.planners = (ArrayList<String>) planners;
    }

    /**
     * @return A List containing all planners of this user.
     */
    public ArrayList<String> getPlanner() {
        return this.planners;
    }

    /**
     * Add single plan to planners.
     * @param plannerId The planner id that need to be added.
     * @return Return true if the plan is successfully added and false if not.
     */
    public boolean setPlanners(String  plannerId) {
        if (!this.planners.contains(plannerId)) {
            this.planners.add(plannerId);
            return true;
        }
        return false;
    }

    /**
     *  Add a list of planners to planners.
     * @param planners the planners id that need to be added.
     * @return Return true if any one of the planner is successfully added.
     */
    public boolean setPlanners(ArrayList<String> planners) {
        boolean flag = false;
        for (String planner : planners) {
            if (!this.planners.contains(planner)) {
                this.planners.add(planner);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Return the role of this account: admin, regular or trial.
     * @return Return false.
     */
    @Override
    public String getAccountType() {
        return  this.accountType;
    }

    /**
     * Return the available information of this account including username, id, and email.
     * @return A String that contains the user name, id and email of this account.
     */
    @Override public String toString() {
        String result;

        result = "This is an Regular Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email" + this.email + "\n";
        return result;
    }
}
