package service.decimal.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import dto.db.ColumnDefinitionDto;
import service.RandomDataCreator;

@Component
public class RandomDecimalCreator implements RandomDataCreator<BigDecimal> {

    @Override
    public BigDecimal create(ColumnDefinitionDto def, int seed, int dataNo) {
	// double random = RandomUtils.nextDouble(0, Math.pow(10, def.getSize()
	// - def.getDegits()));
	// BigDecimal val = BigDecimal.valueOf(random).setScale(def.getDegits(),
	// RoundingMode.HALF_DOWN);
	
	int mergeSeed =  seed + dataNo;

	// 初期値
	String stringSeisu = String.valueOf(mergeSeed);
	
	// 小数生成
	int intShosu = RandomUtils.nextInt(0, (int) Math.pow(10, def.getDegits()));
	
	// 整数＋小数
	String constructorValue = stringSeisu + "." + String.valueOf(intShosu);
	
	// DECIMAL生成
	BigDecimal val = new BigDecimal(constructorValue);
	val = val.setScale(def.getDegits(), RoundingMode.HALF_DOWN);
	
	if (val.precision() > def.getSize()) {
	    val = val.remainder(BigDecimal.TEN.multiply(BigDecimal.valueOf(def.getSize())));
	}
	
	return val;
    }
}
