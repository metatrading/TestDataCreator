package miniservice;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import app.AppConfig;
import dao.TableDefinitionDao;
import dto.db.ColumnDefinitionDto;
import dto.db.TableDefinitionDto;

@Named
public class TableAndColumnExistsCheck {

    String[] tables = { "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test",
	    "test" };

    String[] columns = { "if_upd_datetime", "update_dt", "last_update_date", "update_date_time", "if_ins_datetime",
	    "date_created", "creation_date", "register_date_time", "transmit_process_flag", "if_status" };

    @Inject
    private TableDefinitionDao dao;

    public static void main(String[] args) throws SQLException {
	try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
	    TableAndColumnExistsCheck app = applicationContext.getBean(TableAndColumnExistsCheck.class);
	    app.main();
	}
    }

    private void main() throws SQLException {
	StringBuilder sb = new StringBuilder();
	sb.append("\t");
	for (String column : columns) {
	    sb.append(column);
	    sb.append("\t");
	}
	sb.append("\r\n");
	for (String tableName : tables) {
	    sb.append(tableName);
	    sb.append("\t");
	    TableDefinitionDto metadata = null;
	    try {
		metadata = dao.getMetadata(tableName);
	    } catch (Exception e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	    }

	    for (String column : columns) {
		boolean exists = false;
		for (ColumnDefinitionDto columnDef : metadata.getColumnList()) {
		    if (column.equals(columnDef.getColumnName())) {
			sb.append("○");
			exists = true;
			break;
		    }
		}
		if (!exists) {
		    sb.append("-");
		}
		sb.append("\t");
	    }
	    sb.append("\r\n");
	}
	System.out.println(sb.toString());
    }

}
