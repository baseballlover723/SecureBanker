import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.security.auth.login.AccountException;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Jul 31, 2014.
 */
public class LoginActionListener implements ActionListener {
	private LoginPanel loginPanel;

	public LoginActionListener(LoginPanel loginPanel) {
		this.loginPanel = loginPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("User: " + this.loginPanel.getUserText());
//		System.out.println("Pass: " + Arrays.toString(this.loginPanel.getPassChars()));
		try {
			System.out.println("PassHash: " + SHA1.hash(this.loginPanel.getPassChars()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException exception) {
			exception.printStackTrace();
			System.exit(-1);
		}
		try {
			User user;
			if ((user = Server.login(this.loginPanel.getUserText(),
					SHA1.hash(this.loginPanel.getPassChars()))) != null) {
				if (user.getNumbOfAccounts() != 0){
					UserAccountsMenuBar menuBar = new UserAccountsMenuBar(user);
					Gui.replaceJPanel(new AccountPanel(user, user.getAccounts().get(0)), menuBar);
				} else {
					Gui.replaceJPanel(new CreateAccountPanel(user));
				}
			}
		} catch (AccountException e) {
			// bad password
			this.loginPanel.updateHeaderError(e);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException exception) {
			exception.printStackTrace();
			System.exit(-1);
		}
	}

}
