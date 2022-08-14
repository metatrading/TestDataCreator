package tdc.controller

import javafx.event.ActionEvent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import tdc.dao.TableDefinitionDao
import tdc.db.TableDefinitionDto
import tdc.infrastructure.MySpringFXMLLoader
import tdc.logger.log
import tdc.service.DatasCreator
import java.math.BigDecimal
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Component
@RequiredArgsConstructor
class CreateDataTabController(
    private val dao: TableDefinitionDao,
//    private val tableListWindowController: TableListWindowController,
    private val mySpringFXMLLoader: MySpringFXMLLoader
) {
    lateinit var vbox: VBox
    lateinit var tableName: TextField
    lateinit var rowSize: TextField
    lateinit var execute: Button
    lateinit var status: Label
    private val yyyyMMddHHmmssSSS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    fun execute(actionEvent: ActionEvent) {
        try {
            val start = OffsetDateTime.now()
            if (rowSize.text.isEmpty()) {
                status.text = "作成件数を指定してください。"
                return
            }

            val metadata: TableDefinitionDto = dao.getMetadata(tableName.text)
            if (metadata.columnList.isEmpty()) return
            if (vbox.children.size == 2) {
                vbox.children.removeAt(1)
            }

            status.maxHeight = Double.MAX_VALUE

            mySpringFXMLLoader.load<VBox>("/fxml/TableListWindow.fxml")
            val tableListWindowController = mySpringFXMLLoader.loader.getController<TableListWindowController>()
            tableListWindowController.calcData(tableName.text, rowSize.text, metadata)

            val end = OffsetDateTime.now()
            val d = Duration.between(start, end)
            status.text = ("\t" + rowSize.text + " rows created("
                    + yyyyMMddHHmmssSSS.format(Date()) + ",") + d[ChronoUnit.NANOS] / 1000000 + "ms)"
        } catch (e: Exception) {
            e.printStackTrace()
            status.text = e.message
        }
    }

}