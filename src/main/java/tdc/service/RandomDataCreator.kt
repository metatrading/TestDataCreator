package tdc.service;

import tdc.db.ColumnDefinitionDto;

public interface RandomDataCreator<T> {
    T create(ColumnDefinitionDto cellDef, int seed, int dataNo);
}
