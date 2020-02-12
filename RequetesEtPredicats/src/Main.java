import javax.swing.JFrame;
import javax.swing.JSplitPane;

import affichage.Vue;
import test.Experiment;

public class Main {
	public static void main(String s[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Geometrie - triangles et cercles");
				String importFile = "data/triangles.csv";
				Vue v = new Vue(800,800);
				Experiment e = new Experiment(importFile,v);
				JSplitPane js = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
				js.add(e);
				js.add(v);
				frame.add(js);
				frame.pack();
				frame.setLocation(200,200);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}			
		});
	}

}
