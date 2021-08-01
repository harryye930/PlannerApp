package UserInterface.Widgets;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Widget {
    protected Widget parent = null;
    protected Widget child;
    protected String text;

    @Override
    public String toString() {
        return this.text;
    }

    public void setParent(Widget parent) {
        this.parent = parent;
    }

    public void setChild(Widget child) {
        this.child = child;
    }

    public void unhold() {
        if (this.child == null) {
            System.out.println("Child widget is not initialized.");
        } else {
            this.child.trigger();
        }
    }

    public void trigger() {
        throw new NotImplementedException();
    }


}
