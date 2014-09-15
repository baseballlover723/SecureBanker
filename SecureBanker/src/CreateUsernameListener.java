import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 18, 2014.
 */
public class CreateUsernameListener implements DocumentListener {

	private JTextField textField;
	private JLabel label;
	private boolean exists;
	private boolean empty;

	/**
	 * TODO Put here a description of what this constructor does.
	 * 
	 * @param userAvailability
	 * @param userTextField
	 *
	 */
	public CreateUsernameListener(JTextField userTextField, JLabel userAvailability) {
		this.textField = userTextField;
		this.label = userAvailability;
		this.exists = false;
		this.empty = true;

	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		this.checkUsername();

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		this.checkUsername();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		this.checkUsername();
	}

	/**
	 * checks to see if the username is availiable and if nessacerry, change the
	 * username availability label
	 *
	 */
	private void checkUsername() {
		boolean override = false;
		boolean tempEmpty = this.textField.getText().isEmpty();
		if (tempEmpty != this.empty) {
			override = true;
			this.empty = tempEmpty;
			if (this.empty) {
				this.setEmpty();
				return;
			}
		}
		boolean tempExists;
		long start = System.nanoTime();
		tempExists = Server.doesUserExist(this.textField.getText());
		System.out.println(Encrypter.getTimeUnits(System.nanoTime() - start));
		if (tempExists != this.exists || override) {
			this.exists = tempExists;
			this.changeLabel();
		}

	}

	/**
	 * sets the username availability label to say that there isn't a username
	 * entered
	 *
	 */
	private void setEmpty() {
		this.label.setText("<html><font color = red>No Username</font></html>");
	}

	/**
	 * sets the username availability label to say if the username is taken or
	 * not
	 *
	 */
	private void changeLabel() {
		String text;
		if (this.exists) {
			text = "<html><font color = red>This username is already taken</font></html>";
		} else {
			text = "<html><font color = green>This username is available</font></html>";
		}
		this.label.setText(text);

	}

}
