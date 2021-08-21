package strategy;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.util.HashMap;

public interface IButton {

    public JPanel getPanel();

    public void setBounds(int x, int y, int width, int height);

//    public boolean setPrompt(String name, String newPrompt);

    public void add(String name, String prompt, GeneralPresenter nextUI);

    public HashMap<String, JButton> getButtons();

//    public void addTextField(String name);

//    public void addLabel(String name, String prompt);
}
