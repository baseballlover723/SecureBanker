import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.audio.AudioStream;
/**
 * Main
 *
 * @author rosspa. Created Jun 30, 2014.
 */
public class Main {

	private static File file;
	private static BufferedReader bufferedReader;

	/**
	 * Main stuff
	 *
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(Encrypter.StringEncrypte("mylongusername"));
//		long start = System.nanoTime();
//		int TEST_SIZE = 100;
//		for (long k = 0; k<TEST_SIZE;k++) {
//			Encrypter.Decrypte(Encrypter.Encrypte(k));
////			Encrypter.Encrypte(k);
//		}
//		System.out.println(Encrypter.getTimeUnits( (System.nanoTime() - start) / (double) (TEST_SIZE) ));
		// new Server(' ',',');
		new Server();
		// Account account = Server.getAccount("blah");
		// Encrypter.testEncrypte();
//		 Server.testOverWriteSpeed(1000_000);
//		 Server.overwrite();

//		try {
//			User blah = Server.login("1", "d5da82d21c6dbd6bb32bea12e32a5970478a00e2");
//			blah.addAccount("New_test", 42);
//			blah.updateServer();
//		} catch (AccountException exception) {
//			// TODO Auto-generated catch-block stub.
//			exception.printStackTrace();
//		}

		 Gui blah = new Gui();
		// System.out.println("done");
		// AudioStream sound = soundHelper("src/td4w.au");
		// AudioPlayer.player.start(sound);
		// try {
		// Thread.sleep(4_000);
		// } catch (InterruptedException exception) {
		// // TODO Auto-generated catch-block stub.
		// exception.printStackTrace();
		// }
		// System.exit(0);
	}

	private static AudioStream soundHelper(String filePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			AudioStream audioStream = new AudioStream(fileInputStream);
			return audioStream;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
