package utils.vecteur;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Vecteur {
	private PointVisible from;
	private PointVisible to;
	private String label;

	public Vecteur(PointVisible f, PointVisible t) {
		setFrom(f);
		to = t;
	}

	public Vecteur(int x, int y, int x2, int y2) {
		from = new PointVisible(x,y);
		to = new PointVisible(x2,y2);		
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toString(){
		return this.getFrom().x +" - "+ to.x;
	}
		
	public void drawLabel(Graphics2D g){
		FontMetrics fm = g.getFontMetrics();
		if (label != null) g.drawString(label, (int) (getFrom().x + fm.stringWidth("_")), (int) (getFrom().y ));
	}
	
	public void dessine(Graphics2D g, Color c) {
		g.setColor(c);
		g.drawLine((int) getFrom().x , (int) getFrom().y , (int) to.x, (int) to.y);
		drawLabel(g);
		getFrom().drawLabel(g);
		to.drawLabel(g);
	}

	public int getX(){
		return to.x - getFrom().x;
	}
	
	public int getY(){
		return to.y - getFrom().y;
	}
	public PointVisible getFrom() {
		return from;
	}

	public void setFrom(PointVisible from) {
		this.from = from;
	}
}
