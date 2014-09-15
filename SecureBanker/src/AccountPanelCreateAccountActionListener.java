import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 10, 2014.
 */
public class AccountPanelCreateAccountActionListener implements ActionListener {
	private User user;

	/**
	 * creates an action listener that connects the account panel and the create
	 * new account panel
	 * 
	 * @param user
	 *            the user of the account panel
	 */
	public AccountPanelCreateAccountActionListener(User user) {
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Gui.replaceJPanel(new CreateAccountPanel(this.user));
		Server.resetTimeout();

	}

}
