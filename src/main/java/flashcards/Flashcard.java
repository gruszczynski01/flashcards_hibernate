package flashcards;


import boxes.BigBox;

import javax.persistence.*;

@Entity
@Table(name = "fc_flashcards")
public class Flashcard implements Comparable{
    @Id
            @Column(name = "fc_flashcard_id", updatable = false, nullable = false)
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fc_flashcards_seq")
            @SequenceGenerator(name = "fc_flashcards_seq", allocationSize = 1)
    private long flashcardId;

    @ManyToOne
    @JoinColumn(name = "bigBoxMother")
    private BigBox bigBoxMother;

    @Column(name = "front_side")
    private String frontSide;
    @Column(name = "back_side")
    private String backSide;

    @Column(name = "smallbox_number")
    private int smallBoxNumber;

//***************************************************************

    public Flashcard(BigBox bigBoxMother, String frontSide, String backSide){
        this.bigBoxMother = bigBoxMother;
        this.frontSide = frontSide;
        this.backSide = backSide;
        this.smallBoxNumber = 0;

    }

    public Flashcard() {}

    public long getFlascardId() {
        return flashcardId;
    }

    public void setFlascardId(long flascardId) {
        this.flashcardId = flascardId;
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

    @Override
    public String toString() {
        return "Flashcard{" +
                "flascardId=" + flashcardId +
                ", bigBoxMother=" + bigBoxMother +
                ", frontSide='" + frontSide + '\'' +
                ", backSide='" + backSide + '\'' +
                ", smallBoxNumber=" + smallBoxNumber +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Flashcard secondFlashcard = (Flashcard) o;
        if(this.getSmallBoxNumber() < secondFlashcard.getSmallBoxNumber())
            return -1;
        else if (this.getSmallBoxNumber() == secondFlashcard.getSmallBoxNumber())
            return 0;
        else
            return 1;
    }
}
