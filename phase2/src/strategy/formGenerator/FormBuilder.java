package strategy.formGenerator;

import UserInterface.GeneralPresenter;
import strategy.IForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FormBuilder implements ActionListener{
    private JPanel panel = new JPanel();
    private int row = 0;
    private int column = 0;

    private final LinkedHashMap<String, JComponent> nameToComponent = new LinkedHashMap<>();
    private final HashMap<String, JButton> nameToSuperButtons = new LinkedHashMap<>();
    private final HashMap<String, GeneralPresenter> nameToUI = new LinkedHashMap<>();

//    public GridStyleForm() {
//        this.panel.setLayout(null);
//    }

    public IForm getForm() {
        return new Form(this.nameToComponent, this.nameToSuperButtons, this.getPanel());
    }

    public void addLabel(String name, String prompt) {
        JLabel label = new JLabel(prompt);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.nameToComponent.put(name, label);
    }

    public void addTitleLabel(String name, String prompt) {
        JLabel label = new JLabel(prompt);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setBounds(0, 100, 700, 50);
        this.nameToComponent.put(name, label);
    }

    public void addTextField(String name) {
        this.nameToComponent.put(name, new JTextField());
    }

    public void addPasswordText(String name) {
        this.nameToComponent.put(name, new JPasswordField());
    }

    public void addSubmitButton(String name, String prompt) {
        this.nameToComponent.put(name, new JButton(prompt));
    }

    public void addSuperButton(String name, String prompt, GeneralPresenter gp) {
        this.nameToSuperButtons.put(name, new JButton(prompt));
        this.nameToUI.put(name, gp);
        this.nameToSuperButtons.get(name).addActionListener(this);
    }

    public void setBounds(int x, int y, int width, int height) {
        this.panel.setBounds(x, y, width, height);
    }

    public void setLayout(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * This method is used to add actionListener for components that are not reacted in this class.
     * @param al A ActionListener object that
     */
    public void addListener(ActionListener al) {
        for (JComponent component: nameToComponent.values()) {
            if (component.getClass() == JButton.class) {
                ((JButton)component).addActionListener(al);
            }
        }
    }

    private JPanel getPanel() {
        int grids = row * column;
        if (grids == 0) {
            panel.setLayout(new GridLayout(nameToComponent.size() + nameToUI.size(), 1));
        } else if (grids < nameToComponent.size() + nameToUI.size()) {
            System.out.println("Invalid grid format!");
        } else {
            panel.setLayout(new GridLayout(row, column));
        }
        for (JComponent component: this.nameToComponent.values()) {
            this.panel.add(component);
        }
        for (JButton button: this.nameToSuperButtons.values()) {
            this.panel.add(button);
        }
        return this.panel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (String name: nameToSuperButtons.keySet()) {
            if (e.getSource() == nameToSuperButtons.get(name) && nameToUI.containsKey(name)) {
                nameToUI.get(name).run();
            }
        }
    }
}
