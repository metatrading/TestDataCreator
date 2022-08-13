package dao

import dto.db.ColumnDefinitionDto
import dto.db.TableDefinitionDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.*
import javax.inject.Named

@Named
@Component
class TableDefinitionDao : Dao() {
    private val logger = LoggerFactory.getLogger(TableDefinitionDao::class.java)

    /**
     * テーブル定義取得.
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getMetadata(tableName: String): TableDefinitionDto {
        var tableName = tableName
        val tableDef = TableDefinitionDto(tableName.also { tableName = it })
        try {
            connection.use { con ->
                con!!.metaData.getColumns(con.catalog, con.schema, tableName, null).use { rs ->
                    setColumnDef(tableDef, rs)
                    setPrimaryKeyDef(tableDef, con)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return tableDef
    }

    @Throws(SQLException::class)
    private fun setPrimaryKeyDef(tableDef: TableDefinitionDto, con: Connection) {
        logger.info("con.getCatalog:{}, con.getSchema():{}",
                con.catalog, con.schema)
        con.metaData.getPrimaryKeys(con.catalog, con.schema,
                tableDef.tableName).use { rs2 ->
            while (rs2.next()) {
                tableDef.primaryKeys.add(rs2.getString("COLUMN_NAME"))
            }
        }
    }

    @Throws(SQLException::class)
    private fun setColumnDef(tableDef: TableDefinitionDto, rs: ResultSet) {
        while (rs.next()) {
            val columnDef = createColumnDef(tableDef.tableName, rs)
            tableDef.columnList.add(columnDef)
        }
    }

    @Throws(SQLException::class)
    private fun createColumnDef(tableName: String, rs: ResultSet): ColumnDefinitionDto {
        val dataType = readColumnType(rs)
        return ColumnDefinitionDto(tableName, rs.getString("COLUMN_NAME"), dataType, rs.getInt("COLUMN_SIZE"),
                rs.getInt("DECIMAL_DIGITS"))
    }

    @Throws(SQLException::class)
    private fun readColumnType(rs: ResultSet): Class<*> {
        val dataType: Class<*>
        dataType = when (rs.getInt("DATA_TYPE")) {
            Types.DECIMAL -> BigDecimal::class.java
            Types.DATE -> Date::class.java
            Types.TIMESTAMP -> Date::class.java
            Types.CHAR -> Char::class.java
            Types.VARCHAR -> String::class.java
            Types.NVARCHAR -> String::class.java
            Types.INTEGER -> Int::class.java
            Types.BIGINT -> Long::class.java
            Types.TINYINT, Types.BIT -> Short::class.java
            else -> throw IllegalArgumentException(
                    "型のマッピングが未定義です。追加してください。TYPE:" + rs.getString("TYPE_NAME") + "[" + rs.getInt("DATA_TYPE") + "]")
        }
        return dataType
    }

    /**
     * テーブル名出力（プレフィックス該当のみ）
     *
     * @param prefix
     * @throws Exception
     */
    @Throws(Exception::class)
    fun printTableByPrefixFilter(prefix: String) {
        val dao = TableDefinitionDao()
        try {
            dao.connection.use { con -> con!!.metaData.getTables(con.catalog, con.schema, null, null).use { rs -> printTableByPrefix(rs, prefix) } }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    private fun printTableByPrefix(rs: ResultSet, prefix: String) {
        while (rs.next()) {
            if (rs.getString("TABLE_NAME").startsWith(prefix)) {
                logger.debug(rs.getString("TABLE_NAME"))
            }
        }
    }

    @get:Throws(Exception::class)
    val tableNames: List<String>
        get() {
            val tableNames: MutableList<String> = ArrayList()
            try {
                this.connection.use { con ->
                    con!!.metaData.getTables(con.catalog, con.schema, null, null).use { rs ->
                        while (rs.next()) {
                            logger.debug("tablename add[{}]", rs.getString("TABLE_NAME"))
                            tableNames.add(rs.getString("TABLE_NAME"))
                        }
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return tableNames
        }

    @Throws(Exception::class)
    fun count(tableName: String): Int {
        logger.debug("$tableName count.")
        val sql = "select count(*) from $tableName"
        try {
            connection.use { connection -> connection!!.createStatement().use { statement -> statement.executeQuery(sql).use { ret -> if (ret.next()) return ret.getInt(1) } } }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return 0
    }

    @Throws(Exception::class)
    fun deleteTable(tableName: String) {
        val sql = "delete from $tableName"
        try {
            connection.use { connection ->
                connection!!.createStatement().use { statement ->
                    statement.execute(sql)
                    logger.debug(tableName + " deleted rows(" + statement.updateCount + ").")
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return
    }
}