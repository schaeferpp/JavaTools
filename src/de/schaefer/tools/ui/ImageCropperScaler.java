package de.schaefer.tools.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImageCropperScaler extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 2050148490997618064L;
    private BufferedImage bi;
    private int givenWidth, givenHeight;
    private int targetX1;
    private int targetY1;
    private int targetX2;
    private int targetY2;

    private int offX;
    private int offY;

    private int oldOffX;
    private int oldOffY;

    private int mouseDownX;
    private int mouseDownY;

    private int curX1;
    private int curY1;
    private int curX2;
    private int curY2;

    private int zoomX;
    private int zoomY;

    private double zoom;

    private enum ScaleTarget {
	WIDTH, HEIGHT
    }

    private ScaleTarget scaleTarget;

    public ImageCropperScaler(String path, int width, int height) {
	if (path == null) {
	    throw new IllegalArgumentException("Path is null!");
	}
	try {
	    bi = ImageIO.read(new File(path));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	curX1 = 0;
	curY1 = 0;
	zoom = 1;
	zoomX = 0;
	zoomY = 0;
	offX = 0;
	offY = 0;

	this.givenWidth = width;
	this.givenHeight = height;

	if (this.bi.getWidth() > this.bi.getHeight()) {
	    this.curX2 = this.bi.getWidth();
	    this.curY2 = (int) ((double) this.bi.getWidth() / (double) this.givenWidth);
	    this.scaleTarget = ScaleTarget.HEIGHT;
	} else {
	    this.curY2 = this.bi.getHeight();
	    this.curX2 = (int) ((double) this.bi.getHeight() / (double) this.givenHeight);
	    this.scaleTarget = ScaleTarget.WIDTH;
	}

	recalc();

	this.addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		Dimension b = e.getComponent().getSize();
		switch (scaleTarget) {
		case HEIGHT:
		    setSize(b.width, b.width
			    / ImageCropperScaler.this.givenWidth);
		    break;
		case WIDTH:
		    setSize(b.height / ImageCropperScaler.this.givenHeight,
			    b.height);
		    break;
		}
		recalc();
	    }
	});

	this.addMouseMotionListener(new MouseMotionListener() {

	    @Override
	    public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {
		int newWidth = (int) ((double) getPicWidth() * zoom);
		int newHeight = (int) ((double) getPicHeight() * zoom);

		offX = (int) (((e.getX() - mouseDownX) * (double) getPicWidth() / (double) getWidth()) * ((double) newWidth / (double) getPicWidth()));
		offY = (int) (((e.getY() - mouseDownY)
			* (double) getPicHeight() / (double) getHeight()) * ((double) newHeight / (double) getPicHeight()));
		repaint();
	    }
	});

	this.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseReleased(MouseEvent e) {
		oldOffX += offX;
		oldOffY += offY;
		offX = 0;
		offY = 0;
		repaint();
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
		    mouseDownX = e.getX();
		    mouseDownY = e.getY();
		} else {
		    offX = 0;
		    offY = 0;
		    repaint();
		}
	    }
	});

	this.addMouseWheelListener(new MouseWheelListener() {

	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.isShiftDown()) {
		    zoom *= 1 - (double) e.getWheelRotation() / 100.0;
		} else {
		    zoom *= 1 - (double) e.getWheelRotation() / 10.0;
		}

		int newWidth = (int) ((double) getPicWidth() * zoom);
		int newHeight = (int) ((double) getPicHeight() * zoom);

		System.out.println(zoom);
		System.out.println(newWidth);
		System.out.println(getPicWidth());
		zoomX = (newWidth - getPicWidth()) / 2;
		zoomY = (newHeight - getPicHeight()) / 2;

		repaint();
	    }
	});

    }

    private int getPicWidth() {
	return curX2 - curX1;
    }

    private int getPicHeight() {
	return curY2 - curY1;
    }

    /*
     * private void recalcHeight() { if (getPicHeight() > this.getHeight()) {
     * targetY1 = 0; targetY2 = this.getHeight(); } else { targetY1 =
     * this.getHeight() / 2 - getPicHeight() / 2; targetY2 = this.getHeight() /
     * 2 + getPicHeight() / 2; } }
     * 
     * private void recalcWidth() { if (getPicWidth() > this.getWidth()) {
     * targetX1 = 0; targetX2 = this.getWidth(); } else { targetX1 =
     * this.getWidth() / 2 - getPicWidth() / 2; targetX2 = this.getWidth() / 2 +
     * getPicWidth() / 2; } }
     * 
     * private void recalc() { if (this.scaleTarget == ScaleTarget.WIDTH) {
     * recalcHeight(); int width = (targetY2 - targetY1) / givenHeight; if
     * (width > this.getWidth()) {
     * 
     * } } else { recalcWidth(); int height = (targetX2 - targetX1) /
     * givenHeight; if (height > this.getHeight()) {
     * 
     * } }
     * 
     * }
     */

    private void recalc() {
	switch (scaleTarget) {
	case HEIGHT:
	    targetX1 = 0;
	    targetY1 = 0;
	    targetX2 = getWidth();
	    targetY2 = (int) ((double) getWidth() / (double) givenWidth);
	    break;
	case WIDTH:
	    targetX1 = 0;
	    targetY1 = 0;
	    targetX2 = (int) ((double) getHeight() / (double) givenHeight);
	    targetY2 = getHeight();
	    break;
	}
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(bi, targetX1, targetY1, targetX2, targetY2, curX1 - offX
		- oldOffX - zoomX, curY1 - offY - oldOffY - zoomY, curX2 - offX
		- oldOffX + zoomX, curY2 - offY - oldOffY + zoomY, null);
    }

    public BufferedImage getImage() {
	BufferedImage dest = bi.getSubimage(curX1 - offX - oldOffX - zoomX,
		curY1 - offY - oldOffY - zoomY,
		(curX2 - offX - oldOffX + zoomX)
			- (curX1 - offX - oldOffX - zoomX), (curY2 - offY
			- oldOffY + zoomY)
			- (curY1 - offY - oldOffY - zoomY));
	return dest;
    }
}
