
import boxes.BigBox;
import database.DB;
import flashcards.Flashcard;
import org.mindrot.jbcrypt.BCrypt;
import users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) {
//        SecureRandom random = new SecureRandom();
//        String salt = random.generateSeed(20).toString();
//        byte seed[] = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);
//        seed = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);
//        seed = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);
//
//        seed = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);
//        seed = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);
//
//        seed = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);
//        seed = random.generateSeed(20);
//        System.out.println("Our random string: " + seed);


//         String passwordIntoDatabase = BCrypt.hashpw("mojeHaselko", BCrypt.gensalt());
//        System.out.println("password in DB: " + passwordIntoDatabase);
//
//        if(BCrypt.checkpw("mojeHaselko", passwordIntoDatabase))
//            System.out.println("Good passwd");
//        else
//            System.out.println("Worong password");
//
//        String login = "Franek";
//        String password = "haslo";
//        User current = new User(login, password);
//        User.addUser(current);
//        current.addBigBox("Maine fiszki", "mojmme");
//        current.addFlashcard(0, "AAAA", "aaaa");
//        current.addFlashcard(0, "BBBBB", "bbbbb");


        EntityManager em =  DB.getInstance().getConnection();

        em.getTransaction().begin();
        //em.remove(em.find(User.class, 3));
        User myUser = em.find(User.class, 6L);
        myUser.addFlashcard(0, "CCCCCC", "ccccccc" );
        em.getTransaction().commit();
    }
}
