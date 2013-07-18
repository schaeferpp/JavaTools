package de.schaefer.tools.ui.listener.action;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.text.JTextComponent;

import de.schaefer.tools.log.Logger;

public class PasteTextActionListener extends TextActionListener {

    public PasteTextActionListener(JTextComponent comp) {
	super(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	String contents;
	try {
	    contents = (String) Toolkit.getDefaultToolkit()
		    .getSystemClipboard().getData(DataFlavor.stringFlavor);
	    getComponent().setText(contents.toString());
	} catch (HeadlessException e1) {
	    Logger.getLogger().logException(e1, Logger.LOG_LEVEL_WARN);
	} catch (UnsupportedFlavorException e1) {
	    Logger.getLogger().logException(e1,
		    "Object in clipboard doesnt seem to be a String",
		    Logger.LOG_LEVEL_WARN);
	} catch (IOException e1) {
	    Logger.getLogger().logException(e1, Logger.LOG_LEVEL_WARN);
	}
    }

}
