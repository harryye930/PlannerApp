package graphical_user_interface;

import controller.AccessController;
import controller.PlannerController;
import controller.TemplateController;

import javax.swing.*;
import java.awt.*;


/**
 * A parent class for all UI classes.
 */
public abstract class GeneralUI {
    /**
     * accessController: An instance of AccessController.
     * templateController: An instance of TemplateController.
     * plannerController: An instance of PlannerController.
     * main: The main JPanel.
     * cl: The CardLayout for main JPanel.
     * frame: The JFrame that the main JPanel will be added to.
     * data: An instance of ViewData which fetches data from controllers.
     */
    protected static AccessController accessController = new AccessController();
    protected static TemplateController templateController = new TemplateController();
    protected static PlannerController plannerController = new PlannerController();

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
     * Runs the UI from the beginning.
     */
    public abstract void run();

    /**
     * Sets the parent of the current UI.
     * @param parent A GeneralUI representing the parent we want to assign.
     */
    public void setParent(GeneralUI parent) {
        this.parent = parent;
    }

    /**
     * @return The Parent UI.
     */
    public GeneralUI getParent() {
        return this.parent;
    }

}
