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
	
	 boolean onSegment(VisiblePoint p, VisiblePoint q,VisiblePoint r) 
	{ 
	    if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && 
	        q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y)) 
	    return true; 
	  
	    return false; 
	} 
	
	
	
	
	public int orientation(VisiblePoint A,VisiblePoint B,VisiblePoint C){
		
		
		
		
		double ABx1= B.x-A.x;
		double ABy1= B.y-A.y;
		double ACx2= C.x-A.x;
		double ACy2= C.y-A.y;
		
		double det=(ABx1*ACy2)-(ABy1*ACx2);
		
		if(det>0) {return -1;}
		if(det<0) {return 1;}
		
		
		return 0;
		
		
		
		
	}
	//etant donne deux segment AB et CD
	//on teste d'abord avec le premier segment ,on passe à l'autre sgment si seulement si le premier est segment a donne des differents
	public boolean intersectionSegment(Segment s2 ){
		
		int o1=orientation(this.sommets[0],this.sommets[1],s2.sommets[0]);
		int o2=orientation(this.sommets[0],this.sommets[1],s2.sommets[1]);
		int o3=orientation(s2.sommets[0],s2.sommets[1],this.sommets[0]);
		int o4=orientation(s2.sommets[0],s2.sommets[1],this.sommets[1]);
		
		if(o1!=o2 && o3!=04) {
			
			return true;
		}
		// Special Cases 
	    // p1, q1 and p2 are colinear and p2 lies on segment p1q1 
	    if (o1 == 0 && onSegment(this.sommets[0],s2.sommets[0],this.sommets[1])) return true; 
	  
	    // p1, q1 and q2 are colinear and q2 lies on segment p1q1 
	    if (o2 == 0 && onSegment(this.sommets[0],s2.sommets[1],this.sommets[1])) return true; 
	  
	    // p2, q2 and p1 are colinear and p1 lies on segment p2q2 
	    if (o3 == 0 && onSegment(s2.sommets[0],this.sommets[0],s2.sommets[1])) return true; 
	  
	    // p2, q2 and q1 are colinear and q1 lies on segment p2q2 
	    if (o4 == 0 && onSegment(s2.sommets[0],this.sommets[1],s2.sommets[1])) return true; 
		//doublex=Math.atan2(y, x)
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
				
		return false ;
		
	}

}
