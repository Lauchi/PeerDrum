package SocketConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * threaded server for var.sockets.tcp.echo Echo service. waits for the next
 * client to connect, creates thread and handles connection in concurrently:
 * sends greeting message to client, reads line by line from client and sends it
 * back adding "echo: " in front of each line until connection is closed by
 * client.
 * 
 * @author Sandro Leuchter
 *
 */
public class PeerDrumServerSocket extends Thread {
	private int port;

	public PeerDrumServerSocket(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)) {
			System.out.println("EchoServer (threaded) auf " + serverSocket.getLocalSocketAddress() + " gestartet ...");
			while (true) {
				Socket socket = serverSocket.accept();
				new EchoThread(socket).start();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private class EchoThread extends Thread {
		private Socket socket;

		public EchoThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			SocketAddress socketAddress = socket.getRemoteSocketAddress();
			System.out.println("Verbindung zu " + socketAddress + " aufgebaut");
			try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
				out.println("Server ist bereit ...");
				String input;
				while ((input = in.readLine()) != null) {
					System.out.println(socketAddress + ">> [" + input + "]");
					out.println("echo: " + input);
				}
			} catch (Exception e) {
				System.err.println(e);
			} finally {
				System.out.println("Verbindung zu " + socketAddress + " abgebaut");
			}
		}
	}
}
