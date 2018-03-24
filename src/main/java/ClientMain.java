import ClientSide.PeerDrumClient;
import Domain.DrumMachine;

import javax.swing.*;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		PeerDrumClient client = new PeerDrumClient(SocketDefinition.ServerIp, SocketDefinition.ServerPort);
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
		client.run();
		DrumMachine drumMachine = new DrumMachine();
		drumMachine.run();
	}
}
