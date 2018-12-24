package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import dao.TableDefinitionDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import lombok.Getter;

/**
 * JDBC Config Tab.
 *
 */
@Named
@Component
public class JdbcConfigTab extends Tab {
	@Inject
	private TableDefinitionDao dao;
	@Getter
	private GridPane settingPane;
	private Label status = new Label();

	public JdbcConfigTab() {
		super("jdbc settings");
		closableProperty().set(false);
	}

	private TextField newTextField(SimpleStringProperty property, String prompt) {
		TextField textField = new TextField(property.get());
		property.bind(textField.textProperty());
		textField.setMaxWidth(Double.MAX_VALUE);
		textField.setPromptText(prompt);
		return textField;
	}

	public void init() {

		settingPane = new GridPane();
		settingPane.add(new Label("url"), 0, 0);
		settingPane.add(new Label("username"), 0, 1);
		settingPane.add(new Label("password"), 0, 2);
		settingPane.add(new Label("schema"), 0, 3);

		settingPane.add(
				newTextField(dao.getUrl(), "jdbc:{subprotocol}://{DBHost}\\{InstanceName};databaseName={DBName}"), 1,
				0);
		settingPane.add(newTextField(dao.getUser(), "username"), 1, 1);
		settingPane.add(newTextField(dao.getPassword(), "password"), 1, 2);
		settingPane.add(newTextField(dao.getSchema(), "schema"), 1, 3);

		Button saveButton = new Button("適用・保存");
		
		// ボタン押下時の挙動
		saveButton.setOnAction(b -> {
			
			// 設定値を保存。
			File file = null;
			try {
				
				// 既存ファイルの削除
				String dir = System.getProperty("java.io.tmpdir");
				Path checkPath = Paths.get(new File(dir).toURI());
				if (!Files.exists(checkPath))
					return;

				List<Path> paths = Files.find(Paths.get(new File(dir).toURI()), Integer.MAX_VALUE, (p, attr) -> {
					return !attr.isDirectory() && p.toFile().getName().startsWith(dao.getSettingFilePrefix());
				}).collect(Collectors.toList());
				paths.stream().forEach(t -> {
					try {
						Files.delete(t);
					} catch (IOException e) {
						e.printStackTrace();
						status.setText(e.getMessage());
					}
				});

				file = File.createTempFile(dao.getSettingFilePrefix(), null);
			} catch (IOException e1) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e1.printStackTrace(pw);
				pw.flush();
				status.setText(sw.toString());
				e1.printStackTrace();
			}

			// 保存
			try (FileWriter out = new FileWriter(file)) {
				Properties prop = new Properties();
				prop.put("url", dao.getUrl().get());
				prop.put("username", dao.getUser().get());
				prop.put("password", dao.getPassword().get());
				prop.put("schema", dao.getSchema().get());
				prop.store(out, "");

				status.setText("設定を保存しました。");
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				pw.flush();
				status.setText(sw.toString());
				e.printStackTrace();
			}
		});
		settingPane.add(saveButton, 1, 4);

		settingPane.add(status, 1, 5);

		settingPane.setMaxWidth(Double.MAX_VALUE);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(10);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		settingPane.getColumnConstraints().addAll(column1, column2); // each get
		// 50% of
		// width
		
		setContent(settingPane);
	}

}
