package tdc.service.datetime.impl

import org.springframework.stereotype.Component
import tdc.check.CollectionCheckUtil.isEmpty
import tdc.db.ColumnDefinitionDto
import tdc.property.impl.PropertyDomainToValues.Companion.getValues
import tdc.property.impl.PropertyTableColumnToDomain.Companion.getDomain
import tdc.service.DatasCreator
import tdc.service.RandomDataCreator
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Component
class DateDatasCreator : DatasCreator<Date?> {
    @Inject
    private val randomDataCreator: RandomDataCreator<Date>? = null
    private var colIndex = 0
    private val sdfYYYYMMDD = SimpleDateFormat("yyyy/MM/dd")
    @Throws(Exception::class)
    override fun create(def: ColumnDefinitionDto?, rowSize: Int): List<Date>? {
        return create(def, rowSize, 0)
    }

    override fun setSeed(colIndex: Int) {
        this.colIndex = colIndex
    }

    @Throws(Exception::class)
    override fun create(def: ColumnDefinitionDto?, rowSize: Int, startSeed: Int): List<Date> {
        // table & column To domain
        val domain = getDomain(def!!)

        // domain to values
        val values: List<String?> = getValues(domain)
        val datas: MutableList<Date> = LinkedList()
        if (!isEmpty(values)) {
            // ドメインに基づくデータ生成
            while (datas.size < rowSize) {
                datas.add(
                    Date(sdfYYYYMMDD.parse(values[(startSeed + datas.size) % values.size]).time)
                )
            }
        } else {
            // 列名・型・桁に応じたデータ生成
            while (datas.size < rowSize) {
                datas.add(randomDataCreator!!.create(def, colIndex - 1, startSeed + datas.size))
            }
        }
        return datas
    }
}