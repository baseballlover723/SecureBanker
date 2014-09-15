import java.util.Arrays;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Jul 3, 2014.
 */
public class Encrypter {
	private static final int RANDOM_CHARS = 7;

	public static String Encrypte(long number) {
		return StringEncrypte(Base64.Encrypte(encrypteHelper(number)));
//		return String.valueOf(encrypteHelper(number));
	}

	public static long Decrypte(String number) {
		return decrypteHelper(Base64.Decrypte(StringDecrypte(number)));
//		return decrypteHelper(Long.valueOf(number));
	}

	private static long encrypteHelper(long number) {
		final int NUMBER_OF_TIMES = 15;
		for (int k = 0; k < NUMBER_OF_TIMES; k++) {
			number = Long.reverseBytes(Long.rotateRight(Long.reverse(number ^ 1L), NUMBER_OF_TIMES - k));
		}
		return number;
	}

	private static long decrypteHelper(long number) {
		final int NUMBER_OF_TIMES = 15;
		for (int k = 0; k < NUMBER_OF_TIMES; k++) {
			number = 1L ^ Long.reverse(Long.rotateLeft(Long.reverseBytes(number), k + 1));

		}
		return number;
	}

	/**
	 * 
	 * returns the Base64 encryption (in string form) of the give clearText with
	 * random chatter before and after the encrypted string
	 *
	 * @param clearText
	 *            the text to encrypte
	 * @return a string with random Base64 chars before and after the encrypted
	 *         string
	 */
	public static String StringEncrypte(String clearText) {
		StringBuilder returnText = new StringBuilder();
		for (int k = 0; k < RANDOM_CHARS; k++) {
			returnText.append(Base64.getBase64Char((int) (64 * Math.random())));
		}
		int paddingCount = (3 - (clearText.length() % 3)) % 3;

		returnText.append(StringEncrypterHelper(clearText, paddingCount));
		if (paddingCount == 0) {
			returnText.append('=');
		}
		int numbOfRandomPadChars = (int) (Math.random() * RANDOM_CHARS);
		for (int k = 0; k < numbOfRandomPadChars; k++) {
			returnText.append(Base64.getBase64Char((int) (64 * Math.random())));
		}
		return returnText.toString();
	}

	/**
	 * 
	 * returns the decrypted form of the given encryptedText
	 *
	 * @param encryptedText
	 *            the encrypted string with random Base64 chatter in the
	 *            begining
	 * @return cleartext in string form
	 */
	public static String StringDecrypte(String encryptedText) {
		StringBuilder returnText = new StringBuilder();
		returnText.append(StringDecrypterHelper(encryptedText.substring(RANDOM_CHARS)));
		return returnText.toString();
	}

	/**
	 * Decryptes the given encryptedText by skipping the random Base64 chars at
	 * the begining and then subsequently taking 4 consecutive Base64 chars and
	 * converting them to 3 ASCII chars, repeating until it encounters the pad
	 * terminator.
	 *
	 * @param encryptedText
	 *            the encrypted text with random Base64 chatter at the begining
	 * @return the stringbuilder it used
	 */
	private static StringBuilder StringDecrypterHelper(String encryptedText) {
		StringBuilder helperText = new StringBuilder();
		int padIndex;
		if ((padIndex = encryptedText.indexOf('=')) == -1) {
			padIndex = encryptedText.length();
		}
		for (int k = 0; k < padIndex; k += 4) {
			int a = Base64.getBase10Int(encryptedText.charAt(k)) << 18;
			int b = Base64.getBase10Int(encryptedText.charAt(k + 1)) << 12;
			int c;
			int d;
			if (padIndex == k + 2) {
				c = 0;
				d = 0;
				helperText.append( (char) (( (a + b) >> 16) & 0xff) );
				return helperText;
			} else {
				c = Base64.getBase10Int(encryptedText.charAt(k + 2)) << 6;
				if (padIndex == k + 3) {
					d = 0;
					helperText.append( (char) (( (a + b+c) >> 16) & 0xff) );
					helperText.append( (char) (( (a + b+c) >> 8) & 0xff) );
					return helperText;
				} else {
					d = Base64.getBase10Int(encryptedText.charAt(k + 3));
				}
			}
			int base64Bytes = a + b + c + d;

			helperText.append((char) ((base64Bytes >> 16) & 0xff));
			helperText.append((char) ((base64Bytes >> 8) & 0xff));
			helperText.append((char) (base64Bytes & 0xff));

		}

		return helperText;

	}

	/**
	 * 
	 * helps encrypte strings to Base64. Works by taking 3 consecutive ascii
	 * chars (uses 0 if not enough chars) and converts it to 4 Base64 chars
	 * repeating as needed. If needed, it will add the pad terminator to the end
	 * and then if still needed a random Base64 char
	 *
	 * @param clearText
	 *            the text to encrypte
	 * @param paddingCount
	 *            the number of pads needed to add to the end
	 * @return
	 */
	private static StringBuilder StringEncrypterHelper(String clearText, int paddingCount) {
		StringBuilder helperText = new StringBuilder();
		byte[] clearBytes = clearText.getBytes();

		byte[] bytes = Arrays.copyOf(clearBytes, clearBytes.length + paddingCount);

		for (int k = 0; k < bytes.length; k += 3) {
			int a = (bytes[k] & 0xff) << 16;
			int b = (bytes[k + 1] & 0xff) << 8;
			int c = (bytes[k + 2] & 0xff);
			int ascii3 = a + b + c;

			helperText.append(Base64.getBase64Char((ascii3 >> 18) & 0x3f));
			helperText.append(Base64.getBase64Char((ascii3 >> 12) & 0x3f));
			helperText.append(Base64.getBase64Char((ascii3 >> 6) & 0x3f));
			helperText.append(Base64.getBase64Char(ascii3 & 0x3f));
		}
		if (paddingCount != 0) {
			helperText.setCharAt(helperText.length() - paddingCount, '=');
			if (paddingCount == 2) {
				helperText.setCharAt(helperText.length() - 1,
						Base64.getBase64Char((int) (64 * Math.random())));
			}
		}
		return helperText;
	}

	/**
	 * returns a string that gives the given time difference in easily read time
	 * units
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeUnits(double time) {
		double newTime = time;
		if (time < 1000) {
			return String.format("%f NanoSeconds", time);
		} else {
			newTime = time / 1000.0;
			if (newTime < 1000) {
				return String.format("%f MicroSeconds", newTime);
			} else {
				newTime /= 1000.0;
				if (newTime < 1000) {
					return String.format("%f MiliSeconds", newTime);
				} else {
					newTime /= 1000.0;
					if (newTime < 300) {
						return String.format("%f Seconds", newTime);
					} else {
						newTime /= 60.0;
						if (newTime < 180) {
							return String.format("%f Minutes", newTime);
						} else {
							return String.format("%f Hours", newTime / 60.0);
						}
					}
				}
			}
		}
	}

}
