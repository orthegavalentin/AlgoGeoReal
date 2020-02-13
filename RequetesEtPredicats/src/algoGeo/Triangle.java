  package algoGeo;

import java.awt.Graphics2D;

import affichage.VisiblePoint;


public class Triangle {
	VisiblePoint sommets[];
	String label;
	
	public Triangle() {
		sommets = new VisiblePoint[3];
	}

	public Triangle(VisiblePoint p1, VisiblePoint p2, VisiblePoint p3, String l) {
		sommets = new VisiblePoint[3];
		sommets[0] = p1;
		sommets[1] = p2;
		sommets[2] = p3;
		label = l;
	}
	
	public void add(VisiblePoint p) {
		int j= 0;
		for (int i = 0; i<sommets.length; i++)
			if (sommets[i]!= null) j++;
		if (j< 3) sommets[j] = p;
	}
	
	public void dessine(Graphics2D g) {
		/*if(this.Orientation()==1) {System.out.println(" sens anti horaire");}
		if(this.Orientation()==-1) {System.out.println(" sens  horaire");}
		if(this.Orientation()==0) {System.out.println(" collineaire");}*/
		
		g.drawLine((int) sommets[0].x , (int) sommets[0].y , (int) sommets[1].x, (int) sommets[1].y);
		g.drawLine((int) sommets[0].x , (int) sommets[0].y , (int) sommets[2].x, (int) sommets[2].y);
		g.drawLine((int) sommets[2].x , (int) sommets[2].y , (int) sommets[1].x, (int) sommets[1].y);
		for (int i = 0; i<sommets.length; i++)
			sommets[i].dessine(g);
	}
	
	public int Orientation() {
		
		double x1 = sommets[1].x - sommets[0].x;
	double y1=sommets[1].y-sommets[0].y;
double x2 = sommets[2].x -sommets[0].x ;
double y2 = sommets[2].y- sommets[0].y;
		double de = x1 * y2 - y1 * x2;
		if (de > 0) return 1;
		if (de < 0) return -1;
		return 0;
	}
}

