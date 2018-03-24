import ServerSide.ServerThreaded;

public class ServerMain {

    public static void main(String[] args) {
        int port = SocketDefinition.ServerPort;
        new ServerThreaded(port).start();
    }
}
