package algo;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import utils.affichage.Vue;
import utils.couleurs.Couleur;
import utils.vecteur.PointVisible;
import utils.vecteur.Vecteur;

public class EnveloppeConvexe implements ActionListener{
	ArrayList<PointVisible> points = new ArrayList<PointVisible>();
	ArrayList<PointVisible> ecpoints = new ArrayList<PointVisible>();
	ArrayList<Vecteur> ecvectors = new ArrayList<Vecteur>();
	ArrayList<Vecteur> nonecvectors = new ArrayList<Vecteur>();
	Vue vue;
	Timer timer;
	int step=-1;
	boolean dejaAjoute[];
	
	public EnveloppeConvexe(ArrayList<PointVisible> v){
		points = v;
		timer = new Timer(3000, this);
	}

	private void init() {
		step = -1;
		ecpoints.clear();
		ecvectors.clear();
		int n = points.size();
		dejaAjoute = new boolean[n];
		for (int i = 0; i < n; i++) {
			dejaAjoute[i] = false;
		}
	}
	
	private void nextStep() {
		nonecvectors.clear();
		int i = step;
		int n = points.size();
		for (int j = i + 1; j < n; j++) {
			System.out.println("Vecteur ("+step+","+j+")");
			if (inEC(i, j)) {
				if (!dejaAjoute[i])
					ecpoints.add(points.get(i));
				if (!dejaAjoute[j])
					ecpoints.add(points.get(j));
				dejaAjoute[i] = dejaAjoute[j] = true;
				ecvectors.add(new Vecteur(points.get(i), points.get(j)));
			}else {
				nonecvectors.add(new Vecteur(points.get(i), points.get(j)));
			}
		}
	}

	private boolean inEC(int i, int j) {
		int d, sign = 0, k = 0, n = points.size();
		boolean memecote = true;
		PointVisible pi = points.get(i), pj = points.get(j), pk;
		while (k < n && memecote ) {
			if (i != k && j != k) {
				pk = points.get(k);
				d = cote(pi,pj,pi,pk);
				if (d != 0) {
					if (sign == 0) { //first test
						sign = d;
					}else{
						memecote = ((sign * d) >= 0);
					}
				}
			}
			k++;
		}
		return memecote;
	}

	private int cote(PointVisible a, PointVisible b, PointVisible c, PointVisible d) {
		int x1 = b.x - a.x, y1 = b.y - a.y, x2 = d.x - c.x, y2 = d.y - c.y;
		int de = x1 * y2 - y1 * x2;
		if (de > 0) return 1;
		if (de < 0) return -1;
		return 0;
	}

	public void dessine(Graphics2D g2d) {
		for (Vecteur v : ecvectors) {
			v.dessine(g2d,Couleur.ec);
		}
		for (Vecteur v : nonecvectors) {
			v.dessine(g2d,Couleur.nonec);
		}
		String msg = "Step "+ step;
		g2d.setColor(Couleur.fg);
		g2d.drawString(msg, 40, 25);
	}

	public void startAnimation(Vue vue) {
		init();
		vue.repaint();
		this.vue = vue;
		timer.start();	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (++step >= points.size()) {
			stopAnimation();
		}else {
			System.out.println("Painting step "+step);
			nextStep();
			vue.repaint();
		}
	}

	public void stopAnimation() {
		timer.stop();
	}
	}

