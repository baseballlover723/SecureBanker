import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 10, 2014.
 */
public class WithdrawActionListener extends AccountActionListener {
	/**
	 * 
	 * Constructs the action listener for the withdraw button the account panel
	 *
	 * @param format
	 *            the currency format
	 * @param account
	 *            the current panel featured in the account panel
	 * @param moneyField
	 *            the formatted money entry field on the current account panel
	 * @param moneyLabel
	 *            the label to update after the button press
	 */
	public WithdrawActionListener(NumberFormat format, Account account, JFormattedTextField moneyField, JLabel moneyLabel) {
		super(format, account, moneyField, moneyLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Withdraw " + this.moneyField.getText());
		double dollers = ((Number) this.moneyField.getValue()).doubleValue();
		this.account.withdraw((long) (100 * dollers));
		this.update();
	}

}
