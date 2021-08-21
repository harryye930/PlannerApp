package strategy.buttonGenerator;

import UserInterface.GeneralPresenter;
import strategy.IButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GridStyleButtons implements IButton, ActionListener {
    private final JPanel panel = new JPanel();

    private final LinkedHashMap<String, JButton> nameToButton = new LinkedHashMap<>();
    private final LinkedHashMap<String, GeneralPresenter> nameToUI = new LinkedHashMap<>();

//    private final ArrayList<String> buttonName = new ArrayList<>();
//    private final ArrayList<String> buttonPrompt = new ArrayList<>();
//    private final ArrayList<GeneralPresenter> nextUI = new ArrayList<>();

    public GridStyleButtons(List<String> buttonNames, List<String> buttonPrompts, List<GeneralPresenter> uiList) {
        assert  buttonNames.size() == buttonPrompts.size();
        for (int i = 0; i < buttonNames.size(); i++) {
            nameToButton.put(buttonNames.get(i), new JButton(buttonPrompts.get(i)));
            nameToUI.put(buttonNames.get(i), uiList.get(i));
            nameToButton.get(buttonNames.get(i)).addActionListener(this);
        }
    }

    public GridStyleButtons() {}

    @Override
    public void add(String name, String prompt, GeneralPresenter nextUI) {
        nameToButton.put(name, new JButton(prompt));
        nameToButton.get(name).addActionListener(this);
        if (nextUI != null) {
            nameToUI.put(name, nextUI);
        }
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        return this.nameToButton;
    }

    @Override
    public JPanel getPanel() {
        this.generate();
        return panel;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        panel.setBounds(x, y, width, height);
    }


    private void generate() {
        panel.setLayout(new GridLayout(nameToButton.size(), 1));
        JButton button;
        for (String name: nameToButton.keySet()) {
            button = nameToButton.get(name);
            panel.add(button);
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e An event object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("triggered");
        for (String name: nameToButton.keySet()) {
            if (e.getSource() == nameToButton.get(name) && nameToUI.containsKey(name)) {
                nameToUI.get(name).run();
            }
        }
    }

}
