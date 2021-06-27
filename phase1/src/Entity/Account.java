package Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A instance of this class represent a account of this application.
 */
public class Account implements InterfaceInfo{
    private boolean isAdmin;
    private String userName;
    private String userId;
    private String password;
    private String[] header;
    private ArrayList<String[]> data = new ArrayList<>();

    public Account() {
        this.isAdmin = false;
        this.userId = ((Integer) this.hashCode()).toString();
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    @Override
    public void read_csv(String file_path) {
        File file = new File(file_path);
        try {
            if (file.createNewFile()) {
                // The file hasn't already be created before this execution.
                // Initialize the csv file.
                String[] head = {"object", "kind", "widget", "info", "account", "stage"};
                this.header = head;
                FileWriter csvFile = new FileWriter(file);
                int i = 0;
                for (; i < head.length - 1; i++){
                    csvFile.append(head[i]).append(",");
                }
                csvFile.append(head[i]).append("\n");
                csvFile.flush();
                csvFile.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Read in the data from csv file.
        try {
            this.read_in_data(file_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void read_in_data(String file_path) throws FileNotFoundException {
        // Read in the data from csv file.
        Scanner scanner = new Scanner(new FileInputStream(file_path));

        // read in the header.
        if (scanner.hasNext()) {
            String header_info = scanner.nextLine();
            this.header = header_info.split(",");
        }

        // read in the data.
        String row;
        while (scanner.hasNext()){
            row = scanner.nextLine();
            String[] curr = row.split(",");
            //ArrayList<String> arr = new A
            this.data.add(row.split(","));
        }
        scanner.close();
    }

}

