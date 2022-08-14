package tdc.service.varchar.impl

import org.springframework.beans.factory.annotation.Qualifier
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
class VarcharDatasCreator(
    @Qualifier("randomStringCreator") private val randomDataCreator: RandomDataCreator<String>
) : DatasCreator<String?> {
    private var colIndex = 0
    override fun create(def: ColumnDefinitionDto?, rowSize: Int): List<String>? {
        try {
            return create(def, rowSize, 0)
        } catch (e: Exception) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace()
        }
        return null
    }

    override fun setSeed(colIndex: Int) {
        this.colIndex = colIndex
    }

    @Throws(Exception::class)
    override fun create(def: ColumnDefinitionDto?, rowSize: Int, startSeed: Int): List<String> {
        // table & column To domain
        val domain = getDomain(def!!)

        // domain to values
        val values = getValues(domain)
        val datas: MutableList<String> = LinkedList()
        if (isNotEmpty(values)) {
            // ドメインに基づくデータ生成
            while (datas.size < rowSize) {
                val index = datas.size % values.size
                datas.add(values[index])
            }
        } else {
            // 列名・型・桁に応じたデータ生成
            while (datas.size < rowSize) {
                datas.add(randomDataCreator.create(def, colIndex, startSeed + datas.size + 1))
            }
        }
        return datas
    }
}