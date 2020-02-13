package affichage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import algoGeo.Segment;
import test.Experiment;
import test.ReadWrite;


public class Vue extends JPanel {
	int width, height;
	Experiment expe;
	Segment s1,s2;
	// n : le nombre de lignes
	// width, height : largeur, hauteur de la fenetre
	public Vue(int ww, int wh) {
		super();
		setBackground(Couleur.bg);
		this.width = ww;
		this.height = wh;
		setPreferredSize(new Dimension(ww,wh));
		s1=new Segment(new VisiblePoint(10,10),new VisiblePoint(60,60));
		s2=new Segment(new VisiblePoint(80,80),new VisiblePoint(50,20));
	}

	// affiche les résultats d'une expérience
	// une expérience = une liste de tests, chaque test correspond à une figure
	// le résultat affiché correspond au test sélectionné
	// la liste des tests de l'expérience apparait dans le JTree
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.translate(100, 0);
		g2d.scale(0.9, 0.9);
		g2d.setColor(Couleur.fg);
		s1.dessine(g2d);
		s2.dessine(g2d);
		System.out.println("l'intersection est ="+s1.intersectionSegment(s2));
		/*if(expe == null) return;
		expe.drawCurrentSelection(g2d);	*/	
	}


	public void setExperiment(Experiment experiment) {
		this.expe = experiment;
		
	}



}
	

