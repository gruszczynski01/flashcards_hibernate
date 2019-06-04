
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
//        SecureRandom random = new SecureRandom();


//
//        String login = "Franek";
//        String password = "haslo";
//        User current = new User(login, password);
//        User.addUser(current);
//        current.addBigBox("Maine fiszki", "mojmme");
//        current.addFlashcard(0, "AAAA", "aaaa");
//        current.addFlashcard(0, "BBBBB", "bbbbb");
//**************************************************************
//
//        EntityManager em =  DB.getInstance().getConnection();
//
//        em.getTransaction().begin();
//        User myUser = em.find(User.class, 6L);
//        BigBox bigBox = myUser.getUserBigBoxes().get(0);
//        em.persist(myUser);
//        em.getTransaction().commit();
//        bigBox.addFlashcard("Dupa", "dupa");
//
        //**************************************************************
//        em.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(this.getClass().getResource("/FXML/loginScreen.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

//        String passwordIntoDatabase = BCrypt.hashpw("mojeHaselko", BCrypt.gensalt());
//        System.out.println("password in DB: " + passwordIntoDatabase);
//
//        if(BCrypt.checkpw("mojeHaselko", passwordIntoDatabase))
//            System.out.println("Good passwd");
//        else
//            System.out.println("Worong password");
//        TranslateTransition transition = new TranslateTransition();
//        transition.setDuration(Duration.seconds(6));
//        transition.setToX(800);
//        transition.setNode(root);
//        transition.play();
    }
}