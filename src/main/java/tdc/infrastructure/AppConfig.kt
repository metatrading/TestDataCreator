package tdc.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import tdc.service.DatasCreator
import tdc.service.RandomDataCreator
import tdc.service.character.impl.CharacterDatasCreator
import tdc.service.character.impl.RandomCharacterCreator
import tdc.service.datetime.impl.DateDatasCreator
import tdc.service.datetime.impl.RandomDateCreator
import tdc.service.decimal.impl.DecimalDatasCreator
import tdc.service.decimal.impl.RandomDecimalCreator
import tdc.service.varchar.impl.RandomStringCreator
import tdc.service.varchar.impl.VarcharDatasCreator
import java.math.BigDecimal
import java.sql.Date

@Configuration
open class AppConfig {
    // ここで独自実装したCreatorを返却するように変更すれば、それが利用される。
    // 現在は、デフォルトと同一。
    // Datas系は変える必要性が低いはず。
//    @Bean
//    open fun createDatesCreator(): DatasCreator<Date> {
//        return DateDatasCreator()
//    }
//
//    @Bean("varcharDatasCreator")
//    open fun varcharDatasCreator(): DatasCreator<String> {
//        return VarcharDatasCreator()
//    }
//
//    @Bean("characterDatasCreator")
//    open fun charactorDatasCreator(): DatasCreator<String> {
//        return CharacterDatasCreator()
//    }
//
//    @Bean
//    open fun createDecimalsCreator(): DatasCreator<BigDecimal> {
//        return DecimalDatasCreator()
//    }
//
//    @Bean("randomDateCreator")
//    open fun randomDateCreator(): RandomDataCreator<Date> {
//        return RandomDateCreator()
//    }
//
//    @Bean("randomDecimalCreator")
//    open fun createBigDecimalCreator(): RandomDataCreator<BigDecimal> {
//        return RandomDecimalCreator()
//    }
//
//    @Bean("randomStringCreator")
//    open fun createStringCreator(): RandomDataCreator<String> {
//        return RandomStringCreator()
//    }
//
//    @Bean("randomCharacterCreator")
//    open fun createCharactorCreator(): RandomDataCreator<String> {
//        return RandomCharacterCreator()
//    }
}