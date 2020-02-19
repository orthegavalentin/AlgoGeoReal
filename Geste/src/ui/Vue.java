package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import geo.Geste;
import geo.PointVisible;
import ui.config.Parameters;

public class Vue extends JPanel {
	Color bgColor;
	Color fgColor; 
	int width, height;
	ArrayList<Geste> gestes;
		
	public Vue(int width, int height) {
		super();
		this.bgColor = Couleur.bg; 
		this.fgColor = Couleur.fg; 
		this.width = width;
		this.height = height;	
		this.setBackground(Couleur.bg);
		this.setPreferredSize(new Dimension(width, width));
		Tracker t = new Tracker(this);
		this.addMouseListener(t);
		this.addMouseMotionListener(t);
		gestes = new ArrayList<Geste>();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.setColor(fgColor);
		
		for (Geste go: gestes) {
			go.draw(g2d);
		}
	}

	public void add(Geste geste) {
		gestes.add(geste);
	}

	public void loadData(String fileName) {
		gestes = new ArrayList<Geste>();
		File wd = new File(fileName);
		if (wd.isDirectory()) {
			for (File f:wd.listFiles()) {
				if (!(f.isDirectory())){
					add(new Geste(f.getAbsolutePath()));
					repaint();
				}
			}
		}else {
			add(new Geste(fileName));
			repaint();
		}
	}

	public void exportData(String fileName) {
		boolean createFile = new File(fileName).isDirectory();
		int i = 0;
		if (createFile) {
			for (Geste g:gestes) {
					g.exportWhenPossible(fileName+File.separator+Parameters.baseGestureFileName+"-"+i+".csv");
					i++;
			}
		} else if (gestes.size() == 1){
			gestes.get(0).exportWhenPossible(fileName);
		} 
	}
	
	public void clear() {
		gestes.clear();
		repaint();
	}
	
	public void resample() {
		MainWindow frame = new MainWindow("Resampling");
		for (int i = 0; i < gestes.size(); i++) {
			frame.addGesture(gestes.get(i).oResample());
		}
		frame.setVisible(true);
	}
	
	// TODO 
	public void rotateToZero() {
		MainWindow frame = new MainWindow("rotateTozero");
		for (int i = 0; i < gestes.size(); i++) {
			double indicativeAngle=gestes.get(i).indicativeAngle();
			frame.addGesture(gestes.get(i).RotateBy(indicativeAngle));
		}
		frame.setVisible(true);
	}

	public void rescale() {
		// TODO Auto-generated method stub	
		MainWindow frame = new MainWindow("rescale");
		for (int i = 0; i < gestes.size(); i++) {
			frame.addGesture(gestes.get(i).OnRescale());
		}
		frame.setVisible(true);
	}
	public void recognize() {
		// TODO Auto-generated method stub	
		MainWindow frame = new MainWindow("recognize");
		Geste recognised=new Geste();
		recognised=gestes.get(gestes.size()-1).Recognize(gestes);
			frame.addGesture(recognised);
		
		frame.setVisible(true);
	}

	public void recenter() {
		MainWindow frame = new MainWindow("recenter");
		PointVisible p0=new PointVisible(0,0);
		for (int i = 0; i < gestes.size(); i++) {
			frame.addGesture(gestes.get(i).translateTo(p0));
		}
		frame.setVisible(true);	
	}
	public void middle() {
		
		//frame.addGesture(gestes.get(gestes.size()-1));
		
		MainWindow frame = new MainWindow("middle");
		PointVisible p0=new PointVisible(width/2,height/2);
		for (int i = 0; i < gestes.size(); i++) {
			frame.addGesture(gestes.get(i).translateTo(p0));
		}
		//System.out.println("nombre de template = "+gestes.size());
		frame.setVisible(true);	
	}
	

}
