package dto.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.ToString
@Builder
public class TableDefinitionDto {
	@lombok.Setter
	@lombok.Getter
	private String tableName;
	
	@lombok.Setter
	@lombok.Getter
	private List<ColumnDefinitionDto> columnList = new LinkedList<>();

	@lombok.Setter
	@lombok.Getter
	private List<String> primaryKeys = new ArrayList<String>();
}
