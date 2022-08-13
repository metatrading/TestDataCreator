package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import dto.db.ColumnDefinitionDto;
import dto.db.TableDefinitionDto;

@Named
@Component
public class TableDefinitionDao extends Dao {

    private Logger logger = LoggerFactory.getLogger(TableDefinitionDao.class);

    public TableDefinitionDao() throws FileNotFoundException, IOException, SQLException {
        super();
    }

    /**
     * テーブル定義取得.
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public TableDefinitionDto getMetadata(String tableName) throws Exception {
        TableDefinitionDto tableDef = TableDefinitionDto.builder().columnList(new ArrayList<>()).tableName(tableName)
                .primaryKeys(new ArrayList<>()).build();
        try (Connection con = getConnection();
             ResultSet rs = con.getMetaData().getColumns(con.getCatalog(), con.getSchema(), tableName, null)) {
            setColumnDef(tableDef, rs);
            setPrimaryKeyDef(tableDef, con);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return tableDef;
    }

    private void setPrimaryKeyDef(TableDefinitionDto tableDef, Connection con) throws SQLException {
        logger.info("con.getCatalog:{}, con.getSchema():{}",
                con.getCatalog(), con.getSchema());
        try (ResultSet rs2 = con.getMetaData().getPrimaryKeys(con.getCatalog(), con.getSchema(),
                tableDef.getTableName())) {
            while (rs2.next()) {
                tableDef.getPrimaryKeys().add(rs2.getString("COLUMN_NAME"));
            }
        }
    }

    private void setColumnDef(TableDefinitionDto tableDef, ResultSet rs) throws SQLException {
        while (rs.next()) {
            ColumnDefinitionDto columnDef = createColumnDef(tableDef.getTableName(), rs);
            tableDef.getColumnList().add(columnDef);
        }
    }

    private ColumnDefinitionDto createColumnDef(String tableName, ResultSet rs) throws SQLException {
        Class<?> dataType = readColumnType(rs);
        return new ColumnDefinitionDto(tableName, rs.getString("COLUMN_NAME"), dataType, rs.getInt("COLUMN_SIZE"),
                rs.getInt("DECIMAL_DIGITS"));
    }

    private Class<?> readColumnType(ResultSet rs) throws SQLException {
        Class<?> dataType;
        switch (rs.getInt("DATA_TYPE")) {
            case Types.DECIMAL:
                dataType = BigDecimal.class;
                break;
            case Types.DATE:
                dataType = Date.class;
                break;
            case Types.TIMESTAMP:
                dataType = Date.class;
                break;
            case Types.CHAR:
                dataType = Character.class;
                break;
            case Types.VARCHAR:
                dataType = String.class;
                break;
            case Types.NVARCHAR:
                dataType = String.class;
                break;
            case Types.INTEGER:
                dataType = Integer.class;
                break;
            case Types.BIGINT:
                dataType = Long.class;
                break;
            case Types.TINYINT:
            case Types.BIT:
                dataType = Short.class;
                break;
            default:
                throw new IllegalArgumentException(
                        "型のマッピングが未定義です。追加してください。TYPE:" + rs.getString("TYPE_NAME") + "[" + rs.getInt("DATA_TYPE") + "]");
        }
        return dataType;
    }

    /**
     * テーブル名出力（プレフィックス該当のみ）
     *
     * @param prefix
     * @throws Exception
     */
    public void printTableByPrefixFilter(String prefix) throws Exception {
        TableDefinitionDao dao = new TableDefinitionDao();
        try (Connection con = dao.getConnection();
             ResultSet rs = con.getMetaData().getTables(con.getCatalog(), con.getSchema(), null, null)) {
            printTableByPrefix(rs, prefix);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printTableByPrefix(ResultSet rs, String prefix) throws SQLException {
        while (rs.next()) {
            if (rs.getString("TABLE_NAME").startsWith(prefix)) {
                logger.debug(rs.getString("TABLE_NAME"));
            }
        }
    }

    public List<String> getTableNames() throws Exception {
        List<String> tableNames = new ArrayList<>();
        try (Connection con = this.getConnection();
             ResultSet rs = con.getMetaData().getTables(con.getCatalog(), con.getSchema(), null, null)) {
            while (rs.next()) {
                logger.debug("tablename add[{}]", rs.getString("TABLE_NAME"));
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    public Integer count(String tableName) throws Exception {
        logger.debug(tableName + " count.");
        String sql = "select count(*) from " + tableName;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet ret = statement.executeQuery(sql)) {
            if (ret.next())
                return ret.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteTable(String tableName) throws Exception {
        String sql = "delete from " + tableName;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(sql);
            logger.debug(tableName + " deleted rows(" + statement.getUpdateCount() + ").");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
}
