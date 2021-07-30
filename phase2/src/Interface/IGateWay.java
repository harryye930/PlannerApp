package Interface;

import java.util.ArrayList;

/**
 * The interface of the Gateway.
 */
public interface IGateWay<T>{
    /**
     * Store an object into a .ser file.
     * @param filePath A String representing the file path you want to store.
     * @param obj The Object you want to store
     * @return A boolean value representing whether the process is successful or not.
     */
    public boolean writeSer(String filePath, T obj);

    /**
     * Store the given data into a .csv file.
     * @param filePath A String representing the file path you want to store.
     * @param header An ArrayList representing the header of the csv file we want to set.
     * @param data A two dimensional ArrayList containing the body of the csv file.
     * @return A boolean value representing whether the process is successful or not.
     */
    public boolean writeCSV(String filePath, ArrayList<String> header,
                            ArrayList<ArrayList<String>> data);

    /**
     * Read .ser file into an object of given type T.
     * @param filePath A String representing the file path you want to store.
     * @return A Object of object type T, return null if failed to load in file.
     */
    public T readSer(String filePath);

    /**
     * Read .csv file into an two dimensional ArrayList.
     * @param filePath A String representing the file path you want to store.
     * @return A two dimensional ArrayList containing String.
     */
    public ArrayList<ArrayList<String>> readCSV(String filePath);
}
