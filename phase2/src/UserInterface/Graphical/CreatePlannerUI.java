package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreatePlannerUI extends GeneralPresenter {
    private boolean flag = false;

    JPanel createPlanner = new JPanel();
    JTextArea templateInfo = new JTextArea();
    JLabel message = new JLabel("<html>Please enter the ID of template you want to use:</html>");
    JTextField id = new JTextField();
    JButton submit = new JButton("Submit");

    public CreatePlannerUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "createPlanner");
        } else {
            this.showMenu();
            cl.show(main, "createPlanner");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void showMenu() {
        String x = "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n" +
                "kdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\nkdafjkjsahdfkafjak\n";
        createPlanner.setLayout(null);

        main.add(createPlanner, "createPlanner");
        JScrollPane templates = new JScrollPane(templateInfo);
        createPlanner.add(templates);

        templates.setBounds(25, 25, 400, 500);
        templateInfo.setText(controller.viewTemplates() + x );
        templates.setBackground(new Color(255, 255, 255));

        createPlanner.add(templates);

        message.setBounds(500, 50, 100, 50);
        createPlanner.add(message);

        id.setBounds(500, 150, 100, 50);
        createPlanner.add(id);

        submit.setBounds(600, 250, 70, 40);
        createPlanner.add(submit);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
