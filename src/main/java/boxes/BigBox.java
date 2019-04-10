package boxes;


import flashcards.Flashcard;
import users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fc_bigboxes")
public class BigBox {
    @Id
            @Column(name = "fc_bigbox_id", updatable = false, nullable = false)
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fc_big_boxes_seq")
            @SequenceGenerator(name = "fc_big_boxes_seq", allocationSize = 1)
    private long bigBoxId;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User ownerId;
    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category; // enum?

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    @OneToMany(mappedBy = "bigBoxMother", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "fk_flashcards")
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();

    public BigBox() {
    }
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
