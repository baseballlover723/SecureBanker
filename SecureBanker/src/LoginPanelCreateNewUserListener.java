import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa.
 *         Created Aug 12, 2014.
 */
public class LoginPanelCreateNewUserListener implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("create new user");
		Gui.replaceJPanel(new CreateNewUserPanel());
	}

}
