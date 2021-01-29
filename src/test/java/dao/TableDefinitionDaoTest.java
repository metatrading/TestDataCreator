package dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dto.db.ColumnDefinitionDto;
import dto.db.TableDefinitionDto;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class TableDefinitionDaoTest extends TestCase {
	
	@Test
	public void daoTest01() throws SQLException, Exception {
		TableDefinitionDao dao = new TableDefinitionDao();
		TableDefinitionDto ret = dao.getMetadata("");
		
		assertNotNull(ret);
		assertThat(ret.getTableName(), is(""));
	}
	
	@Test
	public void daoTest02() throws SQLException, Exception {
		TableDefinitionDao dao = new TableDefinitionDao();
		TableDefinitionDto ret = dao.getMetadata("not_exists_table");
		
		assertNotNull(ret);
		assertThat(ret.getTableName(), is("not_exists_table"));
		
		List<ColumnDefinitionDto> columnList = ret.getColumnList();
		assertThat(columnList.size(), is(0));
	}
	
	@Test
	public void daoTest03() throws SQLException, Exception {
		TableDefinitionDao dao = new TableDefinitionDao();
		TableDefinitionDto ret = dao.getMetadata("test");
		
		assertNotNull(ret);
		assertThat(ret.getTableName(), is("test"));
		
		List<ColumnDefinitionDto> columnList = ret.getColumnList();
		assertThat(columnList.size(), is(112));
		assertThat(columnList.get(0).getColumnName(),is("seq_no"));
		assertEquals(columnList.get(0).getDataType(),BigDecimal.class);
		assertThat(columnList.get(0).getSize(), is(28));
		assertThat(columnList.get(0).getDegits(), is(0));
	}
}
