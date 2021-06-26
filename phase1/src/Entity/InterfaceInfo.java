package Entity;

import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class InterfaceInfo {
    private Map<String, String> regular_info = new HashMap<>();
    private Map<String, String> admin_info = new HashMap<>();
    private Map<String, String> account_info = new HashMap<>();


    InterfaceInfo() {
        File file = new File("phase1/src/Entity/InterfaceInfo.csv");
        try {
            if (file.createNewFile()) {
                // The file hasn't already be created before this execution.
                String[] head = {"kind", "text"};
                FileWriter csvFile = new FileWriter(file);
                int i = 0;
                for (; i < head.length - 1; i++){
                    csvFile.append(head[i]).append(", ");
                }
                csvFile.append(head[i]).append("\n");
                csvFile.flush();
                csvFile.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
    InterfaceInfo x = new InterfaceInfo();
    }
}
