package UserInterface;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * A parent class for all presenters.
 */
public abstract class GeneralPresenter {
    private GeneralPresenter child;
    private GeneralPresenter parent;

    /**
     * run the presenter from the beginning.
     */
    public abstract void run();

    /**
     * run the presenter from the menu stage.
     */
    public abstract void runMenu();

    /**
     * set the child presenter of the current presenter.
     * @param child A GeneralPresenter representing the child presenter we want to set.
     */
    public void setChild(GeneralPresenter child) {
        this.child = child;
    }

    /**
     * Set the parent of the current presenter.
     * @param parent A GeneralPresenter representing the parent we want to assign.
     */
    public void setParent(GeneralPresenter parent) {
        this.parent = parent;
        this.parent.child = this;
    }

    /**
     * Return to the previous menu.
     */
    public void returnToMenu() {
        this.getParent().runMenu();
    }

    /**
     * @return The Parent Presenter
     */
    public GeneralPresenter getParent() {
        return this.parent;
    }

    /**
     * @return The child Presenter.
     */
    public GeneralPresenter getChild() {
        return this.child;
    }
}
