package tdc.service

import tdc.db.ColumnDefinitionDto

interface DatasCreator<T> {
    @Throws(Exception::class)
    fun create(def: ColumnDefinitionDto?, rowSize: Int): List<T>?

    @Throws(Exception::class)
    fun create(def: ColumnDefinitionDto?, rowSize: Int, startSeed: Int): List<T>
    fun setSeed(colIndex: Int)
}