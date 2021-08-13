package UserInterface.Graphical;

import Gateway.UIGateway;
import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

// TODO: Extract the left panel (the JScrollPane)
public class FriendUI extends GeneralPresenter implements ActionListener {
    private boolean flag = false;
    private Map<String, String> labelToStrings = new UIGateway().loadFriendUITexts();

    // Pane/Panel
    JScrollPane friendsInfo;
    JPanel friendUI = new JPanel();

    // Text
    JTextField text = new JTextField();

    // Button
    JButton addFriend = new JButton(labelToStrings.get("addFriend"));
    JButton deleteFriend = new JButton(labelToStrings.get("deleteFriend"));
    JButton back = new JButton(labelToStrings.get("goBack"));

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

        JLabel prompt = new JLabel(labelToStrings.get("prompt"));
        prompt.setBounds(475, 50, 300, 50);
        text.setBounds(475, 100, 200, 40);

        friendUI.add(prompt);
        friendUI.add(text);

        addFriend.setBounds(515, 150, 100, 40);
        deleteFriend.setBounds(515, 200, 100, 40);
        back.setBounds(515, 250, 100, 40);
        friendUI.add(addFriend);
        friendUI.add(deleteFriend);
        friendUI.add(back);
        addFriend.addActionListener(this);
        deleteFriend.addActionListener(this);
        back.addActionListener(this);
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
        if (e.getSource() == addFriend) {
            accessController.addFriend(accessController.getCurrUserId(), text.getText());
            update();
        } else if (e.getSource() == deleteFriend) {
            accessController.deleteFriend(accessController.getCurrUserId(), text.getText());
            update();
        } else if (e.getSource() == back) {
            this.getParent().run();
        }
    }
}
