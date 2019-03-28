
import org.mindrot.jbcrypt.BCrypt;

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


         String passwordIntoDatabase = BCrypt.hashpw("mojeHaselko", BCrypt.gensalt());
        System.out.println("password in DB: " + passwordIntoDatabase);

        if(BCrypt.checkpw("mojeHaselko", passwordIntoDatabase))
            System.out.println("Good passwd");
        else
            System.out.println("Worong password");


    }
}
