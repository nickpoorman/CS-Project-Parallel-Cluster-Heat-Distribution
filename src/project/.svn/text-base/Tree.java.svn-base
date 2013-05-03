package project;

import head.ClientConnection;
import head.ClientManager;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import packet.FullCircleTask;
import packet.Packet;
import packet.ResultsPacket;

public class Tree implements Runnable {

	final int rows;
	final int columns;
	GUI gui;
	final long[][] cellsA;
	final long[][] cellsB;
	final ClientManager clientManager;
	AtomicBoolean readA;

	public Tree(GUI gui, int columns, int rows, ClientManager clientManager) {
		this.clientManager = clientManager;
		this.gui = gui;
		this.rows = rows;
		this.columns = columns;
		readA = new AtomicBoolean(true);
		cellsA = new long[rows][columns];
		cellsB = new long[rows][columns];
		updateGUI(cellsA);
	}

	@Override
	public void run() {
		final double[] metalConstants = new double[] { 1.0, 1.0, 1.0 };
		cellsA[0][0] = Long.MAX_VALUE >> 3;
		cellsA[rows - 1][columns - 1] = Long.MAX_VALUE >> 1;
		cellsB[0][0] = Long.MAX_VALUE >> 3;
		cellsB[rows - 1][columns - 1] = Long.MAX_VALUE >> 1;

		final int[][][] percentageOfMetals = new int[rows][columns][3];
		final int rowMin = 0;
		final int columnMin = 0;
		final int rowMax = rows - 1;
		final int columnMax = columns - 1;

		generateRandomPercentageOfMetals(percentageOfMetals);

		// check to see if there are any servers that can do this work for us
		// if there is then set do self to be false and send them the work
		if (clientManager.getClients().size() == 2) {
			// split the work up to the two clients
			Collection<ClientConnection> clients = clientManager.getClients().values();
			ClientConnection left = null;
			ClientConnection right = null;
			for (ClientConnection c : clients) {
				if (left == null) {
					left = c;
				} else if (right == null) {
					right = c;
				}
			}
			// split the data in half
			// left
			int rowMinLeft = rowMin;
			int rowMaxLeft = rowMax;
			int columnMinLeft = columnMin;
			double half = ((columnMax + columnMin) / 2.0);
			int columnMaxLeft = (int) Math.floor(half);
			// right
			int rowMinRight = rowMin;
			int rowMaxRight = rowMax;
			int columnMinRight = (int) Math.ceil(half);
			int columnMaxRight = columnMax;

			// create a new Packet to send to the client
			// it will need everything at first to create the initial tree on
			// its end
			// create the left
			// send the initial all data, then from there out only send the
			// edges

			boolean firstTime = true;
			for (int itr = 0; itr < 30000; itr++) {
				long[] leftEastGhost = this.getEastGhost(columnMaxLeft, this.getReadArray());
				long[] leftWestGhost = this.getWestGhost(columnMinLeft, this.getReadArray());
				int[][] leftGhostWestPercentageOfMetal = getGhostWestPercentageOfMetal(rowMinLeft, columnMinLeft,
						rowMaxLeft, columnMaxLeft, percentageOfMetals);
				int[][] leftGhostEastPercentageOfMetal = getGhostEastPercentageOfMetal(rowMinLeft, columnMinLeft,
						rowMaxLeft, columnMaxLeft, percentageOfMetals);
				int[][][] leftCenterPercentageOfMetal = getCenterPercentageOfMetal(rowMinLeft, columnMinLeft,
						rowMaxLeft, columnMaxLeft, percentageOfMetals);
				long[] rightEastGhost = this.getEastGhost(columnMaxRight, this.getReadArray());
				long[] rightWestGhost = this.getWestGhost(columnMinRight, this.getReadArray());
				int[][] rightGhostWestPercentageOfMetal = getGhostWestPercentageOfMetal(rowMinRight, columnMinRight,
						rowMaxRight, columnMaxRight, percentageOfMetals);
				int[][] rightGhostEastPercentageOfMetal = getGhostEastPercentageOfMetal(rowMinRight, columnMinRight,
						rowMaxRight, columnMaxRight, percentageOfMetals);
				int[][][] rightCenterPercentageOfMetal = getCenterPercentageOfMetal(rowMinRight, columnMinRight,
						rowMaxRight, columnMaxRight, percentageOfMetals);

				// if this is the very first time sending anything send them all
				// of it
				Packet leftPacket = null;
				Packet rightPacket = null;
				if (firstTime) {
					long[][] leftData = getCenterData(rowMinLeft, columnMinLeft, rowMaxLeft, columnMaxLeft, this
							.getReadArray());
					long[][] rightData = getCenterData(rowMinRight, columnMinRight, rowMaxRight, columnMaxRight, this
							.getReadArray());
					leftPacket = new Packet(rows, columns, rowMinLeft, rowMaxLeft, columnMinLeft, columnMaxLeft,
							leftData, leftWestGhost, leftEastGhost, metalConstants, leftCenterPercentageOfMetal,
							leftGhostEastPercentageOfMetal, leftGhostWestPercentageOfMetal);
					rightPacket = new Packet(rows, columns, rowMinRight, rowMaxRight, columnMinRight, columnMaxRight,
							rightData, rightWestGhost, rightEastGhost, metalConstants, rightCenterPercentageOfMetal,
							rightGhostEastPercentageOfMetal, rightGhostWestPercentageOfMetal);
					firstTime = false;
				} else {
					boolean giveBackAllData = ((itr & 31) == 31);
					// only send the edges
					// every n iterations ask for all our data back
					leftPacket = new Packet(giveBackAllData, rowMinLeft, rowMaxLeft, columnMinLeft, columnMaxLeft,
							leftWestGhost, leftEastGhost);
					rightPacket = new Packet(giveBackAllData, rowMinRight, rowMaxRight, columnMinRight, columnMaxRight,
							rightWestGhost, rightEastGhost);
				}

				// //////////////////// send the left packet to one server
				// send the right packet to another server
				FullCircleTask leftFullCircleTask = new FullCircleTask(leftPacket, left.getSocket());
				FullCircleTask rightFullCircleTask = new FullCircleTask(rightPacket, right.getSocket());
				leftFullCircleTask.start();
				rightFullCircleTask.start();
				try {
					leftFullCircleTask.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					rightFullCircleTask.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// ///////////////////// when they are finished running we just
				// have to update our data
				// now with the data they sent us back
				ResultsPacket leftResult = leftFullCircleTask.getResultsPacket();
				ResultsPacket rightResult = rightFullCircleTask.getResultsPacket();

				// we need to check to see if they sent back all their data
				if (leftResult.isSendingAllData()) {
					// they sent all their data back so now we must update all
					// our
					// data with theirs
					setCenterData(leftResult.getRowMin(), leftResult.getColumnMin(), leftResult.getRowMax(), leftResult
							.getColumnMax(), leftResult.getData(), this.getWriteArray());
				} else {
					this.setWestEdge(leftResult.getColumnMin(), leftResult.getWestEdgeData(), this.getWriteArray());
					this.setEastEdge(leftResult.getColumnMax(), leftResult.getEastEdgeData(), this.getWriteArray());

				}
				// do the same thing for the data returned from the right
				if (rightResult.isSendingAllData()) {
					setCenterData(rightResult.getRowMin(), rightResult.getColumnMin(), rightResult.getRowMax(),
							rightResult.getColumnMax(), rightResult.getData(), this.getWriteArray());
				} else {
					this.setWestEdge(rightResult.getColumnMin(), rightResult.getWestEdgeData(), this.getWriteArray());
					this.setEastEdge(rightResult.getColumnMax(), rightResult.getEastEdgeData(), this.getWriteArray());
				}

				// //////////////////// update the gui
				if (readA.get()) {
					readA.set(false);
				} else {
					readA.set(true);
				}
				if (leftResult.isSendingAllData() || rightResult.isSendingAllData())
					updateGUI(this.getReadArray());

			}
		}

	}

	public long[][] getWriteArray() {
		if (readA.get()) {
			return cellsB;
		} else {
			return cellsA;
		}
	}

	public long[][] getReadArray() {
		if (readA.get()) {
			return cellsA;
		} else {
			return cellsB;
		}
	}

	public int[][][] getCenterPercentageOfMetal(int rowMin, int columnMin, int rowMax, int columnMax, int[][][] read) {
		int[][][] centerPercentageData = new int[((rowMax - rowMin) + 1)][((columnMax - columnMin) + 1)][3];
		for (int row = rowMin; row <= rowMax; row++) {
			for (int column = columnMin; column <= columnMax; column++) {
				for (int a = 0; a < 3; a++) {
					centerPercentageData[row - rowMin][column - columnMin][a] = read[row][column][a];
				}
			}
		}
		return centerPercentageData;
	}

	public int[][] getGhostEastPercentageOfMetal(int rowMin, int columnMin, int rowMax, int columnMax, int[][][] read) {
		if (columnMax + 1 >= read[0].length)
			return null;
		// copy each of the three
		int[][] tmp = new int[((rowMax - rowMin) + 1)][3];
		for (int a = rowMin; a <= rowMax; a++) {
			System.arraycopy(read[a][(columnMax + 1)], 0, tmp[a], 0, read[0][0].length);
		}
		return tmp;
	}

	public int[][] getGhostWestPercentageOfMetal(int rowMin, int columnMin, int rowMax, int columnMax, int[][][] read) {
		if ((columnMin - 1) < 0)
			return null;
		int[][] tmp = new int[((rowMax - rowMin) + 1)][3];
		for (int a = rowMin; a < (rowMax + 1); a++) {
			System.arraycopy(read[a][(columnMin - 1)], 0, tmp[a], 0, read[0][0].length);
		}
		return tmp;
	}

	// 0-7 8-15
	// 0-3 0-3
	// 8 8
	// 0 8 3 15 smaller larger
	public void setCenterData(int rowMin, int columnMin, int rowMax, int columnMax, long[][] read, long[][] write) {
		for (int a = rowMin; a < (rowMax + 1); a++) {
			System.arraycopy(read[a], 0, write[a], columnMin, (columnMax - columnMin) + 1);
		}
	}

	public long[][] getCenterData(int rowMin, int columnMin, int rowMax, int columnMax, long[][] data) {
		long[][] centerData = new long[((rowMax - rowMin) + 1)][((columnMax - columnMin) + 1)];
		for (int a = rowMin; a < (rowMax + 1); a++) {
			System.arraycopy(data[a], columnMin, centerData[a], 0, (columnMax - columnMin) + 1);
		}
		return centerData;
	}

	public long[] getWestGhost(int columnMin, long read[][]) {
		if ((columnMin - 1) < 0) {
			return null; // there is no west ghost before 0
		}
		long[] tmp = new long[read.length];
		for (int i = 0; i < read.length; i++) {
			tmp[i] = read[i][columnMin - 1];
		}
		return tmp;
	}

	public long[] getEastGhost(int columnMax, long read[][]) {
		if ((columnMax + 1) >= read[0].length) {
			return null; // there is no east after the max
		}
		long[] tmp = new long[read.length];
		for (int i = 0; i < read.length; i++) {
			tmp[i] = read[i][columnMax + 1];
		}
		return tmp;
	}

	public void setWestEdge(int columnMin, long read[], long write[][]) {
		for (int i = 0; i < read.length; i++) {
			write[i][columnMin] = read[i];
		}
	}

	public void setEastEdge(int columnMax, long read[], long write[][]) {
		for (int i = 0; i < read.length; i++) {
			write[i][columnMax] = read[i];
		}
	}

	public static void setInitialTemp() {
		// long temp = (Long.MAX_VALUE - 1000);
		// long temp = 0;
		// for (int row = 0; row < cells.length; row++) {
		// for (int column = 0; column < cells[0].length; column++) {
		// cells[row][column] = temp;
		// }
		// }

	}

	public static void generateRandomPercentageOfMetals(int[][][] percentageOfMetals) {
		Random r = new Random();

		for (int row = 0; row < percentageOfMetals.length; row++) {
			for (int column = 0; column < percentageOfMetals[0].length; column++) {
				int totalPercentLeft = 100;
				for (int a = 0; a < percentageOfMetals[0][0].length; a++) {
					if (a == 0) {
						int d = r.nextInt(101);
						percentageOfMetals[row][column][a] = d;
						totalPercentLeft = totalPercentLeft - d;
					} else if (a == 1) {
						int d = r.nextInt(totalPercentLeft + 1);
						percentageOfMetals[row][column][a] = d;
						totalPercentLeft = totalPercentLeft - d;
					} else {
						percentageOfMetals[row][column][a] = totalPercentLeft;
					}
				}
			}
		}
	}

	public void updateGUI(final long[][] pixels) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				gui.pixelCanvas.setPixels(pixels);
				gui.pixelCanvas.repaint();
			}
		});
	}
}
