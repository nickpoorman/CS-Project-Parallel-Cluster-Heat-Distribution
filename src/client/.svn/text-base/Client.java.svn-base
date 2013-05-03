package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import packet.Packet;
import packet.ResultsPacket;

public class Client extends Thread {

	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String host;
	private final static boolean DEBUG = true;

	public Client(String host) {
		this.host = host;
	}

	public void run() {
		try {

			try {
				socket = new Socket(getHost(), 31337);
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream())), true);
				in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			} catch (UnknownHostException e) {
				System.err.println("Don't know about host: " + getHost() + ".");
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Couldn't get I/O for the connection to: " + getHost() + ".");
				System.exit(1);
			}

			ClientTree clientTree = null;
			for (;;) {
				// wait for the server to send us a packet object
				ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
				Packet packet = null;
				try {
					packet = (Packet) objectIn.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (packet.isSendingAllData()) {
					clientTree = new ClientTree(packet, socket);
				}
				if (clientTree != null) {
					if (!packet.isSendingAllData()) {
						clientTree.setWestGhost(packet.getWestGhost(), clientTree.ghostWestEdgeData);
						clientTree.setEastGhost(packet.getEastGhost(), clientTree.ghostEastEdgeData);
					}
					clientTree.doIteration();
					ResultsPacket resultsPacket = null;
					if (packet.isGiveBackAllData()) {
						resultsPacket = new ResultsPacket(clientTree.getRowMin(), clientTree.getRowMax(), clientTree
								.getColumnMin(), clientTree.getColumnMax(), clientTree.getReadArray());
					} else {
						resultsPacket = new ResultsPacket(clientTree.getRowMin(), clientTree.getRowMax(), clientTree
								.getColumnMin(), clientTree.getColumnMax(), clientTree.getEastEdge(clientTree
								.getReadArray()), clientTree.getWestEdge(clientTree.getReadArray()));
					}
					objectOut.writeObject(resultsPacket);
				}
			}
		} catch (SocketException e) {
			if (DEBUG)
				System.err.println("Server Died");
			/* Put into the output of the client that the server disconnected */
		} catch (IOException e) {
			if (DEBUG)
				e.printStackTrace();
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
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

}
