package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents a user account in this application.
 */
public class UserAccount extends Account {

    private final List<String> planners = new ArrayList<>();
    private final List<String> trashPlanner = new ArrayList<>();

    /**
     * Constructs a UserAccount.
     * @param email represent the email of this user account.
     */
    public UserAccount(String email) {
        super();
        this.accountType = "regular";
        super.email = email;
        super.userId = ((Integer) (email).hashCode()).toString();
    }

    /**
     * @return A List containing all planners of this user.
     */
    public List<String> getPlanner() {
        return this.planners;
    }

    /**
     * Add single plan to planners.
     * @param plannerId The planner id that need to be added.
     * @return Return true if the plan is successfully added and false if not.
     */
    public boolean setPlanners(String plannerId) {
        if (!this.planners.contains(plannerId)) {
            this.planners.add(plannerId);
            return true;
        }
        return false;
    }

    /**
     * Return the role of this account: admin, regular, trial or temporary.
     * @return A string.
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
        StringBuilder result = new StringBuilder();
        if (this.suspendedTime.isAfter(LocalDateTime.now())) {
            result.append("This  user is currently suspended till:\n");
            result.append(this.suspendedTime.toString()).append("\n");
        }
        result.append("This is an Regular Account with following information available:\n" + "User Name: ")
                .append(this.userName).append("\n").append("User ID: ").
                append(this.userId).append("\n").append("User Email:").append(this.email).append("\n");
        return result.toString();
    }

    /**
     * delete the planner, add it to the trashcan
     * @param plannerId the id of the planner to be deleted
     */
    public boolean removePlanner(String plannerId) {
        if (planners.contains(plannerId)) {
            this.planners.remove(plannerId);
            trashPlanner.add(plannerId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * return the trashed planners
     * @return List<String> representing the trash can
     */
    public List<String> getTrashPlanner(){
        return trashPlanner;
    }

    /**
     * remove the planner from trash
     * @param plannerId the String id of the planner to be deleted
     * @return whether the planner is removed or not
     */
    public boolean removeFromTrash(String plannerId){
        if(trashPlanner.contains(plannerId)){
            trashPlanner.remove(plannerId);
            return true;
        } else {
            return false;
        }
    }
}
