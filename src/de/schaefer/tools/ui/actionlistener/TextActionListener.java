package de.schaefer.tools.ui.actionlistener;

import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class TextActionListener implements ActionListener {

    private TextComponent component;

    public TextActionListener(TextComponent comp) {
	this.component = comp;
    }

    public TextComponent getComponent() {
	return component;
    }

    public void setComponent(TextComponent component) {
	this.component = component;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
