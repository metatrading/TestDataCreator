package property.impl;

import tdc.property.impl.PropertyDomainToValues;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

public class PropertyDomainToValuesTest {
	
	public void test01() {
		List<String> values = PropertyDomainToValues.getValues("ヘッダテキスト");
	}
}
