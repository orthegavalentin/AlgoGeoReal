package affichage;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class VisiblePoint extends Point2D.Double{
	String label;
	Ellipse2D.Double shape;
	public static int midSize=3;
	int position;
	
	public VisiblePoint(double x, double y) {
		super(x,y);
		shape = new Ellipse2D.Double(x-midSize,y-midSize,2 * midSize, 2 * midSize);
	}

	
	public void dessine(Graphics2D g2d) {
		g2d.fill(this.shape);
		drawLabel(g2d);
	}
	
	// convention pour éviter superposition des labels les sommets A, B, C, D se positionnent différemment par rapport à x,y
	// 0 1
	// 2 3
	public void drawLabel(Graphics2D g){
		if(label != null) {
			FontMetrics fm = g.getFontMetrics();
			int w = fm.stringWidth(label+"m");
			int h = fm.getHeight();
			int l = fm.stringWidth("m");
			int lx, ly;
			switch (position ) {
			case 0:
				lx = (int) x - w ;
				ly = (int) y ;
				break;
			case 1:
				lx = (int) x + l;
				ly = (int) y ;
				break;
			case 2:
				lx = (int) x - w ;
				ly = (int) y + h;
				break;
			case 3:
				lx = (int) x + l;
				ly = (int) y + h;
				break;
			default:
				lx = (int) x ;
				ly = (int) y ;
				break;		
			}
			g.drawString(label, lx, ly);
		}
	}

	public VisiblePoint copy() {
		return new VisiblePoint(x,y);
	}

	// convention pour éviter superposition des labels les sommets A, B, C, D se positionnent différemment par rapport à x,y
	// 0 1
	// 2 3
	public void setLabel(String string) {
		label = string;
		if (label.equals("A")) position = 0;
		if (label.equals("B")) position = 1;
		if (label.equals("C")) position = 2;
		if (label.equals("D")) position = 3;
		label = (label == null) ? "("+x +","+y+")": label + " ("+x +","+y+")";
	}

	public String print() {
		return "("+x+","+y+")";
	}
	public String csvprint() {
		return x+";"+y;
	}

	public boolean hsc(VisiblePoint p1) { // has same coords
		if (x == p1.x && y == p1.y) {
			return true;
		}
		return false;
	}
	
}
