package tdc.service.varchar.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import tdc.check.CollectionCheckUtil;
import tdc.db.ColumnDefinitionDto;
import tdc.property.impl.PropertyDomainToValues;
import tdc.property.impl.PropertyTableColumnToDomain;
import tdc.service.DatasCreator;
import tdc.service.RandomDataCreator;

@Component
public class VarcharDatasCreator implements DatasCreator<String> {
	@Inject
	@Named("randomStringCreator")
	private RandomDataCreator<String> randomDataCreator;
	private int colIndex;

	@Override
	public List<String> create(ColumnDefinitionDto def, int rowSize) {
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
	public List<String> create(ColumnDefinitionDto def, int rowSize, int startSeed) throws Exception {
		// table & column To domain
		String domain = PropertyTableColumnToDomain.Companion.getDomain(def);

		// domain to values
		List<String> values = PropertyDomainToValues.getValues(domain);

		List<String> datas = new LinkedList<>();
		if (CollectionCheckUtil.isNotEmpty(values)) {
			// ドメインに基づくデータ生成
			while (datas.size() < rowSize) {
				int index = datas.size() % values.size();
				datas.add(values.get(index));
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
