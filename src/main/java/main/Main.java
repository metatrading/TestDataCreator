package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import app.AppConfig;
import gui.CreateDataTab;
import gui.JdbcConfigTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * ・CTRLで範囲選択 ・範囲選択状態でコピー ・
 * 
 * @author Takagi Satoshi
 *
 */
public class Main extends Application {

	private CreateDataTab createDataTab;

	private JdbcConfigTab jdbcConfigTab;

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		TabPane tabpane = new TabPane();
		try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
			createDataTab = applicationContext.getBean(CreateDataTab.class);
			jdbcConfigTab = applicationContext.getBean(JdbcConfigTab.class);
			jdbcConfigTab.init();
			tabpane.getTabs().addAll(createDataTab, jdbcConfigTab);
		} catch (Exception e) {
			e.printStackTrace();
		}

		stage.setOnCloseRequest(e -> System.exit(0));

		stage.setTitle("TestData");
		stage.setScene(new Scene(tabpane));
		stage.setMaximized(true);
		stage.show();
	}

}
