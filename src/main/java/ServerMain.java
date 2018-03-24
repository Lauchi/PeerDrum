import ServerSide.PeerDrumServer;

import java.net.ServerSocket;

public class ServerMain {

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(SocketDefinition.ServerPort);
        try {
            while (true) {
                new PeerDrumServer.Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }
}
