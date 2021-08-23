package graphical_user_interface.builder;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Form implements IForm {
    private final Map<String, JComponent> components;
    private final Map<String, JButton> superButtons;
    private final JPanel panel;

    public Form(Map<String, JComponent> components, Map<String, JButton> superButtons, JPanel panel) {
        this.components = components;
        this.superButtons = superButtons;
        this.panel = panel;
    }

    /**
     * Get the JPanel that contains all the Swing components built in the form.
     * @return A JPanel contains all the Swing components built in the form.
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Get the component object in the form by its name.
     * @param name A String representing the name of the component.
     * @return A JComponent that has the given name.
     */
    @Override
    public JComponent get(String name) {
        if (components.containsKey(name)) {
            return components.get(name);
        } else if (superButtons.containsKey(name)) {
            return superButtons.get(name);
        }
        System.out.println("Invalid name!");
        return null;
    }

}
