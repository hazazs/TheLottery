package TheLottery;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TheLotteryViewController implements Initializable {
//<editor-fold defaultstate="collapsed" desc="Class variables">
    private boolean allInteger, allValid, allUnique;
    private final ArrayList<Integer> redDotIndexI = new ArrayList<>();
    private final ArrayList<Integer> redDotIndexV = new ArrayList<>();
    private final ArrayList<Label> redDotLabelU = new ArrayList<>();
    private final ArrayList<Label> redDotLabelD = new ArrayList<>();
    private final HashSet<String> tfmx = new HashSet<>();
    private final int[] mynumbers = new int[5];
    private final int[] rnumbers = new int[5];
    private int scoreNumber;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="FXML items">
    @FXML
    private TextField tfm1, tfm2, tfm3, tfm4, tfm5, tfr1, tfr2, tfr3, tfr4, tfr5;
    @FXML
    private Label label3, label4, label6, dot01, dot02, dot03, dot04, dot05, label7, dot06, dot07, dot08, dot09, dot10, label8;
    @FXML
    private Pane errorPane;
    @FXML
    private HBox hBox1, hBox2;
//</editor-fold>
    @FXML
    private void handleButtonAction(ActionEvent event) {
        setVisibility(false);
        inputValidation();
        if (allInteger && allValid && allUnique) {
            errorPane.setVisible(false);
            draw();
            setVisibility(true);
            evaluate();
        } else
            printError();
    }
    private void setVisibility(boolean b) {
        label3.setVisible(b);
        tfr1.setVisible(b);
        tfr2.setVisible(b);
        tfr3.setVisible(b);
        tfr4.setVisible(b);
        tfr5.setVisible(b);
        label4.setVisible(b);
    }
    private void inputValidation() {
        allIntegerCheck();
        allValidCheck();
        allUniqueCheck();
    }
    private void allIntegerCheck() {
        allInteger = true;
        redDotIndexI.clear();
        isInteger(0, tfm1);
        isInteger(1, tfm2);
        isInteger(2, tfm3);
        isInteger(3, tfm4);
        isInteger(4, tfm5);
    }
    private void isInteger(int i, TextField tfmx) {
        try {
            mynumbers[i] = Integer.parseInt(tfmx.getText());
        } catch(Exception e) {
            allInteger = false;
            redDotIndexI.add(i);
        }
    }
    private void allValidCheck() {
        allValid = true;
        redDotIndexV.clear();
        isValid(0, tfm1);
        isValid(1, tfm2);
        isValid(2, tfm3);
        isValid(3, tfm4);
        isValid(4, tfm5);
    }
    private void isValid(int i, TextField tfmx) {
        try {
            if (Integer.parseInt(tfmx.getText()) < 1 || Integer.parseInt(tfmx.getText()) > 90) {
                allValid = false;
                redDotIndexV.add(i);
            }
        } catch (Exception e) {
        }
    }
    private void allUniqueCheck() {
        allUnique = true;
        tfmx.clear();
        tfmx.add(tfm1.getText());
        tfmx.add(tfm2.getText());
        tfmx.add(tfm3.getText());
        tfmx.add(tfm4.getText());
        tfmx.add(tfm5.getText());
        if (tfmx.size() <5)
            allUnique = false;
    }
    private void draw() {
        for (int i = 0; i < 5; i++)
            createRandomNumber(i);
        tfr1.setText(String.valueOf(rnumbers[0]));
        tfr2.setText(String.valueOf(rnumbers[1]));
        tfr3.setText(String.valueOf(rnumbers[2]));
        tfr4.setText(String.valueOf(rnumbers[3]));
        tfr5.setText(String.valueOf(rnumbers[4]));
    }
    private void createRandomNumber(int i) {
        rnumbers[i] = (int) ((Math.random() * 90) + 1);
        for (int j = i; j > 0; j--)
            if (rnumbers[i] == rnumbers[j - 1])
                createRandomNumber(i);
    }
    private void evaluate() {
        scoreCounter();
        printScore();
    }
    private void scoreCounter() {
        scoreNumber = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (mynumbers[i] == rnumbers[j])
                    scoreNumber++;
    }
    private void printScore() {
        switch (scoreNumber) {
            case 1:
                printScoreVariant("#d82700", "1 HIT!"); break;
            case 2:
                printScoreVariant("#9f6000", "2 HIT!"); break;
            case 3:
                printScoreVariant("#609f00", "3 HIT!"); break;
            case 4:
                printScoreVariant("#27d800", "4 HIT!"); break;
            case 5:
                printScoreVariant("#00ff00", "5 HIT!"); break;
            default:
                printScoreVariant("#ff0000", "0 HIT!");
        }
    }
    private void printScoreVariant(String clr, String hit) {
        label4.setTextFill(Color.web(clr));
        label4.setText(hit);
    }
    private void printError() {
        errorPane.setVisible(true);
        hBox1.setVisible(false);
        hBox2.setVisible(false);
        label8.setVisible(false);
        for (int i = 0; i < 5; i++) {
            redDotLabelU.get(i).setStyle("-fx-background-image: url(/greenDot.png)");
            redDotLabelD.get(i).setStyle("-fx-background-image: url(/greenDot.png)");
        }
        if (allInteger) {
            if (allValid) {
                label6.setText("");
                label7.setText("");
            } else {
                textAndColor("Between 1 and 90:", redDotLabelU, redDotIndexV);
                label7.setText("");
              }
        } else {
            if (allValid) {
                textAndColor("Round number:", redDotLabelU, redDotIndexI);
                label7.setText("");
            } else {
                if (label6.getText().equals("Between 1 and 90:")) {
                    textAndColor("Between 1 and 90:", redDotLabelU, redDotIndexV);
                    textAndColor("Round number:", redDotLabelD, redDotIndexI);
                } else {
                    textAndColor("Round number:", redDotLabelU, redDotIndexI);
                    textAndColor("Between 1 and 90:", redDotLabelD, redDotIndexV);
                  }
              }
          }
        if (!allUnique)
            label8.setVisible(true);
    }
    private void textAndColor(String str, ArrayList<Label> listL, ArrayList<Integer> listI) {
        if (listL == redDotLabelU) {
            label6.setText(str);
            hBox1.setVisible(true);
        }
            else {
                label7.setText(str);
                hBox2.setVisible(true);
            }
        listI.forEach((i) -> listL.get(i).setStyle("-fx-background-image: url(/redDot.png)"));
    }
    private void dotsToArrayList() {
        redDotLabelU.add(dot01);
        redDotLabelU.add(dot02);
        redDotLabelU.add(dot03);
        redDotLabelU.add(dot04);
        redDotLabelU.add(dot05);
        redDotLabelD.add(dot06);
        redDotLabelD.add(dot07);
        redDotLabelD.add(dot08);
        redDotLabelD.add(dot09);
        redDotLabelD.add(dot10);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dotsToArrayList();
    }
}