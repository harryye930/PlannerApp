package UserInterface.Widgets;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;


/**
 * A Class represent a fundamental element of UI.
 */
public abstract class Widget {
    protected Widget parent = null;
    protected Widget child;
    protected String text;

    /**
     * @return Return the information or prompt about this Widget.
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     * @param parent A widget to be set as the parent of current widget.
     */
    public void setParent(Widget parent) {
        this.parent = parent;
    }

    /**
     * @param child A widget to be set as the child of the current widget.
     */
    public void setChild(Widget child) {
        this.child = child;
    }

    /**
     * call the trigger method of the the child widget since in some cases
     *          the widget wight hold.
     */
    public void unhold() {
        if (this.child == null) {
            System.out.println("Child widget is not initialized.");
        } else {
            this.child.trigger();
        }
    }

    /**
     * Trigger the widget.
     */
    public abstract void trigger();


}
