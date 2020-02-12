package algoGeo;

import java.awt.Graphics2D;

import affichage.VisiblePoint;

public class Segment {
	
	VisiblePoint sommets[];
	public Segment() {
		sommets = new VisiblePoint[2];
	}
	public Segment(VisiblePoint p1, VisiblePoint p2) {
		sommets = new VisiblePoint[2];
		sommets[0] = p1;
		sommets[1] = p2;
		
		
	}
	public void dessine(Graphics2D g) {
		g.drawLine((int) sommets[0].x , (int) sommets[0].y , (int) sommets[1].x, (int) sommets[1].y);
		
		for (int i = 0; i<sommets.length; i++)
			sommets[i].dessine(g);
	}
	public boolean orientation(){
		
		/*double x1= 
		double y1=
		double x2=
		double y2=*/
		
		
		
		
		return false;
		
		
		
		
	}
	//etant donne deux segment AB et CD
	//on teste d'abord avec le premier segment ,on passe à l'autre sgment si seulement si le premier est segment a donne des differents
	public void intersectionSegment(Segment s1,Segment s2 ){
		
		double ABx= s1.sommets[1].x-s1.sommets[0].x;
		double ABy= s1.sommets[1].y-s1.sommets[0].y;
		double ACx= s2.sommets[0].x-s1.sommets[0].x;
		double ACy= s2.sommets[0].y-s1.sommets[0].y;
		double ADx= s2.sommets[1].x-s1.sommets[0].x;
		double ADy= s2.sommets[1].y-s1.sommets[0].y;
		double CDx= s2.sommets[1].x-s2.sommets[0].x;
		double CDy= s2.sommets[1].y-s2.sommets[0].y;
		double CAx= s1.sommets[0].x-s2.sommets[0].x;
		double CAy= s1.sommets[0].y-s2.sommets[0].y;
		double CBx= s1.sommets[1].x-s2.sommets[0].x;
		double CBy= s1.sommets[1].y-s2.sommets[0].y;
		doublex=Math.atan2(y, x)
		//AB,AC
		//Segment vectorAB=new Segment(new VisiblePoint(ABx,ABy),)
		
		/*si orientation de AB == orientation de AC alors pas d'intersection
				sinon (si orintation de AB == orientation de AD alors pas d'intersection
				sinon on passe a l'autre segment et on fait la meme chose 
				si  orientation de CD == orientation de CB alors  pas d'intersection
				sinon(si orientation de CD == orientation de CA alors pas d'intersection 
				sinon alors les deux segments intersectent ))
				
				cas particulier 1: cas ou les quatres points des deux segments sont alignés(leurs orientations sont ni gauche ni droite)
				1)trier les coordonnes(absicees ou ordonnees) par l'ordre croissant
				2)tester si il y a alternance de coordonnéé  de deux segment alors intersection sion pas d'intersection
				cas particulier 2: cas ou les 3 points sont alignés et pas 4 points alignes
				1)trier les absicees ou ordonnes de deux segments(2 points du premier et 1 de l'autre) pars ordre croissant 
						comment les savoir voir celles qui partagnet les memes y;
				2)tester si alternance parmis les trois coordonnes si il n y a pas d'alternance alors pas d'intersection
				
				(alternance veux dire que le point de su dsegment 1 est parmis lesd deusx autres points)*/
				
		return false;
		
	}

}
