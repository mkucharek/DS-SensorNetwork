package dk.dtu.imm.distributedsystems.projects.sensornetwork.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class GlobalUtility {
	
	public static final int ACK_TIMEOUT_MS = 1000;
	
	/* Here is the instance of the Singleton */
	private static GlobalUtility instance;

	/* Need the following object to synchronize */
	/* a block */
	private static Object syncObject = new Object();

	/* Prevent direct access to the constructor */
	private GlobalUtility() {
		super();
	}

	public static GlobalUtility getInstance() {
		/*
		 * in a non-thread-safe version of a Singleton the following line could
		 * be executed, and the
		 */
		/* thread could be immediately swapped out */
		if (instance == null) {
			synchronized (syncObject) {
				if (instance == null) {
					instance = new GlobalUtility();
				}
			}
		}
		return instance;
	}
	
    public static Properties getProperties(String filename) throws FileNotFoundException {
    	
    	URI uri;
		Properties properties = new Properties();
		
		try {
			uri = new URI(GlobalUtility.getInstance().getClass().getResource("/" + filename).toString());
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
