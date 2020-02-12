package test;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import affichage.Vue;
import algoGeo.Triangle;

public class Experiment extends JPanel implements TreeSelectionListener {
	Vue vue;
	private ArrayList<Test> tests;
	private Test currentSelection;
	private JTree jt;

	public Experiment(String importFile, Vue v) {
		ReadWrite rw = new ReadWrite(importFile);
		try {
			ArrayList<Triangle> triangles = rw.readTriangles();
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Experiments");
			int i = 0;
			for (Triangle t : triangles) {
				root.add(new Test(i++, t));
			}
			jt = new JTree(root);
			jt.setRootVisible(false);
			jt.addTreeSelectionListener(this);
			setLayout(new BorderLayout());
			add(jt);
			currentSelection = (Test) root.getChildAt(0);
			vue = v;
			v.setExperiment(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawCurrentSelection(Graphics2D g2d) {
		currentSelection.dessine(g2d);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath tps[] = jt.getSelectionPaths();
		for (int i = 0; i < tps.length; i++) {
			currentSelection = (Test) (tps[i].getLastPathComponent());
		}
		vue.repaint();
	}

}
