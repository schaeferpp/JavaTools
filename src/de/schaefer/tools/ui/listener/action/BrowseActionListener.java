package de.schaefer.tools.ui.listener.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

public class BrowseActionListener implements ActionListener {

    JTextComponent comp;
    String location;
    FileFilter filter;
    int selMode = JFileChooser.FILES_AND_DIRECTORIES;

    public BrowseActionListener(JTextComponent comp) {
	this.comp = comp;
    }

    public BrowseActionListener(JTextComponent comp, String location) {
	this(comp);
	this.location = location;
    }

    public BrowseActionListener(JTextComponent comp, String location,
	    FileFilter filter) {
	this(comp, location);
	this.filter = filter;
    }

    public BrowseActionListener(JTextComponent comp, String location, int mode) {
	this(comp, location);
	this.selMode = mode;
    }

    public BrowseActionListener(JTextComponent comp, FileFilter filter) {
	this(comp);
	this.filter = filter;
    }

    public BrowseActionListener(JTextComponent comp, int mode) {
	this(comp);
	this.selMode = mode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	JFileChooser jfc;
	if (location != null) {
	    jfc = new JFileChooser(location);
	} else {
	    jfc = new JFileChooser();
	}
	jfc.setFileSelectionMode(selMode);
	if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(null)) {
	    comp.setText(jfc.getSelectedFile().getAbsolutePath());
	}
    }

}
