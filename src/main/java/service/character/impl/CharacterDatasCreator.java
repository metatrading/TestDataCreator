package service.character.impl;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import check.CollectionCheckUtil;
import dto.db.ColumnDefinitionDto;
import property.impl.PropertyDomainToValues;
import property.impl.PropertyTableColumnToDomain;
import service.DatasCreator;
import service.RandomDataCreator;

@Component
public class CharacterDatasCreator implements DatasCreator<String> {
	@Inject
	@Named("RandomCharacterCreator")
	private RandomDataCreator<String> randomCharacterCreator;
	private int colIndex;

	@Override
	public List<String> create(ColumnDefinitionDto def, int rowSize) throws Exception {
		return create(def, colIndex, 0);
	}

	@Override
	public void setSeed(int colIndex) {
		this.colIndex = colIndex;
	}

	@Override
	public List<String> create(ColumnDefinitionDto def, int rowSize, int startSeed) throws Exception {

		List<String> columnDatas = new LinkedList<>();

		// table & column To domain
		String domain = PropertyTableColumnToDomain.getDomain(def);

		// domain to values
		List<String> domainValues = PropertyDomainToValues.getValues(domain);

		boolean isExistsDomainValues = CollectionCheckUtil.isNotEmpty(domainValues);
		while (columnDatas.size() < rowSize) {
			if (isExistsDomainValues) {
				// ドメインに基づくデータ生成
				int index = columnDatas.size() % domainValues.size();
				columnDatas.add(domainValues.get(index));
			} else {
				// 列名・型・桁に応じたデータ生成
				columnDatas.add(randomCharacterCreator.create(def, colIndex, startSeed + columnDatas.size() + 1));
			}
		}
		return columnDatas;
	}
}
