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
public class AccountGateway extends Reader<HashMap<String, Account>> {

    //Assign the file path of data.
    private String idMapPath = "phase1/src/UserData/idMap.ser";
    private String emailMapPath = "phase1/src/UserData/emailMap.ser";

    private HashMap<String, Account> idToAccount = new HashMap<String, Account>();

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
    public boolean load() { return this.readMaps(); }

    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.writeMaps();
    }

    // Private methods.
    private boolean readMaps() {
        try {
            HashMap<String, Account> hm = super.readSer(this.idMapPath);
            if (hm == null) {return true;} else {
                this.idToAccount = hm;
            }
            for (Account acc: this.idToAccount.values()) {
                am.addAccount(acc);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean writeMaps() {
        for (Account acc: am.getAllAccount()) {
            this.idToAccount.put(acc.getUserId(), acc);
        }
        return super.writeSer(this.idMapPath, this.idToAccount);
    }
}
