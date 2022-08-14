package tdc.service.character.impl

import org.springframework.stereotype.Component
import tdc.check.CollectionCheckUtil.isNotEmpty
import tdc.db.ColumnDefinitionDto
import tdc.property.impl.PropertyDomainToValues.Companion.getValues
import tdc.property.impl.PropertyTableColumnToDomain.Companion.getDomain
import tdc.service.DatasCreator
import tdc.service.RandomDataCreator
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@Component
class CharacterDatasCreator : DatasCreator<String?> {
    @Inject
    @Named("randomCharacterCreator")
    private val randomCharacterCreator: RandomDataCreator<String>? = null
    private var colIndex = 0
    @Throws(Exception::class)
    override fun create(def: ColumnDefinitionDto?, rowSize: Int): List<String>? {
        return create(def, colIndex, 0)
    }

    override fun setSeed(colIndex: Int) {
        this.colIndex = colIndex
    }

    @Throws(Exception::class)
    override fun create(def: ColumnDefinitionDto?, rowSize: Int, startSeed: Int): List<String> {
        val columnDatas: MutableList<String> = LinkedList()

        // table & column To domain
        val domain = getDomain(def!!)

        // domain to values
        val domainValues = getValues(domain)
        val isExistsDomainValues = isNotEmpty(domainValues)
        while (columnDatas.size < rowSize) {
            if (isExistsDomainValues) {
                // ドメインに基づくデータ生成
                val index = columnDatas.size % domainValues.size
                columnDatas.add(domainValues[index])
            } else {
                // 列名・型・桁に応じたデータ生成
                columnDatas.add(randomCharacterCreator!!.create(def, colIndex, startSeed + columnDatas.size + 1))
            }
        }
        return columnDatas
    }
}