package users;

import boxes.BigBox;
import database.DB;
import flashcards.Flashcard;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "fc_users")
public class User {




    @Id
            @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "user_seq")
            @SequenceGenerator(name = "user_seq", allocationSize = 1)
            @Column(name = "user_id", updatable = false, nullable = false)
    private int userId;
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user_id", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "fk_big_boxes")
    private List<BigBox> userBigBoxes = new ArrayList<BigBox>();


    public User() {}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());

    }
    public static User addUser(User newUser){

       EntityManager em =  DB.getInstance().getConnection();
       em.getTransaction().begin();
       em.persist(newUser);
       em.getTransaction().commit();
       return newUser;
    }
    public void addBigBox(String title, String category){
        BigBox tmp =new BigBox(this, title, category);
        userBigBoxes.add(tmp);
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        em.persist(tmp);
        em.getTransaction().commit();

    }
    public void addFlashcard(int bigBoxNumber, String frontSide, String backSide){
        Flashcard tmp = new Flashcard(userBigBoxes.get(bigBoxNumber), frontSide, backSide);
        userBigBoxes.get(bigBoxNumber).getFlashcards().add(tmp);
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        em.persist(tmp);
        em.getTransaction().commit();
    }

    public List<BigBox> getUserBigBoxes() {
        return userBigBoxes;
    }

    public void setUserBigBoxes(List<BigBox> userBigBoxes) {
        this.userBigBoxes = userBigBoxes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
