package dk.dtu.imm.distributedsystems.projects.sensornetwork.common;
/*****************************************
 *                                       *
 * Log syntax checker for course 02220   *
 * Alessio Di Mauro and Xenofon Fafoutis *
 * Copyright 2012                        *
 *
 ****************************************/
import java.io.*;
import java.util.regex.*;

public class LogChecker {

	// changed to public - mkucharek
	public static final int SENSOR = 0;
	public static final int SINK = 1;
	public static final int ADMIN = 2;

	public static boolean checkFile(int type, final String s){

		// mkucharek
		Matcher matcher = getMatcher(type);

		boolean res = true;

		LineNumberReader lineReader = null;
		InputStreamReader isr;

		try {
			if (s.equals("")){
				System.out.println("Reading from standard input.");
				isr = new InputStreamReader(System.in);
			}
			else{
				System.out.println("Reading file " + s.toString());
				isr = new FileReader(new File(s));
			}

			lineReader = new LineNumberReader(isr);
			String line = null;
			while ((line = lineReader.readLine()) != null){
				matcher.reset(line);
				if (!matcher.find()) {
					String msg = "Line " + lineReader.getLineNumber() + " is bad: " + line;
					res = false;
					System.out.println(msg);
				}
			}
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		finally {
			try {
				if (lineReader!= null) lineReader.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return res;

	}
	
	// mkucharek
	public static boolean checkLine(String line, int type) {
		
		Matcher matcher = getMatcher(type);
		
		matcher.reset(line);
		if (!matcher.find()) {
			return false;
		}
		return true;
	}

	public static void main(String args[]){

		/* File or stdin */
		if (args.length == 1 || args.length == 2){
			int type = 0;
			if (args[0].toLowerCase().equals("sensor"))
				type = SENSOR;
			else if (args[0].toLowerCase().equals("sink"))
				type = SINK;
			else if (args[0].toLowerCase().equals("admin"))
				type = ADMIN;
			else{
				System.out.println("Unrecognized type");
				System.out.println("\tUsage: LogChecker sensor|sink|admin [inputFile]");
				System.exit(1);
			}

			if (args.length == 2){
				/* File */
				if (LogChecker.checkFile(type, args[1]))
					System.out.println("Compliance test passed.");
			}
			else{
				/* Stdin */
				if (LogChecker.checkFile(type, ""))
					System.out.println("Compliance test passed.");
			}
		}
		else{
			System.out.println("\tUsage: LogChecker sensor|sink|admin [inputFile]");
			System.exit(1);
		}
	}
	
	// mkucharek - moved the existing code to a new method.
	private static Matcher getMatcher(int type) {
		
		Pattern regexp = null;

		switch(type){
		case SENSOR:
			regexp = Pattern.compile("^(\\d+)\\t((\\d+)|SINK)\\t((\\d+)|SINK)\\t(GEN|SND|RCV|SET)\\t(DAT|ALM|ACK|THR|PRD)(\\t((\\d+:\\d+)|(\\d+)))?$"); // Sensor
			break;
		case SINK:
			regexp = Pattern.compile("^(\\d+)\t((\\d+)|ADMIN)\\t(SND|RCV)\\t(DAT|ALM|ACK|THR|PRD|MIN|MAX|AVG)(\\t((\\d+:\\d+)|(\\d+)))?$"); // Sink
			break;
		case ADMIN:
			regexp = Pattern.compile("^(\\d+)\\t(SND|RCV)\\t(ALM|ACK|THR|PRD|MIN|MAX|AVG)(\\t((\\d+:\\d+)|(\\d+)))?$"); // Admin
			break;
		default:
			return null;
		}

		return regexp.matcher("");
		
	}
}
