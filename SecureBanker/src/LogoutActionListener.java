import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa.
 *         Created Aug 5, 2014.
 */
public class LogoutActionListener implements ActionListener{
	public LogoutActionListener() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Log me out!");
		Server.logout();
		
	}
}
