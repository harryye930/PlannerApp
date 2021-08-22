package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;
import builder.IForm;
import builder.formBuilder.FormBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FriendUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    private final Map<String, String> labelToStrings = new UIGateway().loadFriendUITexts();

    private IForm form;

    // Pane/Panel
    JScrollPane friendsInfo;
    JPanel friendUI = new JPanel();


    public FriendUI(GeneralPresenter parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            friendsInfo = data.getFriendsInfo(friendUI);
            cl.show(main, "friendPage");
        } else {
            this.showFriendUI();
            cl.show(main, "friendPage");
            flag = !flag;
        }
    }

    private void showFriendUI() {
        friendUI.setLayout(null);
        main.add(friendUI, "friendPage");
        friendsInfo = data.getFriendsInfo(friendUI);

        FormBuilder fb = new FormBuilder();
        fb.setBounds(475, 50, 300, 240);
        fb.addLabel("prompt", labelToStrings.get("prompt"));
        fb.addTextField("id");
        fb.addSubmitButton("addFriend", labelToStrings.get("addFriend"));
        fb.addSubmitButton("deleteFriend", labelToStrings.get("deleteFriend"));
        fb.addSuperButton("goBack", labelToStrings.get("goBack"), this.getParent());
        fb.addListener(this);

        form = fb.getForm();
        friendUI.add(form.getPanel());
    }

    private void update() {
        friendsInfo = data.getFriendsInfo(friendUI);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.get("addFriend")) {
            accessController.addFriend(accessController.getCurrUserId(),
                    ((JTextField) form.get("id")).getText());
            update();
        } else if (e.getSource() == form.get("deleteFriend")) {
            accessController.deleteFriend(accessController.getCurrUserId(),
                    ((JTextField) form.get("id")).getText());
            update();
        }
    }
}
