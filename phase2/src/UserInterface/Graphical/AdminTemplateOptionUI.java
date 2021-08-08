package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * GUI class for displaying template options for an admin user.
 * Options include: edit template, return to main menu.
 */
public class AdminTemplateOptionUI extends GeneralPresenter {
    private boolean flag = false;
    // all buttons
    JButton editTemplate = new JButton("Edit Template");
    JButton returnToMainMenuButton= new JButton("Return to Main Menu");

    // panel
    private final JPanel templateMenu = new JPanel();

    // menu text
    private JLabel prompt;

    private GeneralPresenter checkTemplate = new CheckTemplateUI("templateMenu");

    public AdminTemplateOptionUI(String parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag) {
            cl.show(main, "templateMenu");
        } else {
            this.buildTemplateMenu();
            cl.show(main, "templateMenu");
            frame.setVisible(true);
            flag = !flag;
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

        panel.add(editTemplate);
        panel.add(returnToMainMenuButton);

        editTemplate.addActionListener(this);
        returnToMainMenuButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnToMainMenuButton){
            cl.show(main, "adminUserMainMenu");
        } else if (e.getSource() == editTemplate) {
            this.checkTemplate.run();
        }
    }
}
