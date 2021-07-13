package Gateway;

import Entity.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Data reader gateway.
 */
public class Reader {
    private final Account account;
    private final String filePath;

    public Reader(Account account, String filePath) {
        this.account = account;
        this.filePath = filePath;
    }

    /**
     * Read in data from file path.
     * The headers in csv file have following meanings:
     *  - object: the identity we make operations on.
     *  - stage: indicate the phase of interaction.
     *  - message: the text that TextUI needs to convey.
     *  - options: the available options that can be provided for the user at current sate.
     */
    protected void read_csv() {
        // read in the csv file into data attribute.
        File file = new File(this.filePath);
        try {
            if (file.createNewFile()) {
                // The file hasn't already be created before this execution.
                // Initialize the csv file.
                String[] head = {"object", "options", "message", "stage"};
                this.account.setHeader(head);
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
            this.read_in_data();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void read_in_data() throws FileNotFoundException {
        // Read in the data from csv file.
        Scanner scanner = new Scanner(new FileInputStream(this.filePath));

        // read in the header.
        if (scanner.hasNext()) {
            String header_info = scanner.nextLine();
            this.account.setHeader(header_info.split(","));
        }

        // read in the data.
        String row;
        while (scanner.hasNext()){
            row = scanner.nextLine();
            String[] curr = row.split(",");
            //ArrayList<String> arr = new A
            this.account.addData(row.split(","));
        }
        scanner.close();
    }
}
