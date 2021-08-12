package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: combine with AccountOptionUI
/**
 * GUI class for displaying template options for an admin user.
 * Options include: create template, edit template, return to main menu.
 */
public class AdminTemplateOptionUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    // all buttons
    JButton createTemplate = new JButton("Create Template");
    JButton editTemplate = new JButton("Edit Template");
    JButton returnToMainMenuButton= new JButton("Return to Main Menu");

    // panel
    private final JPanel templateMenu = new JPanel();

    // menu text
    private JLabel prompt;

    private GeneralPresenter createTemplateUI = new CreateTemplateUI(this);
    private GeneralPresenter checkTemplateUI = new CheckTemplateUI(this);

    public AdminTemplateOptionUI(GeneralPresenter parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag) {
            cl.show(main, "adminTemplateMenu");
        } else {
            this.buildTemplateMenu();
            cl.show(main, "adminTemplateMenu");
            frame.setVisible(true);
            flag = !flag;
        }
    }

    private void buildTemplateMenu(){
        main.add(templateMenu, "adminTemplateMenu");
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
        panel.setLayout(new GridLayout(3, 1));
        templateMenu.add(panel);

        panel.add(createTemplate);
        panel.add(editTemplate);
        panel.add(returnToMainMenuButton);

        createTemplate.addActionListener(this);
        editTemplate.addActionListener(this);
        returnToMainMenuButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnToMainMenuButton){
            cl.show(main, "adminUserMainMenu");
        } else if (e.getSource() == editTemplate){
            this.checkTemplateUI.run();
        } else if (e.getSource() == createTemplate){
            this.createTemplateUI.run();
        }
    }
}
