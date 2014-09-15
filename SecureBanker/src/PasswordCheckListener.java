import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa. Created Aug 16, 2014.
 */
public class PasswordCheckListener implements DocumentListener {
	private final static String correctColor = "<font color = green>";
	private final static String wrongColor = "<font color = red>";
	private final static int MIN_LENGTH = 8;

	private JPasswordField top;
	private JPasswordField bottom;
	private JLabel description;
	private boolean match;
	private boolean hasLength;
	private boolean hasNumber;
	private boolean hasUpper;
	private boolean hasOther;
	private JLabel matchLabel;
	private boolean valid;

	public PasswordCheckListener(JPasswordField top, JPasswordField bottom, JLabel description,
			JLabel matchLabel) {
		this.top = top;
		this.bottom = bottom;
		this.description = description;
		this.matchLabel = matchLabel;
		this.match = false;
		this.hasLength = false;
		this.hasNumber = false;
		this.hasUpper = false;
		this.hasOther = false;
		this.valid = false;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// nothing
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		this.check();

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		this.check();

	}

	private void check() {
		// check top
		char[] passTop = this.top.getPassword();
		boolean tempHasLength = passTop.length >= MIN_LENGTH ? true : false;
		boolean tempHasNumber = false;
		boolean tempHasUpper = false;
		boolean tempHasOther = false;
		for (char letter : passTop) {
			switch (Character.getType(letter)) {
				case Character.DECIMAL_DIGIT_NUMBER:
					tempHasNumber = true;
					break;
				case Character.UPPERCASE_LETTER:
					tempHasUpper = true;
					break;
				case Character.LOWERCASE_LETTER:
					break;
				default:
					tempHasOther = true;
			}
		}
		// if you need to change the description label
		if (tempHasLength != this.hasLength || tempHasNumber != this.hasNumber
				|| tempHasUpper != this.hasUpper || tempHasOther != this.hasOther) {
			this.hasLength = tempHasLength;
			this.hasNumber = tempHasNumber;
			this.hasUpper = tempHasUpper;
			this.hasOther = tempHasOther;
			this.setDetailsColors();
		}
		char[] passBottom = this.bottom.getPassword();
		boolean tempMatch = Arrays.equals(passTop, passBottom) ? true : false;
		System.out.println();
		System.out.println(Arrays.toString(passTop));
		System.out.println(Arrays.toString(passBottom));
		if (tempMatch != this.match) {
			this.match = tempMatch;
			this.setMatchColor();
		}
		this.valid = (this.match && this.hasLength) && this.hasNumber ? this.hasUpper || this.hasOther
				: this.hasUpper && this.hasOther;
		System.out.println(this.valid);
		Arrays.fill(passTop, '\u0000');
		Arrays.fill(passBottom, '\u0000');
		
	}

	private void setDetailsColors() {
		String fontColor;
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");

		sb.append("Password must be ");
		fontColor = (this.hasLength) ? correctColor : wrongColor;
		sb.append(fontColor);

		sb.append("at least 8 characters long</font> and have at least <b><u>2</b></u> of the following: <br>");

		fontColor = (this.hasNumber) ? correctColor : wrongColor;
		sb.append(fontColor);
		sb.append("at least 1 number</font>, ");

		fontColor = (this.hasUpper) ? correctColor : wrongColor;
		sb.append(fontColor);
		sb.append("at least 1 uppercase letter</font>, ");

		fontColor = (this.hasOther) ? correctColor : wrongColor;
		sb.append(fontColor);
		sb.append("at least 1 other symbol</font>");

		sb.append("</html>");
		this.description.setText(sb.toString());
		this.description.revalidate();
	}

	private void setMatchColor() {
		if (this.match) {
			this.matchLabel.setText("Passwords Match         ");
			this.matchLabel.setForeground(Color.green);
		} else {
			this.matchLabel.setText("Passwords Don't Match");
			this.matchLabel.setForeground(Color.red);
		}
		this.matchLabel.revalidate();
	}
	
	protected boolean getPassValid() {
		return this.valid;
	}
}
