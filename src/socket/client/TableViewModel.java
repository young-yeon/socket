package socket.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableViewModel {
    private StringProperty memo;

    public TableViewModel(String memo) {
        this.memo = new SimpleStringProperty(memo);
    }

    public StringProperty memoProperty() {
        return memo;
    }

    public String getName() {
        return memo.get();
    }

}
