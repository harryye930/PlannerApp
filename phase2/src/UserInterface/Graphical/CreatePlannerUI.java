package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreatePlannerUI extends GeneralPresenter {
    private boolean flag = false;

    private final GeneralPresenter checkPlanner = new CheckPlannerUI("createPlanner");

    JPanel createPlanner = new JPanel();
    JTextArea message = new JTextArea("Please enter the ID of template \nyou want to use:");
    JTextField id = new JTextField();
    JButton submit = new JButton("Submit");
    JButton back = new JButton("Go back");

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
        createPlanner.setLayout(null);

        main.add(createPlanner, "createPlanner");
        JScrollPane templateInfo = data.getTemplates();
        createPlanner.add(templateInfo);

        templateInfo.setBounds(25, 25, 400, 500);
        templateInfo.setBackground(new Color(143, 141, 141));

        message.setBounds(450, 50, 200, 50);
        message.setEditable(false);
        createPlanner.add(message);

        id.setBounds(500, 130, 100, 50);
        createPlanner.add(id);

        submit.setBounds(515, 200, 70, 40);
        submit.addActionListener(this);
        createPlanner.add(submit);

        back.setBounds(515, 250, 70, 40);
        back.addActionListener(this);
        createPlanner.add(back);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if (!controller.checkTemplate(id.getText())) {
                this.message.setText("Invalid ID, please try again!");
            } else {
                controller.createPlanner();
                this.checkPlanner.run();
            }
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }
}
