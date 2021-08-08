package UserInterface.Graphical;

import UserInterface.GeneralPresenter;
import com.sun.tools.javac.comp.Check;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * GUI class for showing all templates stored in the system to an admin user. Asks the admin user which template they
 * would like to edit.
 */
public class CheckTemplateUI extends GeneralPresenter {
    private boolean flag = false;

    private GeneralPresenter editTemplate = new EditTemplateUI("checkTemplate");

    //JPanel
    JPanel checkTemplate = new JPanel();

    //TextArea/Field
    JScrollPane templateInfo;
    JTextArea prompt = new JTextArea("Please enter the ID of the template\n you would like to edit:");
    JTextField templateId = new JTextField();

    //Button
    JButton back = new JButton("Go back");
    JButton submit = new JButton("Submit");

    public CheckTemplateUI(String parent){
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag) {
            templateInfo = data.getTemplates();
            cl.show(main, "checkTemplate");
        } else {
            this.showTemplates();
            cl.show(main, "checkTemplate");
            frame.setVisible(true);
            flag = !flag;
        }

    }

    public void showTemplates() {
        checkTemplate.setLayout(null);
        main.add(checkTemplate, "checkTemplate");

        templateInfo = data.getTemplates();
        templateInfo.setBounds(25, 25, 400, 500);
        templateInfo.setBackground(new Color(213, 212, 212));
        checkTemplate.add(templateInfo);

        prompt.setBounds(450, 50, 225, 50);
        prompt.setEditable(false);
        checkTemplate.add(prompt);

        templateId.setBounds(515, 110, 70, 40);
        checkTemplate.add(templateId);

        submit.setBounds(515, 200, 70, 40);
        submit.addActionListener(this);
        checkTemplate.add(submit);

        back.setBounds(515, 250, 70, 40);
        back.addActionListener(this);
        checkTemplate.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            if (templateController.checkTemplate(templateId.getText())) {
                this.editTemplate.run();
            } else {
                this.prompt.setText("Invalid ID, please try again!");
            }
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }
}
