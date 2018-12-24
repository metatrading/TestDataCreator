package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dto.db.TableDefinitionDto;
import gui.eventhandler.CopieAndPaster;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class TableListWindow {

	private TableView<Map<String, String>> table;
	private List<TableColumn<Map<String, String>, String>> columnList;
	private TableDefinitionDto def;
	private List<Map<String, String>> columnValueMapList;

	public TableView<Map<String, String>> get() {
		return table;
	}

	public TableListWindow(List<Map<String, String>> columnValueMapList, TableDefinitionDto def) {
		this.columnValueMapList = columnValueMapList;
		this.def = def;
		initialize();
	}

	public void initialize() {
		if (columnValueMapList.isEmpty())
			return;
		table = new TableView<>();
		columnList = new ArrayList<>();

		// create columns
		for (Entry<String, String> col : columnValueMapList.get(0).entrySet()) {
			TableColumn<Map<String, String>, String> colum = new TableColumn<>(capilize(col.getKey()));
			colum.setCellValueFactory(
					new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> arg0) {
							return new SimpleStringProperty(arg0.getValue().get(col.getKey()));
						}
					});
			columnList.add(colum);
		}
		table.getColumns().addAll(columnList);
		table.addEventHandler(KeyEvent.KEY_RELEASED, new CopieAndPaster(table, def));
		table.setItems(FXCollections.observableArrayList(columnValueMapList));
		table.getStylesheets().add("listView.css");

	}

	private String capilize(String name) {
		StringBuilder sb = new StringBuilder();
		Pattern reg = Pattern.compile("[A-Z]");
		for (int i = 0; i < name.length(); i++) {
			String sub = name.substring(i, i + 1);
			if (i == 0) {
				sb.append(sub.toLowerCase());
				continue;
			}
			Matcher match = reg.matcher(sub);
			if (match.find()) {
				sb.append("_");
				sb.append(sub.toLowerCase());
			} else {
				sb.append(sub.toLowerCase());
			}
		}

		return sb.toString();
	}
}
