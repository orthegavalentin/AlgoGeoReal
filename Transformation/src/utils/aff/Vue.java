package utils.aff;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Matrice;
import utils.go.PointVisible;
import utils.go.Vecteur;
import utils.io.ReadWritePoint;


public class Vue extends JPanel implements MouseListener, MouseMotionListener{
	Color bgColor;
	Color fgColor; 
	int width, height;
	private ArrayList<PointVisible> points = new ArrayList<PointVisible>();
	private ArrayList<Vecteur> aretes = new ArrayList<Vecteur>();
	Point initialLocation, previousLocation, newLocation;
	Rectangle rectangleElastique;
	PointVisible p,p1;
	Matrice transformation;
	Vecteur v1;
		
	public Vue(int width, int height, String fileName, boolean modelCoordinates) {
		super();
		Couleur.forPrinter(true);
		this.bgColor = Couleur.bg; 
		this.fgColor = Couleur.fg; 
		this.width = width;
		this.height = height;	
		this.setBackground(Couleur.bg);
		this.setPreferredSize(new Dimension(width,height));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		initFromLog(fileName, modelCoordinates); 
		if(!modelCoordinates)export("trio-hypo-2.csv");
		p=new PointVisible(50,150);
		p1=new PointVisible(100,150);
		v1=new Vecteur(p,p1);
		transformation=new Matrice(0.0,width,0.0,height);
		this.setBackground(Color.cyan);
	}

	
	
	private void copyModelToViewportCoords() {
		for(PointVisible p: points) {
			p.copyModelToViewportCoords();
		}
	}
	
	private void initFromLog(String fileName, boolean modelCoordinates) {
		ReadWritePoint rw = new ReadWritePoint(fileName);
		points = rw.read();
		aretes = new ArrayList<Vecteur>();
		int n = points.size();
		for (int i = 0 ; i < n; i++) {
			aretes.add(new Vecteur(points.get(i), points.get((i+1)%n)));
		}

	}
	
	public void export(String logFile) {
		ReadWritePoint rw = new ReadWritePoint(logFile);
		for (PointVisible p: points){
			rw.add((int)p.getMC().x+";"+(int)p.getMC().y+";"+p.toString());
		}
		rw.write();
	}
	
	public void setPoints(ArrayList<PointVisible> points) {
		this.points = points;
	}	
			
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.setColor(fgColor);
		
		//g2d.drawOval(10, y, width, height);
		//p.dessine(g2d);
		
		v1.dessine(g2d);
		transformation.ViewPortToModel(0, 0,width,height);
		
		Vecteur v2= new Vecteur(0,0,0,0);
		Vecteur v3= new Vecteur(0,0,0,0);
		v2=transformation.MultiplyMatrixandVector(v1);
		//v2.setTo(v1.getTo());
		//v2.setFrom(v1.getFrom());
		
		v2.dessine(g2d);
		v3=v2.Rotate(Math.PI/3);
		v3.dessine(g2d);
		
		
		
		if (rectangleElastique != null) g2d.draw(rectangleElastique);
		
		for (Vecteur v: aretes) {
			v.dessine(g2d);
		}		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		initialLocation = new Point(e.getX(), e.getY());
		rectangleElastique = new Rectangle (e.getX(), e.getY(), 0, 0);
		previousLocation = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		updateElasticRectangle(e.getX(), e.getY());
		previousLocation = null;
		initialLocation = null;
	}

	private void updateElasticRectangle(int newX, int newY) {
		int w = newX - initialLocation.x;
		int h = newY - initialLocation.y;
		previousLocation.x = newX;
		previousLocation.y = newY;		
		
		rectangleElastique.width = (w >=0)? w: -w;
		rectangleElastique.height = (h >=0)? h: -h;
		
		if (h < 0) {
			rectangleElastique.y = initialLocation.y +h;
		}

		if (w < 0) {
			rectangleElastique.x = initialLocation.x +w;
		}
		
		repaint();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if (previousLocation != null) {
			updateElasticRectangle(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
	

