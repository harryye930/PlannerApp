package Gateway;

import Entity.Account;
import UseCase.AccountManager;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An Account Gateway responsible for reading and writing data.
 */
public class AccountGateway extends Reader {

    //Assign the file path of data.
    private final String filePath = "phase1/src/UserData/";
    private String idMapPath = "phase1/src/UserData/idMap.ser";
    private String emailMapPath = "phase1/src/UserData/emailMap.ser";

    private HashMap<String, Account> idToAccount = new HashMap<String, Account>();
    private HashMap<String, Account> emailToAccount = new HashMap<String, Account>();

    private final AccountManager am;

    /**
     * Initialize the Gateway with a AccountManager.
     * @param am An AccountManager object.
     */
    public AccountGateway(AccountManager am) {
        this.am = am;
    }

    /**
     * Load in the data from database, call this function when initialize an account manager.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean load() {
        return this.readHashMaps() && this.readAccounts();
    }

    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.writeAccounts(am) && this.writeMaps();
    }

    // Private methods.
    private boolean readHashMaps() {
        try {
            this.idToAccount = (HashMap<String, Account>) super.readSer(this.idMapPath);
            am.setIdToAccount(this.idToAccount);

            this.emailToAccount = (HashMap<String, Account>) super.readSer(this.emailMapPath);
            am.setEmailToAccount(this.emailToAccount);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean readAccounts() {
        try {
            for (String id : this.idToAccount.keySet()){
                Account acc = (Account) super.readSer(idMapPath + id + ".ser");
                this.idToAccount.put(id, acc);
                this.emailToAccount.put(acc.getEmail(), acc);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean writeAccounts(AccountManager am) {
        boolean fg = true;
        ArrayList<Account> allAccount = am.getAllAccount();
        for (Account acc : allAccount) {
            if (!super.writeSer(filePath + acc.getUserId() + ".ser", acc)) {
                fg = false;
            }
            this.emailToAccount.put(acc.getUserId(), acc);
            this.idToAccount.put(acc.getEmail(), acc);
        }
        return fg;
    }

    private boolean writeAccount (Account acc){
        String id = acc.getUserId();
        String thisPath = this.filePath + id + ".ser";

        try {
            File nf = new File(thisPath);
            if (nf.createNewFile() || nf.delete()) {
                FileOutputStream fileOut = new FileOutputStream(thisPath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(acc);
                objectOut.close();
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean writeMaps() {
        return this.writeMap(this.emailToAccount, this.emailMapPath) && (this.writeMap(idToAccount, this.idMapPath));
    }

    private boolean writeMap(HashMap<String, Account> hm, String fp) {
        try {
            File nf = new File(fp);
            if (nf.createNewFile() || nf.delete()) {
                FileOutputStream fileOut = new FileOutputStream(fp);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(hm);
                objectOut.close();
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
