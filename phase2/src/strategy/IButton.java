package strategy;

import UserInterface.GeneralPresenter;

import javax.swing.*;

public interface IButton {

    public JPanel getPanel();

    public void setBounds(int x, int y, int width, int height);

//    public boolean setPrompt(String name, String newPrompt);

    public void add(String name, String prompt, GeneralPresenter nextUI);

//    public void addTextField(String name);

//    public void addLabel(String name, String prompt);
}
