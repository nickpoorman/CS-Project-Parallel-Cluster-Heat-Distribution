package client;

import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import packet.Packet;
import project.Node;

public class ClientTree {

	final long[][] cellsA;
	final long[][] cellsB;
	final Socket socket;
	final int rowMin;
	final int rowMax;
	final int columnMin;
	final int columnMax;
	final double[] metalConstants;
	final int[][][] percentageOfMetals;
	final int totalRowLength;
	final int totalColumnLength;
	final int rowOffset;
	final int columnOffset;
	AtomicBoolean readA;
	final long[] ghostEastEdgeData;
	final long[] ghostWestEdgeData;
	final int[][] ghostEastPercentageOfMetal;
	final int[][] ghostWestPercentageOfMetal;
	final ForkJoinPool pool;

	public ClientTree(Packet packet, Socket socket) {
		this.socket = socket;
		// first get the two array's by cloning the first one
		this.cellsA = packet.getData().clone();
		this.cellsB = packet.getData().clone();
		// set the array constraints
		rowMin = packet.getRowMin();
		rowMax = packet.getRowMax();
		columnMin = packet.getColumnMin();
		columnMax = packet.getColumnMax();
		metalConstants = packet.getMetalConstants();
		percentageOfMetals = packet.getPercentageOfMetals();
		this.totalRowLength = packet.getTotalRowLength();
		this.totalColumnLength = packet.getTotalColumnLength();
		// we have to figure out our own offset from 0,0
		this.rowOffset = (0 - rowMin);
		this.columnOffset = (0 - columnMin);
		System.out.println("rowMin is: " + rowMin);
		System.out.println("rowMax is: " + rowMax);
		System.out.println("columnMin is: " + columnMin);
		System.out.println("columnMax is: " + columnMax);
		readA = new AtomicBoolean(true);
		ghostEastEdgeData = packet.getEastGhost();
		ghostWestEdgeData = packet.getWestGhost();
		this.ghostEastPercentageOfMetal = packet.getGhostEastPercentageOfMetal();
		this.ghostWestPercentageOfMetal = packet.getGhostWestPercentageOfMetal();
		pool = new ForkJoinPool();
	}

	public void doIteration() {

		pool.invoke(new Node(cellsA, cellsB, totalRowLength, totalColumnLength, rowMin, columnMin, rowMax, columnMax,
				readA.get(), metalConstants, percentageOfMetals, rowOffset, columnOffset, ghostEastEdgeData,
				ghostWestEdgeData, ghostEastPercentageOfMetal, ghostWestPercentageOfMetal));
		if (readA.get()) {
			readA.set(false);
		} else {
			readA.set(true);
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

	public void setWestGhost(long[] read, long[] write) {
		if (read == null)
			return; // they just tried to set a side that doesn't have a west
					// ghost
		for (int i = 0; i < read.length; i++) {
			write[i] = read[i];
		}
	}

	public void setEastGhost(long[] read, long[] write) {
		if (read == null)
			return; // they just tried to set a side that doesn't have an east
					// ghost
		for (int i = 0; i < read.length; i++) {
			write[i] = read[i];
		}
	}

	public long[] getWestEdge(long[][] read) {
		long[] tmp = new long[read.length];
		for(int i = 0; i < read.length; i++){
			tmp[i] = read[i][columnMin - columnMin];
		}
		return tmp;
	}

	public long[] getEastEdge(long[][] read) {
		long[] tmp = new long[read.length];
		for(int i = 0; i < read.length; i++){
			tmp[i] = read[i][columnMax - columnMin];
		}
		return tmp;
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

	/**
	 * @return the rowMin
	 */
	public int getRowMin() {
		return rowMin;
	}

	/**
	 * @return the rowMax
	 */
	public int getRowMax() {
		return rowMax;
	}

	/**
	 * @return the columnMin
	 */
	public int getColumnMin() {
		return columnMin;
	}

	/**
	 * @return the columnMax
	 */
	public int getColumnMax() {
		return columnMax;
	}
}
