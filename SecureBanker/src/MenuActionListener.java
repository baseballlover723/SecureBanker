import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 10, 2014.
 */
public class MenuActionListener implements ActionListener {

	private Account account;
	private User user;

	public MenuActionListener(User user, Account account) {
		this.account = account;
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("switch to " + this.account.toString());
		Gui.replaceJPanel(new AccountPanel(this.user, this.account));
		Server.resetTimeout();

	}

}
