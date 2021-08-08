package UserInterface.Graphical;

import UserInterface.GeneralPresenter;
import com.sun.tools.javah.Gen;

import java.awt.event.ActionEvent;

/**
 * GUI class for displaying planner options for an admin user.
 * Options include: change privacy setting of any planner, edit/interact with any planner, delete any planner
 */
public class AdminPlannerOptionUI extends GeneralPresenter {

    public AdminPlannerOptionUI(String parent) {
        this.setParent(parent);
    }

    @Override
    public void run(){
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
