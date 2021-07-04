package UseCase;

import Entity.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountManager implements Serializable {
    private HashMap<String, Account> idToAccount = new HashMap<>();
    private ArrayList<Account> allAccount = new ArrayList<>();

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

}
