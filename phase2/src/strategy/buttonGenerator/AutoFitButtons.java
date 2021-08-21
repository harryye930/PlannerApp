package strategy.buttonGenerator;

import UserInterface.GeneralPresenter;
import strategy.IButton;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AutoFitButtons implements IButton {
    private final JPanel panel = new JPanel();

    private final LinkedHashMap<String, JButton> nameToButton = new LinkedHashMap<>();
    private final LinkedHashMap<String, GeneralPresenter> nameToUI = new LinkedHashMap<>();

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {

    }

    @Override
    public void add(String name, String prompt, GeneralPresenter nextUI) {

    }

    @Override
    public HashMap<String, JButton> getButtons() {
        return null;
    }
}
