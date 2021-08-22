package graphical_user_interface.builder;


import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public interface IForm {

    JPanel getPanel();

    Map<String, JComponent> getComponents();

    Map<String, JButton> getSuperButtons();

    JComponent get(String name);
}
