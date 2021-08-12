package strategy.textFieldGenerator;

import strategy.ILabel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SingleLineLabelText implements ILabel {

    private final JPanel panel = new JPanel();

    private final HashMap<String, JComponent>  nameToLabel = new HashMap<>();
    private final HashMap<String, JTextField> nameToText = new HashMap<>();

    public SingleLineLabelText() {}


    @Override
    public JPanel getPanel() {
        this.generate();
        return panel;
    }

    @Override
    public HashMap<String, JTextField> getText() {
        return nameToText;
    }

    @Override
    public void add(String name, String prompt) {
        nameToLabel.put(name, new JLabel(prompt));
        nameToText.put(name, new JTextField());
    }

    public String getText(String name) {
        return nameToText.get(name).getText();
    }

    public void setPrompt(String name, String prompt) {
        this.nameToText.get(name).setText(prompt);
    }

    private void generate() {
        panel.setLayout(new GridLayout(nameToLabel.size() , 2));
        for (String name: nameToLabel.keySet()) {
            panel.add(nameToLabel.get(name));
            nameToLabel.get(name).setAlignmentX(JLabel.RIGHT);
            panel.add(nameToText.get(name));
        }
    }
}
