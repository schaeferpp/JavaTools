package de.schaefer.tools.ui.listener.action;

import java.awt.event.ActionEvent;

import javax.swing.text.JTextComponent;

public class SelectAllTextActionListener extends TextActionListener {

    public SelectAllTextActionListener(JTextComponent comp) {
	super(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	getComponent().selectAll();
    }

}
