package graphical_user_interface.builder;


import javax.swing.*;
import java.util.HashMap;

public interface IForm {

    JPanel getPanel();

    HashMap<String, JComponent> getComponents();

    HashMap<String, JButton> getSuperButtons();

    JComponent get(String name);
}
