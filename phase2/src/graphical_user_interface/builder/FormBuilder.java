package graphical_user_interface.builder;

import graphical_user_interface.GeneralUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create a Builder to construct a form which contain the components of GUI.
 */
public class FormBuilder implements ActionListener{
    private final JPanel panel = new JPanel();
    private int row = 0;
    private int column = 0;

    private final Map<String, JComponent> nameToComponent = new LinkedHashMap<>();
    private final Map<String, JButton> nameToSuperButtons = new LinkedHashMap<>();
    private final Map<String, GeneralUI> nameToUI = new LinkedHashMap<>();

    /**
     * Create the form with all the components built.
     * @return An IForm object that contains the GUI components.
     */
    public IForm getForm() {
        return new Form(this.nameToComponent, this.nameToSuperButtons, this.getPanel());
    }

    /**
     * Add a JLabel to the form, can be accessed later by name.
     * @param name A String representing the name of the component, can be used to access component.
     * @param prompt A String representing the prompt in the label.
     */
    public void addLabel(String name, String prompt) {
        JLabel label = new JLabel(prompt);
        label.setHorizontalAlignment(JLabel.LEFT);
        this.nameToComponent.put(name, label);
    }

    /**
     * Add an JLabel as a title of the form, this label would be centered and bigger.
     * @param name A String representing the name of the component, can be used to access component.
     * @param prompt A String representing the prompt in the label.
     */
    public void addTitleLabel(String name, String prompt) {
        JLabel label = new JLabel(prompt);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setBounds(0, 100, 700, 50);
        this.nameToComponent.put(name, label);
    }

    /**
     * Add a JList to the form which contains a list of choices.
     * @param name A String representing the name of the component, can be used to access component.
     * @param listOptions An array representing the available choices.
     */
    public void addList(String name, String[] listOptions){
        JList<String> list = new JList<>(listOptions);
        this.nameToComponent.put(name, list);
    }

    /**
     * Add a JTextField to the form which can be used to take input.
     * @param name A String representing the name of the component, can be used to access component.
     */
    public void addTextField(String name) {
        this.nameToComponent.put(name, new JTextField());
    }

    /**
     * Add a JButton to the form, the event triggered by this button is decided outside this class.
     * @param name A String representing the name of the component, can be used to access component.
     * @param prompt A String representing the prompt on the button.
     */
    public void addSubmitButton(String name, String prompt) {
        this.nameToComponent.put(name, new JButton(prompt));
    }

    /**
     * Add a JButton to the form, then a GeneralUI will be run.
     * @param name A String representing the name of the component, can be used to access component.
     * @param prompt A String representing the prompt on the button.
     * @param gp A GeneralUI object that will be called when pressing this button.
     */
    public void addSuperButton(String name, String prompt, GeneralUI gp) {
        this.nameToSuperButtons.put(name, new JButton(prompt));
        this.nameToUI.put(name, gp);
        this.nameToSuperButtons.get(name).addActionListener(this);
    }

    /**
     * Set bounds for the form.
     * @param x x coordinate
     * @param y y coordinate
     * @param width with of the form
     * @param height height of the form
     */
    public void setBounds(int x, int y, int width, int height) {
        this.panel.setBounds(x, y, width, height);
    }

    /**
     * Set the vertical and horizontal length of the form
     * @param row An int representing the row length
     * @param column An int representing the column length
     */
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

    /**
     * Get the panel for form.
     * @return A JPanel that contains all the components of form.
     */
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
     * @param e An event object
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
