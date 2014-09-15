import java.util.TimerTask;


/**
 * TODO Put here a description of what this class does.
 *
 * @author rosspa.
 *         Created Aug 10, 2014.
 */
public class Timeout extends TimerTask{

	@Override
	public void run() {
		Server.logout(true);
		
	}

}
