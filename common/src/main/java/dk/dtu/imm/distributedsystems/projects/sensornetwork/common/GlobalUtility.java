package dk.dtu.imm.distributedsystems.projects.sensornetwork.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * The Class GlobalUtility.
 *
 * @author Maciej Kucharek <a href="mailto:s091828 (at) student.dtu.dk">s091828 (at) student.dtu.dk</a>
 */
public class GlobalUtility {

	/** The Constant ACK_TIMEOUT_MS. */
	public static final int ACK_TIMEOUT_MS = 1000;

	/** The Constant UDP_PACKET_SIZE. */
	public static final int UDP_PACKET_SIZE = 512;

	/**
	 * Gets the properties.
	 *
	 * @param filename the filename
	 * @return the properties
	 * @throws FileNotFoundException the file not found exception
	 */
	public static Properties getProperties(String filename)
			throws FileNotFoundException {

		URI uri;
		Properties properties = new Properties();

		try {
			uri = new URI(GlobalUtility.class.getResource("/" + filename)
					.toString());
			properties.load(new FileInputStream(uri.getPath()));
		} catch (URISyntaxException e) {
			// File could not be reached
			throw new FileNotFoundException(e.getLocalizedMessage());
		} catch (IOException e) {
			// File could not be reached
			throw new FileNotFoundException(e.getLocalizedMessage());
		}

		return properties;
	}

	/**
	 * Convert string arrayto int array.
	 *
	 * @param sarray the sarray
	 * @return the int[]
	 */
	public static int[] convertStringArraytoIntArray(String[] sarray) {
		int intarray[] = new int[sarray.length];

		if (sarray[0].length() == 0) {
			intarray[0] = -1;
		} else {
			for (int i = 0; i < sarray.length; i++) {
				intarray[i] = Integer.parseInt(sarray[i]);
			}
		}

		return intarray;
	}

}
