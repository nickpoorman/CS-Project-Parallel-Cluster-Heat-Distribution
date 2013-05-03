package project;

import java.util.concurrent.RecursiveAction;

public class Node extends RecursiveAction {

	final long[][] cellsA;
	final long[][] cellsB;
	final int totalRowLength;
	final int totalColumnLength;
	final int rowMin;
	final int columnMin;
	final int rowMax;
	final int columnMax;
	final boolean readA;
	final double[] metalConstants;
	final int[][][] percentageOfMetals;
	final int rowOffset;
	final int columnOffset;
	final long[] ghostEastEdgeData;
	final long[] ghostWestEdgeData;
	final int[][] ghostEastPercentageOfMetal;
	final int[][] ghostWestPercentageOfMetal;

	// each node will take in an two dimensional array of data
	// then the node will check to see if its at its base case leaf or
	// it will attempt to split the data some more and create two new nodes left
	// and right
	public Node(long[][] cellsA, long[][] cellsB, int totalRowLength, int totalColumnLength, int rowMin, int columnMin,
			int rowMax, int columnMax, boolean readA, double[] metalConstants, int[][][] percentageOfMetals,
			int rowOffset, int columnOffset, long[] ghostEastEdgeData, long[] ghostWestEdgeData,
			int[][] ghostEastPercentageOfMetal, int[][] ghostWestPercentageOfMetal) {
		this.cellsA = cellsA;
		this.cellsB = cellsB;
		this.totalRowLength = totalRowLength;
		this.totalColumnLength = totalColumnLength;
		this.rowMin = rowMin;
		this.columnMin = columnMin;
		this.rowMax = rowMax;
		this.columnMax = columnMax;
		this.readA = readA;
		this.metalConstants = metalConstants;
		this.percentageOfMetals = percentageOfMetals;
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
		this.ghostEastEdgeData = ghostEastEdgeData;
		this.ghostWestEdgeData = ghostWestEdgeData;
		this.ghostEastPercentageOfMetal = ghostEastPercentageOfMetal;
		this.ghostWestPercentageOfMetal = ghostWestPercentageOfMetal;

	}

	protected void compute() {
		if (isLeaf()) {
			// do the leaf compute
			doLeaf();
		} else {
			// do node split
			doNode();
		}
		return;
	}

	private boolean isLeaf() {
		return ((this.columnMax - this.columnMin) < 4);
	}

	private void doLeaf() {
		if (readA) {
			doCalculation(this.cellsA, this.cellsB);
		} else {
			doCalculation(this.cellsB, this.cellsA);
		}
		return;
	}

	public void doCalculation(final long[][] cellsRead, final long[][] cellsWrite) {
		for (int row = rowMin; row <= rowMax; row++) {
			for (int column = columnMin; column <= columnMax; column++) {
				if (row == 0) {
					if (column == 0) {
						continue;
					}
				}
				// don't set the last cell (static heat)
				if (row == (this.totalRowLength - 1)) {
					if (column == (this.totalColumnLength - 1))
						continue;

				}
				long totalTemp = 0;
				// for each of the three base metals
				for (int i = 0; i < 3; i++) {

					// for each of the neighboring regions

					// check to see if north exists
					// if it does then get the temperature and get the
					// percentage of metal
					long north = -1;
					long east = -1;
					long south = -1;
					long west = -1;
					if (northExists(new CellPoint(row, column, this.rowOffset, this.columnOffset), cellsRead)) {
						long northTemperature = getTemperature(getNorthPoint(new CellPoint(row, column, this.rowOffset,
								this.columnOffset)), cellsRead);

						int percentageOfMetalInNeighbor = getPercentageOfMetal(i, getNorthPoint(new CellPoint(row,
								column, this.rowOffset, this.columnOffset)));
						north = (long) (northTemperature * (percentageOfMetalInNeighbor / 100.0));
					}
					// if the east doesn't exist we should check to see if the
					// ghost east edge data has any data
					if (eastExists(new CellPoint(row, column, this.rowOffset, this.columnOffset), cellsRead)) {
						long eastTemperature = getTemperature(getEastPoint(new CellPoint(row, column, this.rowOffset,
								this.columnOffset)), cellsRead);
						int percentageOfMetalInNeighbor = getPercentageOfMetal(i, getEastPoint(new CellPoint(row,
								column, this.rowOffset, this.columnOffset)));
						east = (long) (eastTemperature * (percentageOfMetalInNeighbor / 100.0));
					} else {

						// now check to see if the ghost data is in range
						CellPoint p = new CellPoint(row, column, this.rowOffset, this.columnOffset);
						if (this.ghostEastEdgeData != null) {
							// if there is ghost data get the eastTemperature of
							// the currentNode
							long eastTemperature = this.ghostEastEdgeData[p.getRow()];
							int percentageOfMetalInNeighbor = this.ghostEastPercentageOfMetal[p.getRow()][i];
							east = (long) (eastTemperature * (percentageOfMetalInNeighbor / 100.0));
						}
						// }
					}
					if (southExists(new CellPoint(row, column, this.rowOffset, this.columnOffset), cellsRead)) {
						long southTemperature = getTemperature(getSouthPoint(new CellPoint(row, column, this.rowOffset,
								this.columnOffset)), cellsRead);
						int percentageOfMetalInNeighbor = getPercentageOfMetal(i, getSouthPoint(new CellPoint(row,
								column, this.rowOffset, this.columnOffset)));
						south = (long) (southTemperature * (percentageOfMetalInNeighbor / 100.0));
					}
					if (westExists(new CellPoint(row, column, this.rowOffset, this.columnOffset), cellsRead)) {
						long westTemperature = getTemperature(getWestPoint(new CellPoint(row, column, this.rowOffset,
								this.columnOffset)), cellsRead);
						int percentageOfMetalInNeighbor = getPercentageOfMetal(i, getWestPoint(new CellPoint(row,
								column, this.rowOffset, this.columnOffset)));
						west = (long) (westTemperature * (percentageOfMetalInNeighbor / 100.0));
					} else {

						// if our point we are looking for is < 0 we should
						// check to see if the ghostData is not null and use
						// that as our neighbor
						CellPoint p = new CellPoint(row, column, this.rowOffset, this.columnOffset);
						if (this.ghostWestEdgeData != null) {
							// if there is ghost data get the westTemperature of
							// the currentNode
							long westTemperature = this.ghostWestEdgeData[p.getRow()];
							int percentageOfMetalInNeighbor = this.ghostWestPercentageOfMetal[p.getRow()][i];
							west = (long) (westTemperature * (percentageOfMetalInNeighbor / 100.0));
						}
					}
					// add the regions together
					long regionsTotal = 0;
					int numNeighbors = 0;
					if (north != -1) {
						regionsTotal = regionsTotal + north;
						numNeighbors++;
					}
					if (east != -1) {
						regionsTotal = regionsTotal + east;
						numNeighbors++;
					}
					if (south != -1) {
						regionsTotal = regionsTotal + south;
						numNeighbors++;
					}
					if (west != -1) {
						regionsTotal = regionsTotal + west;
						numNeighbors++;
					}
					regionsTotal = (long) (regionsTotal / numNeighbors);
					// multiply the metal constant by the the regions total
					totalTemp = (long) (totalTemp + (regionsTotal * metalConstants[i]));
				}
				setCellTemperature(new CellPoint(row, column, this.rowOffset, this.columnOffset), totalTemp, cellsWrite);
			}
		}
	}

