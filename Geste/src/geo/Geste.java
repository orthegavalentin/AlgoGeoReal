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
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import ui.Style;
import ui.config.Parameters;
import ui.io.ReadWritePoint;

public class Geste {
	private ArrayList<PointVisible> points ;
	private Style style = new Style();
	public float score;

	public Geste() {
		points = new ArrayList<PointVisible>();
		score=0;
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
			newPoints.add(this.points.get(0));
			float D=0;
			
			for(int i=1;i<this.points.size();i++) {
				
				float d=Distance(this.points.get(i-1),this.points.get(i));
				PointVisible q=new PointVisible(0,0);
				if((D+d)>=l) {
					
					q.x=(int) (this.points.get(i-1).x+((l-D)/d)*(this.points.get(i).x-this.points.get(i-1).x));
					q.y=(int) (this.points.get(i-1).y+((l-D)/d)*(this.points.get(i).y-this.points.get(i-1).y));
					
					newPoints.add(q);
					this.points.add(i,q);
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
			for(PointVisible point:this.points) {
				xx+=point.x;
				yy+=point.y;
				
			}
			p0.x=xx/this.points.size();
			p0.y=yy/this.points.size();
			return p0;
			
			
		}
		
		public double indicativeAngle() {
			
			PointVisible centroid=new PointVisible(0,0);
			centroid=this.Centroid();
			
			double angle=Math.atan2(centroid.y-this.points.get(0).y, centroid.x-this.points.get(0).x);
			
			System.out.println("angle="+angle);
			return angle;
		}
		
		
		public Geste RotateBy(double w) {
			PointVisible centroid=new PointVisible(0,0);
			//w=this.indicativeAngle();
			System.out.println((int)this.indicativeAngle());
			centroid=this.Centroid();
			ArrayList<PointVisible> newPoints =new ArrayList<PointVisible>();
			
			for(PointVisible p:this.points) {
				
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
			for(PointVisible point:this.points) {
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
			for(PointVisible point:this.points) {
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
		
		
		
		
		public float PathDistance(Geste B) {
			
			float d=0;
			
			
			for(int i=0;i<this.points.size();i++) {
				if(i<B.points.size())
				d+=Distance(this.points.get(i),B.points.get(i));
				
				
				
			}
			
			
			
			return d/this.points.size();
		
		}
		
		
	public float DistanceAtAngle(Geste template, double angle) {
		
		
		Geste newGeste=new Geste();
		
		newGeste=this.RotateBy(angle);
		float d=0;
		
		d=newGeste.PathDistance(template);
		
		return d;
		
		}
	
	
	public float DistanceAtBestAngle(Geste template,double thetaA,double thetaB,double delta) {
		
		float  phi=(float) Math.sqrt((-1+Math.sqrt(5)));
		
		
		float x1=(float) ((phi*thetaA)+((1-phi)*thetaB));
		
		float f1=this.DistanceAtAngle(template, x1);
		
		float x2=(float) (((1-phi)*thetaA)+(phi*thetaB));
		
		float f2=this.DistanceAtAngle(template, x2);
		
		if(Math.abs(thetaB-thetaA)>delta) {
			
			if(f1<f2) {
				
				thetaB=x2;
				x2=x1;
				f2=f1;
				x1=(float) (phi*thetaA+(1-phi)*thetaB);
				f1=this.DistanceAtAngle(template,x1);
				
		
		
			}else {
				thetaA=x1;
				x1=x2;
				f1=f2;
				
				x2=(float) (((1-phi)*thetaA)+(phi*thetaB));
				f2=this.DistanceAtAngle(template,x2);
				
				
				
				
				
				
			}
			
			
		}
		if(f1>f2) {return f2;}
		
		
		return f1;
		
		
	}
	
	
	public Geste Recognize(ArrayList<Geste> templates){
		
		float b=Float.POSITIVE_INFINITY;
		double theta=Math.PI/4;
		double delta=Math.PI/90;
		Geste newT= new Geste();
		int size=250;
		Map<Float,Geste> result = new HashMap<Float,Geste>();
		float score=0;
		
		for(int i =0;i<templates.size()-1;i++) {
			
			
			
			float d=this.DistanceAtBestAngle(templates.get(i),-theta,theta,delta);
			
			if(d<b) {
				
				b=d;
				newT=templates.get(i);
				score= (float) ((1-b)/0.5*(Math.sqrt((size*size)+(size*size))));
				
			}
			
			
			
			
		}
		
	
		newT.score=score;
		
		
		return newT;
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geste other = (Geste) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}
	
	
	
	
		
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

