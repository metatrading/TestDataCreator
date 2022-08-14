package tdc.service.decimal.impl

import org.springframework.stereotype.Component
import tdc.check.CollectionCheckUtil.isNotEmpty
import tdc.db.ColumnDefinitionDto
import tdc.property.impl.PropertyDomainToValues.Companion.getValues
import tdc.property.impl.PropertyTableColumnToDomain.Companion.getDomain
import tdc.service.DatasCreator
import tdc.service.RandomDataCreator
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@Component
class DecimalDatasCreator : DatasCreator<BigDecimal?> {
    @Inject
    private val randomDataCreator: RandomDataCreator<BigDecimal>? = null
    private var colIndex = 0
    override fun create(def: ColumnDefinitionDto?, rowSize: Int): List<BigDecimal>? {
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
    override fun create(def: ColumnDefinitionDto?, rowSize: Int, startSeed: Int): List<BigDecimal> {
        // table & column To domain
        val domain = getDomain(def!!)

        // domain to values
        val values: List<String?> = getValues(domain)
        val datas: MutableList<BigDecimal> = LinkedList()
        if (isNotEmpty(values)) {
            // ドメインに基づくデータ生成
            while (datas.size < rowSize) {
                val index = datas.size % values.size
                datas.add(BigDecimal(values[index]))
            }
        } else {
            // 列名・型・桁に応じたデータ生成
            while (datas.size < rowSize) {
                datas.add(randomDataCreator!!.create(def, colIndex, startSeed + datas.size + 1))
            }
        }
        return datas
    }
}