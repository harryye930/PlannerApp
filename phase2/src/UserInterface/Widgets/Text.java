package UserInterface.Widgets;

import java.util.Scanner;

public class Text extends Widget{
    String prompt;
    String text;
    boolean hold = true;

    public Text() {
        super();
    }

    public Text(Widget parent, String prompt, boolean hold) {
        super();
        super.parent = parent;
        this.parent.child = this;
        this.setPrompt(prompt);
        this.hold = hold;
    }

    public Text(Widget parent, Widget child, String prompt) {
        super();
        super.parent = parent;
        this.parent.child = this;
        super.child = child;
        this.prompt = prompt;
    }

    public String getText() {
        return text;
    }

    public void trigger() {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        this.text = scanner.next();
        if (!this.hold) {
            super.child.trigger();
        }
    }

    public void unhold() {
        super.child.trigger();
    }

    public void setHoldingStatus(boolean holdingStatus) {
        this.hold = holdingStatus;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }


    public void goBack() {
        super.parent.trigger();
    }

    public String toString() {
        return this.text;
    }

}
