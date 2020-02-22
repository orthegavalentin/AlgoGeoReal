package utils.affichage;
import javax.swing.*;

import algo.EnveloppeConvexe;
import utils.couleurs.Couleur;
import utils.fileIo.ReadWritePoint;

import utils.vecteur.PointVisible;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class Vue extends JPanel implements MouseWheelListener, MouseListener, ActionListener{
	Color bgColor = Couleur.bg; // la couleur de fond de la fen�tre
	Color fgColor = Couleur.fg; // la couleur des lignes
	int width;
	int numFileDataDir = 0;
	ArrayList<PointVisible> points = new ArrayList<PointVisible>();
	EnveloppeConvexe ec ;
	private boolean animationOn;
	
	// n : le nombre de lignes
	// width, height : largeur, hauteur de la fen�tre
	public Vue(int n, int width) {
		super();
		Box b = Box.createHorizontalBox();
		b.add(createButton("Enregistrer", "b1", this));
		b.add(Box.createHorizontalStrut(10));
		b.add(createButton("Dernier enregistré", "b2", this));
		b.add(Box.createHorizontalStrut(10));
		b.add(createButton("Jeux de test", "b3", this));
		add(b);
		
		setBackground(bgColor);
		this.width = width;
		setPreferredSize(new Dimension(width, width));
		System.out.println("initialisation avec n = "+n);
		initPoints(n, 100 , width);
		addMouseListener(this);
		addMouseWheelListener(this);
	}
	
	private JButton createButton(String titre, String code, ActionListener al) {
		JButton b1 = new JButton(titre);
		b1.setActionCommand(code);
		b1.addActionListener(al);
		return b1;
	}

	// initialisation random
	// NB: l'initialisation dans disque est � faire (exercice 1)
	
	public void initPoints(int n, int r, int width){
			int xp,yp;
			points = new ArrayList<PointVisible>();
			for (int i = 0; i <n; i++){
				xp = random((width/2)-r, (width/2)+r);
				yp = random((width/2)-r, (width/2)+r);
				points.add(new PointVisible(xp,  yp));
				points.get(i).setLabel("Point "+i);
			}
			ec = new EnveloppeConvexe(points);
	}
				
	// m�thode utilitaire 
	// retourne un entier compris entre xmin et xmax
	int random(int xmin,int xmax){
		double dr = Math.random() * (double) (xmax - xmin) + (double) xmin;
		return (int) dr;
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaintMode(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	

		g2d.setColor(fgColor);
		
		if (ec != null) {
			ec.dessine(g2d);
		}
		for (PointVisible p: points) {
			p.dessine(g2d);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int n = points.size();
		if (animationOn) ec.stopAnimation();
		if (e.getButton() == MouseEvent.BUTTON1){
			initPoints(n, 100, width);
			repaint();
		}else {
			ec.startAnimation(this);
			animationOn = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String testFile = "tmp.csv";
		String dataDir = "data/Points";
		ReadWritePoint rw = new ReadWritePoint(testFile);
		if (animationOn) ec.stopAnimation();
	
		if(e.getActionCommand().equals("b1")){
			for (PointVisible s: ec.getecpoints()){
				rw.add(s.x+";" + s.y+";"+s.getLabel());
			}
			try {
				rw.write();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}else if(e.getActionCommand().equals("b2")){
			initFromFile(testFile);
			repaint();
		}else{
			if ((new File(dataDir)).exists()) {
				initFromFile(nextTestFile("data/Points"));
			}
			repaint();
			
		}
	}

	//retrouver le premier fichier de jeux de test (en dehors des répertoires) qui n'a pas été encore lu
	private String nextTestFile(String dirName) {
		File dir = new File(dirName);
		int n = dir.listFiles().length;
		File f;
		do {
			f = (dir.listFiles())[numFileDataDir];
			numFileDataDir = numFileDataDir < n - 1 ? numFileDataDir + 1 : 0;
			System.out.println("jeu de test "+ f.getName());
		}while(f.isDirectory());
		return f.getPath();
	}

	public void initFromFile(String f){
		ReadWritePoint rw = new ReadWritePoint(f);
		try {
			points = rw.read();
			ec = new EnveloppeConvexe(points);

		} catch (IOException e) {
			e.printStackTrace();
		}
}

}
	

