package ClientSide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThreaded implements Runnable{
    private String host;
    private int port;



    public ClientThreaded(String host, int port) {

        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {

        try (Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {

            // Begrüßung vom Server empfangen und auf Konsole ausgeben
            String msg = in.readLine();
            System.out.println(msg);

            // Zeile von Konsole einlesen, an Server senden und Antwort von
            // Server auf Konsole ausgeben, bis eingegebene Zeile == "q"
            while (true) {

                System.out.print(">> ");
                String line = stdin.readLine();

                out.println(line);
                System.out.println(in.readLine());
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
