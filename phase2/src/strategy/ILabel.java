package strategy;

import javax.swing.*;
import java.util.HashMap;

public interface ILabel {

    public void add(String name, String prompt);

    public JPanel getPanel();

    public HashMap<String, JTextField> getText();
}
