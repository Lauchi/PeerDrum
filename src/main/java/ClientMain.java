import ClientSide.PeerDrumClient;

import javax.swing.*;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		PeerDrumClient client = new PeerDrumClient(SocketDefinition.ServerIp, SocketDefinition.ServerPort, false, 128);
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
		client.run();
	}
}
