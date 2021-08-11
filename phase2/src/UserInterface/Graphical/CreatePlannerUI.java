package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * GUI class for the first planner creation screen.
 * Displays a list of templates available, and asks the user to enter the ID of the template they would like to use.
 */
public class CreatePlannerUI extends GeneralPresenter {
    private boolean menuFlag = false;
    private boolean createPageFlag = false;

    private final GeneralPresenter checkPlanner = new CheckPlannerUI("createPlanner");

    private final JPanel createPlanner = new JPanel();
    private final JPanel inputPage = new JPanel();

    private final JTextArea message = new JTextArea("Please enter the ID of template \nyou want to use:");
    private final JTextField id = new JTextField();
    private final JTextField p1 = new JTextField();
    private final JTextField p2 = new JTextField();
    private final JTextField p3 = new JTextField();
    JTextField name = new JTextField();

    private final JButton submit = new JButton("Submit");
    private final JButton back = new JButton("Return to Planner Menu");
    private final JButton confirm = new JButton("Confirm");
    private final JButton back1 = new JButton("Go back");


    public CreatePlannerUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (menuFlag) {
            cl.show(main, "createPlanner");
        } else {
            this.showMenu();
            cl.show(main, "createPlanner");
            frame.setVisible(true);
            menuFlag = !menuFlag;
        }
    }

    private void showMenu() {
        createPlanner.setLayout(null);

        main.add(createPlanner, "createPlanner");
        JScrollPane templateInfo = data.getTemplates(createPlanner);

        message.setBounds(450, 50, 200, 50);
        message.setEditable(false);
        createPlanner.add(message);

        id.setBounds(500, 130, 100, 50);
        createPlanner.add(id);

        submit.setBounds(515, 200, 70, 40);
        submit.addActionListener(this);
        createPlanner.add(submit);

        back.setBounds(445, 250, 210, 40);
        back.addActionListener(this);
        createPlanner.add(back);
    }

    private void showCreatePage() {
        ArrayList<String> prompts = templateController.
                getTemplatePrompts(Integer.parseInt(templateController.getCurrTemplateId()));
        JPanel curr = new JPanel();
        curr.setLayout(new GridLayout(10, 1));
        inputPage.add(curr);
        curr.setBounds(50, 100, 400, 300);

        main.add(inputPage, "inputPage");

        JLabel prompt1 = new JLabel(prompts.get(0));
        JLabel prompt2 = new JLabel(prompts.get(1));
        JLabel prompt3 = new JLabel(prompts.get(2));
        JLabel prompt4 = new JLabel(prompts.get(3));


        curr.add(prompt1);
        curr.add(name);
        curr.add(prompt2);
        curr.add(p1);
        curr.add(prompt3);
        curr.add(p2);
        curr.add(prompt4);
        curr.add(p3);
        curr.add(confirm);
        curr.add(back1);

        confirm.addActionListener(this);
        back1.addActionListener(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if (!templateController.checkTemplate(id.getText())) {
                this.message.setText("Invalid ID, please try again!");
            } else {
                if (createPageFlag) {
                    cl.show(main, "inputPage");
                } else {
                    this.showCreatePage();
                    cl.show(main, "inputPage");
                    createPageFlag = !createPageFlag;
                }

            }
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        } else if (e.getSource() == confirm) {
            plannerController.createPlanner(p1.getText(), p2.getText(), p3.getText(), name.getText());
            this.checkPlanner.run();
        } else if (e.getSource() == back1) {
            cl.show(main, this.getParent());
        }
    }
}
