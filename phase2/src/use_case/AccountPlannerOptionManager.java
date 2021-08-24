package use_case;

import entity.Account;
import entity.UserAccount;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Manages Planner functionalities for users stored in AccountManager.
 */
public class AccountPlannerOptionManager {
    private final AccountManager accountManager;

    /**
     * Constructs an AccountPlannerOptionManager.
     * @param accountManager An instance of AccountManager that stores Accounts that AccountPlannerOptionManager can operate on.
     */
    public AccountPlannerOptionManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * Add new planner to a given account. return true if the planner is added.
     * @param retriever A String representing the user ID or Email.
     * @param plannerId An Planner id that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     */
    public boolean setPlanners(String retriever, String  plannerId){
        Account account = this.accountManager.findAccount(retriever);
        String status = account.getAccountType();

        if (!status.equals("admin")){
            return ((UserAccount) account).setPlanners(plannerId);
        } else {
            return false;
        }
    }

    /**
     * Get all planners created by the given user
     * @param retriever A String representing the User ID or Email.
     * @return List of Planner that owned by this account, if the account is regular. Else, return
     * null.
     */
    public List<String> getPlanners(String retriever) {
        Account account = this.accountManager.findAccount(retriever);
        String status = account.getAccountType();

        if (!status.equals("admin") ){
            return ((UserAccount) account).getPlanner();
        } else {
            HashSet<String> ids = new HashSet<>();
            for (Account acc: accountManager.getAllAccount()) {
                if (!accountManager.checkAccountRole(acc.getUserId()).equals("admin")) {
                    ids.addAll(this.getPlanners(acc.getUserId()));
                }
            }
            return new ArrayList<>(ids);
        }
    }

    /**
     * Remove given planner from user's planners
     * @param retriever A String representing the User ID or Email.
     * @param plannerId A String representing the planner id.
     */
    public boolean removePlanner(String retriever, String plannerId) {
        Account account = this.accountManager.findAccount(retriever);
        String status = account.getAccountType();

        if (!Objects.equals(status, "admin")) {
            return ((UserAccount) account).removePlanner(plannerId);
        } else {
            return false;
        }
    }

    /**
     * return the trashed planners of the given user
     * @param userId the user id of the user
     * @return List<String> that contains trashed planners of the user
     */
    public List<String> getTrashPlanner(String userId){
        UserAccount acc = (UserAccount) accountManager.findAccount(userId);
        return acc.getTrashPlanner();
    }

    /**
     * remove the planner from trash, add it to all the planners of the user
     * @param userId the id of the user
     * @param plannerId the id of the planner
     * @return whether the removing and adding are successful or not
     */
    public boolean unTrashPlanner(String userId, String plannerId){
        UserAccount acc = (UserAccount) accountManager.findAccount(userId);
        if (acc.getTrashPlanner().contains(plannerId)){
            acc.setPlanners(plannerId);
            acc.removeFromTrash(plannerId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * remove the planner permanently.
     * @param userId A String representing the user id
     * @param plannerId A String representing the planner id.
     * @return whether the removing is successful or not
     */
    public boolean permanentTrashPlanner(String userId, String plannerId) {
        UserAccount acc = (UserAccount) accountManager.findAccount(userId);
        return acc.removeFromTrash(plannerId);
    }
}