import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;


/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa.
 *         Created Aug 10, 2014.
 */
public class TransferToActionListener extends AccountActionListener {

	private JComboBox comboBox;

	/**
	 * 
	 * Constructs the action listener for the Transfer To button in the account
	 * panel
	 *
	 * @param account
	 *            the current panel featured in the account panel
	 * @param moneyField
	 *            the formatted money entry field on the current account panel
	 * @param comboBox
	 *            the combo box that contains the other account the user has
	 */
	public TransferToActionListener(NumberFormat format, Account account, JFormattedTextField moneyField,
			JLabel moneyLabel, JComboBox comboBox) {
		super(format, account, moneyField, moneyLabel);
		this.comboBox = comboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Transfer $" + this.moneyField.getText() + " to " + this.comboBox.getSelectedItem());
		Account otherAccount = (Account) this.comboBox.getSelectedItem();
		double dollers = ((Number) this.moneyField.getValue()).doubleValue();
		long cents = (long) dollers * 100;
		this.account.withdraw(cents);
		otherAccount.deposit(cents);
		this.update(otherAccount);
	}

}
