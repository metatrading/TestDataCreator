package tdc

import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import org.springframework.stereotype.Component
import tdc.dao.TableDefinitionDao

@Component
class DeleteTableButton(
    private val dao: TableDefinitionDao, private val record: TableCountRecord
) : Button("all delete") {
    init {
        onMouseClicked = EventHandler { b: MouseEvent? ->
            try {
                dao.deleteTable(record.tableNameProperty()!!.get())
            } catch (e: Exception) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace()
            }
            record.countProperty()!!.set(0)
        }
    }
}