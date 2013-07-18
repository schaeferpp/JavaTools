package de.schaefer.tools.ui;

import java.util.LinkedList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import de.schaefer.tools.ui.listener.action.CopyTextActionListener;
import de.schaefer.tools.ui.listener.action.PasteTextActionListener;
import de.schaefer.tools.ui.listener.action.SelectAllTextActionListener;

public class DefaultTextPopupMenu extends JPopupMenu {

    /**
     * 
     */
    private static final long serialVersionUID = 8883756534333882231L;

    private LinkedList<JMenuItem> items = new LinkedList<JMenuItem>();
    private JTextComponent parent;

    public DefaultTextPopupMenu(JTextComponent comp) {
	this.parent = comp;
    }

    public void addDefaultCopyOption(String text) {
	JMenuItem copyItem = new JMenuItem(text);
	copyItem.setActionCommand("copy");
	copyItem.setHorizontalTextPosition(JMenuItem.LEFT);
	copyItem.addActionListener(new CopyTextActionListener(parent));
	add(copyItem);
    }

    public void addDefaultPasteOption(String text) {
	JMenuItem pasteItem = new JMenuItem(text);
	pasteItem.setActionCommand("copy");
	pasteItem.setHorizontalTextPosition(JMenuItem.LEFT);
	pasteItem.addActionListener(new PasteTextActionListener(parent));
	add(pasteItem);
    }

    public void addDefaultSelectAllOption(String text) {
	JMenuItem selectionItem = new JMenuItem(text);
	selectionItem.setActionCommand("copy");
	selectionItem.setHorizontalTextPosition(JMenuItem.LEFT);
	selectionItem
		.addActionListener(new SelectAllTextActionListener(parent));
	add(selectionItem);
    }

    @Override
    public JMenuItem add(JMenuItem item) {
	items.add(item);
	return super.add(item);
    }

}
