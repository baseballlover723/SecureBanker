import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * this is GUI
 *
 * @author rosspa. Created Jul 16, 2014.
 */
public class Gui {

	private LoginPanel loginPanel;
	private static Timer timer;
	private static JFrame frame;
	private static JPanel currentPanel;

	public Gui() {
		this.loginPanel = new LoginPanel();
		frame = new JFrame();
		frame.setSize(900, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentPanel = null;
		replaceJPanel(this.loginPanel);
		frame.setVisible(true);


	}

	/**
	 * 
	 * removes the current JPanel and then adds the given JPanel and sets the
	 * currentPanel to the given JPanel and sets the JFrame to have the minimum
	 * size of the preferred size of the new JPanel
	 *
	 * @param newPanel
	 */
	public static void replaceJPanel(JPanel newPanel) {
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}
		frame.add(newPanel);
		currentPanel = newPanel;
		frame.setMinimumSize(newPanel.getPreferredSize());
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.revalidate();
	}

	/**
	 * 
	 * removes the current JPanel and then adds the given JPanel and sets the
	 * currentPanel to the given JPanel and sets the JFrame to have the minimum
	 * size of the preferred size of the new JPanel
	 *
	 * @param newPanel
	 * @param menuBar
	 *            set to null to delete menubar
	 */
	public static void replaceJPanel(JPanel newPanel, JMenuBar menuBar) {
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}
		frame.add(newPanel);
		currentPanel = newPanel;
		frame.setJMenuBar(menuBar);
		frame.setMinimumSize(newPanel.getPreferredSize());
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.revalidate();
	}

	/**
	 * 
	 * repacks the frame and updates the minimum size of the frame to the size
	 * after packing. Then it revalidates the frame.
	 *
	 */
	public static void updateFrameSize() {
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.revalidate();
	}
}
