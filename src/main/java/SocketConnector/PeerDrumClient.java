package SocketConnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PeerDrumClient {

	private final String host;
	private final int port;

	public PeerDrumClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void sendText(String msg) {

		try (Socket socket = new Socket(this.host, this.port);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {

			out.println(msg);
			System.out.println(in.readLine());

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
