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
            @Column(name = "bigbox_id", updatable = false, nullable = false)
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "big_box_seq")
    @SequenceGenerator(name = "big_box_seq", allocationSize = 1)
    private long bigBoxId;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User ownerId;
    @Column(name = "title")
    private String title;
    @Column(name = "category")
    private String category; // enum?

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_flashcards")
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();

    public BigBox() {
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
