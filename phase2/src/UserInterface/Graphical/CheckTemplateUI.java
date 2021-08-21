package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import strategy.IForm;
import strategy.formGenerator.FormBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


/**
 * GUI class for showing all templates stored in the system to an admin user. Asks the admin user which template they
 * would like to edit.
 */
public class CheckTemplateUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadCheckTemplateUITexts();

    private IForm form;

    private final GeneralPresenter editTemplate = new EditTemplateUI(this);

    //JPanel
    JPanel checkTemplate = new JPanel();
    JScrollPane templateInfo;


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

        FormBuilder fb = new FormBuilder();
        fb.setBounds(450, 50, 225, 240);
        fb.addLabel("prompt", labelToStrings.get("prompt"));
        fb.addTextField("templateId");
        fb.addSubmitButton("submit", labelToStrings.get("prompt"));
        fb.addSuperButton("goBack", labelToStrings.get("goBack"), this.getParent());
        fb.addListener(this);

        form = fb.getForm();
        checkTemplate.add(form.getPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.get("submit")){
            if (templateController.checkTemplate(((JTextField)form.get("templateId")).getText())) {
                this.editTemplate.run();
            } else {
                ((JLabel) this.form.get("prompt")).setText(labelToStrings.get("invalidInput"));
            }
        }
        }
}
