package use_case;

import entity.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages friend functionalities for users stored in AccountManager (check if two users are friends, delete friend,
 * add friend, etc.)
 */
public class AccountFriendManager {
    private final AccountManager accManager;

    /**
     * Constructs an AccountFriendManager.
     * @param accManager An instance of AccountManager that stores Accounts that AccountFriendManager can operate on.
     */
    public AccountFriendManager(AccountManager accManager) {
        this.accManager = accManager;
    }

    /**
     * return whether the 2 users are friends or not
     * @param selfId the id of the first user
     * @param friendId the id of the 2nd user
     * @return whether the 2 users are friends or not
     */
    public boolean isFriend(String selfId, String friendId){
        return accManager.getAllAccount().contains(accManager.findAccount(friendId))
                && accManager.getAllAccount().contains(accManager.findAccount(selfId))
                && accManager.findAccount(selfId).findFriend(accManager.findAccount(friendId));
    }

    /**
     * add 2 users to friend of each other
     * @param selfId the id of the first user
     * @param friendId the id of the 2nd user
     * @return whether adding friend is successful or not
     */
    public boolean addFriend(String selfId, String friendId){
        if (accManager.getAllAccount().contains(accManager.findAccount(friendId))
                && !accManager.findAccount(selfId).getFriends().contains(accManager.findAccount(friendId))){
            accManager.findAccount(selfId).addFriend(accManager.findAccount(friendId));
            accManager.findAccount(friendId).addFriend(accManager.findAccount(selfId));
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete friends of both users
     * @param selfId the id of the first user
     * @param friendId the id of the 2nd user
     * @return whether deleting friend is successful or not
     */
    public boolean deleteFriend(String selfId, String friendId){
        if (accManager.getAllAccount().contains(accManager.findAccount(friendId)) && isFriend(selfId, friendId)){
            accManager.findAccount(selfId).removeFriend(accManager.findAccount(friendId));
            accManager.findAccount(friendId).removeFriend(accManager.findAccount(selfId));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the List of friend ids of a user
     * @param selfId the user
     * @return List of friend ids (String) of the user
     */
    public List<String> getFriends(String selfId){
        List<Account> friends = accManager.findAccount(selfId).getFriends();
        List<String> friendIds = new ArrayList<>();
        for (Account i : friends){
            friendIds.add(i.getUserId());
        }
        return friendIds;
    }

}
