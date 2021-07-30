package UserInterface.Widgets;

public class Option extends Widget{
    private String context;
    private Widget child;
    private String prompt;

    public Option() {
        super();
        context = "";
    }

    public Option(Widget parent, String context) {
        super();
        this.parent = parent;
        this.context = context;
        if (this.parent.getClass() == MultiOptions.class) {
            ((MultiOptions) this.parent).addBranch(this);
        }
    }


    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setChild(Widget child) {
        this.child = child;
        this.child.setParent(this);
    }

    @Override
    public void trigger() {
        if (this.prompt != null) {
            System.out.println(this.prompt);
        }
        if (this.child != null) {
            this.child.trigger();
        } else {
            System.out.println("TextUI ended.");
        }
    }

    @Override
    public String toString() {
        return this.context;
    }
}
