package test;

import java.awt.Graphics2D;

import javax.swing.tree.DefaultMutableTreeNode;

import algoGeo.Triangle;

public class Test extends DefaultMutableTreeNode{
	private Triangle triangle;
	public Test() {
		super();
	}
	
	public Test(int id, Triangle t) {
		super(id);
		triangle = t;
	}
	
	public void dessine(Graphics2D g) {
		if (triangle != null) triangle.dessine(g);
	}
}
