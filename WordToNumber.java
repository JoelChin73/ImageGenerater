//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

/*
 * Converts numbers words from one to ninety nine to the respective int values.
 */
public class WordToNumber {

	private String[][] placement = {
			{ "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" },
			{ "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" },
			{ "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" } };
	private String[] multiply = { "twice", "thrice" };

	int value;
	int multiplicand;
	int multiplier;

	public int replace(String wordNum) {
		String num = "";
		String[] split = wordNum.split(" ");
		for (int i = 0; i < split.length; i++) {
			for (int j = 0; j < placement.length; j++) {
				for (int k = 0; k < placement[j].length; k++) {
					if (split[i].equals(placement[j][k].toLowerCase()))
						num += String.valueOf(k + 1);
				}
			}
		}
		if (num.equals(""))
			return 0;
		else
			return Integer.parseInt(num);
	}

	public void replaceMultiply(String multNum) {
		if (multNum.equals(multiply[0].toLowerCase())) {
			multiplier = 2;
		} else if (multNum.equals(multiply[1].toLowerCase())) {
			multiplier = 3;
		}
	}

	public void replaceMultiply(String multNum, String multicand) {
		replaceMultiply(multNum);
		value = Integer.parseInt(multicand) * multiplier;
	}

	public static void main(String[] arg) {
		System.out.println(new WordToNumber().replace("forty three"));
	}
}
