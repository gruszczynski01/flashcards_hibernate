package flashcards;

import javafx.beans.property.SimpleStringProperty;

public class FlashcardRowTableView {
    private long flashcardId;
    private SimpleStringProperty frontSide;
    private SimpleStringProperty backSide;
    private SimpleStringProperty smallBoxNumber;

    public FlashcardRowTableView(long flashcardId, String frontSide, String backSide, String  smallBoxNumber) {
        this.flashcardId = flashcardId;
        this.frontSide = new SimpleStringProperty(frontSide);
        this.backSide = new SimpleStringProperty(backSide);
        this.smallBoxNumber = new SimpleStringProperty(smallBoxNumber);
    }

    public long getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(long flashcardId) {
        this.flashcardId = flashcardId;
    }

    public String getFrontSide() {
        return frontSide.get();
    }

    public SimpleStringProperty frontSideProperty() {
        return frontSide;
    }

    public void setFrontSide(String frontSide) {
        this.frontSide.set(frontSide);
    }

    public String getBackSide() {
        return backSide.get();
    }

    public SimpleStringProperty backSideProperty() {
        return backSide;
    }

    public void setBackSide(String backSide) {
        this.backSide.set(backSide);
    }

    public String getSmallBoxNumber() {
        return smallBoxNumber.get();
    }

    public SimpleStringProperty smallBoxNumberProperty() {
        return smallBoxNumber;
    }

    public void setSmallBoxNumber(String smallBoxNumber) {
        this.smallBoxNumber.set(smallBoxNumber);
    }
}
