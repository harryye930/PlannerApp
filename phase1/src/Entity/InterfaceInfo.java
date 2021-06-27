package Entity;

import java.io.*;
import java.util.*;

public class InterfaceInfo {
    private String file_path = "phase1/src/Entity/InterfaceInfo.csv";

    private Map<String, String[]> regular_info = new HashMap<>();
    private Map<String, String[]> admin_info = new HashMap<>();
    private Map<String, String> account_info = new HashMap<>();

    private String[] header;
    private ArrayList<String[]> data = new ArrayList<String[]>();

    InterfaceInfo() {
        File file = new File(this.file_path);
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
            this.read_in_data(this.file_path);
            this.data_sorting();
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

    private void data_sorting(){
        // Sort data.
        int index_object, index_stage;
        if (this.data == null) {
            return;
        }

        //System.out.println(Arrays.toString(this.header));
        index_object = this.find_item(this.header, "object");
        index_stage = this.find_item(this.header, "stage");
        for (String[] lst: this.data){

            if (index_object == -1 || index_stage == -1) {
                continue;
            }

            if (lst[index_object].equals("regular")){
                this.regular_info.put(lst[index_stage], lst);
            } else if (lst[index_object].equals("admin")) {
                this.admin_info.put(lst[index_stage], lst);
            }
            }

        }

    private int find_item(String[] lst, String item){
        // Return the index of given item, -1 if not found.
        for (int i = 0; i < lst.length; i++) {
            if (lst[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        InterfaceInfo x = new InterfaceInfo();
        x.regular_info.get("0");
    }

}