	private void doNode() {
		// if its a node split the node in two
		// left
		int rowMinLeft = this.rowMin;
		int rowMaxLeft = this.rowMax;
		int columnMinLeft = this.columnMin;
		double half = ((this.columnMax + this.columnMin) / 2.0);
		int columnMaxLeft = (int) Math.floor(half);
		// right
		int rowMinRight = this.rowMin;
		int rowMaxRight = this.rowMax;
		int columnMinRight = (int) Math.ceil(half);
		int columnMaxRight = this.columnMax;

		Node left = new Node(this.cellsA, this.cellsB, totalRowLength, totalColumnLength, rowMinLeft, columnMinLeft,
				rowMaxLeft, columnMaxLeft, this.readA, this.metalConstants, percentageOfMetals, rowOffset,
				columnOffset, ghostEastEdgeData, ghostWestEdgeData, ghostEastPercentageOfMetal,
				ghostWestPercentageOfMetal);
		left.fork();
		Node right = new Node(this.cellsA, this.cellsB, totalRowLength, totalColumnLength, rowMinRight, columnMinRight,
				rowMaxRight, columnMaxRight, this.readA, this.metalConstants, percentageOfMetals, rowOffset,
				columnOffset, ghostEastEdgeData, ghostWestEdgeData, ghostEastPercentageOfMetal,
				ghostWestPercentageOfMetal);
		right.invoke();
		left.join();

	}

	public static CellPoint getNorthPoint(CellPoint p) {
		int pX = p.getRow() - 1;
		int pY = p.getColumn();

		return new CellPoint(pX, pY);
	}

	public static CellPoint getEastPoint(CellPoint p) {
		int pX = p.getRow();
		int pY = p.getColumn() + 1;

		return new CellPoint(pX, pY);
	}

	public static CellPoint getSouthPoint(CellPoint p) {
		int pX = p.getRow() + 1;
		int pY = p.getColumn();

		return new CellPoint(pX, pY);
	}

	public static CellPoint getWestPoint(CellPoint p) {
		int pX = p.getRow();
		int pY = p.getColumn() - 1;

		return new CellPoint(pX, pY);
	}

	public static boolean isInBounds(CellPoint p, long[][] cellsRead) {
		if (p.getRow() < 0) {
			return false;
		}
		if (p.getColumn() < 0) {
			return false;
		}
		if (p.getRow() >= cellsRead.length) {
			return false;
		}
		if (p.getColumn() >= cellsRead[0].length) {
			return false;
		}
		return true;
	}

	public static boolean northExists(CellPoint current, long[][] cellsRead) {
		return isInBounds(getNorthPoint(current), cellsRead);
	}

	public static boolean eastExists(CellPoint current, long[][] cellsRead) {
		return isInBounds(getEastPoint(current), cellsRead);
	}

	public static boolean southExists(CellPoint current, long[][] cellsRead) {
		return isInBounds(getSouthPoint(current), cellsRead);
	}

	public static boolean westExists(CellPoint current, long[][] cellsRead) {
		return isInBounds(getWestPoint(current), cellsRead);
	}

	public long getTemperature(CellPoint p, long[][] cellsRead) {
		return cellsRead[p.getRow()][p.getColumn()];
	}

	public int getPercentageOfMetal(int metal, CellPoint neighbor) {
		return this.percentageOfMetals[neighbor.getRow()][neighbor.getColumn()][metal];
	}

	public void setCellTemperature(CellPoint p, long temp, long cellsWrite[][]) {
		cellsWrite[p.getRow()][p.getColumn()] = temp;
	}

}
