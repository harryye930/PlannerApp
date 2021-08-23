package gateway;

import java.io.*;
import java.util.*;

/**
 * Data reader gateway.
 */
public class Reader<T> implements IGateWay<T> {
    protected String folderPath;

    /**
     * Initialize the Reader with two file paths。
     * @param originalFolderPath A string representing one possible file path.
     * @param alternativePath A String representing another possible file path.
     */
    public Reader(String originalFolderPath, String alternativePath) {
        File file = new File(originalFolderPath);
        try {
            if (file.createNewFile() && file.delete()) {
                folderPath = alternativePath;
            } else {
                folderPath = originalFolderPath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Store an object into a .ser file.
     * @param filePath A String representing the file path you want to store.
     * @param obj The Object you want to store
     * @return A boolean value representing whether the process is successful or not.
     */
    @Override
    public boolean writeSer(String filePath, T obj) {
        File nf = new File(filePath);
        try {
            if (nf.createNewFile() || nf.delete()) {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(obj);
                objectOut.close();
                return true;
            }
            return false;
        } catch (IOException ex) {
            System.out.println("Saving terminated, data lost.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Read .ser file into an object of given type T.
     * @param filePath A String representing the file path you want to read in.
     * @return An object of object type T, return null if failed to load in file.
     */
    @Override
    public T readSer(String filePath) {
        File nf = new File(filePath);
        try {
            if (nf.createNewFile() && nf.delete()) {
                return null;
            }
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            T obj = (T) objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (IOException e){
            System.out.println("Database outdated, automatically deleted.");
            nf.delete();
            return null;
        } catch (Exception ex) {
            System.out.println("Please check if the casting type is the correct type ");
            ex.printStackTrace();
            return null;
        }
    }

    /***
     * Writes lines to a text file at filePath. Returns true if content is successfully saved to text file,
     * false otherwise.
     * @param filePath A string representing the file path where you would like to store the text file.
     * @param lines A List of Strings containing content you would like to write to text file.
     * @return A boolean value representing whether the process is successful.
     */
    public boolean writeTextFile(String filePath, List<String> lines){
        try {
            File file = new File(filePath);
            file.delete();
            FileWriter myWriter = new FileWriter(file);
//            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            for (String line : lines) {
                myWriter.write(line); // write each line in lines to text file
            }
            myWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reads data from a text file at filePath, and saves the data in a Map.
     * Precondition: each line in the text file is formatted like this: "variable name: variable info", i.e., the name
     * of what's stored in that line and what's actually stored are separated by a colon.
     * @param filePath The location where the text file is stored.
     * @return Map object containing the data read from the text file. Each line in the text file is stored as a
     * key-value pair, the key is the name of what's stored (i.e., variable name), the value is the information that's
     * actually stored (i.e., variable info).
     */
    public Map<String, String> readTextFile(String filePath){
        Map<String, String> labelToString = new HashMap<>();
        try{
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] data = line.split(":", 2);
                labelToString.put(data[0].trim(), data[1].trim());
            }
            scanner.close();
            return labelToString;
        } catch (FileNotFoundException e){
            System.out.printf("File %s is missing.", filePath);
            e.printStackTrace();
            return null;
        }
    }
}
