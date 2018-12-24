package service.decimal.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import check.CollectionCheckUtil;
import dto.db.ColumnDefinitionDto;
import property.impl.PropertyDomainToValues;
import property.impl.PropertyTableColumnToDomain;
import service.DatasCreator;
import service.RandomDataCreator;

@Component
public class DecimalDatasCreator implements DatasCreator<BigDecimal> {
    @Inject
    private RandomDataCreator<BigDecimal> randomDataCreator;
    private int colIndex;

    @Override
    public List<BigDecimal> create(ColumnDefinitionDto def, int rowSize) {
	try {
	    return create(def, rowSize, 0);
	} catch (Exception e) {
	    // TODO 自動生成された catch ブロック
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    public void setSeed(int colIndex) {
	this.colIndex = colIndex;
    }

    @Override
    public List<BigDecimal> create(ColumnDefinitionDto def, int rowSize, int startSeed) throws Exception {
	// table & column To domain
	String domain = PropertyTableColumnToDomain.getDomain(def);

	// domain to values
	List<String> values = PropertyDomainToValues.getValues(domain);

	List<BigDecimal> datas = new LinkedList<>();
	if (CollectionCheckUtil.isNotEmpty(values)) {
	    // ドメインに基づくデータ生成
	    while (datas.size() < rowSize) {
		int index = datas.size() % values.size();
		datas.add(new BigDecimal(values.get(index)));
	    }
	} else {
	    // 列名・型・桁に応じたデータ生成
	    while (datas.size() < rowSize) {
		datas.add(randomDataCreator.create(def, colIndex, startSeed + datas.size() + 1));
	    }
	}
	return datas;
    }

}
