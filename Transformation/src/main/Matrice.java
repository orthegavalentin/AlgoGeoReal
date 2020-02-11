package main;

import java.awt.geom.AffineTransform;

import utils.go.PointVisible;
import utils.go.Vecteur;

public class Matrice extends AffineTransform{
	
	 double minX,maxX,minY,maxY;



	public Matrice (double minX,double maxX,double minY,double maxY,double x00,
	double x01,double x02,double x10,double x11,double x12){

	super(x00,x01,x02,x10,x11,x12);
	this.minX=minX;
	this.minY=minY;
	this.maxX=maxX;
	this.maxY=maxY;
	




	}
	public Matrice (double minX,double maxX,double minY,double maxY){

			super();
			this.minX=minX;
			this.minY=minY;
			this.maxX=maxX;
			this.maxY=maxY;
			




			}

	public void ModelToViewPort(double x0,double y0,double Width,double Height){

	double Aa =Width/(maxX-minX);
	double Ba =(x0-Width*minX)/(maxX-minX);
	double Ca =Height/(minY-maxY);
	double Da =(y0-Height*maxY)/(minY-maxY);
	System.out.println("da ="+Ba);

	this.setToIdentity();
	this.translate(Ba, Da);
	this.scale(Aa, Ca);



	}

	public void ViewPortToModel(double x0,double y0,double Width,double Height){

	double Ab =(maxX-minX)/Width;
	double Bb =minX-(x0*(maxX-minX))/Width;
	double Cb =(minY-maxY)/Height;
	double Db =maxY-(y0*(minY-maxY))/Height;

	this.setToIdentity();
	this.translate(Bb, Db);
	this.scale(Ab, Cb);





	}



	public PointVisible MultiplyMatrixandPoint(PointVisible p1){

	PointVisible p=new PointVisible(0,0);

	double[] matrix=new double[6];

	this.getMatrix(matrix);
	for(double x:matrix) {
		System.out.println("element ="+x);
	}
	p.x= (int) (p1.x*matrix[0]+matrix[4]);
	p.y= (int) (p1.y*matrix[3]+matrix[5]);


	


	return p;





	}
	public Vecteur MultiplyMatrixandVector(Vecteur v1){

		PointVisible to=new PointVisible(0,0);
		PointVisible from=new PointVisible(0,0);
		

		double[] matrix=new double[6];

		this.getMatrix(matrix);
		for(double x:matrix) {
			System.out.println("element ="+x);
		}
		to.x= (int) (v1.getTo().x*matrix[0]+matrix[4]);
		to.y= (int) (v1.getTo().y*matrix[3]+matrix[5]);
		from.x= (int) (v1.getFrom().x*matrix[0]+matrix[4]);
		from.y= (int) (v1.getFrom().y*matrix[3]+matrix[5]);

		Vecteur v=new Vecteur(from,to);
		


		return v;





		}















	}



