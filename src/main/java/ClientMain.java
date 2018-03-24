import ClientSide.ClientThreaded;

public class ClientMain {

	public static void main(String[] args) {
		String host = SocketDefinition.ServerIp;
		int port = SocketDefinition.ServerPort;

		ClientThreaded clientThreaded = new ClientThreaded(host, port);
		clientThreaded.run();
	}
}
