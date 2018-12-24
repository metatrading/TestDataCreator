package miniservice;

import dao.TableDefinitionDao;
import javafx.scene.control.Button;

public class DeleteTableButton extends Button {

	public DeleteTableButton(TableCountRecord record) {
		super("all delete");
		setOnMouseClicked(b -> {
			TableDefinitionDao dao;
			try {
				dao = new TableDefinitionDao();
				dao.deleteTable(record.tableNameProperty().get());
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			record.countProperty().set(0);
		});

	}

}
