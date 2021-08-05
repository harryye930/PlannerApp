package UserInterface.Graphical;

import Interface.IController;

import javax.swing.*;
import java.awt.*;

public class ViewData {
    private IController controller;

    JTextArea templateInfo = new JTextArea();
    JTextArea plannerInfo = new JTextArea();

    JScrollPane planners = new JScrollPane(plannerInfo);
    JScrollPane templates = new JScrollPane(templateInfo);

    public ViewData(IController controller) {
        this.controller = controller;
    }

    public JScrollPane getTemplates() {
        this.update();
        return this.templates;
    }

    public JScrollPane getPlanners() {
        this.update();
        return this.planners;
    }

    private void update() {

        //templates.setBounds(25, 25, 400, 500);
        templates.setBackground(new Color(143, 141, 141));
        templateInfo.setText(controller.viewTemplates());
        templateInfo.removeAll();
        templateInfo.setLayout(null);
        templateInfo.setEditable(false);

        //plannerInfo.setBounds(25, 25, 400, 500);
        plannerInfo.setText(controller.viewUserPlanners() + controller.viewPublicPlanners());
        plannerInfo.setEditable(false);
        plannerInfo.setBackground(new Color(143, 141, 141));
        planners.removeAll();
        planners.setLayout(null);
        planners.add(plannerInfo);


    }

}
