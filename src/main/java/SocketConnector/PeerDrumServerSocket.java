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
public class PeerDrumServerSocket {
	/**
	 * port on which this service is currently listening on localhost
	 */
	private int port;

	/**
	 * the only constructor for this class
	 * 
	 * @param port
	 *            port on which this service will be listening on localhost
	 */
	public PeerDrumServerSocket(int port) {
		this.port = port;
	}

	/**
	 * creates server socket on localhost:port, infinitely handles connections
	 * to clients concurrently
	 */
	public void start() {
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

	/**
	 * Each connection is handled with an instance of this class.
	 */
	private class EchoThread extends Thread {
		/**
		 * TCP connection to client
		 */
		private Socket socket;

		/**
		 * the only constructor for this class
		 * 
		 * @param socket
		 *            the individual socket that the server created on accepting
		 *            a client that this EchoThread instance will be
		 *            communicating with
		 */
		public EchoThread(Socket socket) {
			this.socket = socket;
		}

		/**
		 * defines the behavior of this Thread instance, will be executed
		 * concurrently if start() is called on instance
		 * 
		 */
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

	/**
	 * main method: entrypoint to run service
	 * 
	 * @param args
	 *            args[0] must be the port number of the server (int); rest of
	 *            args is ignored
	 */
	public static void main(String[] args) {
		int port = 8081;
		new PeerDrumServerSocket(port).start();
	}
}
