package tdc

import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.Stage
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import tdc.dao.TableDefinitionDao

@Component
@RequiredArgsConstructor
class TableCounter : Application() {
    private val tableDefinitionDao: TableDefinitionDao? = null
    private val deleteTableButton: Button? = null
    private val values = FXCollections.observableArrayList<TableCountRecord>()
    var observable: ObservableValue<out ObservableList<TableCountRecord>>? = null
    private var progressIndicator: ProgressIndicator? = null
    private var table: TableView<TableCountRecord>? = null

    @Throws(Exception::class)
    override fun start(arg0: Stage) {
        val tableNames = tableDefinitionDao!!.tableNames
        val vbox = VBox()
        table = TableView()
        val tableNameColumn = TableColumn<TableCountRecord, String>("tableName")
        tableNameColumn.cellValueFactory = PropertyValueFactory("tableName")
        table!!.columns.add(tableNameColumn)
        val coutnerColumn = TableColumn<TableCountRecord, Int>("count")
        coutnerColumn.cellValueFactory = PropertyValueFactory("count")
        table!!.columns.add(coutnerColumn)
        val deleteButtonColumn = TableColumn<TableCountRecord, DeleteTableButton>("deleteButton")
        deleteButtonColumn.cellValueFactory = PropertyValueFactory("deleteButton")
        table!!.columns.add(deleteButtonColumn)

        // table.setItems(values);
        observable = object : ObservableValue<ObservableList<TableCountRecord>> {
            override fun addListener(listener: ChangeListener<in ObservableList<TableCountRecord>>?) {
                TODO("Not yet implemented")
            }

            override fun addListener(arg0: InvalidationListener) {
                println("added")
            }

            override fun removeListener(listener: ChangeListener<in ObservableList<TableCountRecord>>?) {
                TODO("Not yet implemented")
            }

            override fun removeListener(arg0: InvalidationListener) {}

            override fun getValue(): ObservableList<TableCountRecord> {
                return values
            }

        }
        values.addListener { b: ListChangeListener.Change<out TableCountRecord> ->
            println("added")
            val maxLength = b.list.stream().map { a: TableCountRecord ->
                a.tableNameProperty()!!
                    .get().length
            }
                .max { a: Int, c: Int -> a - c }
            maxLength.ifPresent { d: Int -> tableNameColumn.setPrefWidth(8.0 * d) }
        }
        // table.itemsProperty().bind(observable);
        table!!.itemsProperty().set(values)
        val button = Button("execute")
        button.onMouseClicked = EventHandler {
            val task: Task<Unit> = object : Task<Unit>() {
                override fun call() {
                    create(tableDefinitionDao, tableNames)
                }
            }
            Thread(task).start()
        }
        val hbox = HBox()
        progressIndicator = ProgressIndicator(0.0)
        hbox.children.addAll(button, progressIndicator)
        VBox.setVgrow(table, Priority.ALWAYS)
        vbox.children.addAll(hbox, table)
        val pane = AnchorPane()
        pane.children.addAll(vbox)
        AnchorPane.setBottomAnchor(vbox, 1.0)
        AnchorPane.setTopAnchor(vbox, 1.0)
        AnchorPane.setRightAnchor(vbox, 1.0)
        AnchorPane.setLeftAnchor(vbox, 1.0)
        arg0.scene = Scene(pane)
        arg0.show()
    }

    private fun create(dao: TableDefinitionDao?, tableNames: List<String>) {
        if (!observable!!.value.isEmpty()) {
            observable!!.value.clear()
            progressIndicator!!.progress = 0.0
        }
        for (tableName in tableNames) {
            // String tableName = tableNames.get(index);
            var count: Int? = null
            try {
                count = dao!!.count(tableName)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val record = TableCountRecord()
            record.setCountProperty(SimpleIntegerProperty(count!!))
            record.setTableNameProperty(SimpleStringProperty(tableName))
            deleteTableButton!!.addEventHandler(MouseEvent.MOUSE_CLICKED) {
                // 再作成
                table!!.refresh()
            }
            record.deleteButton = deleteTableButton
            observable!!.value.add(record)
            progressIndicator!!.progress = tableNames.indexOf(tableName).toDouble() / (tableNames.size - 1)
        }
        observable!!.value.sortWith { a: TableCountRecord, b: TableCountRecord ->
            b.countProperty()!!
                .intValue() - a.countProperty()!!.intValue()
        }
        // index++;
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(*args)
        }
    }
}