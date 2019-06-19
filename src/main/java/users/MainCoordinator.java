package users;


import java.util.regex.Pattern;
/**
 * Klasa koordynujaca dzialanie calego programu, przechowuje obecnie zalogowanego uzytkownika
 */
public class MainCoordinator {
    /**
     * Obecnie zalogowany uzytkownik
     */
    public static User loggedUser;
    /**
     * Metoda sprawdzajaca forme stringa. Waliduje czy argument sklada sie ze znakow a-z, A-Z, 0-9, _,
     * oraz od 2 do 20 znakow
     * @param toCheck obiekt klasy String przechowujacy ciag znakow do zwalidowania
     * @return zwracana wartosc true, jezeli podany ciag jest zgodny z wymaganiami i false jezeli
     * nie jest zgodny z wymaganiami
     */
    public static boolean wordPattern(String toCheck){
        System.out.println("wordPattern: \""+toCheck+"\"");
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{1}[a-zA-Z0-9_ ]{1,19}");
        return pattern.matcher(toCheck).matches();
    }
    /**
     * Metoda sprawdzajaca forme stringa. Od 8 do 20 znakow, musi zawierac, mala litere, duza litere, cyfre, oraz
     * znak specjalny.
     * @param toCheck obiekt klasy String przechowujacy ciag znakow do zwalidowania
     * @return zwracana wartosc true, jezeli podany ciag jest zgodny z wymaganiami i false jezeli
     * nie jest zgodny z wymaganiami
     */
    public static boolean passwordPattern(String toCheck){
        System.out.println("passwordPattern: \""+toCheck+"\"");
        Pattern pattern = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!_]).{8,20})");
        return pattern.matcher(toCheck).matches();
    }

}
