package packet;

import java.io.Serializable;

public class Packet implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3498729645618803952L;
	
	private boolean giveBackAllData;
	private boolean sendingAllData;
	private int totalRowLength;
	private int totalColumnLength;
	private int rowMin;
	private int rowMax;
	private int columnMin;
	private int columnMax;
	private long[][] data;
	private long[] westGhost;
	private long[] eastGhost;
	private final double[] metalConstants;
	private final int[][][] percentageOfMetals;
	private int[][] ghostEastPercentageOfMetal;
	private int[][] ghostWestPercentageOfMetal;

	public Packet(boolean giveBackAllData, int rowMin, int rowMax, int columnMin, int columnMax, long[] westGhost,
			long[] eastGhost) {
		this.sendingAllData = false;
		this.giveBackAllData = giveBackAllData;
		this.eastGhost = eastGhost;
		this.westGhost = westGhost;
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.columnMin = columnMin;
		this.columnMax = columnMax;
		this.percentageOfMetals = null;
		this.metalConstants = null;
		this.data = null;
		this.ghostEastPercentageOfMetal = null;
		this.ghostWestPercentageOfMetal = null;
	}

	public Packet(int totalRowLength, int totalColumnLength, int rowMin, int rowMax, int columnMin, int columnMax,
			long[][] data, long[] westGhost, long[] eastGhost, double[] metalConstants,
			int[][][] percentageOfMetals, int[][] ghostEastPercentageOfMetal, int[][] ghostWestPercentageOfMetal) {
		this.giveBackAllData = true;
		this.sendingAllData = true;
		this.totalRowLength = totalRowLength;
		this.totalColumnLength = totalColumnLength;
		this.rowMin = rowMin;
		this.rowMax = rowMax;
		this.columnMin = columnMin;
		this.columnMax = columnMax;
		this.data = data;
		this.westGhost = westGhost;
		this.eastGhost = eastGhost;
		this.metalConstants = metalConstants;
		this.percentageOfMetals = percentageOfMetals;
		this.ghostEastPercentageOfMetal = ghostEastPercentageOfMetal;
		this.ghostWestPercentageOfMetal = ghostWestPercentageOfMetal;
	}

	

	/**
	 * @return the giveBackAllData
	 */
	public boolean isGiveBackAllData() {
		return giveBackAllData;
	}

	/**
	 * @param giveBackAllData
	 *            the giveBackAllData to set
	 */
	public void setGiveBackAllData(boolean giveBackAllData) {
		this.giveBackAllData = giveBackAllData;
	}

	/**
	 * @return the sendingAllData
	 */
	public boolean isSendingAllData() {
		return sendingAllData;
	}

	/**
	 * @param sendingAllData
	 *            the sendingAllData to set
	 */
	public void setSendingAllData(boolean sendingAllData) {
		this.sendingAllData = sendingAllData;
	}

	/**
	 * @return the rowMin
	 */
	public int getRowMin() {
		return rowMin;
	}

	/**
	 * @param rowMin
	 *            the rowMin to set
	 */
	public void setRowMin(int rowMin) {
		this.rowMin = rowMin;
	}

	/**
	 * @return the rowMax
	 */
	public int getRowMax() {
		return rowMax;
	}

	/**
	 * @param rowMax
	 *            the rowMax to set
	 */
	public void setRowMax(int rowMax) {
		this.rowMax = rowMax;
	}

	/**
	 * @return the columnMin
	 */
	public int getColumnMin() {
		return columnMin;
	}

	/**
	 * @param columnMin
	 *            the columnMin to set
	 */
	public void setColumnMin(int columnMin) {
		this.columnMin = columnMin;
	}

	/**
	 * @return the columnMax
	 */
	public int getColumnMax() {
		return columnMax;
	}

	/**
	 * @param columnMax
	 *            the columnMax to set
	 */
	public void setColumnMax(int columnMax) {
		this.columnMax = columnMax;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(long[][] data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public long[][] getData() {
		return data;
	}

	/**
	 * @return the metalConstants
	 */
	public double[] getMetalConstants() {
		return metalConstants;
	}

	/**
	 * @return the percentageOfMetals
	 */
	public int[][][] getPercentageOfMetals() {
		return percentageOfMetals;
	}

	/**
	 * @return the totalRowLength
	 */
	public int getTotalRowLength() {
		return totalRowLength;
	}

	/**
	 * @param totalRowLength
	 *            the totalRowLength to set
	 */
	public void setTotalRowLength(int totalRowLength) {
		this.totalRowLength = totalRowLength;
	}

	/**
	 * @return the totalColumnLength
	 */
	public int getTotalColumnLength() {
		return totalColumnLength;
	}

	/**
	 * @param totalColumnLength
	 *            the totalColumnLength to set
	 */
	public void setTotalColumnLength(int totalColumnLength) {
		this.totalColumnLength = totalColumnLength;
	}

	/**
	 * @return the ghostEastPercentageOfMetal
	 */
	public int[][] getGhostEastPercentageOfMetal() {
		return ghostEastPercentageOfMetal;
	}

	/**
	 * @param ghostEastPercentageOfMetal
	 *            the ghostEastPercentageOfMetal to set
	 */
	public void setGhostEastPercentageOfMetal(int[][] ghostEastPercentageOfMetal) {
		this.ghostEastPercentageOfMetal = ghostEastPercentageOfMetal;
	}

	/**
	 * @return the ghostWestPercentageOfMetal
	 */
	public int[][] getGhostWestPercentageOfMetal() {
		return ghostWestPercentageOfMetal;
	}

	/**
	 * @param ghostWestPercentageOfMetal
	 *            the ghostWestPercentageOfMetal to set
	 */
	public void setGhostWestPercentageOfMetal(int[][] ghostWestPercentageOfMetal) {
		this.ghostWestPercentageOfMetal = ghostWestPercentageOfMetal;
	}

	/**
	 * @return the westGhost
	 */
	public long[] getWestGhost() {
		return westGhost;
	}

	/**
	 * @param westGhost the westGhost to set
	 */
	public void setWestGhost(long[] westGhost) {
		this.westGhost = westGhost;
	}

	/**
	 * @return the eastGhost
	 */
	public long[] getEastGhost() {
		return eastGhost;
	}

	/**
	 * @param eastGhost the eastGhost to set
	 */
	public void setEastGhost(long[] eastGhost) {
		this.eastGhost = eastGhost;
	}
}
