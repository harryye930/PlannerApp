package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TemplateOptionUI extends GeneralPresenter {
    private boolean flag = false;
    // all buttons
    private final JButton viewAllTemplate = new JButton("View All Template");
    private final JButton returnToMainMenuButton= new JButton("Return to Main Menu");

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


        }


    }
}
