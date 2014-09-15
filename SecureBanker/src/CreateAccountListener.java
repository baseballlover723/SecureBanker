import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Jul 31, 2014.
 */
public class CreateAccountListener implements ActionListener {

	private JTextField accountTypeField;
	private JFormattedTextField depositField;
	private User user;

	public CreateAccountListener(JTextField accountTypeField, JFormattedTextField depositField, User user) {
		this.accountTypeField = accountTypeField;
		this.depositField = depositField;
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		double dollers = ((Number) this.depositField.getValue()).doubleValue();
		long cents = (long) (dollers * 100);
		System.out.println("Account type: " + this.accountTypeField.getText());
		System.out.printf("Starting Deposit: $%,.2f\n", cents/100.0);
		System.out.println(Encrypter.Encrypte(cents));
		Server.resetTimeout();
		Account newAccount = this.user.addAccount(this.accountTypeField.getText(), cents);
		Gui.replaceJPanel(new AccountPanel(this.user, newAccount), new UserAccountsMenuBar(this.user));
		
	}

	public double rounder(Number numb) {
		DecimalFormat twoDForm = new DecimalFormat("$#.##");
		return Double.valueOf(twoDForm.format(numb));
	}
}
