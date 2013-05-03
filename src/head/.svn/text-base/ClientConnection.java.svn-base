package head;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection extends Thread {

	private Socket socket = null;
	private ClientManager clientManager = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String currentUser = null;
	private final static boolean DEBUG = true;
	private boolean forceLogOut = false;

	public ClientConnection(Socket socket, ClientManager manager) {
		super("ClientConnectionThread");
		this.socket = socket;
		this.clientManager = manager;
		if (DEBUG)
			System.out.println("New user has connected");
	}

	public void setUsername(String user) {
		this.currentUser = user;
	}

	public void run() {
		//since this really isn't doing much of anything anymore lets just have it add itself to the clientManager so the server doesn't have to do it
		this.clientManager.addClient(socket.getRemoteSocketAddress().toString(), this);
	}

	public void forceLogOut() {
		this.forceLogOut = true;
		logOutUser();
	}

	private void logOutUser() {
		try {
			getOut().close();
			getIn().close();
			getSocket().close();
		} catch (IOException ex) {
			if (DEBUG)
				ex.printStackTrace();
		} finally {
			if (DEBUG)
				System.out.println("User has quit: " + getCurrentUser());
			if (!getCurrentUser().equals("")) {
				boolean b = clientManager.removeClient(getCurrentUser());
				if (DEBUG)
					System.out.println("Removed user: " + b);
			}
		}
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}

	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * @return the currentUser
	 */
	public String getCurrentUser() {
		if (currentUser != null) {
			return currentUser;
		}
		return "";
	}

}
