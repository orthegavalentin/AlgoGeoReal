package utils.vecteur;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Comparator;

import utils.couleurs.Couleur;


public class PointVisible extends Point implements Comparator<PointVisible>{
	public static int midWidth = 5;
	private Color color = Couleur.nw;
	private String label;
	private Rectangle shape;
	
	public void setLabel(String label) {
		this.label = label;
	}

	public PointVisible(int x, int y) {
		super(x , y);
		shape = new Rectangle(x-midWidth, y -midWidth, 2 * PointVisible.midWidth,2 * PointVisible.midWidth);
	}

	public void dessine(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(this.shape);	
		//drawLabel(g2d);
	}

	@Override
	public int compare(PointVisible p1, PointVisible p2) {
		return p2.x - p1.x;
	}	

	public void print() {
		System.out.println("x = " + x + " y = " + y );
	}

	public void drawLabel(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		int centeredText = (int) (x - fm.stringWidth(getLabel())/2 + fm.stringWidth("_"));
		g.drawString(getLabel(), centeredText, (int) (y-2));
	}

	public String getLabel() {
		return label;
	}

}

