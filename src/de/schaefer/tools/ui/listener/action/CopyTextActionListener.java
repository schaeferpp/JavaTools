package de.schaefer.tools.ui.listener.action;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.text.JTextComponent;

public class CopyTextActionListener extends TextActionListener {

    public CopyTextActionListener(JTextComponent comp) {
	super(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Toolkit.getDefaultToolkit()
		.getSystemClipboard()
		.setContents(new StringSelection(getComponent().getText()),
			null);
    }

    // TODO maybe clipboardowner?
}
