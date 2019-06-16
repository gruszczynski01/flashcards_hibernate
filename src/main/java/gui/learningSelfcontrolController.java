package gui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class learningSelfcontrolController implements Initializable {
    RotateTransition rtAnimation;
    FadeTransition fadeAnimationOFF;
    FadeTransition fadeAnimationON;
    PhongMaterial yellowMaterial;
    SequentialTransition sequentialTransitionON_OFF;
    FadeTransition checkButtonOFF;
    FadeTransition checkButtonON;
    FadeTransition goodButtonON;
    FadeTransition goodButtonOFF;
    FadeTransition wrongButtonON;
    FadeTransition wrongButtonOFF;
    SequentialTransition showChoiceButtons;
    SequentialTransition showCheckButton;

    @FXML
    private StackPane flashcardStackPane;

    @FXML
    private Box flashcardBox;

    @FXML
    private Text flashcardText;

    @FXML
    private Button checkButton;

    @FXML
    private ImageView goodAnswerButton;

    @FXML
    private ImageView wrongAnswerButton;

    @FXML
    void check(ActionEvent event) {
        rtAnimation.play();
        sequentialTransitionON_OFF.play();
        checkButtonOFF.play();
        goodAnswerButton.setDisable(false);
        wrongAnswerButton.setDisable(false);
        checkButton.setDisable(true);
    }




    @FXML
    void goodAnswerAction(MouseEvent event) {
        goodAnswerButton.setDisable(true);
        wrongAnswerButton.setDisable(true);
        checkButton.setDisable(false);
        goodButtonOFF.play();
        wrongButtonOFF.play();

    }

    @FXML
    void wrongAnswerAction(MouseEvent event) {
        goodAnswerButton.setDisable(true);
        wrongAnswerButton.setDisable(true);
        checkButton.setDisable(false);
        goodButtonOFF.play();
        wrongButtonOFF.play();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Color myYellow = Color.web("0xf2e830");
        yellowMaterial = new PhongMaterial();
        yellowMaterial.setSpecularColor(Color.ORANGE);
        yellowMaterial.setDiffuseColor(myYellow);
        flashcardBox.setMaterial(yellowMaterial);

        rtAnimation = new RotateTransition(Duration.seconds(1.5), flashcardStackPane);
        rtAnimation.setFromAngle(0);
        rtAnimation.setToAngle(360);

        fadeAnimationOFF = new FadeTransition(Duration.seconds(0.5),flashcardText);
        fadeAnimationOFF.setFromValue(1);
        fadeAnimationOFF.setToValue(0);


        fadeAnimationON = new FadeTransition(Duration.seconds(1),flashcardText);
        //flascardText.rotateProperty()//
        fadeAnimationON.setFromValue(0);
        fadeAnimationON.setToValue(1);



        fadeAnimationOFF.setOnFinished( (x) -> {
            flashcardText.setText("NOWY :D");
            //fadeAnimationON.play();
        });
        sequentialTransitionON_OFF = new SequentialTransition(
                fadeAnimationOFF,
                new PauseTransition(Duration.millis(700)),
                fadeAnimationON
        );

        //---------------
        checkButtonOFF = new FadeTransition(Duration.seconds(0.7), checkButton);
        checkButtonOFF.setFromValue(1);
        checkButtonOFF.setToValue(0);
        goodButtonON = new FadeTransition(Duration.seconds(0.5), goodAnswerButton);
        goodButtonON.setFromValue(0);
        goodButtonON.setToValue(1);
        wrongButtonON = new FadeTransition(Duration.seconds(0.5), wrongAnswerButton);
        wrongButtonON.setFromValue(0);
        wrongButtonON.setToValue(1);
        checkButtonOFF.setOnFinished( x -> {
                    goodButtonON.play();
                    wrongButtonON.play();
        });
        checkButtonON = new FadeTransition(Duration.seconds(0.7), checkButton);
        checkButtonON.setFromValue(0);
        checkButtonON.setToValue(1);
        goodButtonOFF = new FadeTransition(Duration.seconds(0.5), goodAnswerButton);
        goodButtonOFF.setFromValue(1);
        goodButtonOFF.setToValue(0);
        wrongButtonOFF = new FadeTransition(Duration.seconds(0.5), wrongAnswerButton);
        wrongButtonOFF.setFromValue(1);
        wrongButtonOFF.setToValue(0);
        goodButtonOFF.setOnFinished( x -> {
            checkButtonON.play();
        });
//        wrongButtonOFF.setOnFinished( x -> {
//            checkButtonON.play();
//        });


        //rtAnimation.play();
    }
}
