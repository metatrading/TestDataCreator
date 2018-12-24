package property.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class PropertyDomainToValuesTest extends TestCase {
	
	@Test
	public void test01() {
		List<String> values = PropertyDomainToValues.getValues("SAPヘッダテキスト");
		assertThat(values.get(0), is("test1"));
		assertThat(values.get(1), is("test2"));
	}
}
