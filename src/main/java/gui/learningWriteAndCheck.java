package gui;

import boxes.BigBox;
import database.DB;
import flashcards.Flashcard;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.AFTERLEARNING;
import static gui.ControllersCoordinator.changeScreen;

public class learningWriteAndCheck implements Initializable {
    public static Long chosenBigBoxId;
    RotateTransition rtAnimation;
    FadeTransition textOff;
    FadeTransition textOn;
    PhongMaterial yellowMaterial;
    SequentialTransition textOff_On;
    List<Flashcard> allFlashcards = new ArrayList<>();
    List<Flashcard> goodAnsweredList = new ArrayList<>();
    List<Flashcard> wrongAnsweredList = new ArrayList<>();
    Flashcard currentFlashcard;
    String textToInsert;

    @SuppressWarnings("Duplicates")
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


        textOff = new FadeTransition(Duration.seconds(0.5),flashcardText);
        textOff.setFromValue(1);
        textOff.setToValue(0);


        textOn = new FadeTransition(Duration.seconds(1),flashcardText);
        //flascardText.rotateProperty()//
        textOn.setFromValue(0);
        textOn.setToValue(1);

        textOff.setOnFinished( (x) -> {
            flashcardText.setText(textToInsert);
            //fadeAnimationON.play();
        });
        textOff_On = new SequentialTransition(
                textOff,
                new PauseTransition(Duration.millis(700)),
                textOn
        );
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        BigBox chosenBigBox = em.find(BigBox.class, chosenBigBoxId);

        chosenBigBox.getFlashcards().stream().forEach(Flashcard -> {
            allFlashcards.add(Flashcard);
        });
        allFlashcards.sort(Flashcard::compareTo);
        em.getTransaction().commit();
        em.close();
        currentFlashcard = allFlashcards.get(0);
        flashcardText.setText(currentFlashcard.getFrontSide());
        smallBoxNumberField.setText(Integer.toString(currentFlashcard.getSmallBoxNumber()));
    }

    @FXML
    private StackPane flashcardStackPane;

    @FXML
    private Box flashcardBox;

    @FXML
    private Text flashcardText;

    @FXML
    private Button nextButton;

    @FXML
    private Button checkButton;

    @FXML
    private Text smallBoxNumberField;

    @FXML
    private TextField answerField;
    @FXML
    private ImageView wrongAnswerIcon;

    @FXML
    private ImageView goodAnswerIcon;

    @FXML
    private Rectangle goodAnswerBG;

    @FXML
    private Rectangle wrongAnwerBG;

    @FXML
    void check(ActionEvent event) {
        if (answerField.getText().equals(currentFlashcard.getBackSide())){
            System.out.println("DOBRA ODP: 1-" + currentFlashcard.getBackSide() + " 2-" + answerField.getText());
            textToInsert = currentFlashcard.getBackSide();
            answerField.clear();
            checkButton.setDisable(true);
            checkButton.setVisible(false);
            rtAnimation.setOnFinished(x -> {
                goodAnswerIcon.setOpacity(1);
                goodAnswerBG.setVisible(true);
                nextButton.setVisible(true);
                nextButton.setDisable(false);
            });
            rtAnimation.play();
            textOff_On.play();
            goodAnsweredList.add(currentFlashcard);
            allFlashcards.remove(currentFlashcard);

        }else {
            System.out.println("ZŁA ODP: 1-" + currentFlashcard.getBackSide() + " 2-" + answerField.getText());

            textToInsert = currentFlashcard.getBackSide();
            answerField.clear();
            checkButton.setDisable(true);
            checkButton.setVisible(false);
            rtAnimation.setOnFinished(x -> {
                wrongAnswerIcon.setOpacity(1);
                wrongAnwerBG.setVisible(true);
                nextButton.setVisible(true);
                nextButton.setDisable(false);
            });
            rtAnimation.play();
            textOff_On.play();
            wrongAnsweredList.add(currentFlashcard);
            allFlashcards.remove(currentFlashcard);
        }
        answerField.setOpacity(0);
        answerField.setDisable(true);
    }

    @FXML
    void next(ActionEvent event) {
        rtAnimation.setOnFinished(x -> {
        });
        answerField.setOpacity(1);
        answerField.setDisable(false);
        goodAnswerIcon.setOpacity(0);
        wrongAnswerIcon.setOpacity(0);
        goodAnswerBG.setVisible(false);
        wrongAnwerBG.setVisible(false);
        if(allFlashcards.size() == 0){
            updateBigbox();
            changeScreen(AFTERLEARNING);
        }else {
            currentFlashcard = allFlashcards.get(0);
            textToInsert = currentFlashcard.getFrontSide();
            nextButton.setVisible(false);
            nextButton.setDisable(true);
            checkButton.setVisible(true);
            checkButton.setDisable(false);
            rtAnimation.play();
            textOff_On.play();
        }
        smallBoxNumberField.setText(Integer.toString(currentFlashcard.getSmallBoxNumber()));

    }
    @SuppressWarnings("Duplicates")
    public void updateBigbox() {
        System.out.println("GOOD SIZE: " + goodAnsweredList.size());
        System.out.println("wrong SIZE: " + wrongAnsweredList.size());
        goodAnsweredList.stream().forEach(Flashcard -> {
            if (Flashcard.getSmallBoxNumber() < 4) {
                EntityManager em = DB.getInstance().getConnection();
                em.getTransaction().begin();
                String hql = "UPDATE Flashcard fc set fc.smallBoxNumber = :newNumber where fc.flashcardId = :FCID";
                Query query = em.createQuery(hql);
                query.setParameter("newNumber", Flashcard.getSmallBoxNumber() + 1);
                query.setParameter("FCID", Flashcard.getFlascardId());
                query.executeUpdate();
                //System.out.println("HQL: " + query.executeUpdate());
                em.getTransaction().commit();
                em.close();
            } else {
                EntityManager em = DB.getInstance().getConnection();
                em.getTransaction().begin();
                String hql = "DELETE from Flashcard fc where fc.flashcardId  = :FCID";

                Query query = em.createQuery(hql);
                query.setParameter("FCID", Flashcard.getFlascardId());
                query.executeUpdate();
                em.getTransaction().commit();
                em.close();
            }
        });
        wrongAnsweredList.stream().forEach(Flashcard -> {
            EntityManager em = DB.getInstance().getConnection();
            em.getTransaction().begin();
            String hql = "UPDATE Flashcard fc set fc.smallBoxNumber = 0 where fc.flashcardId = :FCID";
            Query query = em.createQuery(hql);
            query.setParameter("FCID", Flashcard.getFlascardId());
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        });


    }
}
