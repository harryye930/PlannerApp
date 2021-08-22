package builder;


import javax.swing.*;
import java.util.HashMap;

public interface IForm {

    public JPanel getPanel();

    public HashMap<String, JComponent> getComponents();

    public HashMap<String, JButton> getSuperButtons();

    public JComponent get(String name);
}
