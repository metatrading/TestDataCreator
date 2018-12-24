
import java.util.List;

import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import junit.framework.TestCase;

public class ConfigGetTest extends TestCase {

	@Test
	public void test01() {
		// コンフィグのパスがシステム設定値にあるか確認。ここに指定がなければ、「application.conf」を使用する
		String configResource = System.getProperty("config.resource");
		System.out.println("configResource : " + configResource);

		// コンフィグを取得する(load(ファイルパス)とすることで、手動でパスを指定することも出来る)
		Config config = ConfigFactory.load("domainValue.conf");
		
		String key = "G/L 勘定";
//		System.out.println("---------------------------");
		List<String> list = config.getStringList(key);
		list.forEach(atr->System.out.println(atr));
	}
}
