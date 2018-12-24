package app;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;
import org.springframework.context.annotation.Primary;

import service.DatasCreator;
import service.RandomDataCreator;
import service.character.impl.CharacterDatasCreator;
import service.character.impl.RandomCharacterCreator;
import service.datetime.impl.DateDatasCreator;
import service.datetime.impl.RandomDateCreator;
import service.decimal.impl.DecimalDatasCreator;
import service.decimal.impl.RandomDecimalCreator;
import service.varchar.impl.RandomStringCreator;
import service.varchar.impl.VarcharDatasCreator;

@Configuration
@ComponentScan(basePackages = { "service", "dao", "main", "miniservice",
		"gui" }, scopeResolver = Jsr330ScopeMetadataResolver.class)
public class AppConfig {

	// ここで独自実装したCreatorを返却するように変更すれば、それが利用される。
	// 現在は、デフォルトと同一。

	// Datas系は変える必要性が低いはず。
	@Bean
	@Primary
	public DatasCreator<Date> createDatesCreator() {
		return new DateDatasCreator();
	}

	@Bean("VarcharDatasCreator")
	@Primary
	public DatasCreator<String> createVacharsCreator() {
		return new VarcharDatasCreator();
	}

	@Bean("CharacterDatasCreator")
	@Primary
	public DatasCreator<String> createCharactorsCreator() {
		return new CharacterDatasCreator();
	}

	@Bean
	@Primary
	public DatasCreator<BigDecimal> createDecimalsCreator() {
		return new DecimalDatasCreator();
	}

	@Bean("RandomDateCreator")
	@Primary
	public RandomDataCreator<Date> createDateCreator() {
		return new RandomDateCreator();
	}

	@Bean("RandomDecimalCreator")
	@Primary
	public RandomDataCreator<BigDecimal> createBigDecimalCreator() {
		return new RandomDecimalCreator();
	}

	@Bean("RandomStringCreator")
	@Primary
	public RandomDataCreator<String> createStringCreator() {
		return new RandomStringCreator();
	}

	@Bean("RandomCharacterCreator")
	@Primary
	public RandomDataCreator<String> createCharactorCreator() {
		return new RandomCharacterCreator();
	}

}
