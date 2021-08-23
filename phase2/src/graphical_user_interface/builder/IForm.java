package graphical_user_interface.builder;


import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public interface IForm {

    /**
     * Get the JPanel that contains all the Swing components built in the form.
     * @return A JPanel contains all the Swing components built in the form.
     */
    JPanel getPanel();

    /**
     * Get the component object in the form by its name.
     * @param name A String representing the name of the component.
     * @return A JComponent that has the given name.
     */
    JComponent get(String name);
}
