package UseCase;

import Entity.Template;
import java.util.ArrayList;

/**
 * Manages templates.
 */
public class TemplateManager {

    // TODO: how do we identify unique template
    // TODO: based on this, all methods with type Template parameters should change

    private ArrayList<Template> templateCollections;  // subjected to change (placeholder for now)

    public TemplateManager() {
        templateCollections = new ArrayList<>();
    }

    /**
     * Manages the stored template collections - e.g., add, remove, count.
     */
    public void add(Template t) {
        // Add template <t> to the collection of templates stored in this TemplateManager object.
        templateCollections.add(t);
    }

    public void remove(Template t) {
        // Remove template <t> from the collection of templates stored in this TemplateManager object.
        templateCollections.remove(t);
    }

    public int count() {
        // Returns the number of templates stored in this TemplateManager object.
        return templateCollections.size();
    }

    /**
     * Edits a given template - e.g., change template name.
     */
    public void editTemplateName(Template t, String newName) {
        // Change the name of the template <t> to <newName>.
        t.setName(newName);
    }

    /**
     * Returns a string representation of the TemplateManager object.
     * @return String that represents the TemplateManager object.
     */
    public String toString() {
        String stringRep = "Number of templates: " + this.count();
        return stringRep;
    }
}
