package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import java.util.Scanner;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.components.TransceiverComponent;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.NodeInitializationException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.nodes.AbstractNode;


/**
 * Admin Node for Sensor Network
 *
 */
public class Admin extends AbstractNode 
{
	
	protected TransceiverComponent transceiverComponent;
	
	public Admin(String id, int leftPortNumber,
			int rightPortNumber, Channel[] leftChannels,
			Channel[] rightChannels, int ackTimeout) {
		
		super(id);

		this.transceiverComponent = new TransceiverComponent(id,
				rightPortNumber, rightChannels,
				ackTimeout);
		
	}
	
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out
					.println("Please provide only one parameter - a suitable property file");
			return;
		}

		Admin admin = null;

//		try {
//			admin = AdminUtility.getAdminInstance(args[0]);
//		} catch (NodeInitializationException e) {
//			System.out.println(e.getMessage());
//			return;
//		}
		
	    Scanner in = new Scanner(System.in);

		in.next();
		in.close();

		System.out.println("Done");
		
		System.exit(0);
	}
}
