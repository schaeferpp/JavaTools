package de.schaefer.tools.ui.actionlistener;

import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class CopyTextActionListener extends TextActionListener {

    public CopyTextActionListener(TextComponent comp) {
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
