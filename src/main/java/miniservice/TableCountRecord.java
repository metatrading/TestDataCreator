package miniservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class TableCountRecord {
    private StringProperty tableNameProperty;
    private IntegerProperty countProperty;
    private Button deleteButton;

    public StringProperty tableNameProperty() {
	return tableNameProperty;
    }

    public void setTableNameProperty(StringProperty tableNameProperty) {
	this.tableNameProperty = tableNameProperty;
    }

    public IntegerProperty countProperty() {
	return countProperty;
    }

    public void setCountProperty(IntegerProperty countProperty) {
	this.countProperty = countProperty;
    }

    public Button getDeleteButton() {
	return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
	this.deleteButton = deleteButton;
    }
}