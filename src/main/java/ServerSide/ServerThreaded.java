package ServerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerThreaded {

	private int port;


	public ServerThreaded(int port) {
		this.port = port;
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
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
