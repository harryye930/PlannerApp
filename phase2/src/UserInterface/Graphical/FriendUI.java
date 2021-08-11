package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;

// TODO: Extract the left panel (the JScrollPane)
public class FriendUI extends GeneralPresenter {
    private boolean flag = false;

    // Pane/Panel
    JScrollPane friendsInfo;
    JPanel friendUI = new JPanel();

    // Text
    JTextField text = new JTextField();

    // Button
    JButton addFriend = new JButton("Add friend");
    JButton deleteFriend = new JButton("Delete friend");
    JButton back = new JButton("Go back");

    public FriendUI(String parent) {
        this.setParent(parent);
    }
    /**
     * run the presenter from the beginning.
     */
    @Override
    public void run() {
        if (flag) {
            friendsInfo = data.getFriendsInfo();
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
        friendsInfo = data.getFriendsInfo();
        friendsInfo.setBounds(20, 20, 450, 500);
        friendUI.add(friendsInfo);

        JLabel prompt = new JLabel("<html>Please enter the user ID you want<br/> to operate on:</html>");
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
        friendsInfo = data.getFriendsInfo();
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
            cl.show(main, this.getParent());
        }
    }
}
