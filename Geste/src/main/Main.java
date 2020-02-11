package main;

import javax.swing.JFrame;

import ui.MainWindow;

public class Main {

	public static void main(String s[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int x = 10, y = 10, w = 800, h = 600;
				MainWindow frame = new MainWindow("Reconnaissance de geste",x,y,w,h);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}});
	}
}
