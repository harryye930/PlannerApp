package UserInterface.Widgets;

import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MultiOptions extends Widget {
    private String prompt = "";
    private HashMap<Character, Widget> options = new HashMap<>();
    private char currOption = 'A';
    private boolean hold = true;
    private Character chosenOp = null;

    public MultiOptions() {
        super();
    }

    public MultiOptions(Widget parent, String prompt) {
        super();
        this.setParent(parent);
        this.setPrompt(prompt);
    }

    public char getChosenOp() {
        return this.chosenOp;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    @Override
    public void trigger() {
        System.out.println(this.toString());
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        char op = userInput.charAt(0);
        if (this.options.containsKey(op)) {
            this.chosenOp = op;
            this.child = this.options.get(this.chosenOp);
        } else {
            this.trigger();
        }
        if (!this.hold) {
            this.options.get(this.chosenOp).trigger();
        }
    }

    public void addBranch(Widget branch) {
        this.options.put(currOption, branch);
        currOption = (char) ((int) currOption + 1);
    }

//    @Override
//    public void setChild(Widget child) {
//        this.addBranch(child);
//    }

    @Override
    public String toString() {
        StringBuilder res;
        res = new StringBuilder(prompt + "\n");
        ArrayList<String> stringList = new ArrayList<>();
        for (char word: this.options.keySet()) {
            stringList.add(word + ": " + this.options.get(word));
        }
        this.optionSort(stringList);

        for (String str:stringList) {
            res.append(str).append("\n");
        }
        return res.toString();
    }

    private void optionSort(ArrayList<String> opLst) {
        for (int i = 0; i < opLst.size() - 1; i ++) {
            for (int j = i + 1; j < opLst.size() - 1; j++) {
                char curr = opLst.get(j).charAt(0);
                char next = opLst.get(j + 1).charAt(0);
                if ((int) curr > (int) next) {
                    String temp = opLst.get(i);
                    opLst.set(i, opLst.get(i + 1));
                    opLst.set(i + 1, temp);
                }
            }
        }
    }
}
