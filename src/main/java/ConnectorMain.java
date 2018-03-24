import SocketConnector.PeerDrumClient;
import SocketConnector.PeerDrumServerSocket;

public class ConnectorMain {

    public static void main(String[] args) {
        PeerDrumServerSocket echoServerThreaded = new PeerDrumServerSocket(8080);
        PeerDrumServerSocket echoServerThreaded2 = new PeerDrumServerSocket(8081);
        echoServerThreaded.start();
        echoServerThreaded2.start();
        PeerDrumClient peerDrumClient = new PeerDrumClient("192.168.170.56", 8080);
        PeerDrumClient peerDrumClient1 = new PeerDrumClient("192.168.170.56", 8081);

        peerDrumClient.sendText("jeah");
        peerDrumClient1.sendText("jeah2");
    }
}
