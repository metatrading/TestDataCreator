package tdc.service

import tdc.db.ColumnDefinitionDto

interface RandomDataCreator<T> {
    fun create(cellDef: ColumnDefinitionDto?, seed: Int, dataNo: Int): T
}