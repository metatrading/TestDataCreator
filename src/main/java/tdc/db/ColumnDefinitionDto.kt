package tdc.db

data class ColumnDefinitionDto(
    val tableName: String = "",
    val columnName: String = "",
    val dataType: Class<*>,
    val size: Int = 0,
    val digits: Int = 0
)