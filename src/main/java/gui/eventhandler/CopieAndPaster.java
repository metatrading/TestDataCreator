package gui.eventhandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.DefaultTemplateResolver;

import dto.db.TableDefinitionDto;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CopieAndPaster implements EventHandler<KeyEvent> {
	private Logger logger = LoggerFactory.getLogger(CopieAndPaster.class);
	private final TableView<Map<String, String>> table;
	private TableDefinitionDto def;

	public CopieAndPaster(final TableView<Map<String, String>> table, TableDefinitionDto def) {
		this.table = table;
		this.def = def;
	}

	@Override
	public void handle(KeyEvent arg0) {
		if (arg0.isControlDown()) {
			if (arg0.getCode().equals(KeyCode.A)) {
				table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				table.getSelectionModel().selectAll();
			}
			if (arg0.getCode().equals(KeyCode.C)) {
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putHtml(tableAllDataForHtml());
				content.putString(tableAllData());
				clipboard.setContent(content);
			}
		}

	}

	private String tableAllDataForHtml() {
		TemplateEngine templateEngine = new TemplateEngine();
		DefaultTemplateResolver templateResolver = new DefaultTemplateResolver();
        // XHTML is the default mode, but we set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
		templateEngine.setTemplateResolver(templateResolver);
		
		Map<String, Object> valiables = new HashMap<>();
		Context context = new Context(Locale.JAPAN, valiables);
		
		String html = templateEngine.process("copiedTable", context);
		
		logger.info("html:{}", html);
		
		
		StringBuilder builder = new StringBuilder();

		builder.append("<TABLE border=\"1\">");
		builder.append("<TR>");
		ObservableList<TableColumn<Map<String, String>, ?>> list = table.getColumns();
		for (TableColumn<Map<String, String>, ?> tableColumn : list) {
			if (def.getPrimaryKeys().contains(tableColumn.getText()))
				// PKは太字のまま
				builder.append("<TH bgcolor=\"#ccffcc\" style=\"text-align:left\" nowrap>")
						.append(tableColumn.getText()).append("</TH>");
			else
				// PK以外は太字解除
				builder.append("<TH bgcolor=\"#ccffcc\" style=\"font-weight: normal text-align:left\" nowrap>")
						.append(tableColumn.getText()).append("</TH>");
		}
		builder.append("</TR>");
		builder.append(System.lineSeparator());

		ObservableList<Map<String, String>> selectedList = table.getSelectionModel().getSelectedItems();
		for (Map<String, String> entity : selectedList) {
			builder.append("<TR>");
			for (Entry<String, String> entry : entity.entrySet()) {
				builder.append("<TD style=\"text-align:left\" nowrap>");

				builder.append(entry.getValue());
				builder.append("</TD>");
			}
			builder.append("</TR>");
			builder.append(System.lineSeparator());
		}
		builder.append("</TABLE>");

		return builder.toString();
	}

	private String tableAllData() {
		StringBuilder builder = new StringBuilder();

		ObservableList<TableColumn<Map<String, String>, ?>> list = table.getColumns();
		list.stream().forEach(p -> builder.append(p.getText()).append("\t"));
		builder.deleteCharAt(builder.length() - 1);
		builder.append(System.lineSeparator());

		ObservableList<Map<String, String>> selectedList = table.getSelectionModel().getSelectedItems();
		for (Map<String, String> entity : selectedList) {
			for (Entry<String, String> entry : entity.entrySet()) {
				builder.append(entry.getValue());
				builder.append("\t");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(System.lineSeparator());
		}
		return builder.toString();
	}

}
