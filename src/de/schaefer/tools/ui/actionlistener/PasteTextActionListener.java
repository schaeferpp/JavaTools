package de.schaefer.tools.ui.actionlistener;

import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class PasteTextActionListener extends TextActionListener {

    public PasteTextActionListener(TextComponent comp) {
	super(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object contents = Toolkit.getDefaultToolkit().getSystemClipboard()
		.getContents(this);
	if (contents instanceof String) {
	    getComponent().setText(contents.toString());
	}
    }

}
