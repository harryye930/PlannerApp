package UseCase;

import Entity.ProjectTemplate;
import Entity.Template;
import Entity.DailyTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * Manages templates.
 */
public class TemplateManager {

    // TODO: *** note from office hour ***
    // TODO: (1) Make template gateway - use an interface (week7) to not violate dependency rule while using gateway to
    // TODO: read the information

    private Map<Integer, Template> templates;  // a mapping of template ID to Template

    /**
     * Creates a new empty TemplateManager.
     */
//     *
//     * @param filePath is the path of the data file.
//     * @throws IOException if an I/O error occurs.

//    public TemplateManager(String filePath) throws IOException {
//        templates = new HashMap<String, Template>();
//
//        // Read .csv file and create Template objects from file.
//        // Populates the record list using stored data, if it exists.
//        File file = new File(filePath);
//        if (file.exists()) {
//            readFromCSVFile(filePath);
//        } else {
//            file.createNewFile();
//        }
//    }

    public TemplateManager(){
        templates = new HashMap<>();
    }

    /**
     * Populates the records map from the file at path filePath.
     *
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     * Citation: this code is adopted from Week6 CSC207H Summer2021 demo code.
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        Template template;

        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            if (record[0].equals("daily")) {
                template = new DailyTemplate("TemplateName", record); // TODO: WIP
            } else {  // template type is "project"
                template = new ProjectTemplate("TemplateName", record);
            }
            templates.put(template.getName(), template);
        }
        scanner.close();
    }

    /**
     * Manages the stored template collections (i.e., add or remove) of this TemplateManager.
     *
     * @param t is the template either being added or removed from this TemplateManager.
     */
    public void add(Template t) {
        // Add template <t> to the collection of templates stored in this TemplateManager object.
        templates.put(t.getName(), t);
    }
    public void remove(Template t) {
        // Remove template <t> from the collection of templates stored in this TemplateManager object.
        templates.remove(t.getName(), t);
    }

    /**
     * Change the name of the template <t> to <newName>.
     *
     * @param t is the template being edited.
     * @param newName is the new name given to the template.
     */
    public void editTemplateName(Template t, String newName) {
        t.setName(newName);
    }

    /**
     * @return number of templates in this TemplateManager.
     */
    public int numberOfTemplates() {
        return templates.size();
    }

    /**
     * Create an empty template to be filled.
     *
     * @param t is the template to be used.
     * @return mapping of template name to prompts to be filled in the template.
     */
    public Map<String, String[]> emptyTemplate(Template t) {
        String[] prompts;
        Map<String, String[]> template;
        template = new HashMap<>();
        prompts = t.getPrompts();
        template.put("name", prompts);
        return template;
    }

    /**
     * @return String that represents the TemplateManager object.
     */
    public String toString() {
        return "Number of templates: " + this.numberOfTemplates();
    }

    /**
     * @return String that contains preview of all Template objects stored in the TemplateManager.
     */
    public String previewAllTemplates(){
        StringBuilder allPreview = new StringBuilder();
        for (Map.Entry items: this.templates.entrySet()){
            String key = (String)items.getKey();
            Template value = (Template)items.getValue();

            allPreview.append("Template ID: ").append(key).append(System.lineSeparator());
            allPreview.append(value.getTemplatePreview());
        }
        return allPreview.toString();
    }

    /**
     * @param ID ID of the Template.
     * @return String representation of the Template object corresponding to the ID. String representation contains
     * detailed representation of the Template, including name, type, number of prompts, and what those prompts are.
     */
    public String viewDetailedTemplate(int ID){
        return this.templates.get(ID).toString();
    }
}
