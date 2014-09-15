import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Jul 20, 2014.
 */
public class AccountPanel extends JPanel implements PropertyChangeListener {
	private static final int SPACER_SIZE = 10;
	private static final String FONT_NAME = "Comic Sans";
	private DecimalFormat moneyFormat;
	private NumberFormat moneyEntryFormat;

	private User user;
	private Account account;

	private Font smallFont;
	private Font bodyFont;
	private Font middleFont;
	private Font headerFont;

	private Box headerBox;

	private JLabel accountIdLabel;
	private JLabel userLabel;
	private JButton logOutButton;

	private Box moneyBox;

	private JLabel moneyLabel;
	private JLabel balanceLabel;
	private JComboBox accountComboBox;

	private Box entryBox;

	private JLabel moneyEntryLabel;
	private JFormattedTextField moneyEntryField;

	private Box buttonBox;
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton transferFromButton;
	private JButton transferToButton;
	private Box accountsBox;
	private JButton createNewAccountButton;

	public AccountPanel(User user, Account account) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.moneyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
		this.moneyFormat.setNegativePrefix(this.moneyFormat.getCurrency().getSymbol() + "-");
		this.moneyFormat.setNegativeSuffix("");
		this.moneyEntryFormat = NumberFormat.getNumberInstance();
		this.moneyEntryFormat.setMinimumFractionDigits(2);
		this.moneyEntryFormat.setMaximumFractionDigits(2);

		this.user = user;
		this.account = account;
		ArrayList<Account> otherAccounts = new ArrayList<Account>(this.user.getAccounts());
		otherAccounts.remove(this.account);

		this.smallFont = new Font(FONT_NAME, Font.PLAIN, 20);
		this.bodyFont = new Font(FONT_NAME, Font.PLAIN, 24);
		this.middleFont = new Font(FONT_NAME, Font.PLAIN, 27);
		this.headerFont = new Font(FONT_NAME, Font.BOLD, 35);

		this.headerBox = Box.createHorizontalBox();

		this.userLabel = new JLabel(this.user.getUsername());
		this.userLabel.setFont(this.smallFont);
		this.logOutButton = new JButton("Logout");
		this.logOutButton.setFont(this.smallFont);
		this.logOutButton.addActionListener(new LogoutActionListener());

		this.accountIdLabel = new JLabel("Account Type: " + this.account.getAccountType());
		this.accountIdLabel.setFont(this.headerFont);
		this.accountIdLabel.setAlignmentX(CENTER_ALIGNMENT);

		this.headerBox.add(Box.createHorizontalGlue());
		this.headerBox.add(this.accountIdLabel);
		this.headerBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.headerBox.add(Box.createHorizontalGlue());
		this.headerBox.add(this.userLabel);
		this.headerBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.headerBox.add(this.logOutButton);

		this.moneyBox = Box.createHorizontalBox();

		this.moneyLabel = new JLabel("Current Balance: ");
		this.moneyLabel.setFont(this.middleFont);
		this.balanceLabel = new JLabel(this.moneyFormat.format(Encrypter.Decrypte(this.account
				.getEncryptedMoney()) / 100.0));
		this.balanceLabel.setFont(this.middleFont);

		this.accountComboBox = new JComboBox(otherAccounts.toArray());
		this.accountComboBox.setMaximumSize(this.accountComboBox.getPreferredSize());
		this.accountComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Server.resetTimeout();
			}
		});
		this.moneyBox.add(this.moneyLabel);
		this.moneyBox.add(this.balanceLabel);
		this.moneyBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.moneyBox.add(Box.createHorizontalGlue());
		this.moneyBox.add(this.accountComboBox);

		this.entryBox = Box.createHorizontalBox();

		this.moneyEntryLabel = new JLabel("Money to transfer ($): ");
		this.moneyEntryLabel.setFont(this.bodyFont);

		this.moneyEntryField = new JFormattedTextField(this.moneyEntryFormat);
		this.moneyEntryField.setColumns(12);
		this.moneyEntryField.addPropertyChangeListener("value", this);
		this.moneyEntryField.setFont(this.bodyFont);
		this.moneyEntryField.setMaximumSize(this.moneyEntryField.getPreferredSize());
		this.moneyEntryField.setText("0.00");

		// this makes the currsor show up where the user clickes when it first
		// focuses
		// instead of having it always start at the left
		this.moneyEntryField.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						int offset = tf.viewToModel(e.getPoint());
						tf.setCaretPosition(offset);
						Server.resetTimeout();
					}
				});
			}
		});
		this.entryBox.add(this.moneyEntryLabel);
		this.entryBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.entryBox.add(this.moneyEntryField);

		this.buttonBox = Box.createHorizontalBox();

		this.depositButton = new JButton("Deposit");
		this.depositButton.setFont(this.bodyFont);
		this.depositButton.addActionListener(new DepositActionListener(this.moneyFormat, this.account,
				this.moneyEntryField, this.balanceLabel));

		this.withdrawButton = new JButton("Withdraw");
		this.withdrawButton.setFont(this.bodyFont);
		this.withdrawButton.addActionListener(new WithdrawActionListener(this.moneyFormat, this.account,
				this.moneyEntryField, this.balanceLabel));

		this.transferFromButton = new JButton("Transfer From");
		this.transferFromButton.setFont(this.bodyFont);
		this.transferFromButton.addActionListener(new TransferFromActionListener(this.moneyFormat,
				this.account, this.moneyEntryField, this.balanceLabel, this.accountComboBox));

		this.transferToButton = new JButton("Transfer To");
		this.transferToButton.setFont(this.bodyFont);
		this.transferToButton.addActionListener(new TransferToActionListener(this.moneyFormat, this.account,
				this.moneyEntryField, this.balanceLabel, this.accountComboBox));

		this.buttonBox.add(this.depositButton);
		this.buttonBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.buttonBox.add(this.withdrawButton);
		this.buttonBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.buttonBox.add(this.transferFromButton);
		this.buttonBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.buttonBox.add(this.transferToButton);

		this.accountsBox = Box.createHorizontalBox();

		this.createNewAccountButton = new JButton("Create a new Account");
		this.createNewAccountButton.setFont(this.middleFont);
		this.createNewAccountButton.addActionListener(new AccountPanelCreateAccountActionListener(this.user));

		this.accountsBox.add((this.createNewAccountButton));

		this.add(this.headerBox);
		this.add(Box.createVerticalStrut(SPACER_SIZE));
		this.add(this.moneyBox);
		this.add(Box.createVerticalStrut(SPACER_SIZE));
		this.add(this.entryBox);
		this.add(Box.createVerticalStrut(SPACER_SIZE));
		this.add(this.buttonBox);
		this.add(Box.createVerticalStrut(SPACER_SIZE));
		this.add(this.accountsBox);

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		Server.resetTimeout();

	}

}
