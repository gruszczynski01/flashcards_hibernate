package flashcards;


import boxes.BigBox;

import javax.persistence.*;

@Entity
@Table(name = "fc_flascards")
public class Flashcard {
    @Id
            @Column(name = "flashcard_id", updatable = false, nullable = false)
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flashcards_seq")
            @SequenceGenerator(name = "flashcards_seq", allocationSize = 1)

    private long flascardId;
    @ManyToOne()
    @JoinColumn(name = "fk_bigbox_mother")
    private BigBox bigBoxMother;
    @Column(name = "front_side")
    private String frontSide;
    @Column(name = "back_side")
    private String backSide;
    @Column(name = "smallbox_number")
    private int smallBoxNumber;

    @Override
    public String toString() {
        return "Flashcard{" +
                "flascardId=" + flascardId +
                ", bigBoxMother=" + bigBoxMother +
                ", frontSide='" + frontSide + '\'' +
                ", backSide='" + backSide + '\'' +
                ", smallBoxNumber=" + smallBoxNumber +
                '}';
    }

    public Flashcard(BigBox bigBoxMother, String frontSide, String backSide){
        this.bigBoxMother = bigBoxMother;
        this.frontSide = frontSide;
        this.backSide = backSide;
        this.smallBoxNumber = 0;
    }

    public Flashcard() {}

    public long getFlascardId() {
        return flascardId;
    }

    public void setFlascardId(long flascardId) {
        this.flascardId = flascardId;
    }

    public BigBox getBigBoxMother() {
        return bigBoxMother;
    }

    public void setBigBoxMother(BigBox bigBoxMother) {
        this.bigBoxMother = bigBoxMother;
    }

    public String getFrontSide() {
        return frontSide;
    }

    public void setFrontSide(String frontSide) {
        this.frontSide = frontSide;
    }

    public String getBackSide() {
        return backSide;
    }

    public void setBackSide(String backSide) {
        this.backSide = backSide;
    }

    public int getSmallBoxNumber() {
        return smallBoxNumber;
    }

    public void setSmallBoxNumber(int smallBoxNumber) {
        this.smallBoxNumber = smallBoxNumber;
    }


}
