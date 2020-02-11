package geo;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ui.Style;
import ui.config.Parameters;
import ui.io.ReadWritePoint;

public class Geste {
	private ArrayList<PointVisible> points ;
	private Style style = new Style();

	public Geste() {
		points = new ArrayList<PointVisible>();
	}
	
	public Geste(String fileName) {
		this();
		ReadWritePoint rwp = new ReadWritePoint(new File(fileName));
		points = rwp.read();
	}
	
	public Geste(Style style2) {
		this();
		style = style2;
	}

	public void add(Point p) {
		add(new PointVisible(p.x, p.y));	
	}
	
	public void add(PointVisible p) {
		points.add(p);	
	}
	
	public void drawPoints(Graphics2D g) {
		for (PointVisible p: points) {
			p.dessine(g, style);
		}
	}

	public void drawFeatures(Graphics2D g) {
		Rectangle r = computeBoundingBox();
		String features = points.size() + " points,  length ~> "+ Math.round(computeLength());
		g.translate(-r.x, -r.y);
		g.scale(2, 2);
		g.drawString(features,r.x, r.y - 10);
		g.scale(.5, .5);
		g.translate(r.x, r.y);
	}
	
	public void draw(Graphics2D g) {
		if (style.drawLine()) {
			drawLines(g);
		}
		if (style.drawPoints()) {
			drawPoints(g);
		}
		drawFeatures(g);	
	}

	private void drawLines(Graphics2D g) {
		PointVisible p1, p2;
		for (int i = 0; i < points.size()-1;i++) {
			p1 = points.get(i);
			p2 = points.get(i+1);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	
	public Rectangle computeBoundingBox() {
		int minx, miny,maxx, maxy;
		minx = points.get(0).x;
		maxx = points.get(0).x;
		miny = points.get(0).y;
		maxy = points.get(0).y;
		for (PointVisible p: points) {
			if(p.x < minx) minx = p.x;
			if(p.y < miny) miny = p.y;
			if(p.x > maxx) maxx = p.x;
			if(p.y > maxy) maxy = p.y;
		}
		return new Rectangle(minx,miny,maxx-minx,maxy-miny);
	}
	
	public Geste rotate(double angle) {
		Geste rotatedGeste = new Geste();
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		double x , y ;
		for (PointVisible p: points) {
			x = (double) p.x;
			y = (double) p.y;
			x = cos * x - sin * y;
			y = sin * x + cos * y;
			rotatedGeste.add(new PointVisible((int)x,(int)y));
		}
		return rotatedGeste;
	}
	
	public void exportWhenPossible(String filePath) {
		Path p = Paths.get(filePath);
		if (Files.exists(p)) {
			JOptionPane.showMessageDialog(null, "Error:"+p.getFileName()+", file exists, no overwrite, choose an empty directory");
		}else {
			export(filePath);
		}
	}
	
	private void export(String path) {
		ReadWritePoint rw = new ReadWritePoint(new File(path));
		for (PointVisible p: points){
			rw.add((int)p.x+";"+(int)p.y+";"+p.toString());
		}
		rw.write();
	}
	
	//Todo	
	public float Distance(PointVisible a,PointVisible b) {
		
		return (float) Math.sqrt(Math.pow((b.x-a.x), 2)+Math.pow((b.y-a.y), 2));
		
	}
		//Calculer la longueur du geste (de la polyligne du tracé)
		private float computeLength() {
			float d=0;
			for(int i=1;i<points.size();i++) {
				
				d+=Distance(points.get(i-1),points.get(i));
				
				
			}
			return d;
		}

		//Creer un geste ré-échantillonné au nombre de points utiles pour l'algo OneDollarRecognizer
		public Geste oResample() {
			int k = Parameters.OneDollarSampleSize;
			
			float l=this.computeLength()/(k-1);
			ArrayList<PointVisible> newPoints =new ArrayList<PointVisible>();
			newPoints.add(points.get(0));
			float D=0;
			
			for(int i=1;i<points.size();i++) {
				
				float d=Distance(points.get(i-1),points.get(i));
				PointVisible q=new PointVisible(0,0);
				if((D+d)>=l) {
					
					q.x=(int) (points.get(i-1).x+((l-D)/d)*(points.get(i).x-points.get(i-1).x));
					q.y=(int) (points.get(i-1).y+((l-D)/d)*(points.get(i).y-points.get(i-1).y));
					
					newPoints.add(q);
					points.add(i,q);
					D=0;
				}else {
					
					D+=d;
				}
				
				
				
			}
			this.points=newPoints;
			
			return this; // à modifier...
		}
		
		
		public PointVisible Centroid() {
			PointVisible p0=new PointVisible(0,0);
			int xx=0;
			int yy =0;
			for(PointVisible point:points) {
				xx+=point.x;
				yy+=point.y;
				
			}
			p0.x=xx/points.size();
			p0.y=yy/points.size();
			return p0;
			
			
		}
		
		public double indicativeAngle() {
			
			PointVisible centroid=new PointVisible(0,0);
			centroid=this.Centroid();
			
			
			return Math.atan2(centroid.y-points.get(0).y, centroid.x-points.get(0).x);
		}
		
		
		public Geste RotateBy() {
			PointVisible centroid=new PointVisible(0,0);
			double w=this.indicativeAngle();
			System.out.println((int)this.indicativeAngle());
			centroid=this.Centroid();
			ArrayList<PointVisible> newPoints =new ArrayList<PointVisible>();
			
			for(PointVisible p:points) {
				
				PointVisible q=new PointVisible(0,0);
			q.x=(int)(((p.x-centroid.x)*Math.cos(-w))-((p.y-centroid.y)*Math.sin(-w))+centroid.x);
			q.y=(int)(((p.x-centroid.x)*Math.sin(-w))+((p.y-centroid.y)*Math.cos(-w))+centroid.y);
			newPoints.add(q);
			
			
			
			}
			this.points=newPoints;
			
			return this;
			
		}
		//Calculer le centre de gravité et le sauvegarder
		public Point2D.Double updateGravity() {			
			return new Point2D.Double(); // à modifier...
		}
		
		//Creer un geste avec une orientation à alpha
		public Geste oRotateTo(double alpha) {
			return this; // à modifier...
		}
		
		//Creer un geste redimensionné 
		public Geste OnRescale() {
			int size=250;
			ArrayList<PointVisible> newPoints =new ArrayList<PointVisible>();
			
			Rectangle B=this.computeBoundingBox();
			for(PointVisible point:points) {
				PointVisible q=new PointVisible(0,0);
				q.x=point.x*size/B.width;
				q.y=point.y*size/B.height;
				newPoints.add(q);
				
				
				
				
				
				
			}
			this.points=newPoints;
			return this; // à modifier...
		}
		
		public Geste translateTo(PointVisible p0) {
			PointVisible centroid=new PointVisible(0,0);
			ArrayList<PointVisible> newPoints =new ArrayList<PointVisible>();
			centroid=this.Centroid();
			for(PointVisible point:points) {
				PointVisible q=new PointVisible(0,0);
				q.x=point.x+p0.x-centroid.x;
				q.y=point.y+p0.y-centroid.y;
				newPoints.add(q);
			}
			this.points=newPoints;
			return this;
			
			
		}
		
		//Creer un geste recentré sur le centre de gravité 
		public Geste oRecenter() {
			return this; // à modifier...
		}
}
