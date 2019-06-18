package boxes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BigBoxRowTableView {
    private long bigBoxId;
    private SimpleStringProperty title;
    private SimpleStringProperty category;

    public BigBoxRowTableView(long bigBoxId, String title, String category) {
        this.bigBoxId = bigBoxId;
        this.title = new SimpleStringProperty(title);
        this.category = new SimpleStringProperty(category);
    }

    public long getBigBoxId() {
        return bigBoxId;
    }


    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }
}
