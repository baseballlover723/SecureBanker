import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 10, 2014.
 */
public abstract class AccountActionListener implements ActionListener {
	protected Account account;
	protected JFormattedTextField moneyField;
	protected JLabel balanceLabel;
	protected NumberFormat format;

	public AccountActionListener(NumberFormat format, Account account, JFormattedTextField moneyField,
			JLabel balanceLabel) {
		this.format = format;
		this.account = account;
		this.moneyField = moneyField;
		this.balanceLabel = balanceLabel;
	}

	protected void update() {
		this.balanceLabel
				.setText(this.format.format(Encrypter.Decrypte(this.account.getEncryptedMoney()) / 100.0));
		Server.resetTimeout();
		this.account.update();
	}
	
	protected void update(Account otherAccount) {
		this.balanceLabel
				.setText(this.format.format(Encrypter.Decrypte(this.account.getEncryptedMoney()) / 100.0));
		Server.resetTimeout();
		this.account.update(otherAccount);
	}

}
