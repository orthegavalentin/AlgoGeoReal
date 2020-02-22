package algo;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;


import utils.affichage.Vue;
import utils.couleurs.Couleur;
import utils.vecteur.PointVisible;
import utils.vecteur.Vecteur;

public class EnveloppeConvexe implements ActionListener{
	ArrayList<PointVisible> points = new ArrayList<PointVisible>();
	ArrayList<PointVisible> ecpoints = new ArrayList<PointVisible>();
	ArrayList<Vecteur> ecvectors = new ArrayList<Vecteur>();
	Set<PointVisible> jarvisPoints=new HashSet<>();
	ArrayList<Vecteur> nonecvectors = new ArrayList<Vecteur>();
	Vue vue;
	Timer timer;
	boolean finished;
	int step=-1;
	boolean dejaAjoute[];
	
	public EnveloppeConvexe(ArrayList<PointVisible> v){
		points = v;
		timer = new Timer(3000, this);
	}

	private void init() {
		step = -1;
		finished=false;
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
	
	
	
	private void  Jarvis() {
		nonecvectors.clear();
		int i = step;
		int n = points.size();
		PointVisible cardinal= new PointVisible((int)Double.POSITIVE_INFINITY,(int)Double.POSITIVE_INFINITY);
		
		for(int j=0;j<points.size();j++) {
			
			if(points.get(j).x<cardinal.x) {
				
				cardinal=points.get(j);
				dejaAjoute[j]=true;
				
			}
			
			
		}
		PointVisible current=cardinal;
		ecpoints.add(cardinal);
		jarvisPoints.add(cardinal);
		ArrayList<PointVisible>  collinearpoints=new ArrayList<>();
		
		while(true) {
			
		PointVisible nextTarget=points.get(0);
		for(int t=1;t<points.size();t++) {
			
			if(points.get(t).x==current.x&&points.get(t).y==current.y) {continue;}
				
				int val=orientation(current,nextTarget,points.get(t));
				
				if(val>0) {
					nextTarget=points.get(t);
					
					}else if(val ==0) {
						/*if val is 0 then current,next target and points[t] are collinear 
						 * if they are collnear then pick the further one but add the closer one to list of collinear
						 * points
						 *
						 * */
						collinearpoints.clear();
						
						if (distance(current,nextTarget,points.get(t))<0){
							
							collinearpoints.add(nextTarget);
							nextTarget=points.get(t);
								
						}else {
							//point[t] is closer then next target remains the same
							collinearpoints.add(points.get(t));
						}
						
						
						
					
					
					
				}
			//else if val <0 then nothin to do since points[i] is on the right side of current->nexttarget
				
				
			
			
		}
		for(PointVisible p:collinearpoints) {
			ecpoints.add(p);
			jarvisPoints.add(p);
			ecvectors.add(new Vecteur(current, p));
			
		}
		if(nextTarget.x==cardinal.x&&nextTarget.y==cardinal.y) {
			ecvectors.add(new Vecteur(current, nextTarget));
			System.out.println("merde");
			finished=true;
			break;
		}
		ecpoints.add(nextTarget);
		jarvisPoints.add(nextTarget);
		ecvectors.add(new Vecteur(current, nextTarget));
		current=nextTarget;
		
			
			
		}
		
		
		
		
		
	}
	public Set<PointVisible> getecpoints(){
		return this.jarvisPoints;
		
	}
	
	  
private int distance(PointVisible a, PointVisible b, PointVisible c) {
		int y1=a.y-b.y;
		int y2=a.y-c.y;
		int x1=a.x-b.x;
		int x2=a.x-c.x;
		return Integer.compare(y1*y1+x1*x1,y2*y2+x2*x2);
	}

public int orientation(PointVisible A,PointVisible B,PointVisible C){
		
		
		
		
		double ABx1= B.x-A.x;
		double ABy1= B.y-A.y;
		double ACx2= C.x-A.x;
		double ACy2= C.y-A.y;
		
		double det=(ABx1*ACy2)-(ABy1*ACx2);
		//left
		if(det>0) {return -1;}
		
		//right
		if(det<0) {return 1;}
		
		
		return 0;
		
		
		
		
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
		if (++step >= points.size()||finished==true) {
			
			stopAnimation();
		}else {
			System.out.println("Painting step "+step);
			Jarvis();
			//nextStep();
			vue.repaint();
		}
	}

	public void stopAnimation() {
		timer.stop();
	}
	}

