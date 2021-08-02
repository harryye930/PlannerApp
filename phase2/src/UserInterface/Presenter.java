package UserInterface;

import Controller.ActionController;
import UserInterface.SubPresenter.*;
import Interface.IController;


public class Presenter {

    public static void main(String[] args) {
        IController controller = new ActionController();
        MenuPresenter menu = new MenuPresenter(controller);
        menu.run();

    }
}
