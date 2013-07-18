package de.schaefer.tools.ui.listener.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.JTextComponent;

public abstract class TextActionListener implements ActionListener {

    private JTextComponent component;

    public TextActionListener(JTextComponent comp) {
	this.component = comp;
    }

    public JTextComponent getComponent() {
	return component;
    }

    public void setComponent(JTextComponent component) {
	this.component = component;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
