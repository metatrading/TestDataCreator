package tdc

import javafx.beans.property.IntegerProperty
import javafx.beans.property.StringProperty
import javafx.scene.control.Button
import org.springframework.stereotype.Component

@Component
class TableCountRecord {
    private var tableNameProperty: StringProperty? = null
    private var countProperty: IntegerProperty? = null
    var deleteButton: Button? = null
    fun tableNameProperty(): StringProperty? {
        return tableNameProperty
    }

    fun setTableNameProperty(tableNameProperty: StringProperty?) {
        this.tableNameProperty = tableNameProperty
    }

    fun countProperty(): IntegerProperty? {
        return countProperty
    }

    fun setCountProperty(countProperty: IntegerProperty?) {
        this.countProperty = countProperty
    }
}