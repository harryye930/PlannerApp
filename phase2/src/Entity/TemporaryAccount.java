package Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A instance of this class represents a temporary account in this application.
 */
public class TemporaryAccount extends Account {

    private ArrayList<String > planners = new ArrayList<>();
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public TemporaryAccount(String email) {
        super();
        this.accountType = "Temporary";
        super.email = email;
        super.userId = ((Integer) (email).hashCode()).toString();

        this.startDate = LocalDateTime.now();
        this.endDate = startDate.plusDays(30);
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
     * @return A String that contains the user name, id, end date and email of this account.
     */
    @Override public String toString() {
        String result;

        result = "This is an Temporary Account with following information available:\n" +
                "User Name: " + this.userName + "\n" +
                "User ID: " + this.userId + "\n" +
                "User Email" + this.email + "\n" +
                "End Date" + this.endDate.toString() + "\n";
        return result;
    }

    /**
     * @return A LocalDateTime represent the start time of this account.
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * @return A LocalDateTime represent the end time of this account.
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * Add a list of planners to planners for this Temporary Account.
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
}
