package ui;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import ui.config.Parameters;

public class UsefulTools extends JToolBar {
	JFileChooser jfc;
	JFrame toplevel;
	Vue vue;
	File importFile, exportFile;

	public UsefulTools(JFrame f, Vue v1) {
		vue = v1;
		toplevel = f;
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Parameters.defaultDirectory));
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		JButton jb = new JButton(new AbstractAction("Clear") {
			public void actionPerformed(ActionEvent e) {
				vue.clear();
			}
		});
		this.add(jb);

		jb = new JButton(new AbstractAction("Resample") {
			public void actionPerformed(ActionEvent e) {
				vue.resample();
			}
		});
		this.add(jb);
		
		
		jb = new JButton(new AbstractAction("RotateToZero") {
			public void actionPerformed(ActionEvent e) {
				vue.rotateToZero();
			}
		});
		this.add(jb);
		
		jb = new JButton(new AbstractAction("Rescale") {
			public void actionPerformed(ActionEvent e) {
				vue.rescale();
			}
		});
		this.add(jb);
		
		
		
		jb = new JButton(new AbstractAction("Recenter") {
			public void actionPerformed(ActionEvent e) {
				vue.recenter();
			}
		});
		this.add(jb);
		
		jb = new JButton(new AbstractAction("Recognize") {
			public void actionPerformed(ActionEvent e) {
				vue.recognize();
			}
		});
		this.add(jb);
		
	
		jb = new JButton(new AbstractAction("middle") {
			public void actionPerformed(ActionEvent e) {
				vue.middle();
			}
		});
		this.add(jb);
		
		jb = new JButton(new AbstractAction("Duration") {
			public void actionPerformed(ActionEvent e) {
				vue.duration();;
			}
		});
		this.add(jb);

		jb = new JButton(new AbstractAction("Importer", new ImageIcon("img/Open16.gif")) {
			public void actionPerformed(ActionEvent e) {
				int result = jfc.showOpenDialog(toplevel);
				if (result == JFileChooser.APPROVE_OPTION) {
					importFile = jfc.getSelectedFile();
					vue.loadData(importFile.getAbsolutePath());
				}
			}
		});
		this.add(jb);

		jb = new JButton(new AbstractAction("Exporter", new ImageIcon("img/Save16.gif")) {
			public void actionPerformed(ActionEvent e) {
				int result = jfc.showOpenDialog(toplevel);
				if (result == JFileChooser.APPROVE_OPTION) {
					exportFile = jfc.getSelectedFile();
					vue.exportData(exportFile.getAbsolutePath());
				}
			}
		});
		this.add(jb);
	}
}
