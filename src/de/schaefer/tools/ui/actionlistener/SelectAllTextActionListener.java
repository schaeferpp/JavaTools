package de.schaefer.tools.ui.actionlistener;

import java.awt.TextComponent;
import java.awt.event.ActionEvent;

public class SelectAllTextActionListener extends TextActionListener {

    public SelectAllTextActionListener(TextComponent comp) {
	super(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	getComponent().selectAll();
    }

}
