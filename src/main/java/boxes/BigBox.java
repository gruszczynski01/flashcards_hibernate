package boxes;


import database.DB;
import flashcards.Flashcard;
import users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static users.MainCoordinator.loggedUser;
/**
 * Klasa definiujaca pudelko nalezace do uzytkownika i zawierajace zbior fiszek.
 */
@Entity
@Table(name = "fc_bigboxes")
public class BigBox {
    @Id
            @Column(name = "fc_bigbox_id", updatable = false, nullable = false)
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fc_big_boxes_seq")
            @SequenceGenerator(name = "fc_big_boxes_seq", allocationSize = 1)
    private long bigBoxId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownerId;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "bigBoxMother", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();


    /**
     * Metoda dodajaca fiszke do pudelka
     * @param frontSide frontalna strona fiszki
     * @param backSide rewrs fiszki
     * @return Zracana jest nowodoana do pudelka fiszka
     */
    public Flashcard addFlashcard(String frontSide, String backSide){
        Flashcard tmp = new Flashcard(this, frontSide, backSide);
        flashcards.add(tmp);
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        em.persist(tmp);
        em.getTransaction().commit();
        em.close();
        return tmp;
    }
    /**
     * Metoda usuwajaca fiszke o zadanym id z pudelka
     * @param id - id fiszki do usuniecia z pudelka
     */
    public void removeFlashcard(long id){
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        String hql = "DELETE FROM Flashcard fc where fc.flashcardId = :id";
        Query query =em.createQuery(hql);
        query.setParameter("id", id);
        query.executeUpdate();
        loggedUser = em.find(User.class, loggedUser.getUserId());
        em.getTransaction().commit();
        em.close();

    }
    /**
     * Metoda usuwajaca wysztkie fiszki z pudelka
     */
    public void deleteAllFlashcards(){
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        BigBox bigBox = em.find(BigBox.class, this.getBigBoxId());
        String hql = "DELETE FROM Flashcard fc where fc.bigBoxMother = :bigMother";
        Query query =em.createQuery(hql);
        query.setParameter("bigMother", bigBox);
        query.executeUpdate();
        loggedUser = em.find(User.class, loggedUser.getUserId());
        em.getTransaction().commit();
        em.close();
    }

    public static List<BigBox> getBigBoxes(String cat){
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        String hql = "select bb from BigBox bb where bb.category = :category";
        Query query =em.createQuery(hql);
        query.setParameter("category", cat);
        List result = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }
    /**
     * Konstruktor klasy BigBox
     * @param ownerId id uzytkownika, ktory jest wlasicielem pudelka
     * @param title tytul pudelka
     * @param category kategoria pudelka
     */
    public BigBox(User ownerId, String title, String category){


        this.setOwnerId(ownerId);
        this.title = title;
        this.category = category;
    }
    /**
     * Konstruktor domylsny pudelka
     */
    public BigBox() {}

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public static List<Flashcard> getMyFlashcards(Long bigBoxId){
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        List<Flashcard> result = em.find(BigBox.class, bigBoxId).flashcards;
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }


    public long getBigBoxId() {
        return bigBoxId;
    }

    public void setBigBoxId(long bigBoxId) {
        this.bigBoxId = bigBoxId;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }




}
