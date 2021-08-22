package graphical_user_interface;

import controller.AccessController;
import controller.PlannerController;
import controller.TemplateController;

import javax.swing.*;
import java.awt.*;


/**
 * A parent class for all presenters.
 */
public abstract class GeneralUI {
    protected static AccessController accessController = new AccessController();
    protected static TemplateController templateController = new TemplateController();
    protected static PlannerController plannerController = new PlannerController();

//    private String child;
    private GeneralUI parent;
    protected static JPanel main = new JPanel();
    protected static CardLayout cl;
    protected static JFrame frame = new JFrame();
    protected static ViewData data = new ViewData(accessController, templateController, plannerController);

    public GeneralUI() {
        accessController.setPlannerController(plannerController);
        accessController.setTemplateController(templateController);
        templateController.setAccessController(accessController);
        templateController.setPlannerController(plannerController);
        plannerController.setAccessController(accessController);
        plannerController.setTemplateController(templateController);

        cl = new CardLayout();
        main.setLayout(cl);
        frame.add(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * run the presenter from the beginning.
     */
    public abstract void run();

//    /**
//     * set the child presenter of the current presenter.
//     * @param child A GeneralUI representing the child presenter we want to set.
//     */
//    public void setChild(String child) {
//        this.child = child;
//    }

    /**
     * Set the parent of the current presenter.
     * @param parent A GeneralUI representing the parent we want to assign.
     */
    public void setParent(GeneralUI parent) {
        this.parent = parent;
    }

    /**
     * @return The Parent Presenter
     */
    public GeneralUI getParent() {
        return this.parent;
    }

//    /**
//     * @return The child Presenter.
//     */
//    public String getChild() {
//        return this.child;
//    }
}
