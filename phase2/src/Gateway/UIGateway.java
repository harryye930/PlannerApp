package Gateway;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class UIGateway extends Reader{

    private String folderPath;
    private final String originalFolderPath = "data/";
    private final String alternativePath = "phase2/Data/";

    public UIGateway() {
        File file = new File(originalFolderPath);
        try {
            if (file.createNewFile() && file.delete()) {
                folderPath = alternativePath;
            } else {
                folderPath = originalFolderPath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> loadAccountOptionUITexts(){
        String accountOptionUIFilePath = folderPath + "AccountOptionUITexts.txt";
        return super.readTextFile(accountOptionUIFilePath);
    }

    public Map<String, String> loadAdminAccountOptionUITexts(){
        String adminAccountOptionUIFilePath = folderPath + "AdminAccountOptionUITexts.txt";
        return super.readTextFile(adminAccountOptionUIFilePath);
    }

    public Map<String, String> loadAdminAccountUITexts(){
        String adminAccountUIFilePath = folderPath + "AdminAccountUITexts.txt";
        return super.readTextFile(adminAccountUIFilePath);
    }

    public Map<String, String> loadAdminCheckPlannerUITexts(){
        String adminCheckPlannerUIFilePath = folderPath + "AdminCheckPlannerUITexts.txt";
        return super.readTextFile(adminCheckPlannerUIFilePath);
    }

    public Map<String, String> loadAdminTemplateOptionUITexts(){
        String adminTemplateOptionUIFilePath = folderPath + "AdminTemplateOptionUITexts.txt";
        return super.readTextFile(adminTemplateOptionUIFilePath);
    }

    public Map<String, String> loadCheckPlannerUITexts(){
        String checkPlannerUIFilePath = folderPath + "CheckPlannerUITexts.txt";
        return super.readTextFile(checkPlannerUIFilePath);
    }

    public Map<String, String> loadCheckTemplateUITexts(){
        String checkTemplateUIFilePath = folderPath + "CheckTemplateUITexts.txt";
        return super.readTextFile(checkTemplateUIFilePath);
    }

    public Map<String, String> loadCreateAccountUITexts(){
        String createAccountUIFilePath = folderPath + "CreateAccountUITexts.txt";
        return super.readTextFile(createAccountUIFilePath);
    }

    public Map<String, String> loadCreatePlannerUITexts(){
        String createPlannerUIFilePath = folderPath + "CreatePlannerUITexts.txt";
        return super.readTextFile(createPlannerUIFilePath);
    }

    public Map<String, String> loadCreateTemplateUITexts(){
        String createTemplateUIFilePath = folderPath + "CreateTemplateUITexts.txt";
        return super.readTextFile(createTemplateUIFilePath);
    }

    public Map<String, String> loadEditTemplateUITexts(){
        //TODO: add EditPlannerUI, EditReminder UI after refactoring
        String editTemplateUIFilePath = folderPath + "EditTemplateUITexts.txt";
        return super.readTextFile(editTemplateUIFilePath);
    }

    public Map<String, String> loadFriendUITexts(){
        String friendUIFilePath = folderPath + "FriendUITexts.txt";
        return super.readTextFile(friendUIFilePath);
    }

    public Map<String, String> loadLoginUITexts(){
        String loginUIFilePath = folderPath + "LoginUITexts.txt";
        return super.readTextFile(loginUIFilePath);
    }

    public Map<String, String> loadPlannerOptionUITexts(){
        String plannerOptionUIFilePath = folderPath + "PlannerOptionUITexts.txt";
        return super.readTextFile(plannerOptionUIFilePath);
    }

    public Map<String, String> loadRegularAccountUITexts(){
        String regularAccountUIFilePath = folderPath + "RegularAccountUITexts.txt";
        return super.readTextFile(regularAccountUIFilePath);
    }

    public Map<String, String> loadSuspendUserUITexts(){
        String suspendUserUIFilePath = folderPath + "SuspendUserUITexts.txt";
        return super.readTextFile(suspendUserUIFilePath);
    }

    public Map<String, String> loadTemplateOptionUITexts(){
        String templateOptionUIFilePath = folderPath + "TemplateOptionUITexts.txt";
        return super.readTextFile(templateOptionUIFilePath);
    }
}
