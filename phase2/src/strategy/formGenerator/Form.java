package strategy.formGenerator;

import strategy.IForm;

import javax.swing.*;
import java.util.HashMap;

public class Form implements IForm {
    private final HashMap<String, JComponent> components;
    private final HashMap<String, JButton> superButtons;
    private final JPanel panel;

    public Form(HashMap<String, JComponent> components, HashMap<String, JButton> superButtons, JPanel panel) {
        this.components = components;
        this.superButtons = superButtons;
        this.panel = panel;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public HashMap<String, JComponent> getComponents() {
        return components;
    }

    @Override
    public HashMap<String, JButton> getSuperButtons() {
        return superButtons;
    }

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
