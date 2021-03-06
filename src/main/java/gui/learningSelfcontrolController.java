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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
/**
 * Kontroler sceny, na ktorej uczymy sie w trybie samokontroli
 */
public class learningSelfcontrolController implements Initializable {
    public static Long chosenBigBoxId;
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
    List<Flashcard> allFlashcards = new ArrayList<>();
    List<Flashcard> goodAnsweredList = new ArrayList<>();
    List<Flashcard> wrongAnsweredList = new ArrayList<>();
    Flashcard currentFlashcard;
    String textToInsert;


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
    private Text smallBoxNumberField;

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
        fadeAnimationOFF.setOnFinished( (x) -> {
            flashcardText.setText(textToInsert);
        });

        fadeAnimationON = new FadeTransition(Duration.seconds(1),flashcardText);
        fadeAnimationON.setFromValue(0);
        fadeAnimationON.setToValue(1);

        sequentialTransitionON_OFF = new SequentialTransition(
                fadeAnimationOFF,
                new PauseTransition(Duration.millis(700)),
                fadeAnimationON
        );
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

    /**
     * Metoda wywolujaca animacje pokazania poprawnej odpowiedzi po drugiej stroni fiszki
     * @param event
     */
    @FXML
    void check(ActionEvent event) {
        checkButton.setDisable(true);
        textToInsert = currentFlashcard.getBackSide();
        checkButtonOFF.play();
        rtAnimation.setOnFinished( x ->{
            goodAnswerButton.setDisable(false);
            wrongAnswerButton.setDisable(false);
        });
        rtAnimation.play();
        sequentialTransitionON_OFF.play();
    }

    /**
     * Metoda zawierajaca zbior akcji wykonywanych po uznaniu odpowidzi za poprawna
     * @param event
     */
    @SuppressWarnings("Duplicates")
    @FXML
    void goodAnswerAction(MouseEvent event) {
        goodAnswerButton.setDisable(true);
        wrongAnswerButton.setDisable(true);
        goodAnsweredList.add(currentFlashcard);
        allFlashcards.remove(currentFlashcard);

        if(allFlashcards.size() == 0){
            updateBigbox();
            changeScreen(AFTER_LEARNING_FXML);
        }else{
            currentFlashcard = allFlashcards.get(0);
            textToInsert = currentFlashcard.getFrontSide();
            rtAnimation.setOnFinished( x ->{
                checkButton.setDisable(false);
            });
            rtAnimation.play();
            sequentialTransitionON_OFF.play();


            goodButtonOFF.play();
            wrongButtonOFF.play();
            smallBoxNumberField.setText(Integer.toString(currentFlashcard.getSmallBoxNumber()));
        }
    }
    /**
     * Metoda zawierajaca zbior akcji wykonywanych po uznaniu odpowidzi za bledna
     * @param event
     */
    @SuppressWarnings("Duplicates")
    @FXML
    void wrongAnswerAction(MouseEvent event) {
        goodAnswerButton.setDisable(true);
        wrongAnswerButton.setDisable(true);
        wrongAnsweredList.add(currentFlashcard);
        allFlashcards.remove(currentFlashcard);

        if(allFlashcards.size() == 0){
            updateBigbox();
            changeScreen(AFTER_LEARNING_FXML);
        }else {
            currentFlashcard = allFlashcards.get(0);
            textToInsert = currentFlashcard.getFrontSide();
            rtAnimation.setOnFinished( x ->{
                checkButton.setDisable(false);
            });
            rtAnimation.play();
            sequentialTransitionON_OFF.play();
            goodButtonOFF.play();
            wrongButtonOFF.play();
            smallBoxNumberField.setText(Integer.toString(currentFlashcard.getSmallBoxNumber()));
        }

    }
    /**
     * Metoda aktualizujaca stan fiszek po przejsciu przez wyszystkie fiszki danym pudelku
     */
    @SuppressWarnings("Duplicates")
    public void updateBigbox(){
        System.out.println("GOOD SIZE: " + goodAnsweredList.size());
        System.out.println("wrong SIZE: " + wrongAnsweredList.size());
        goodAnsweredList.stream().forEach( Flashcard -> {
            if(Flashcard.getSmallBoxNumber() < 4) {
                EntityManager em = DB.getInstance().getConnection();
                em.getTransaction().begin();
                String hql = "UPDATE Flashcard fc set fc.smallBoxNumber = :newNumber where fc.flashcardId = :FCID";
                Query query = em.createQuery(hql);
                query.setParameter("newNumber", Flashcard.getSmallBoxNumber()+1);
                query.setParameter("FCID", Flashcard.getFlascardId());
                query.executeUpdate();
                em.getTransaction().commit();
                em.close();
            }
            else{
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
        wrongAnsweredList.stream().forEach( Flashcard -> {
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

    @FXML
    void back(ActionEvent event) {
        changeScreen(WELCOME_SCREEN_FXML);
    }

}
