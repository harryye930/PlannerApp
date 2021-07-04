package UseCase;

import Entity.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountManager implements Serializable {
    private HashMap<String, Account> idToAccount;
    private ArrayList<Account> allAccount;

    public AccountManager(){
        idToAccount = new HashMap<>();
        allAccount = new ArrayList<>();
    }

    public void setUserName(Account account, String userName){
        account.setUserName(userName);
    }

    public boolean setPassword(Account account, String enteredPassword, String newPassword){
        if (enteredPassword.equals(account.getPassword())){
            account.setPassword(newPassword);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(Account account, String enteredPassword){
        return account.getPassword().equals(enteredPassword);
    }

    public Account findAccount(String userId){
        return idToAccount.getOrDefault(userId, null);
    }

    private void createRegAcc(String email){
        UserAccount newAccount = new UserAccount(email);
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
    }
    private void createAdminAcc(String email){
        AdminAccount newAccount = new AdminAccount(email);
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
    }
    private void createTrialAcc(){
        TrialAccount newAccount = new TrialAccount();
        idToAccount.put(newAccount.getUserId(), newAccount);
        allAccount.add(newAccount);
    }
    public void createAccount(String email){
        if (email.contains("@admin.com")){
            createAdminAcc(email);
        } else if (email.equals("")){
            createTrialAcc();
        } else {
            createRegAcc(email);
        }
    }

    public void removeAccount(Account account){
        allAccount.remove(account);
        idToAccount.values().remove(account);
    }

    public boolean accountRole(Account account){
        return account.getIsAdmin();
    }

}
