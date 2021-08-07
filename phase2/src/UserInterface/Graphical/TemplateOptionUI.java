package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * GUI class for displaying template options for a regular user.
 * Options include: view all planners, return to main menu.
 */
public class TemplateOptionUI extends GeneralPresenter {
    private boolean flag = false;
    // all buttons
    JButton viewAllTemplate = new JButton("View All Template");
    JButton returnToMainMenuButton= new JButton("Return to Main Menu");
    JButton back = new JButton("Go back");
    JPanel temp = new JPanel();

    // panel
    private final JPanel templateMenu = new JPanel();

    // menu text
    private JLabel prompt;

    public TemplateOptionUI(String parent) {
        this.setParent(parent);
    }

    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            cl.show(main, "templateMenu");
        } else {
            this.buildTemplateMenu();
            cl.show(main, "templateMenu");
            frame.setVisible(true);
            flag = !flag; // flag = false?
        }
    }
    private void buildTemplateMenu(){
        main.add(templateMenu, "templateMenu");
        templateMenu.setLayout(null);

        prompt = new JLabel("Template Menu");
        prompt.setHorizontalAlignment(JLabel.CENTER);
        prompt.setVerticalAlignment(JLabel.TOP);
        prompt.setFont(new Font("MV Boli", Font.PLAIN, 20));
        prompt.setBounds(0, 100, 700, 50);
        prompt.setOpaque(true);
        templateMenu.add(prompt);

        JPanel panel = new JPanel();
        panel.setBounds(150, 150, 400, 200);
        panel.setLayout(new GridLayout(4, 1));
        templateMenu.add(panel);

        panel.add(viewAllTemplate);
        panel.add(returnToMainMenuButton);

        returnToMainMenuButton.addActionListener(this);
        viewAllTemplate.addActionListener(this);

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnToMainMenuButton){
            cl.show(main, "regularUserMainMenu");
        } else if (e.getSource() == viewAllTemplate) {
            JPanel temp = new JPanel();
            temp.setLayout(null);

            JScrollPane templates = data.getTemplates();
            templates.setBounds(25, 25, 400, 500);
            temp.add(templates);
            main.add(temp, "templateInfo");

            back.addActionListener(this);
            back.setBounds(515, 150, 70, 40);
            temp.add(back);

            cl.show(main, "templateInfo");
        } else if (e.getSource() == back) {
            main.remove(temp);
            cl.show(main, "templateMenu");
        }

    }
}
