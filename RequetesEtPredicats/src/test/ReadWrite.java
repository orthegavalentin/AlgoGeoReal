package test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import affichage.VisiblePoint;
import algoGeo.Triangle;


/** Assumes UTF-8 encoding. JDK 7+. */
public class ReadWrite {
	private static String testFile = "data/triangles.csv";
	File rf;
	ArrayList<String> textToWrite;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private static final String csvDelimiter = ",";
	private String fileName;

	//orienté lecture - ouvre le fichier précisément demandé
	public ReadWrite(String aFileName) {
		fileName = aFileName;
		textToWrite = new ArrayList<String>();
		rf = new File(aFileName);
//		if (rf.exists()) System.out.println("success opening " + aFileName);
//		else  System.out.println("error opening " + aFileName);
	}
	
	private Triangle triangleReadLine(String aLine, int i) {
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(csvDelimiter);
		String x,y;
		Triangle t = new Triangle();
		
		if (scanner.hasNext()) {
			for (int j = 0; j < 3; j++) {
				x = scanner.next();
				y = scanner.next();
				System.out.println(x+" - "+y);
				t.add(new VisiblePoint(Integer.parseInt(x), Integer.parseInt(y)));
		}}
		scanner.close();
		return t;
	}

	public ArrayList<Triangle> readTriangles() throws IOException {
		if (rf.exists()) {
			ArrayList<Triangle> triangles = new ArrayList<Triangle>();
			try (Scanner scanner = new Scanner(rf, ENCODING.name())) {
				int i = 0;
				while (scanner.hasNextLine()) {
					triangles.add(triangleReadLine(scanner.nextLine(), i++));
				}
			}
//			System.out.println(triangles.size() + " triangles lus");
			return triangles;
		} else {
			return null;
		}
	}	


}
