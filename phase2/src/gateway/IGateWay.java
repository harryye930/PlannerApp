package gateway;

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
    boolean writeSer(String filePath, T obj);

    /**
     * Read .ser file into an object of given type T.
     * @param filePath A String representing the file path you want to store.
     * @return A Object of object type T, return null if failed to load in file.
     */
    T readSer(String filePath);

}
