package UserInterface;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GeneralPresenter {
    private GeneralPresenter child;
    private GeneralPresenter parent;

    public void run() {
        throw new NotImplementedException();
    }

    public void runMenu() {throw new NotImplementedException();}

    public void setChild(GeneralPresenter child) {
        this.child = child;
    }

    public void setParent(GeneralPresenter parent) {
        this.parent = parent;
        this.parent.child = this;
    }

    public void returnToMenu() {
        this.getParent().runMenu();
    }

    public GeneralPresenter getParent() {
        return this.parent;
    }

    public GeneralPresenter getChild() {
        return this.child;
    }
}
