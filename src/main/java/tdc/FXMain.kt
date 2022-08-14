package tdc

import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.stage.Stage
import lombok.RequiredArgsConstructor
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component
import tdc.gui.CreateDataTab
import tdc.infrastructure.MySpringFXMLLoader
import tdc.logger.log
import kotlin.system.exitProcess

/**
 * ・CTRLで範囲選択 ・範囲選択状態でコピー ・
 *
 * @author Takagi Satoshi
 */
@Component
@RequiredArgsConstructor
open class FXMain : Application() {
    private lateinit var mySpringFXMLLoader: MySpringFXMLLoader
    private lateinit var applicationContext: ConfigurableApplicationContext
    private lateinit var tabpane:TabPane


    @Throws(Exception::class)
    override fun init() {
        super.init()
        applicationContext = SpringApplicationBuilder(Main::class.java).run()
        mySpringFXMLLoader = applicationContext.getBean(MySpringFXMLLoader::class.java)
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        tabpane = mySpringFXMLLoader.load("fxml/FXMain.fxml")
        stage.onCloseRequest = EventHandler { exitProcess(0) }
        stage.title = "TestData"
        stage.scene = Scene(tabpane)
        stage.isMaximized = true
        stage.show()
    }

    @Throws(Exception::class)
    override fun stop() {
        super.stop()
        applicationContext.close()
        Platform.exit()
    }
}