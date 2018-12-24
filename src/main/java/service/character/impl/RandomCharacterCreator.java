package service.character.impl;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import dto.db.ColumnDefinitionDto;
import service.RandomDataCreator;

@Component
public class RandomCharacterCreator implements RandomDataCreator<String> {

    @Override
    public String create(ColumnDefinitionDto def, int seed, int dataNo) {
	if (def.getSize() == 1) {
	    int val = safeValue(seed + dataNo, def.getSize());
	    return String.valueOf(val);
	} else {
	    int colSize = def.getSize();
	    String format = def.getColumnName().substring(0, 1) + "{0,number," + StringUtils.repeat("0", colSize - 1)
		    + "}";
	    String formated = MessageFormat.format(format, safeValue(seed + dataNo, colSize - 1));

	    // 桁数オーバーの考慮
	    while (formated.length() > colSize) {
		formated = create(def, seed, dataNo % 10);
	    }
	    return formated;
	}
    }

    /**
     * オーバフローしない値生成.
     * 
     * @param val
     *            基準値
     * @param size
     *            データ型の桁数
     * @return データ型の桁数を超過しない値
     */
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
