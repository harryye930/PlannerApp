package Controller;

import Interface.IController;

import java.util.ArrayList;
import java.util.Objects;

public class ActionController implements IController{
    private PlannerController plannerController;
    private AccessController accessController;
    private TemplateController templateController;

    private String currRetriever;
    private String currPlannerId;
    private String currTemplateId;

    public ActionController() {
        this.accessController = new AccessController();
        this.templateController = new TemplateController();
        this.plannerController = new PlannerController();

        accessController.load();
        templateController.load();
    }

    @Override
    public boolean logIn(String retriever, String password) {
        if (this.accessController.logIn(retriever, password)) {
            this.currRetriever = retriever;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String accountRole(String retriever) {
        return accessController.isAdmin(retriever);
    }

    @Override
    public String accountRole() {
        return this.accessController.isAdmin(this.currRetriever);
    }

    @Override
    public String getCurrentId() {
        return this.currRetriever;
    }

    @Override
    public String viewUserPlanners() {
        StringBuilder res = new StringBuilder();
        ArrayList<String> plannerIds = this.accessController.getPlanners(this.currRetriever);
        for (String plannerId: plannerIds) {
            res.append(this.plannerController.toString(Integer.parseInt(plannerId)));
            res.append("==================================");
        }
        return res.toString();
    }

    @Override
    public String viewPublicPlanners() {
        StringBuilder res = new StringBuilder();
        ArrayList<Integer> publicPlanners = plannerController.getPublicPlanners();
        for (int plannerId: publicPlanners) {
            res.append(plannerController.toString(plannerId));
            res.append("==================================");
        }
        return res.toString();
    }

    @Override
    public String createAccount(String email, String userName, String password) {
        String  retriever =  accessController.createAccount(email, userName, password);
        this.accessController.save();
        return retriever;
    }

    @Override
    public String createDailyPlanner() {
        String id =  ((Integer)plannerController.createNewDailyPlanner()).toString();
        this.plannerController.setPlannerAuthor(Integer.parseInt(id), this.currRetriever);
        this.accessController.setPlanner(this.currRetriever, id);
        this.accessController.save();
        return id;
    }

    @Override
    public String createProjectPlanner() {
        String id =  ((Integer)plannerController.createNewProjectPlanner()).toString();
        this.plannerController.setPlannerAuthor(Integer.parseInt(id), this.currRetriever);
        this.accessController.setPlanner(this.currRetriever, id);
        this.accessController.save();
        return id;
    }

    @Override
    public String viewTemplates() {
        return templateController.detailViewAllTemplates();
    }

    @Override
    public boolean checkTemplate(String id) {
        for (String tempId: templateController.getAllTemplateIds()) {
            if (Objects.equals(id, tempId)) {
                this.currTemplateId = id;
                return true;
            }
        }
        return false;
    }

    @Override
    public String viewPlanner(String id) {
        return plannerController.toString(Integer.parseInt(id));
    }

    @Override
    public String viewPlanner() {
        return plannerController.toString(Integer.parseInt(this.currPlannerId));
    }

    @Override
    public boolean getPlannerStatus() {
        return this.plannerController.getPrivacyStatus(Integer.parseInt(this.currPlannerId)).equals("private");
    }

    @Override
    public boolean getPlannerStatus(String id) {
        return this.plannerController.getPrivacyStatus(Integer.parseInt(id)).equals("private");
    }

    @Override
    public void setPlannerStatus() {
        if (getPlannerStatus()) {
            this.plannerController.changePrivacyStatus(Integer.parseInt(this.currPlannerId), "public");
        } else {
            this.plannerController.changePrivacyStatus(Integer.parseInt(this.currPlannerId), "private");
        }
    }

    @Override
    public boolean deletePlanner(String id) {
        boolean flag = plannerController.deletePlanner(Integer.parseInt(id));
        if (flag) {
            this.accessController.removePlanner(currRetriever, id);
            this.accessController.save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkPlanner(String id) {
        ArrayList<String> plannerIds = this.accessController.getPlanners(this.currRetriever);
        ArrayList<Integer> publicIds = this.plannerController.getPublicPlanners();
        if (plannerIds.contains(id) || publicIds.contains(Integer.parseInt(id))) {
            this.currPlannerId = id;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editPlanner(String timeSlot, String agenda) {
        boolean res = this.plannerController.edit(Integer.parseInt(this.currPlannerId), timeSlot, agenda);
        this.accessController.save();
        return res;
    }

    @Override
    public String getPlannerType() {
        return this.plannerController.getType(Integer.parseInt(this.currPlannerId));
    }
}
