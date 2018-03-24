import ClientSide.PeerDrumClient;

import javax.swing.*;

public class ClientMain {


	/**
	 * Runs the client as an application with a closeable frame.
	 */
	public static void main(String[] args) throws Exception {
		PeerDrumClient client = new PeerDrumClient("Client1");
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
		client.run();
	}
}
