package dk.dtu.imm.distributedsystems.projects.sensornetwork.admin;

import java.util.Scanner;

import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.components.TransceiverComponent;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.admin.gui.AdminFrame;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.channels.Channel;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.exceptions.NodeInitializationException;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.nodes.AbstractNode;
import dk.dtu.imm.distributedsystems.projects.sensornetwork.common.nodes.NodeType;


/**
 * Admin Node for Sensor Network
 *
 */
public class Admin extends AbstractNode 
{
	protected TransceiverComponent transceiverComponent;
	
	protected AdminFrame userInterface;
	
	public Admin(int rightPortNumber, Channel[] rightChannels, int ackTimeout) {
		
		super(NodeType.ADMIN.toString());

		this.transceiverComponent = new TransceiverComponent(this, id,
				rightPortNumber, rightChannels,
				ackTimeout);
		
		this.userInterface = new AdminFrame(this.transceiverComponent);
		this.userInterface.setVisible(true);
		this.userInterface.setLocationRelativeTo( null );
		
	}
	
	public AdminFrame getUserInterface() {
		return userInterface;
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out
					.println("Please provide only one parameter - a suitable property file");
			return;
		}
		
		try {
        	
        	String laf = javax.swing.UIManager.getSystemLookAndFeelClassName();
        	javax.swing.UIManager.setLookAndFeel(laf);
        	
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

		@SuppressWarnings("unused")
		Admin admin = null;

		try {
			admin = AdminUtility.getAdminInstance(args[0]);
		} catch (NodeInitializationException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		
	    Scanner in = new Scanner(System.in);

	    int i = 1;
	    while (i != 0) {
	    	in.next();
	    }
		in.close();

		System.out.println("Done");
		
		System.exit(0);
	}	
}
