package service;

import dto.db.ColumnDefinitionDto;

public interface RandomDataCreator<T> {
	T create(ColumnDefinitionDto cellDef, int seed, int dataNo);
}
