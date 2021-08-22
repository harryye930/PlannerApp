package UseCase;

import Entity.Account;
import Entity.UserAccount;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class AccountPlannerOptionManager {
    private final AccountManager accountManager;

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

        if (status.equals("regular") | status.equals("temporary")){
            return ((UserAccount) account).setPlanners(plannerId);
        } else {
            return false;
        }
    }

    /**
     * Get all planners created by the given user
     * @param retriever A String representing the User ID or Email.
     * @return An ArrayList of Planner that owned by this account, if the account is regular. Else, return
     * null.
     */
    public ArrayList<String> getPlanners(String retriever) {
        Account account = this.accountManager.findAccount(retriever);
        String status = account.getAccountType();

        if (status.equals("regular") || status.equals("temporary") || status.equals("trial") ){
            return ((UserAccount) account).getPlanner();
        } else if (status.equals("admin")){
            HashSet<String> ids = new HashSet<>();
            for (Account acc: accountManager.getAllAccount()) {
                if (!accountManager.checkAccountRole(acc.getUserId()).equals("admin")) {
                    ids.addAll(this.getPlanners(acc.getUserId()));
                }
            }
            return new ArrayList<>(ids);
        }
        return new ArrayList<>();
    }

    /**
     * Remove given planner from user's planners
     * @param retriever A String representing the User ID or Email.
     * @param plannerId A String representing the planner id.
     */
    public void removePlanner(String retriever, String plannerId) {
        Account account = this.accountManager.findAccount(retriever);
        String status = account.getAccountType();

//        if (status.equals("regular") | status.equals("temporary")){
//            ((UserAccount) account).removePlanner(plannerId);
//        }
        if (!Objects.equals(status, "admin")) {
            ((UserAccount) account).removePlanner(plannerId);
        }
    }

    /**
     * return the trashed planners of the given user
     * @param userId the user id of the user
     * @return the ArrayList<String>, trashed planner of the user
     */
    public ArrayList<String> getTrashPlanner(String userId){
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
}
