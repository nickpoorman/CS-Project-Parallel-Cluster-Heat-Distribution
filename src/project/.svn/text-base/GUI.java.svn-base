package project;

import head.Server;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {

	BorderLayout layout;
	PixelCanvas pixelCanvas;
	int width = 800;
	//long[][] pixels;

	public GUI() {
		super("CSC375 - Pixel Canvas");
		initLookAndFeel();
		int inset = 100;
		setBounds(inset, inset, (int) width+100, (int) (width / 4)+100);
		setResizable(true);
		Container contentPane = this.getContentPane();
		layout = new BorderLayout();
		getContentPane().setLayout(layout);
		 this.setPreferredSize(new Dimension(width, (width / 4)));
		createComponents();
		initComponents();
		addComponents();
		Server server = new Server(width, this);
		server.start();
	}

	public void createComponents() {
		pixelCanvas = new PixelCanvas(width);
		this.setPreferredSize(new Dimension(width, (int) (width / 4)));
	}

	public void initComponents() {

	}

	public void addComponents() {
		this.add(pixelCanvas, BorderLayout.CENTER);
	}

	public void initLookAndFeel() {

	}

	// Quit the application.
	protected void quit() {
		System.exit(0);
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		GUI frame = new GUI();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				createAndShowGUI();
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("update".equals(e.getActionCommand())) {
		}

	}

}
