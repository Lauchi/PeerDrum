import SocketConnector.PeerDrumClient;
import SocketConnector.PeerDrumServerSocket;

public class ConnectorMain {

    public static void main(String[] args) {
        PeerDrumServerSocket echoServerThreaded = new PeerDrumServerSocket(8080);
    }
}
