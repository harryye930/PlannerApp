package strategy;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public interface IForm {
    public void addLabel(String name, String prompt);
    public void addTextField(String name);
    public void addPasswordText(String name);
    public void addSubmitButton(String name, String prompt);
    public HashMap<String, JComponent> getComponents();
    public void setBounds(int x, int y, int width, int height);
    public JPanel getPanel();
    public void setLayout(int row, int column);
    public void addListener(ActionListener gp);
}
