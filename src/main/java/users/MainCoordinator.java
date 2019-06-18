package users;


import java.util.regex.Pattern;

public class MainCoordinator {
    public static User loggedUser;

    public static boolean wordPattern(String toCheck){
        //Pattern pattern = Pattern.compile("[^\\s][a-zA-Z0-9_ ]{1,20}");
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{1}[a-zA-Z0-9_ ]{1,19}");
        return pattern.matcher(toCheck).matches();
    }
    public static boolean passwordPattern(String toCheck){
        //Pattern pattern = Pattern.compile("[^\\s][a-zA-Z0-9_ ]{1,20}");
        Pattern pattern = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!_]).{8,20})");
        return pattern.matcher(toCheck).matches();
    }

}
