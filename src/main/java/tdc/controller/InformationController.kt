package tdc.controller

import javafx.scene.control.Label
import org.springframework.stereotype.Component

@Component
class InformationController {

    lateinit var information: Label

    fun setInformation(message: String?) {
        message?.let {
            information.text = it
        }
    }
}