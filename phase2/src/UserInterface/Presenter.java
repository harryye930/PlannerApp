package UserInterface;

import Controller.ActionController;
import UserInterface.SubPresenter.*;
import UserInterface.Widgets.*;
import Interface.IController;

import java.util.ArrayList;

public class Presenter {
    private ArrayList<Widget> widgets;
    private IController controller;
    private Widget currWidget;
    private String retriever;



    public static void main(String[] args) {
        IController controller = new ActionController();
        MenuPresenter menu = new MenuPresenter(controller);
        menu.run();

    }


}
