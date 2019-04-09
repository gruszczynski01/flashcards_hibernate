
import org.mindrot.jbcrypt.BCrypt;
import users.User;

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

        String login = "NowyTestowy";
        String password = "haslo";
        User current = new User(login, password);
        User.addUser(current);
        current.addBigBox("Moje fiszki", "ogolne");
        current.addFlashcard(0, "Dog", "pies");
        current.addFlashcard(0, "Cat", "DUPA");




    }
}
