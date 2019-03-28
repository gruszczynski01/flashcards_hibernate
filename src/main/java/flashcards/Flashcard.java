package flashcards;


import javax.persistence.*;

@Entity
@Table(name = "flascards")
public class Flashcard {
    @Id
            @Column(name = "flashcard_id", updatable = false, nullable = false)
            @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int flascardId;
    @ManyToOne
    @JoinColumn( )
    @Column(name = "bigbox_mother")
    int bigBoxMother;
    @Column(name = "front_side")
    String frontSide;
    @Column(name = "back_side")
    String backSide;
    @Column(name = "smallbox_number")
    int smallBoxNumber;
}
