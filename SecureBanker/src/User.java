import java.awt.HeadlessException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Jul 21, 2014.
 */
public class User {
	private String username;
	private String passHash;
	private ArrayList<Account> accounts;
	private boolean loggedIn;
	private static final String[] accountTypeList = { "Checking", "Savings", "CertifiateofDeposit", "Hidden" };

	public User(String username, String passHash, ArrayList<Account> accounts) {
		this.username = username;
		this.passHash = passHash;
		this.accounts = accounts;
	}

	public User(String username, String passHash) {
		this.username = username;
		this.passHash = passHash;
		this.accounts = new ArrayList<Account>();
		// test below
//		while (Math.random() > 0.33) {
//			this.accounts.add(new Account(this.username,
//					accountTypeList[(int) (Math.random() * accountTypeList.length)], Encrypter.Encrypte(0)));
//		}
	}

	// /**
	// * Logges in
	// *
	// */
	// private void login() {
	// String enteredPass;
	// try {
	// enteredPass = SHA1.hash(JOptionPane.showInputDialog("Enter Password"));
	// if (enteredPass.equals(this.passHash)) {
	// this.loggedIn = true;
	// }
	// } catch (HeadlessException | NoSuchAlgorithmException |
	// UnsupportedEncodingException exception) {
	// // TODO Auto-generated catch-block stub.
	// exception.printStackTrace();
	// }
	// }

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param text
	 * @param numb
	 */
	public Account addAccount(String text, long numb) {
		Account newAccount;
		this.accounts.add(newAccount=new Account(this.username, text, Encrypter.Encrypte(numb)));
		this.updateServer();
		return newAccount;

	}

	protected String getUsername() {
		return this.username;
	}

	protected String getPassHash() {
		return this.passHash;
	}

	protected ArrayList<Account> getAccounts() {
		return this.accounts;
	}

	protected int getNumbOfAccounts() {
		return this.accounts.size();
	}

	protected boolean updateServer() {
		return Server.updateUsers(this);
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param k
	 */
	protected void setAccountsMoneyTo(int k) {
		for (Account account : this.accounts) {
			account.setMoney(k);
		}
	}

}
