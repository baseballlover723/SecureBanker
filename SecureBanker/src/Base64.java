/**
 * Converts numbers to and from Base 64
 *
 * @author rosspa. Created Jul 3, 2014.
 */
public class Base64 {

	/**
	 * 
	 * encryptes the Base10 number into Base64
	 *
	 * @param base10Number
	 * @return the number in Base64 form
	 */
	public static String Encrypte(long base10Number) {
		if (base10Number < 64) {
			if (base10Number > -1) {
				return getBase64Char(base10Number);
			}
			// handles negitive base10Numbers
			return "-" + Encrypte(-base10Number);
		}
		// divide by 64
		return Encrypte(base10Number >> 6) + getBase64Char((long)(base10Number % 64));
	}

	/**
	 * 
	 * Converts the given String from Base64 to Base10
	 *
	 * @param base64String
	 * @return the Base10 representation of the given Base64 String
	 * @throws NumberFormatException
	 *             when it encounters a invaild character
	 */
	public static long Decrypte(String base64String) throws NumberFormatException {
		long base10Number = 0L;
		char[] chars = base64String.toCharArray();
		int size = chars.length;
		// if negitive, skip the first char and subtract instead of add
		if (chars[0] == '-') {
			for (int k = 1; k < size; k++) {
				// gets the correct power of 64 to multiply by
				base10Number -= (1L << (size - k - 1) * 6) * getBase10Long(chars[k]);
			}
		} else {
			for (int k = 0; k < size; k++) {
				// gets the correct power of 64 to multiply by
				base10Number += (1L << (size - k - 1) * 6) * getBase10Long(chars[k]);
			}
		}
		return base10Number;
	}

	/**
	 * 
	 * converts the given Base64 char into a Base10 int
	 *
	 * @param letter
	 *            a Base64 character in character form
	 * @return an int that represents the Base64 char in a Base10 int
	 * @throws NumberFormatException
	 *             if the given character is invaild
	 */
	private static long getBase10Long(char letter) throws NumberFormatException {
		switch (Character.getType(letter)) {
			case Character.LOWERCASE_LETTER:
				return letter - 'a' + 10;
			case Character.UPPERCASE_LETTER:
				return letter - 'A' + 36;
			case Character.DECIMAL_DIGIT_NUMBER:
				return letter - '0';
			default:
				if (letter == '+') {
					return 62;
				} else if (letter == '/') {
					return 63;
				} else {
					throw new NumberFormatException("Invaild Character");
				}
		}
	}

	/**
	 * 
	 * converts the given Base64 char into a Base10 int
	 *
	 * @param letter
	 *            a Base64 character in character form
	 * @return an int that represents the Base64 char in a Base10 int
	 * @throws NumberFormatException
	 *             if the given character is invaild
	 */
	public static int getBase10Int(char letter) throws NumberFormatException {
		switch (Character.getType(letter)) {
			case Character.LOWERCASE_LETTER:
				return letter - 'a' + 10;
			case Character.UPPERCASE_LETTER:
				return letter - 'A' + 36;
			case Character.DECIMAL_DIGIT_NUMBER:
				return letter - '0';
			default:
				if (letter == '+') {
					return 62;
				} else if (letter == '/') {
					return 63;
				} else {
					throw new NumberFormatException("Invaild Character");
				}
		}
	}

	/**
	 * 
	 * Converts a Base10 integer into a Base64 character
	 *
	 * @param number
	 *            an integer ranging from 0 to 63
	 * @return a single Base64 character in String form.
	 * @throws NumberFormatException
	 *             if the given number is above 63 or below 0 it throws a
	 *             NumberFormatException
	 */
	private static String getBase64Char(long number) throws NumberFormatException {
		if (number > -1 && number < 10) {
			return String.valueOf(number);
		} else if (number < 36) {
			return String.valueOf((char) ('a' + number - 10));
		} else if (number < 62) {
			return String.valueOf((char) ('A' + number - 36));
		} else if (number == 62) {
			return "+";
		} else if (number == 63) {
			return "/";
		} else {
			throw new NumberFormatException("Invaild Number");
		}
	}

	/**
	 * 
	 * Converts a Base10 integer into a Base64 character
	 *
	 * @param number
	 *            an integer ranging from 0 to 63
	 * @return a single Base64 character in String form.
	 * @throws NumberFormatException
	 *             if the given number is above 63 or below 0 it throws a
	 *             NumberFormatException
	 */
	public static char getBase64Char(int number) throws NumberFormatException {
		if (number > -1 && number < 10) {
			return (char) ('0' + number);
		} else if (number < 36) {
			return (char) ('a' + number - 10);
		} else if (number < 62) {
			return (char) ('A' + number - 36);
		} else if (number == 62) {
			return '+';
		} else if (number == 63) {
			return '/';
		} else {
			throw new NumberFormatException("Invaild Number");
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 */
	private void testBase64() {
		System.out.println(getBase64Char(10));
		System.out.println(Encrypte((int) (Math.pow(2, 32) - 1)));
		System.out.println(Encrypte(1252));
		System.out.println(Encrypte(1234));
		System.out.println(Encrypte(2600));
		System.out.println();
		long total = 0;
		for (int k = 0; k < 100_000_000; k++) {
			long start = System.nanoTime();
			Encrypte(k);
			total += System.nanoTime() - start;
		}
		System.out.println(total / 100_000_000.0);
		System.out.println();
		System.out.println(Decrypte(Encrypte(1234)));
		System.out.println(Decrypte(Encrypte(2600)));
		System.out.println();
		total = 0;
		for (int k = 0; k < 100_000_000; k++) {
			String string = Encrypte(k);
			long start = System.nanoTime();
			Decrypte(string);
			total += System.nanoTime() - start;
		}
		System.out.println(total / 100_000_000.0);

	}

}
