package strategy.formGenerator;

import strategy.IForm;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GridStyleForm implements IForm {
    private JPanel panel = new JPanel();
    private int row = 0;
    private int column = 0;

    private final LinkedHashMap<String, JComponent> nameToComponent = new LinkedHashMap<>();

//    public GridStyleForm() {
//        this.panel.setLayout(null);
//    }

    @Override
    public void addLabel(String name, String prompt) {
        this.nameToComponent.put(name, new JLabel(prompt));
    }

    @Override
    public void addTextField(String name) {
        this.nameToComponent.put(name, new JTextField());
    }

    @Override
    public void addPasswordText(String name) {
        this.nameToComponent.put(name, new JPasswordField());
    }

    @Override
    public void addSubmitButton(String name, String prompt) {
        this.nameToComponent.put(name, new JButton(prompt));
    }

    @Override
    public HashMap<String, JComponent> getComponents() {
        return this.nameToComponent;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        this.panel.setBounds(x, y, width, height);
    }

    @Override
    public JPanel getPanel() {
        int grids = row * column;
        if (grids == 0 || grids < this.nameToComponent.size()) {
            System.out.println("Grid size incorrect!");
        }
        panel.setLayout(new GridLayout(row, column));
        for (JComponent component: this.nameToComponent.values()) {
            this.panel.add(component);
        }
        return this.panel;
    }

    @Override
    public void setLayout(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
