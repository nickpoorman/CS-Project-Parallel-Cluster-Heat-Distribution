package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PixelCanvas extends JPanel {

	long[][] pixelsA = null;

	public PixelCanvas(int width) {
	}

	public void paintComponent(Graphics g) {
		if (pixelsA != null) {
			Graphics2D g2;

			g2 = (Graphics2D) g;
//				for (int row = pixelsA.length - 1; row > 0; row--) {
//					for (int column = pixelsA[0].length - 1; column > 0; column--) {
//						g2.setColor(getColorFromLong(pixelsA[row][column]));
//
//						// g2.drawRect(w*10, h*10, 10, 10);
//						g2.drawRect(column, row, 1, 1);
//					}
//				}
			
			for (int row = 0; row < pixelsA.length; row++) {
				for (int column = 0; column < pixelsA[0].length; column++) {
					g2.setColor(getColorFromLong(pixelsA[row][column]));

					// g2.drawRect(w*10, h*10, 10, 10);
					g2.fillRect(column, row, 1, 1);
				}
			}
			

//			for (int row = 0; row < pixelsA.length; row++) {
//				for (int column = 0; column < pixelsA[0].length; column++) {
//					g2.setColor(getColorFromLong(pixelsA[row][column]));
//
//					// g2.drawRect(w*10, h*10, 10, 10);
//					g2.fillRect(column * 10, row * 10, 10, 10);
//				}
//			}
		}

	}

	public void setPixel(int w, int h, long value) {
		pixelsA[w][h] = value;
	}

	public Color getColorFromLong(long l) {
		if (l < 0) {
			return Color.WHITE;
		}
		if (l == Long.MAX_VALUE) {
			return Color.red;
			//return Color.WHITE;
		}
		if (l == 0) {
			return Color.BLUE;
			//return Color.BLACK;
		}
		int[] b = new int[3];
		b[2] = Long.numberOfLeadingZeros(l) * 4;
		// b[2] = Long.numberOfLeadingZeros(l);
		// System.out.println("The logb2 number is: " +
		// Long.nu.numberOfLeadingZeros(l) + " long value is: " + l +
		// " times 4 is: " + (Long.numberOfLeadingZeros(l) * 4));
		if (b[2] == 256) {
			b[2]--;
		}
		b[0] = Math.abs(256 - b[2]);		
		return new Color(b[0], b[1], b[2]);
		 //int greyScale = (int) ((b[0] * .3) + (b[1] * .59) + (b[2] * .11));
		//return new Color(greyScale, greyScale,greyScale);

	}

	/**
	 * @param pixels
	 *            the pixels to set
	 */
	public void setPixels(long[][] pixels) {
		this.pixelsA = pixels;
	}

}
