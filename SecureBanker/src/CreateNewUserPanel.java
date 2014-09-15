import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 12, 2014.
 */
public class CreateNewUserPanel extends JPanel {

	private static final int SMALL_SPACER_WIDTH = 10;
	private static final int BIG_HALF_SPACER_WIDTH = 32;

	private Font bodyFont;
	private Font smallFont;
	private Font buttonFont;

	private Box box;

	private Box userBox;
	private JLabel userLabel;
	private JTextField userTextField;
	
	private JLabel userAvailability;

	private JLabel passDetailsLabel;

	private Box passBox0;

	private JLabel passLabel0;
	private JPasswordField passTextField0;

	private Box passBox1;

	private JLabel passLabel1;
	private JPasswordField passTextField1;

	private Box footer;

	private JLabel passMatchLabel;
	private JButton registerButton;

	private PasswordCheckListener passChecker;
	private CreateUsernameListener userChecker;
	private CreateNewUserListener createUserListener;

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 */
	public CreateNewUserPanel() {
		// this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.smallFont = new Font("Comic Sans", Font.PLAIN, 17);
		this.bodyFont = new Font("Comic Sans", Font.PLAIN, 20);
		this.buttonFont = new Font("Comic Sans", Font.PLAIN, 25);
		this.box = Box.createVerticalBox();

		this.userBox = Box.createHorizontalBox();

		this.userLabel = new JLabel("Enter your username: ");
		this.userTextField = new JTextField(25);
		this.userLabel.setFont(this.bodyFont);
		this.userTextField.setFont(this.bodyFont);
		this.userTextField.setMaximumSize(this.userTextField.getPreferredSize());
		this.userTextField.setMinimumSize(this.userTextField.getPreferredSize());

		this.userBox.add(Box.createHorizontalStrut(BIG_HALF_SPACER_WIDTH));
		this.userBox.add(this.userLabel);
		this.userBox.add(Box.createHorizontalStrut(BIG_HALF_SPACER_WIDTH));
		this.userBox.add(this.userTextField);
		
		this.userAvailability = new JLabel("<html><font color = red>No Username</font></html>");
		this.userAvailability.setFont(this.bodyFont);
		this.userAvailability.setHorizontalAlignment(SwingConstants.LEFT);
		this.userAvailability.setAlignmentX(RIGHT_ALIGNMENT);
//		this.userAvailability.setBorder(BorderFactory.createLineBorder(Color.red));
		
		this.passDetailsLabel = new JLabel("this is bad, something is wrong, call philip, you shouldn't see this");
		this.passDetailsLabel.setFont(this.smallFont);
//		this.passDetailsLabel.setBorder(BorderFactory.createLineBorder(Color.cyan));

		String fontColor = "<font color = red>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		
		sb.append("Password must be ");
		sb.append(fontColor);
		sb.append("at least 8 characters long</font> and have at least <b><u>2</b></u> of the following: <br>");

		sb.append(fontColor);
		sb.append("at least 1 number</font>, ");

		sb.append(fontColor);
		sb.append("at least 1 uppercase letter</font>, ");

		sb.append(fontColor);
		sb.append("at least 1 other symbol</font>");

		sb.append("</html>");

		this.passDetailsLabel.setText(sb.toString());

		this.passDetailsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.passDetailsLabel.setAlignmentX(CENTER_ALIGNMENT);

		this.passBox0 = Box.createHorizontalBox();

		this.passLabel0 = new JLabel("Enter your password: ");
		this.passTextField0 = new JPasswordField(25);
		this.passLabel0.setFont(this.bodyFont);
		this.passTextField0.setFont(this.bodyFont);
		this.passTextField0.setEchoChar('*');
		this.passTextField0.setMaximumSize(this.passTextField0.getPreferredSize());
		this.passTextField0.setMinimumSize(this.passTextField0.getPreferredSize());

		this.passBox0.add(Box.createHorizontalStrut(BIG_HALF_SPACER_WIDTH));
		this.passBox0.add(this.passLabel0);
		this.passBox0.add(Box.createHorizontalStrut(BIG_HALF_SPACER_WIDTH));
		this.passBox0.add(this.passTextField0);

		this.passBox1 = Box.createHorizontalBox();

		this.passLabel1 = new JLabel("Enter your password again: ");
		this.passTextField1 = new JPasswordField(25);
		this.passLabel1.setFont(this.bodyFont);
		this.passTextField1.setFont(this.bodyFont);
		this.passTextField1.setEchoChar('*');
		this.passTextField1.setMaximumSize(this.passTextField1.getPreferredSize());
		this.passTextField1.setMinimumSize(this.passTextField1.getPreferredSize());

		this.passBox1.add(this.passLabel1);
		this.passBox1.add(Box.createHorizontalStrut(SMALL_SPACER_WIDTH + 1));
		this.passBox1.add(this.passTextField1);

		// this.passTextField0.addKeyListener(this.passChecker);
		// this.passTextField1.addKeyListener(this.passChecker);

		this.footer = Box.createHorizontalBox();

		this.passMatchLabel = new JLabel("Passwords Don't Match");
		this.passMatchLabel.setFont(this.bodyFont);
		this.passMatchLabel.setForeground(Color.red);
		this.passMatchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		System.out.println(this.passMatchLabel.getPreferredSize());

		this.registerButton = new JButton("Register!");
		this.registerButton.setFont(this.buttonFont);

		this.footer.add(this.passMatchLabel);
		this.footer.add(Box.createHorizontalStrut(2 * BIG_HALF_SPACER_WIDTH));
		this.footer.add(this.registerButton);
		this.footer.setAlignmentX(CENTER_ALIGNMENT);

		this.userChecker = new CreateUsernameListener(this.userTextField, this.userAvailability);
		this.userTextField.getDocument().addDocumentListener(this.userChecker);
		
		this.passChecker = new PasswordCheckListener(this.passTextField0, this.passTextField1,
				this.passDetailsLabel, this.passMatchLabel);
		this.passTextField0.getDocument().addDocumentListener(this.passChecker);
		this.passTextField1.getDocument().addDocumentListener(this.passChecker);
		
		this.createUserListener = new CreateNewUserListener(this.passChecker, this.userTextField, this.passTextField0);
		
		this.userTextField.addActionListener(this.createUserListener);
		this.passTextField0.addActionListener(this.createUserListener);
		this.passTextField1.addActionListener(this.createUserListener);
		this.registerButton.addActionListener(this.createUserListener);

		this.box.add(this.userBox);
		this.box.add(Box.createVerticalStrut(SMALL_SPACER_WIDTH));
		this.box.add(this.userAvailability);
		this.box.add(Box.createVerticalStrut(2 * SMALL_SPACER_WIDTH));
		this.box.add(this.passDetailsLabel);
		// this.box.add(blah);
		this.box.add(Box.createVerticalStrut(2 * SMALL_SPACER_WIDTH));
		this.box.add(this.passBox0);
		this.box.add(Box.createVerticalStrut(2 * SMALL_SPACER_WIDTH));
		this.box.add(this.passBox1);
		this.box.add(Box.createVerticalStrut(SMALL_SPACER_WIDTH));
		this.box.add(this.footer);

		this.add(Box.createGlue());
		this.add(this.box);
		this.add(Box.createGlue());
		// this.add(this.passMatchLabel);
		// this.add(Box.createVerticalStrut(SMALL_SPACER_WIDTH));
		// this.add(this.registerButton);

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

}
