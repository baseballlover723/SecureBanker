import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 1, 2014.
 */
public class CreateAccountPanel extends JPanel implements PropertyChangeListener {
	private static final int SPACER_SIZE = 10;
	private static final String FONT_NAME = "Comic Sans";

	private NumberFormat moneyFormat;
	private User user;
	private Font smallFont;
	private Font bodyFont;
	private Font middleFont;
	private Font headerFont;

	private Box headerBox;
	private JLabel header;
	private JLabel userLabel;
	private JButton logOutButton;

	private Box accountBox;
	private JLabel accountTypeLabel;
	private JTextField accountEntryField;

	private Box depositBox;
	private JLabel depositLabel;
	private JFormattedTextField depositEntryField;

	private JButton button;
	private CreateAccountListener actionListener;

	public CreateAccountPanel(User user) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.moneyFormat = NumberFormat.getNumberInstance();
		this.moneyFormat.setMinimumFractionDigits(2);
		this.moneyFormat.setMaximumFractionDigits(2);

		this.user = user;
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

		this.header = new JLabel("Create New Account");
		this.header.setAlignmentX(CENTER_ALIGNMENT);
		this.header.setFont(this.headerFont);

		this.headerBox.add(Box.createHorizontalGlue());
		this.headerBox.add(this.header);
		this.headerBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.headerBox.add(Box.createHorizontalGlue());
		this.headerBox.add(this.userLabel);
		this.headerBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.headerBox.add(this.logOutButton);

		this.accountBox = Box.createHorizontalBox();
		this.depositBox = Box.createHorizontalBox();
		// this.bodyBox = Box.createHorizontalBox();

		this.accountTypeLabel = new JLabel("Account Type: ");
		this.depositLabel = new JLabel("Starting Deposit ($): ");
		this.accountTypeLabel.setFont(this.bodyFont);
		this.depositLabel.setFont(this.bodyFont);

		this.accountEntryField = new JTextField(20);
		this.depositEntryField = new JFormattedTextField(this.moneyFormat);
		this.depositEntryField.setColumns(20);
		this.depositEntryField.addPropertyChangeListener("value", this);

		// this makes the currsor show up where the user clickes when it first
		// focuses
		// instead of having it always start at the left
		this.depositEntryField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JTextField tf = (JTextField) e.getSource();
						int offset = tf.viewToModel(e.getPoint());
						tf.setCaretPosition(offset);
						Server.resetTimeout();
					}
				});
			}
		});

		this.accountEntryField.setText("Checking");
		this.depositEntryField.setValue(0.00);
		this.accountEntryField.setFont(this.bodyFont);
		this.depositEntryField.setFont(this.bodyFont);
		this.accountEntryField.setMaximumSize(this.accountEntryField.getPreferredSize());
		this.depositEntryField.setMaximumSize(this.depositEntryField.getPreferredSize());

		this.accountBox.add(Box.createHorizontalStrut((int) ((this.depositLabel.getPreferredSize().getWidth()
				- this.accountTypeLabel.getPreferredSize().getWidth() + SPACER_SIZE)) / 2));
		this.accountBox.add(this.accountTypeLabel);
		this.accountBox.add(Box.createHorizontalStrut((int) ((this.depositLabel.getPreferredSize().getWidth()
				- this.accountTypeLabel.getPreferredSize().getWidth() + SPACER_SIZE)) / 2));
		this.accountBox.add(this.accountEntryField);
		this.accountBox.setAlignmentX(CENTER_ALIGNMENT);

		this.depositBox.add(this.depositLabel);
		this.depositBox.add(Box.createHorizontalStrut(SPACER_SIZE));
		this.depositBox.add(this.depositEntryField);
		this.depositBox.setAlignmentX(CENTER_ALIGNMENT);

		// this.bodyBox.add(this.accountBox, BorderLayout.WEST);
		// this.bodyBox.add(Box.createHorizontalStrut(SPACER_SIZE / 4));
		// this.bodyBox.add(this.depositBox, BorderLayout.WEST);

		this.button = new JButton("Create New Account");
		this.button.setFont(this.middleFont);
		this.button.setAlignmentX(CENTER_ALIGNMENT);

		// this.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.add(this.headerBox, BorderLayout.CENTER);
		this.add(Box.createVerticalStrut(2 * SPACER_SIZE));
		// this.add(this.bodyBox);
		this.add(this.accountBox, BorderLayout.CENTER);
		this.add(Box.createVerticalStrut(SPACER_SIZE));
		this.add(this.depositBox, BorderLayout.CENTER);
		this.add(Box.createVerticalStrut(2 * SPACER_SIZE));
		this.add(this.button, BorderLayout.CENTER);
		// this.bodyBox.setBorder(BorderFactory.createLineBorder(Color.black));
		// this.header.setBorder(BorderFactory.createLineBorder(Color.orange));
		// this.accountBox.setBorder(BorderFactory.createLineBorder(Color.red));
		// this.depositBox.setBorder(BorderFactory.createLineBorder(Color.green));
		// this.button.setBorder(BorderFactory.createLineBorder(Color.blue));
		// this.revalidate();

		// System.out.println(this.bodyBox.getPreferredSize());
		// System.out.println(this.accountTypeLabel.getPreferredSize().getWidth());
		// System.out.println(this.accountEntryField.getPreferredSize().getWidth());
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.actionListener = new CreateAccountListener(this.accountEntryField, this.depositEntryField,
				this.user);
		this.accountEntryField.addActionListener(this.actionListener);
		this.depositEntryField.addActionListener(this.actionListener);
		this.button.addActionListener(this.actionListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Server.resetTimeout();

	}

}
