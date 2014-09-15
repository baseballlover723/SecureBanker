import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 18, 2014.
 */
public class CreateNewUserListener implements ActionListener {

	private PasswordCheckListener passChecker;
	private JTextField userField;
	private JPasswordField passField;

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param passChecker
	 * @param userTextField
	 * @param passTextField0
	 */
	public CreateNewUserListener(PasswordCheckListener passChecker, JTextField userTextField,
			JPasswordField passTextField0) {
		this.passChecker = passChecker;
		this.userField = userTextField;
		this.passField = passTextField0;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			System.out.println("action");
			System.out.println(this.passChecker.getPassValid());
			if (this.passChecker.getPassValid()) {
				User newUser = Server.createNewUser(this.userField.getText(),
						SHA1.hash(this.passField.getPassword()));
				Gui.replaceJPanel(new CreateAccountPanel(newUser));
			} else {
				System.out.println("Bad password");
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
