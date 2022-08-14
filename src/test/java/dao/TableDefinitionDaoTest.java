package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import tdc.dao.TableDefinitionDao;
import tdc.db.ColumnDefinitionDto;
import tdc.db.TableDefinitionDto;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class TableDefinitionDaoTest {
	TableDefinitionDao dao;
	@Test
	public void daoTest01() throws SQLException, Exception {
		TableDefinitionDto ret = dao.getMetadata("");
		
		assertNotNull(ret);
		assertThat(ret.getTableName()).isEqualTo("");
	}
	
	@Test
	public void daoTest02() throws SQLException, Exception {
		TableDefinitionDto ret = dao.getMetadata("not_exists_table");
		
		assertNotNull(ret);
		assertThat(ret.getTableName()).isEqualTo("not_exists_table");

		List<ColumnDefinitionDto> columnList = ret.getColumnList();
		assertThat(columnList.size()).isEqualTo(0);
	}
	
	@Test
	public void daoTest03() throws SQLException, Exception {
		TableDefinitionDto ret = dao.getMetadata("test");
		
		assertNotNull(ret);
		assertThat(ret.getTableName()).isEqualTo("test");
		
		List<ColumnDefinitionDto> columnList = ret.getColumnList();
		assertThat(columnList.size()).isEqualTo(112);
		assertThat(columnList.get(0).getColumnName()).isEqualTo("seq_no");
		assertEquals(columnList.get(0).getDataType(),BigDecimal.class);
		assertThat(columnList.get(0).getSize()).isEqualTo(28);
		assertThat(columnList.get(0).getDigits()).isEqualTo(0);
	}
}
