package graphical_user_interface;

import gateway.UIGateway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

// TODO: To be merged later.
/**
 * GUI class for the first planner creation screen.
 * Displays a list of templates available, and asks the user to enter the ID of the template they would like to use.
 */
public class CreatePlannerUI extends GeneralUI implements ActionListener {
    private boolean menuFlag = false;
    private boolean createPageFlag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadCreatePlannerUITexts();

    private final GeneralUI checkPlanner = new CheckPlannerUI(this);

    private final JPanel createPlanner = new JPanel();
    private final JPanel inputPage = new JPanel();

    private final JLabel message = new JLabel(labelToStrings.get("message"));
    private final JTextField id = new JTextField();
    private final JTextField p1 = new JTextField();
    private final JTextField p2 = new JTextField();
    private final JTextField p3 = new JTextField();
    JTextField name = new JTextField();

    private final JButton submit = new JButton(labelToStrings.get("submit"));
    private final JButton returnToPlannerMenu = new JButton(labelToStrings.get("returnToPlannerMenu"));
    private final JButton confirm = new JButton(labelToStrings.get("confirm"));
    private final JButton back = new JButton(labelToStrings.get("goBack"));


    public CreatePlannerUI(GeneralUI parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (menuFlag) {
            data.getTemplates(createPlanner);
            cl.show(main, "createPlanner");
        } else {
            this.showMenu();
            cl.show(main, "createPlanner");
            menuFlag = !menuFlag;
        }
    }

    private void showMenu() {
        createPlanner.setLayout(null);

        main.add(createPlanner, "createPlanner");
        JScrollPane templateInfo = data.getTemplates(createPlanner);

        message.setBounds(450, 50, 200, 50);
        createPlanner.add(message);

        id.setBounds(500, 130, 100, 50);
        createPlanner.add(id);

        submit.setBounds(515, 200, 70, 40);
        submit.addActionListener(this);
        createPlanner.add(submit);

        returnToPlannerMenu.setBounds(445, 250, 210, 40);
        returnToPlannerMenu.addActionListener(this);
        createPlanner.add(returnToPlannerMenu);
    }

    private void showCreatePage() {
        List<String> prompts = templateController.
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
        curr.add(back);

        confirm.addActionListener(this);
        back.addActionListener(this);
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
                this.message.setText(labelToStrings.get("invalidInput"));
            } else {
                if (createPageFlag) {
                    cl.show(main, "inputPage");
                } else {
                    this.showCreatePage();
                    cl.show(main, "inputPage");
                    createPageFlag = !createPageFlag;
                }

            }
        } else if (e.getSource() == returnToPlannerMenu || e.getSource() == back) {
            this.getParent().run();
        } else if (e.getSource() == confirm) {
            plannerController.createPlanner(p1.getText(), p2.getText(), p3.getText(), name.getText());
            this.checkPlanner.run();
        }
    }
}
