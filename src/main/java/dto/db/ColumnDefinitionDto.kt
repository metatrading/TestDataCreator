package dto.db;

@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.ToString
public class ColumnDefinitionDto {
	
	@lombok.Setter
	@lombok.Getter
	private String tableName;
	
	@lombok.Setter
	@lombok.Getter
	private String columnName;
	
	@lombok.Setter
	@lombok.Getter
	private Class<?> dataType;
	
	@lombok.Setter
	@lombok.Getter
	private int size;
	
	@lombok.Setter
	@lombok.Getter
	private int degits;
}
