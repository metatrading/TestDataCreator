package tdc.controller

import javafx.event.ActionEvent
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import tdc.dao.TableDefinitionDao
import tdc.db.TableDefinitionDto
import tdc.logger.log
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Component
@RequiredArgsConstructor
class MainTabController(
    private val dao: TableDefinitionDao,
    private val tableListWindowController: TableListWindowController,
    private val informationController: InformationController
) {
    lateinit var vbox: VBox
    lateinit var tableName: TextField
    lateinit var rowSize: TextField
    lateinit var execute: Button
    private val yyyyMMddHHmmssSSS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    fun execute(actionEvent: ActionEvent) {
        try {
            val start = OffsetDateTime.now()
            if (rowSize.text.isEmpty()) {
                informationController.setInformation("作成件数を指定してください。")
                return
            }

            val metadata: TableDefinitionDto = dao.getMetadata(tableName.text)
            if (metadata.columnList.isEmpty()) return

            tableListWindowController.calcData(tableName.text, rowSize.text, metadata)

            val end = OffsetDateTime.now()
            val d = Duration.between(start, end)
            informationController.setInformation(("\t" + rowSize.text + " rows created("
                    + yyyyMMddHHmmssSSS.format(Date()) + ",") + d[ChronoUnit.NANOS] / 1000000 + "ms)")

        } catch (e: Exception) {
            log.error(e.message, e)
            informationController.setInformation(e.message)
        }
    }

}