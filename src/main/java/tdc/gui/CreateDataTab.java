package tdc.gui;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import org.springframework.stereotype.Component;
import tdc.infrastructure.AppConfig;
import tdc.dao.TableDefinitionDao;
import tdc.db.ColumnDefinitionDto;
import tdc.db.TableDefinitionDto;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import tdc.service.DatasCreator;

@Named
@Component
public class CreateDataTab extends Tab {
//
//	@Inject
//	private TableDefinitionDao dao;
//
//	@Inject
//	private DatasCreator<Date> dateDatasCreator;
//	@Inject
//	private DatasCreator<String> varcharDatasCreator;
//	@Inject
//	private DatasCreator<BigDecimal> decimalDatasCreator;
//	@Inject
//	private DatasCreator<String> characterDatasCreator;
//
//	private TableListWindow tableListWindow;
//
//	private List<Map<String, String>> columnValueMapList = new ArrayList<>();
//	private SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//	private Logger logger = LoggerFactory.getLogger(CreateDataTab.class);
//
//	public CreateDataTab() {
//		super("main");
//		try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
//
//			VBox vbox = new VBox();
//			Button button = new Button("execute");
//			Label status = new Label();
//			HBox hbox = new HBox();
//			TextField tableInputField = new TextField("BITCOIN_ORDER");
//			tableInputField.setPromptText("テーブル名");
//			TextField rowsize = new TextField("3");
//			rowsize.setPromptText("作成件数");
//
//			button.setOnAction(b -> {
//				try {
//					OffsetDateTime start = OffsetDateTime.now();
//
//					if (rowsize.getText().isEmpty()) {
//						status.setText("作成件数を指定してください。");
//						return;
//					}
//
//					create(new String[]{tableInputField.getText()},
//							Integer.parseInt(rowsize.getText()));
//
//					TableDefinitionDto metadata = dao.getMetadata(tableInputField.getText());
//					if (metadata.getColumnList().isEmpty())
//						return;
//
//					if (vbox.getChildren().size() == 2) {
//						vbox.getChildren().remove(1);
//					}
//					tableListWindow.setInitialBefore(columnValueMapList, metadata);
//					vbox.getChildren().add(tableListWindow.get());
//					VBox.setVgrow(tableListWindow.get(), Priority.ALWAYS);
//					status.setMaxHeight(Double.MAX_VALUE);
//					OffsetDateTime end = OffsetDateTime.now();
//					Duration d = Duration.between(start, end);
//					status.setText("\t" + tableListWindow.get().getItems().size() + " rows created("
//							+ yyyyMMddHHmmssSSS.format(new java.util.Date()) + "," + d.get(ChronoUnit.NANOS) / 1000000
//							+ "ms)");
//				} catch (Exception e) {
//					e.printStackTrace();
//					status.setText(e.getMessage());
//				}
//			});
//			tableInputField.setOnKeyPressed(b -> {
//				if (b.getCode().equals(KeyCode.ENTER)) {
//					button.fire();
//				}
//			});
//			hbox.getChildren().addAll(tableInputField, rowsize, button, status);
//			// ListView 表示
//			vbox.setAlignment(Pos.TOP_LEFT);
//			vbox.getChildren().addAll(hbox);
//
//			setContent(vbox);
//			closableProperty().set(false);
//
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//	}
//
//	/**
//	 *
//	 */
//	public void create(String[] targetTables, int rowsize) throws Exception {
//		columnValueMapList = new ArrayList<>();
//		// テーブルループ
//		for (String tableName : targetTables) {
//
//			logger.info(tableName + " start.");
//			if (tableName.isEmpty())
//				throw new Exception("テーブル名が未指定です。");
//			TableDefinitionDto def = dao.getMetadata(tableName);
//			if (def.getColumnList().isEmpty()) {
//				logger.error(tableName + "は、DBに存在しません。");
//				throw new Exception(tableName + "は、DBに存在しません。");
//			}
//			List<List<?>> datasList = null;
//			int createSeed = rowsize;
//			datasList = preCreateTestData(def, createSeed, 0);
//
//			// 行ループ
//			for (int rownum = 1; rownum <= rowsize; rownum++) {
//				Map<String, String> map = new LinkedHashMap<>();
//
//				// 列ループ
//				for (int colnum = 0; colnum < def.getColumnList().size(); colnum++) {
//					String value = datasList.get(colnum).get(rownum - 1).toString();
//
//					// カラムごとに値を設定
//					map.put(def.getColumnList().get(colnum).getColumnName(), value);
//				}
//
//				columnValueMapList.add(map);
//			}
//		}
//	}
//
//	private List<List<?>> preCreateTestData(TableDefinitionDto def, int rowSize, int currentRow) throws Exception {
//		List<List<?>> columnDatasList = new LinkedList<>();
//		// 事前データ準備
//		for (ColumnDefinitionDto colDef : def.getColumnList()) {
//			int colIndex = def.getColumnList().indexOf(colDef);
//			List<?> columnValues = new LinkedList<>();
//			DatasCreator<?> datasCreator = null;
//			if (colDef.getDataType().equals(String.class)) {
//				datasCreator = varcharDatasCreator;
//			}
//			if (colDef.getDataType().equals(Character.class)) {
//				datasCreator = characterDatasCreator;
//			}
//			if (colDef.getDataType().equals(BigDecimal.class)) {
//				datasCreator = decimalDatasCreator;
//			}
//			if (colDef.getDataType().equals(Long.class)) {
//				datasCreator = decimalDatasCreator;
//			}
//			if (colDef.getDataType().equals(Integer.class)) {
//				datasCreator = decimalDatasCreator;
//			}
//			if (colDef.getDataType().equals(Short.class)) {
//				datasCreator = decimalDatasCreator;
//			}
//			if (colDef.getDataType().equals(Date.class) || colDef.getDataType().equals(Timestamp.class)) {
//				datasCreator = dateDatasCreator;
//			}
//			datasCreator.setSeed(colIndex);
//			columnValues = datasCreator.create(colDef, rowSize, currentRow);
//			columnDatasList.add(columnValues);
//		}
//		return columnDatasList;
//	}
}
