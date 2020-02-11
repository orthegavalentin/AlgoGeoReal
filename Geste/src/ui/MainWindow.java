package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import geo.Geste;

public class MainWindow extends JFrame{
	Vue v1;
	
	public MainWindow(String title, int x, int y, int w, int h) {
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		v1 = new Vue(w, h);
		add(new UsefulTools(this, v1), BorderLayout.NORTH);
		add(v1);
		pack();
		setLocation(x, y);
	}
	
	public MainWindow(String title) {
		super(title);
		int x = 900, y = 40, w = 800, h = 600;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		v1 = new Vue(w, h);
		add(new UsefulTools(this, v1), BorderLayout.NORTH);
		add(v1);
		pack();
		setLocation(x, y);
	}

	public void addGesture(Geste g) {
		v1.add(g);
	}
}
