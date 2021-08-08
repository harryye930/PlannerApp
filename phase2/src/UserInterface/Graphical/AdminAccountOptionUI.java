package UserInterface.Graphical;

import UserInterface.GeneralPresenter;

import java.awt.event.ActionEvent;

/**
 * GUI class for displaying account options for an admin user.
 * Options include: suspend any account by any number of days.
 */
public class AdminAccountOptionUI extends GeneralPresenter {

    public AdminAccountOptionUI(String parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
