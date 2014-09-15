
/**
 * A bank Account
 * 
 * @author rosspa. Created Jun 30, 2014.
 */
public class Account {
	private static long idGenerator = -1;
	
	private String username;
	private String accountType;
	private String encryptedCents;
	private long accountId;

	

	/**
	 * 
	 * Only used by the server
	 *
	 * @param user
	 * @param accountType
	 * @param encryptedMoney
	 */
	public Account(String username, String accountType, String encryptedMoney) {
		this.username = username;
		this.accountType = accountType;
		this.encryptedCents = encryptedMoney;
		this.accountId = ++idGenerator;
	}

	protected String getAccountType() {
		return this.accountType;
	}
	
	protected String getEncryptedMoney() {
		return this.encryptedCents;
	}
	
	protected String getUsername() {
		return this.username;
	}
	protected long getId() {
		return this.accountId;
	}
	
	protected void deposit(long number) {
//		System.out.println(number + Encrypter.Decrypte(this.encryptedMoney));
		this.encryptedCents = Encrypter.Encrypte(number + Encrypter.Decrypte(this.encryptedCents));
	}
	protected void withdraw(long number) {
		this.encryptedCents = Encrypter.Encrypte(Encrypter.Decrypte(this.encryptedCents) - number);
	}
	protected void setMoney(int number) {
		this.encryptedCents = Encrypter.Encrypte(number);
	}
	
	protected boolean update() {
		return Server.updateAccount(this);
	}
	
	protected boolean update(Account otherAccount) {
		return Server.updateAccount(this, otherAccount);
	}
	
	@Override
	public String toString() {
		return this.accountType; //+ " ($" + Encrypter.Decrypte(this.encryptedMoney) + ")";
	}

	

}