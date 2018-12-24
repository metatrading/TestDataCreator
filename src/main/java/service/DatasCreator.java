package service;

import java.util.List;

import dto.db.ColumnDefinitionDto;

public interface DatasCreator<T> {
	List<T> create(ColumnDefinitionDto def, int rowSize) throws Exception;
	List<T> create(ColumnDefinitionDto def, int rowSize, int startSeed) throws Exception;

	void setSeed(int colIndex);
}
