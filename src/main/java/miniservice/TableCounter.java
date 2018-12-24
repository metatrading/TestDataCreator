package miniservice;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dao.TableDefinitionDao;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableCounter extends Application {
	private ObservableList<TableCountRecord> values = FXCollections.observableArrayList();
	ObservableValue<? extends ObservableList<TableCountRecord>> observable;
	private ProgressIndicator progressIndicator;
	private TableView<TableCountRecord> table;

	@Override
	public void start(Stage arg0) throws Exception {
		TableDefinitionDao dao = new TableDefinitionDao();
		List<String> tableNames = dao.getTableNames();

		VBox vbox = new VBox();
		table = new TableView<>();

		TableColumn<TableCountRecord, String> tableNameColumn = new TableColumn<>("tableName");
		tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("tableName"));
		table.getColumns().add(tableNameColumn);

		TableColumn<TableCountRecord, Integer> coutnerColumn = new TableColumn<>("count");
		coutnerColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
		table.getColumns().add(coutnerColumn);

		TableColumn<TableCountRecord, DeleteTableButton> deleteButtonColumn = new TableColumn<>("deleteButton");
		deleteButtonColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
		table.getColumns().add(deleteButtonColumn);

		// table.setItems(values);
		observable = new ObservableValue<ObservableList<TableCountRecord>>() {

			@Override
			public void addListener(InvalidationListener arg0) {
				System.out.println("added");
			}

			@Override
			public void removeListener(InvalidationListener arg0) {
			}

			@Override
			public void addListener(ChangeListener arg0) {
				System.out.println("changed");

			}

			@Override
			public ObservableList<TableCountRecord> getValue() {
				return values;
			}

			@Override
			public void removeListener(ChangeListener arg0) {
			}
		};
		values.addListener((ListChangeListener.Change<? extends TableCountRecord> b) -> {
			System.out.println("added");
			Optional<Integer> maxLength = b.getList().stream().map(a -> a.tableNameProperty().get().length())
					.max((a, c) -> a.intValue() - c.intValue());
			maxLength.ifPresent(d -> tableNameColumn.setPrefWidth(8d * d));
		});
		// table.itemsProperty().bind(observable);
		table.itemsProperty().set(values);

		Button button = new Button("execute");
		button.setOnMouseClicked(b -> {
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					create(dao, tableNames);
					return null;
				}
			};
			new Thread(task).start();
		});
		HBox hbox = new HBox();
		progressIndicator = new ProgressIndicator(0d);
		hbox.getChildren().addAll(button, progressIndicator);

		VBox.setVgrow(table, Priority.ALWAYS);
		vbox.getChildren().addAll(hbox, table);

		AnchorPane pane = new AnchorPane();
		pane.getChildren().addAll(vbox);

		AnchorPane.setBottomAnchor(vbox, 1d);
		AnchorPane.setTopAnchor(vbox, 1d);
		AnchorPane.setRightAnchor(vbox, 1d);
		AnchorPane.setLeftAnchor(vbox, 1d);

		arg0.setScene(new Scene(pane));
		arg0.show();
	}

	private void create(TableDefinitionDao dao, List<String> tableNames) {

		if (!observable.getValue().isEmpty()) {
			observable.getValue().clear();
			progressIndicator.setProgress(0);
		}

		for (String tableName : tableNames) {
			// String tableName = tableNames.get(index);
			Integer count = null;
			try {
				count = dao.count(tableName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			TableCountRecord record = new TableCountRecord();
			record.setCountProperty(new SimpleIntegerProperty(count));
			record.setTableNameProperty(new SimpleStringProperty(tableName));
			Button deleteButton = new DeleteTableButton(record);
			deleteButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, b -> {
				// 再作成
				table.refresh();
			});
			record.setDeleteButton(deleteButton);
			observable.getValue().add(record);
			progressIndicator.setProgress((double) tableNames.indexOf(tableName) / (tableNames.size() - 1));
		}
		Collections.sort(observable.getValue(), (a, b) -> b.countProperty().intValue() - a.countProperty().intValue());
		// index++;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
