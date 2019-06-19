package users;

import boxes.BigBox;
import database.DB;
import flashcards.Flashcard;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static users.MainCoordinator.loggedUser;

/**
 * Klasa definiujaca uzytkownika, uzytkownik posiada pudelka, w ktorych znajduja sie fiszki.
 * Atrybuty klasy zmapowane sa z baza danych. Komunikacja do bazy danych odbywa sie za posrednictwem technologii
 * Hibernate.
 */
@Entity
@Table(name = "fc_users")
public class User {


    @Id
            @Column(name = "fc_user_id", updatable = false, nullable = false)
            @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "fc_users_seq")
            @SequenceGenerator(name = "fc_users_seq", allocationSize = 1)
    private long userId;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BigBox> userBigBoxes = new ArrayList<BigBox>();


    /**
     * Konstruktor tworzacy nowego uzytkownika, podane haslo jest odrazu hashowane motoda 'hashpw' z klasy BCrypt
     * @param userName nazwa uzytkownika
     * @param password haslo do zahashowania
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());

    }
    /**
     * Metoda zapisujaca uzytkownika podanego w argumencie do bazy danych
     * @param newUser obiekt klasy User, ktorego chcemy zapisac do bazy danych
     * @return zwraca obiekt klasy User, ktory zapisalismy do bazy danych
     */
    public static User addUser(User newUser){

       EntityManager em =  DB.getInstance().getConnection();
       em.getTransaction().begin();
       em.persist(newUser);
       em.getTransaction().commit();
       em.close();
       return newUser;
    }


    /**
     * Metoda dodajace pudelko do uzytkownika
     * @param title tytul pudelka, ktore stowrzymy i dodamy do listy pudelek uzytkownika
     * @param category kategoria pudelka, ktore stowrzymy i dodamy do listy pudelek uzytkownika
     * @return zwraca pudelko, ktore dodalismy bo listy pudelek uzytkownika
     */
    public BigBox addBigBox(String title, String category){
        BigBox tmp =new BigBox(this, title, category);
        userBigBoxes.add(tmp);
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        em.persist(tmp);
        em.getTransaction().commit();
        em.close();
        return tmp;


    }
    /**
     * Metoda usuwajaca pudelko wraz z wszystkimi fiszkami z listy pudelek uzytkownika
     * @param id id pudelka, ktore chcemy usunac z listy pudelek uzytkownika
     */
    @SuppressWarnings("Duplicates")
    public void removeBigBox(long id){
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        String hql = "DELETE FROM Flashcard fc where fc.bigBoxMother = :bigMother";
        Query query =em.createQuery(hql);
        query.setParameter("bigMother", em.find(BigBox.class, id));
        query.executeUpdate();
        loggedUser = em.find(User.class, loggedUser.getUserId());
        em.getTransaction().commit();
        em.close();
        em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        String hql2 = "DELETE FROM BigBox bb where bb.bigBoxId = :id";
        Query query2 =em.createQuery(hql2);
        query2.setParameter("id", id);
        query2.executeUpdate();
        loggedUser = em.find(User.class, loggedUser.getUserId());
        em.getTransaction().commit();
        em.close();
    }
    public void removeUser(long userId){
        EntityManager em = DB.getInstance().getConnection();
        em.getTransaction().begin();
        em.remove(em.find(User.class, userId));
        em.getTransaction().commit();
    }

    /**
     * Konstruktor domyslny klasy User
     */
    public User() {}

    public List<BigBox> getUserBigBoxes() {
        return userBigBoxes;
    }

    public void setUserBigBoxes(List<BigBox> userBigBoxes) {
        this.userBigBoxes = userBigBoxes;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
