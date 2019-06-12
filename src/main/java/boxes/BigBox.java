package boxes;


import database.DB;
import flashcards.Flashcard;
import users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static users.MainCoordinator.loggedUser;

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

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category; // enum?

    @OneToMany(mappedBy = "bigBoxMother", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();

    //***************************************************************
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }


    public void addFlashcard(String frontSide, String backSide){
        System.out.println("bede otwieral transakcje");
        EntityManager em =  DB.getInstance().getConnection();
        System.out.println("zrobilem get connection");
        em.getTransaction().begin();
        System.out.println("Rozpoczolem transakcje");
        Flashcard tmp = new Flashcard(this, frontSide, backSide);
        System.out.println("stworzylem fiszke");
        flashcards.add(tmp);
        System.out.println("dodalem fiszke do zbioru w danym big box");
//        em.persist(this);
        em.persist(tmp);
        System.out.println("porobilem presist");
        em.getTransaction().commit();
        System.out.println("zrobilem commit");
        em.close();
        System.out.println("zamknalem tranfakcie");
    }
    @SuppressWarnings("Duplicates")
    public void deleteAllFlashcards(){
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        BigBox bigBox = em.find(BigBox.class, this.getBigBoxId());
        String hql = "DELETE FROM Flashcard fc where fc.bigBoxMother = :bigMother";
        Query query =em.createQuery(hql);
        query.setParameter("bigMother", bigBox);
        query.executeUpdate();
        loggedUser = em.find(User.class, loggedUser.getUserId());
        //em.persist(bigBox);
        em.getTransaction().commit();
        em.close();
    }

    public BigBox() {}

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public BigBox(User ownerId, String title, String category){


        this.setOwnerId(ownerId);
        this.title = title;
        this.category = category;
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
