package dto.db

import java.util.*

class TableDefinitionDto(
        var tableName: String,
        var columnList: MutableList<ColumnDefinitionDto> = LinkedList(),
        var primaryKeys: MutableList<String> = mutableListOf()
)