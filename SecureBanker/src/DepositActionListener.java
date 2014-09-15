import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;


/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 10, 2014.
 */
public class DepositActionListener extends AccountActionListener {

	/**
	 * 
	 * Constructs the action listener for the deposit button the account panel
	 *
	 * @param format
	 *            the currency format
	 * @param account
	 *            the current panel featured in the account panel
	 * @param moneyField
	 *            the formatted money entry field on the current account panel
	 * @param balanceLabel
	 *            the label to update after the button press
	 */
	public DepositActionListener(NumberFormat format, Account account, JFormattedTextField moneyField,
			JLabel balanceLabel) {
		super(format, account, moneyField, balanceLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Deposit " + this.moneyField.getValue());
		System.out.println(this.moneyField.getValue());
		double dollers = ((Number) this.moneyField.getValue()).doubleValue();
		this.account.deposit((long) (100 * dollers));
		this.update();

	}

}
