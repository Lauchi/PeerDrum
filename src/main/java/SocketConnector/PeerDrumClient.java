package SocketConnector;

import java.io.*;
import java.net.Socket;

public class PeerDrumClient {

	private Socket socket;
	private Reader sendQuue = null;

	public PeerDrumClient(String host, int port) {
		try {
			socket = new Socket(host, port);
			sendQuue = new InputStreamReader(null);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader stdin = new BufferedReader(sendQuue);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*



		try (Socket {

			// Begrüßung vom Server empfangen und auf Konsole ausgeben
			String msg = in.readLine();
			System.out.println(msg);

			// Zeile von Konsole einlesen, an Server senden und Antwort von
			// Server auf Konsole ausgeben, bis eingegebene Zeile == "q"
			while (true) {
				System.out.print(">> ");
				String line = stdin.readLine();
				if ("q".equals(line)) {
					break;
				}
				out.println(line);
				System.out.println(in.readLine());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}*/
}
