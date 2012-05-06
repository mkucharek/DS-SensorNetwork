package dk.dtu.imm.distributedsystems.projects.sensornetwork.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;

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
	
	public static final String TEMPLATE_PROPERTIES_FILE_NAME = "template.properties";
	
	public static final String VALUE_DELIMITER = ";";

	/**
	 * Gets the properties.
	 *
	 * @param filename the filename
	 * @return the properties
	 * @throws IOException 
	 * @throws FileNotFoundException the file not found exception
	 * @throws URISyntaxException 
	 */
	public static Properties getPropertiesFromClasspath(String filename) throws FileNotFoundException, IOException, URISyntaxException {

		InputStream is;
		Properties properties = new Properties();

		is = GlobalUtility.class.getClassLoader().getResourceAsStream(filename);
		properties.load(is);

		return properties;
	}
	
	public static Properties getProperties(String filename) throws FileNotFoundException, IOException, URISyntaxException {

		Properties properties = new Properties();
		
		properties.load(new FileInputStream(filename));

		return properties;
	}
	
	public static Channel[] getChannelArray(String[] ids, String[] ips, String[] portNumbers) {
		
		if(ids.length != ips.length || ips.length != portNumbers.length) {
			throw new IllegalStateException("Cannot construct Channel array - different number of provided parameters");
		}
		
		Channel[] channels = new Channel[ids.length];
		
		for(int i=0; i<ids.length; ++i) {
			channels[i] = new Channel(ids[i], 
    				ips[i], 
    				Integer.parseInt(portNumbers[i]));
    	}
		
		return channels;
   
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
