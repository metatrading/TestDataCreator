package miniservice;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class CharactorCreator {
	public static void main(String[] args) {
		int row = 1;
		int column = 4;
		int length = 4;

		for (int y = 0; y < row; y++) {
			for (int i = 0; i < column; i++) {

				String number = RandomStringUtils.randomNumeric(length, length);
				number = StringUtils.leftPad(number, 8, "0");
				System.out.print(number);
				if (i + 1 != column)
					System.out.print("\t");
			}
			System.out.println();
		}
	}
}
