import java.awt.FontFormatException;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountExpiredException;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Takes care of file reading
 *
 * @author rosspa. Created Jul 4, 2014.
 */
public class Server {
	private static final char userSeperator = '|';
	private static final char accountSeperator = ',';
	private static final long TIMEOUT_TIME_MS = 60_000L; // 60 seconds
														// time out

	private static final File file = new File("src/100Accounts.txt");
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private static ArrayList<User> Users;
	private static long lastTime;

	private static JFrame frame;
	private static JButton abortButton;
	private static boolean stop;
	private static Timer timer;
	private static long last;

	// private static long last;

	public Server() {
//		 frame = new JFrame();
		// frame.setSize(200, 100);
		// abortButton = new JButton("ABORT!!!");
		// stop = false;
		// abortButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// stop = true;
		// }
		// });
		// frame.add(abortButton);
		// frame.setLocationRelativeTo(null);
		// frame.setVisible(true);
		last = System.nanoTime();

		try {
			inititalize();
		} catch (FileNotFoundException exception) {
			try {
				file.createNewFile();
				inititalize();
			} catch (IOException | FontFormatException exception1) {
				System.out.println("Error creating new file");
				exception1.printStackTrace();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		} catch (FontFormatException exception) {
			System.out.println("There is a bad line in the txt file");
			exception.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 
	 * to change from one encoding to another
	 *
	 * @param oldUserSeperator
	 * @param oldAccountSeperator
	 */
	public Server(char oldUserSeperator, char oldAccountSeperator) {
		try {
			changeEncoding(oldUserSeperator, oldAccountSeperator);
		} catch (IOException exception) {
			exception.printStackTrace();
		} catch (FontFormatException exception) {
			System.out.println("Error converting file to new encoding");
			exception.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param oldUserSeperator
	 * @param oldAccountSeperator
	 * @throws FontFormatException
	 * @throws IOException
	 */
	private void changeEncoding(char oldUserSeperator, char oldAccountSeperator) throws FontFormatException,
			IOException {
		reader = new BufferedReader(new FileReader(file));
		Users = new ArrayList<User>();
		String line;
		String splitter = assignSpliter(oldUserSeperator);
		while ((line = reader.readLine()) != null) {
			// split by spaces
			String[] lines = line.split(splitter);
			if (lines.length < 2) {
				throw new FontFormatException("Invaild format outside of accounts");
			}
			// line format is
			// username|passhash|account1type,account1money|account2type,account2money|account3type,account3money...
			// DONE decide how to initialize Account[] for each user
			String username = Encrypter.StringDecrypte(lines[0]);
			Users.add(new User(username, lines[1], readAccounts(username,
					Arrays.asList(lines).subList(2, lines.length), oldAccountSeperator)));
		}
		overwrite();
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 *
	 */
	private void inititalize() throws FontFormatException, IOException {
		reader = new BufferedReader(new FileReader(file));
		Users = new ArrayList<User>();
		String blah = "hello";
		// last = System.nanoTime();
		// last -= 5_000_000_000L;
		String line;
		String splitter = assignSpliter(userSeperator);
		while ((line = reader.readLine()) != null) {
			// split by spaces
			String[] lines = line.split(splitter);
			if (lines.length < 2) {
				throw new FontFormatException("Invaild format outside of accounts");
			}
			// line format is
			// username|passhash|account1type,account1money|account2type,account2money|account3type,account3money...
			// DONE decide how to initialize Account[] for each user
			String username = Encrypter.StringDecrypte(lines[0]);
			Users.add(new User(username, lines[1], readAccounts(username,
					Arrays.asList(lines).subList(2, lines.length))));
		}
		System.out.println("done inititalizing");
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param subList
	 * @return
	 */
	private ArrayList<Account> readAccounts(String username, List<String> list) throws FontFormatException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String[] lines;
		String splitter = assignSpliter(accountSeperator);
		for (String stringAccount : list) {
			lines = stringAccount.split(splitter);
			if (lines.length != 2) {
				System.out.println(stringAccount);
				throw new FontFormatException("Invaild format inside of the acounts");
			}
			accounts.add(new Account(username, Encrypter.StringDecrypte(lines[0]), lines[1]));
		}
		return accounts;
	}

	/**
	 * 
	 * Reads the accounts with a user specified account seperator char, to be
	 * used for changing encoding
	 *
	 * @param list
	 * @param oldAccountSeperator
	 * @return
	 * @throws FontFormatException
	 */
	private ArrayList<Account> readAccounts(String username, List<String> list, char oldAccountSeperator)
			throws FontFormatException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String[] lines;
		String splitter = assignSpliter(oldAccountSeperator);
		for (String stringAccount : list) {
			lines = stringAccount.split(splitter);
			if (lines.length != 2) {
				System.out.println(stringAccount);
				throw new FontFormatException("Invaild format inside of the acounts");
			}
			accounts.add(new Account(username, Encrypter.StringDecrypte(lines[0]), lines[1]));
		}
		return accounts;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param aChar
	 * @return
	 */
	private String assignSpliter(char aChar) {
		switch (aChar) {
			case '|':
				return "\\" + String.valueOf(aChar);
			default:
				return String.valueOf(aChar);
		}
	}

	/**
	 * 
	 * gets the account with the given username
	 *
	 * @param username
	 * @param passHash
	 * @return
	 * @throws AccountException
	 */
	public static User login(String username, String passHash) throws AccountException {
		Iterator<User> iter = Users.iterator();
		while (iter.hasNext()) {
			User current;
			if (username.equals((current = iter.next()).getUsername())) {
				// System.out.println("found");
				// if (Arrays.equals(passHash,current.getPassHash())) {
				if (passHash.equals(current.getPassHash())) {
					timer = new Timer();
					timer.schedule(new Timeout(), TIMEOUT_TIME_MS);
					return current;
				} else {
					throw new AccountException("Incorrect Username/Password");
				}
			}
		}
		throw new AccountException("Incorrect Username/Password");
		// return createNewUser(username);
		// System.out.println("Couldn't find the user");
		// return null;
	}
	
	public static boolean doesUserExist(String username) {
		for (User user : Users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public static User createNewUser(String username, String passHash) {
		User newUser = new User(username, passHash);
		Users.add(newUser);
		overwrite();
		timer = new Timer();
		timer.schedule(new Timeout(), TIMEOUT_TIME_MS);
		return newUser;
	}
	
	/**
	 * creates a new account with the given username
	 *
	 * @param username
	 */
	private static User createNewUser(String username) {
		try {
			// TODO convert to password field, do when implementing gui
			// String passHash1 = AeSimpleSHA1.SHA1(JOptionPane
			// .showInputDialog("Enter your new password."));
			// String passHash2 = AeSimpleSHA1.SHA1(JOptionPane
			// .showInputDialog("Enter your new password again."));
			// if (!passHash1.equals(passHash2)) {
			// createNewAccount(username);
			// }
			String passHash1 = SHA1.hash(randomPassword());
			User newUser = new User(username, passHash1);
			Users.add(newUser); // 5 min
			// if (System.nanoTime() - last > 300_000_000_000L) {
			 if (System.nanoTime() - last > 100_000_000L) {
			overwrite();
			 last = System.nanoTime();
			 }
			// System.out.println("New account created, you have $0 in your account currently");
			return newUser;
		} catch (HeadlessException | NoSuchAlgorithmException | UnsupportedEncodingException exception) {
			exception.printStackTrace();
			System.exit(-1);
			return null;
		} catch (NullPointerException e) {
			// if the user hits cancel, fail sliently
			System.out.println("User declined to create a new account.");
			return null;
		}

	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public static void overwrite() {
		try {
			long start = System.nanoTime();
			writer = new BufferedWriter(new FileWriter(file));
			for (User user : Users) {
				writer.write(Encrypter.StringEncrypte(user.getUsername()));
				writer.write(userSeperator);
				writer.write(user.getPassHash());
				for (Account account : user.getAccounts()) {
					writer.write(userSeperator);
					writer.write(Encrypter.StringEncrypte(account.getAccountType()));
					writer.write(accountSeperator);
					writer.write(account.getEncryptedMoney());
				}
				writer.newLine();
			}
			writer.close();
			long end = System.nanoTime();
			System.out.printf("%,d Time took %s\n", Users.size(), Encrypter.getTimeUnits(end - start));
			if (stop) {
				System.exit(0);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Updates the servers list of users by overwrighting the user with the same
	 * id as the given user with the given user
	 *
	 * @param newUser
	 *            the user to update
	 * @return true if it finds the given user in the servers copy of users and
	 *         false if it can't find the given user in the servers copy of
	 *         users
	 */
	public static boolean updateUsers(User newUser) {
		for (User user : Users) {
			if (user.getUsername().equals(newUser.getUsername())) {
				user = newUser;
				overwrite();
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * goes through the user list until it finds the user who own the account
	 * and then goes through the accounts until it mataches the account id's and
	 * then copys to give account over the old account that mataches the account
	 * id and then overwrites the text file
	 *
	 * @param newAccount
	 *            the account to update
	 * @return true if it suceedes and false if it can't find the account
	 */
	public static boolean updateAccount(Account newAccount) {
		for (User user : Users) {
			if (user.getUsername().equals(newAccount.getUsername())) {
				for (Account account : user.getAccounts()) {
					if (account.getId() == newAccount.getId()) {
						account = newAccount;
						overwrite();
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * updating 2 accounts in the same user
	 *
	 * @param newAccount
	 *            one account to update
	 * @param otherAccount
	 *            the other account to update
	 * @return retruns true ONLY if BOTH accounts are found, otherwise it
	 *         returns false;
	 */
	public static boolean updateAccount(Account newAccount, Account otherAccount) {
		int count = 0;
		for (User user : Users) {
			if (user.getUsername().equals(newAccount.getUsername())) {
				for (Account account : user.getAccounts()) {
					if (account.getId() == newAccount.getId()) {
						account = newAccount;
						count++;
						if (count == 2) {
							overwrite();
							return true;
						}
					}
					if (account.getId() == otherAccount.getId()) {
						account = otherAccount;
						count++;
						if (count == 2) {
							overwrite();
							return true;
						}
					}
				}
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * checks the current time against the last action time and checks for
	 * timeouts. If it detects a timeout it will automaticly log out
	 *
	 * @return true if the user's last action was longer then the set
	 *         TIMEOUT_TIME_NS and return FALSE if the user hasn't timed out yet
	 */
	public static void resetTimeout() {
		System.out.println("RESET");
		timer.cancel();
		timer = new Timer();
		timer.schedule(new Timeout(), TIMEOUT_TIME_MS);
	}

	public static void logout() {
		timer.cancel();
		Gui.replaceJPanel(new LoginPanel(), null);
	}

	public static void logout(boolean timeout) {
		if (timeout) {
			timer.cancel();
			Gui.replaceJPanel(new LoginPanel(new AccountExpiredException(
					"Account logged out for security reasons")), null);
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param numb
	 */
	public static void testOverWriteSpeed(int numb) {
		int count = 0;
		for (int k = Users.size(); k < numb; k++) {
			// for (int k = 0; k < accounts.size(); k++) {
			String username = String.valueOf(k);
			User user = Server.createNewUser(username);
			user.setAccountsMoneyTo(k);
		}
	}

	private static String randomPassword() {
		int size = randomLength();
		char[] chars = new char[size];
		for (int k = 0; k < chars.length; k++) {
			chars[k] = genRandomChar();
		}
		return chars.toString();

	}

	private static int randomLength() {
		final int MAX_LENGTH = 16;
		double size = 1;
		size *= Math.random();
		if (Math.random() < 0.25) {
			size *= Math.random();
		}
		double index = MAX_LENGTH - 0.5;
		if (Math.random() > 0.5) {
			index += (int) (MAX_LENGTH * size);
		} else {
			index -= (int) (MAX_LENGTH * size);
		}
		return (int) index;
	}

	private static char genRandomChar() {
		double number = 62 * Math.random();
		if (number < 10) {
			return (char) number;
		} else if (number < 36) {
			return (char) ('a' + number - 10);
		} else {
			return (char) ('A' + number - 36);
		}
	}

}
