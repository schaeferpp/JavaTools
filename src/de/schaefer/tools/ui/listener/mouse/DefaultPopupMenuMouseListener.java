package de.schaefer.tools.ui.listener.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

public class DefaultPopupMenuMouseListener extends MouseAdapter {

    private JComponent invoker;
    private JPopupMenu menu;

    public DefaultPopupMenuMouseListener(JComponent invoker, JPopupMenu menu) {
	this.invoker = invoker;
	this.menu = menu;
    }

    public void openPopup(MouseEvent e) {
	if (e.isPopupTrigger()) {
	    menu.show(invoker, e.getX(), e.getY());
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	openPopup(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	openPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	openPopup(e);
    }

}
