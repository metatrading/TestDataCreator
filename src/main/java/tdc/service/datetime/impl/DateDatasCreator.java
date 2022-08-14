package tdc.service.datetime.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import tdc.check.CollectionCheckUtil;
import tdc.db.ColumnDefinitionDto;
import tdc.property.impl.PropertyDomainToValues;
import tdc.property.impl.PropertyTableColumnToDomain;
import tdc.service.DatasCreator;
import tdc.service.RandomDataCreator;

@Component
public class DateDatasCreator implements DatasCreator<Date> {
    @Inject
    private RandomDataCreator<Date> randomDataCreator;
    private int colIndex;

    private SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public List<Date> create(ColumnDefinitionDto def, int rowSize) throws Exception {
	return create(def, rowSize, 0);
    }

    @Override
    public void setSeed(int colIndex) {
	this.colIndex = colIndex;
    }

    @Override
    public List<Date> create(ColumnDefinitionDto def, int rowSize, int startSeed) throws Exception {
	// table & column To domain
	String domain = PropertyTableColumnToDomain.Companion.getDomain(def);

	// domain to values
	List<String> values = PropertyDomainToValues.getValues(domain);

	List<Date> datas = new LinkedList<>();
	if (!CollectionCheckUtil.isEmpty(values)) {
	    // ドメインに基づくデータ生成
	    while (datas.size() < rowSize) {
		datas.add(
			new Date(sdfYYYYMMDD.parse(values.get((startSeed + datas.size()) % values.size())).getTime()));
	    }
	} else {
	    // 列名・型・桁に応じたデータ生成
	    while (datas.size() < rowSize) {
		datas.add(randomDataCreator.create(def, colIndex - 1, startSeed + datas.size()));
	    }
	}
	return datas;
    }

}
