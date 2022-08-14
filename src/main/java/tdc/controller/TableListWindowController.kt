package tdc.controller

import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.input.KeyEvent
import org.springframework.stereotype.Component
import tdc.dao.TableDefinitionDao
import tdc.db.TableDefinitionDto
import tdc.gui.eventhandler.CopieAndPaster
import tdc.logger.log
import tdc.service.DatasCreator
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import java.util.regex.Pattern

@Component
class TableListWindowController(
    val dao: TableDefinitionDao,
    private val varcharDatasCreator: DatasCreator<String>,
    private val dateDatasCreator: DatasCreator<java.sql.Date>,
    private val decimalDatasCreator: DatasCreator<BigDecimal>,
    private val characterDatasCreator: DatasCreator<String>
) {
    @FXML
    lateinit var tableView: TableView<Map<String, String>>

    fun initialize() {
        tableView.stylesheets.add("listView.css")
    }

    fun calcData(tableName: String, rowSizeString: String, metadata: TableDefinitionDto) {
        if (tableName.isEmpty()) throw java.lang.Exception("テーブル名が未指定です。")

        val rowSize: Int = rowSizeString.toInt()
        val columnValueMapList = tableView.items
        val def: TableDefinitionDto = resolveTableDefinition(tableName)
        // テーブル確定、Data生成、
        setData(def, columnValueMapList, rowSize)
        // ヘッダ生成
        val columns = resolveColumns(columnValueMapList[0].keys)
        tableView.columns.setAll(columns)
        // then Copy
        tableView.addEventHandler(KeyEvent.KEY_RELEASED, CopieAndPaster(tableView, def))
    }

    private fun resolveTableDefinition(tableName: String): TableDefinitionDto {
        val def: TableDefinitionDto = dao.getMetadata(tableName)
        if (def.columnList.isEmpty()) {
            log.error(tableName + "は、DBに存在しません。")
            throw java.lang.Exception(tableName + "は、DBに存在しません。")
        }
        return def;
    }

    /**
     * data create.
     * */
    @Throws(java.lang.Exception::class)
    fun setData(
        def: TableDefinitionDto,
        columnValueMapList: ObservableList<Map<String, String>>,
        rowSize: Int
    ) {
        // sample data generate.
        val datasList: List<List<*>>? = createTestDataListByColumn(def, rowSize, 0)
        for (rowNumber in 1..rowSize) {
            val map: MutableMap<String, String> = LinkedHashMap()
            for (column in def.columnList.indices) {
                val value = datasList!![column][rowNumber - 1].toString()
                // カラムごとに値を設定
                map[def.columnList[column].columnName] = value
            }
            columnValueMapList.add(map)
        }
    }

    /**
     * カラム別データパターン。
     */
    @Throws(java.lang.Exception::class)
    private fun createTestDataListByColumn(def: TableDefinitionDto, rowSize: Int, currentRow: Int): List<List<*>>? {
        val columnDatasList: MutableList<List<*>> = LinkedList()
        // 事前データ準備
        for (colDef in def.columnList) {
            val colIndex = def.columnList.indexOf(colDef)
            var columnValues: List<Any?>?
            var datasCreator: DatasCreator<*>? = null
            if (colDef.dataType == String::class.java) {
                datasCreator = varcharDatasCreator
            }
            if (colDef.dataType == Char::class.java) {
                datasCreator = characterDatasCreator
            }
            if (colDef.dataType == BigDecimal::class.java) {
                datasCreator = decimalDatasCreator
            }
            if (colDef.dataType == Long::class.java) {
                datasCreator = decimalDatasCreator
            }
            if (colDef.dataType == Int::class.java) {
                datasCreator = decimalDatasCreator
            }
            if (colDef.dataType == Short::class.java) {
                datasCreator = decimalDatasCreator
            }
            if (colDef.dataType == java.sql.Date::class.java || colDef.dataType == Timestamp::class.java) {
                datasCreator = dateDatasCreator
            }
            datasCreator!!.setSeed(colIndex)
            columnValues = datasCreator.create(colDef, rowSize, currentRow)
            columnDatasList.add(columnValues)
        }
        return columnDatasList
    }

    private fun resolveColumns(
        columnNames: Set<String>
    ): MutableList<TableColumn<Map<String, String>, String>> {
        val columnList: MutableList<TableColumn<Map<String, String>, String>> = mutableListOf()
        // 1 row pick
        for (key in columnNames) {
            val colum = TableColumn<Map<String, String>, String>(
                key
            )
            colum.setCellValueFactory { arg0 ->
                SimpleStringProperty(
                    arg0.value[key]
                )
            }
            columnList.add(colum)
        }
        return columnList
    }

    private fun capitalize(name: String): String {
        val sb = StringBuilder()
        val reg = Pattern.compile("[A-Z]")
        for (i in name.indices) {
            val sub = name.substring(i, i + 1)
            if (i == 0) {
                sb.append(sub.lowercase(Locale.getDefault()))
                continue
            }
            val match = reg.matcher(sub)
            if (match.find()) {
                sb.append("_")
                sb.append(sub.lowercase(Locale.getDefault()))
            } else {
                sb.append(sub.lowercase(Locale.getDefault()))
            }
        }
        return sb.toString()
    }
}