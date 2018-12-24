package property.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import dto.db.ColumnDefinitionDto;
import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class PropertyTableColumnToDomainTest extends TestCase {
	@Test
	public void test01() {
		String tableName = "mfif_out_sap_journals";
		String columnName = "bank_charge_code_1";
		Class<?> dataType = String.class;
		int size = 2;
		int degits = 0;
		ColumnDefinitionDto dto = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		String domain = PropertyTableColumnToDomain.getDomain(dto);

		assertThat(domain, is("銀行手数料負担コード"));
	}

	@Test
	public void test02() {
		String tableName = "mfif_out_sap_journals";
		String columnName = "register_user_id";
		Class<?> dataType = String.class;
		int size = 2;
		int degits = 0;
		ColumnDefinitionDto dto = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		String domain = PropertyTableColumnToDomain.getDomain(dto);

		assertThat(domain, is("ユーザコード"));
	}

	@Test
	public void test03() {
		String tableName = "mfif_out_sap_journals";
		String columnName = "not_exists_code";
		Class<?> dataType = String.class;
		int size = 2;
		int degits = 0;
		ColumnDefinitionDto dto = new ColumnDefinitionDto(tableName, columnName, dataType, size, degits);
		String domain = PropertyTableColumnToDomain.getDomain(dto);
		assertThat(domain, is(""));
	}
}
