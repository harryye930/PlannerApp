package gateway;

import entity.Account;
import use_case.AccountManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An Account Gateway responsible for reading and writing data.
 */
public class AccountGateway extends Reader<HashMap<String, Account>> {

    //Assign the file path of data.
    private final String idMapPath;
    private final String tempPasswordPath = "tempPassword.txt";

    private HashMap<String, Account> idToAccount = new HashMap<>();

    private final AccountManager am;

    /**
     * Initialize the Gateway with a AccountManager.
     * @param am An AccountManager object.
     */
    public AccountGateway(AccountManager am) {
        super("data", "phase2/data");
        this.idMapPath = this.folderPath + "/idToAccountMap.ser";
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

    /***
     * Saves tempPassword to a text file.
     * @param tempPassword A string representing the temporary password to be saved.
     * @return A boolean value representing whether the saving process is successful.
     */
    public boolean saveTempPassword(String tempPassword){
        List<String> lines = new ArrayList<>();
        lines.add(tempPassword);
        return super.writeTextFile(this.tempPasswordPath, lines);
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
