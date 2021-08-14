package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import com.sun.tools.javac.comp.Check;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

//TODO: combine with CheckPlannerUI
// TODO: Extract the left panel (the JScrollPane)
/**
 * GUI class for showing all templates stored in the system to an admin user. Asks the admin user which template they
 * would like to edit.
 */
public class CheckTemplateUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    private Map<String, String> labelToStrings = new UIGateway().loadCheckTemplateUITexts();

    private final GeneralPresenter editTemplate = new EditTemplateUI(this);

    //JPanel
    JPanel checkTemplate = new JPanel();

    //TextArea/Field
    JScrollPane templateInfo;
    JLabel prompt = new JLabel(labelToStrings.get("prompt"));
    JTextField templateId = new JTextField();

    //Button
    JButton back = new JButton(labelToStrings.get("goBack"));
    JButton submit = new JButton(labelToStrings.get("submit"));

    public CheckTemplateUI(GeneralPresenter parent){
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag) {
            templateInfo = data.getTemplates(checkTemplate);
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

        templateInfo = data.getTemplates(checkTemplate);

        prompt.setBounds(450, 50, 225, 50);
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
                this.prompt.setText(labelToStrings.get("invalidInput"));
            }
        } else if (e.getSource() == back) {
            this.getParent().run();
        }
    }
}
