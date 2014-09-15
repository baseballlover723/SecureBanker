import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 10, 2014.
 */
public class UserAccountsMenuBar extends JMenuBar {
	private User user;
	private JMenu menu;

	public UserAccountsMenuBar(User user) {
		this.user = user;
		if (this.user.getNumbOfAccounts() > 4) {
			this.menu = new JMenu("Accounts");
			this.menu.setMnemonic(KeyEvent.VK_A);
			this.menu.getAccessibleContext().setAccessibleDescription("this does stuff i swear");
			this.add(this.menu);
			for (Account account : this.user.getAccounts()) {
				JMenuItem accountMenuItem = new JMenuItem(account.getAccountType());
				accountMenuItem.addActionListener(new MenuActionListener(user,account));
				this.menu.add(accountMenuItem);
			}
		} else {
			for (Account account : this.user.getAccounts()) {
				JMenuItem accountMenuItem = new JMenuItem(account.getAccountType());
				accountMenuItem.addActionListener(new MenuActionListener(user,account));
				this.add(accountMenuItem);
			}
		}
	}
}
