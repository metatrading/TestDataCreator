package service.varchar.impl;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import dto.db.ColumnDefinitionDto;
import service.RandomDataCreator;
@Component
public class RandomStringCreator implements RandomDataCreator<String> {

	public String create(ColumnDefinitionDto def, int seed, int dataNo) {

		if (def.getSize() == 1) {
			int val = safeValue(seed + dataNo, def.getSize());
			return String.valueOf(val);
		} else {
			// TODO
			// とりあえず、列名1文字目と数字と０パディングして返す.本当は、大文字の部分だけ抽出して結合して、桁に応じて切り下げて返す。
			// 0多すぎるので列物理名の文字長か列サイズどちらか小さいほうを採用する。
			int colSize;
			if (def.getColumnName().length() > def.getSize()) {
				colSize = def.getSize();
			} else {
				colSize = def.getColumnName().length();
			}
			String format = def.getColumnName().substring(0, 1) + "{0,number,"
					+ StringUtils.repeat("0", colSize - 1) + "}";
			String formated = MessageFormat.format(format, safeValue(seed + dataNo, colSize - 1));

			// 桁数オーバーの考慮
			while (formated.length() > colSize) {
				formated = create(def, seed, dataNo % 10);
			}
			return formated;
		}
	}

	private int safeValue(int val, int size) {
		int safeVal;
		if (String.valueOf(val).length() > size) {
			safeVal = val % 10;
		} else {
			safeVal = val;
		}
		return safeVal;
	}
}
