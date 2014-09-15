import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.security.auth.login.AccountException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Jul 16, 2014.
 */
public class LoginPanel extends JPanel {

	private static final int SPACER_WIDTH = 10;

	private Font newFont;
	private Font buttonFont;
	
	private Box errorBox;
	private JLabel errorHeaderLabel;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField userTextField;
	private JPasswordField passTextField;
	private Box userBox;
	private Box passBox;
	private Component userSpacerBox;
	private Component passSpacerBox;
	private Box buttonBox;
	private JButton loginButton;
	private JButton createNewUserButton;
	private LoginActionListener actionListener;


	public LoginPanel() {
		this.prepare();

	}

	public LoginPanel(AccountException error) {
		this.prepare();
		this.updateHeaderError(error);

	}

	private void prepare() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.newFont = new Font("Comic Sans", Font.PLAIN, 20);
		this.buttonFont = new Font("Comic Sans", Font.PLAIN, 25);

		this.userLabel = new JLabel("Username: ");
		this.passLabel = new JLabel("Password: ");
		this.userLabel.setFont(this.newFont);
		this.passLabel.setFont(this.newFont);

		this.userTextField = new JTextField(25);
		this.passTextField = new JPasswordField(25);
		this.userTextField.setFont(this.newFont);
		this.passTextField.setFont(this.newFont);
		this.passTextField.setEchoChar('*');

		this.userTextField.setMaximumSize(this.userTextField.getPreferredSize());
		this.passTextField.setMaximumSize(this.passTextField.getPreferredSize());

		this.userBox = Box.createHorizontalBox();
		this.passBox = Box.createHorizontalBox();
		this.userSpacerBox = Box.createHorizontalStrut(SPACER_WIDTH);
		this.passSpacerBox = Box.createHorizontalStrut(SPACER_WIDTH + 1);

		this.userBox.add(this.userLabel);
		this.userBox.add(this.userSpacerBox);
		this.userBox.add(this.userTextField);

		this.passBox.add(this.passLabel);
		this.passBox.add(this.passSpacerBox);
		this.passBox.add(this.passTextField);

		this.buttonBox = Box.createHorizontalBox();
		
		this.loginButton = new JButton("Login");
		this.loginButton.setAlignmentX(CENTER_ALIGNMENT);
		this.loginButton.setFont(this.buttonFont);
		
		this.createNewUserButton = new JButton("Create new User");
		this.createNewUserButton.setAlignmentX(CENTER_ALIGNMENT);
		this.createNewUserButton.setFont(this.buttonFont);
		
		this.buttonBox.add(Box.createHorizontalGlue());
		this.buttonBox.add(this.createNewUserButton);
		this.buttonBox.add(Box.createHorizontalGlue());
		this.buttonBox.add(this.loginButton);
		this.buttonBox.add(Box.createHorizontalGlue());
		
		this.add(this.userBox);
		this.add(Box.createVerticalStrut(20));
		this.add(this.passBox);
		this.add(Box.createVerticalStrut(SPACER_WIDTH));
		this.add(this.buttonBox);

		this.actionListener = new LoginActionListener(this);
		this.userTextField.addActionListener(this.actionListener);
		this.passTextField.addActionListener(this.actionListener);
		this.loginButton.addActionListener(this.actionListener);
		this.createNewUserButton.addActionListener(new LoginPanelCreateNewUserListener());

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	protected String getUserText() {
		return this.userTextField.getText();
	}

	protected char[] getPassChars() {
		return this.passTextField.getPassword();
	}

	protected boolean updateHeaderError(AccountException e) {
		if (this.errorBox == null) {
			// make error box
			this.errorBox = Box.createVerticalBox();
			
			this.errorHeaderLabel = new JLabel("This shouldn't ever appear");
			this.errorHeaderLabel.setFont(this.newFont);
			this.errorHeaderLabel.setForeground(Color.red);
			this.errorHeaderLabel.setAlignmentX(CENTER_ALIGNMENT);

			this.errorBox.add(this.errorHeaderLabel);
			this.errorBox.add(Box.createVerticalStrut(2 * SPACER_WIDTH));
			System.out.println("error");
			this.add(this.errorBox,0);
			Gui.updateFrameSize();
			
		}
		//3:45 - 5:45
		this.errorHeaderLabel.setText(e.getMessage());
		this.errorHeaderLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.errorHeaderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		this.revalidate();
		return true;
	}
}
