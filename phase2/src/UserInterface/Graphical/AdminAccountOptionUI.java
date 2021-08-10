package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * GUI class for displaying account options for an admin user.
 * Options include: suspend any account by any number of days.
 */
public class AdminAccountOptionUI extends GeneralPresenter implements KeyListener {
    private String userId;
    private boolean flag = false;


    // Panel
    private JScrollPane accountsInfo;
    private final JPanel adminAccount = new JPanel();

    // Label
    private final JLabel prompt = new JLabel("<html>Please enter the user<br/> id you want to operate on:</html>");

    // Text
    private final JTextField text = new JTextField();

    // Button
    private  final JButton suspend = new JButton("Suspend Operation");
    private final JButton checkPlanner = new JButton("Check Planner");
    private final JButton back = new JButton("Go back");

    private final SuspendUserUI adminSuspendAccount = new SuspendUserUI("adminAccountOption");
    private final AdminCheckPlannerUI adminCheckPlannerUI = new AdminCheckPlannerUI("adminAccountOption");

    public AdminAccountOptionUI(String parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
        if (flag) {
            accountsInfo = data.getAccounts();
            cl.show(main, "adminAccountOption");
        } else {
            this.showMenu();
            cl.show(main, "adminAccountOption");
            flag = !flag;
        }
    }

    private void showMenu() {
        adminAccount.setLayout(null);
        main.add(adminAccount, "adminAccountOption");

        accountsInfo = data.getAccounts();
        accountsInfo.setBounds(25, 25, 400, 500);
        adminAccount.add(accountsInfo);

        prompt.setBounds(450, 50, 200, 50);
        adminAccount.add(prompt);

        text.setBounds(450, 100, 200, 30);
        text.addKeyListener(this);
        adminAccount.add(text);

        suspend.setBounds(515, 150, 100, 40);
        checkPlanner.setBounds(515, 200, 100, 40);
        back.setBounds(515, 250, 100, 40);

        adminAccount.add(suspend);
        adminAccount.add(checkPlanner);
        adminAccount.add(back);

        suspend.addActionListener(this);
        checkPlanner.addActionListener(this);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == suspend) {
            this.adminSuspendAccount.run();
        } else if (e.getSource() == checkPlanner) {
            this.adminCheckPlannerUI.run();
        } else if (e.getSource() == back) {
            cl.show(main, this.getParent());
        }
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == text) {
            System.out.println(text.getText());
            adminSuspendAccount.setUserId(text.getText());
            adminCheckPlannerUI.setUserId(text.getText());
        }
    }
}
