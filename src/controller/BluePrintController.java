// Keith Williams
// 03/11/2015
// BluePrintController

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.SwingUtilities;

import model.BluePrintModel;
import view.BluePrintView;

public class BluePrintController implements Controller, ActionListener, MouseListener, MouseMotionListener, ComponentListener {
	// Variables ********************************
	BluePrintModel model;
	BluePrintView view;
	
	// Methods **********************************
	//invoked when a button is pressed
	@Override
	public void actionPerformed(ActionEvent e){
		// switch here for all commands
		switch (e.getActionCommand()) {
			case "Select":
				model.setCurrentTool('s');
				break;
			case "Wall":
				model.setCurrentTool('w');
				break;
			case "Door":
				model.setCurrentTool('d');
				break;
			case "Roof":
				model.setCurrentTool('r');
				break;
			case "Delete":
				model.setCurrentTool('x');
				break;
			case "Open Image":
				try {
					model.browseBackgroundImage();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				break;
			case "Show Image":
				model.setImageVisibility();
				break;
			case "Show Grid":
				model.setGridVisibility();
				break;
			case "Enlarge Image":
				model.enlargeImage();
				break;
			case "Shrink Image":
				model.shrinkImage();
				break;
			case "Zoom In":
				model.zoomIn();
				break;
			case "Zoom Out":
				model.zoomOut();
				break;
			case "Meters":
				model.setMeasurement('m');
				break;
			case "Feet":
				model.setMeasurement('f');
				break;
			default: 
				System.out.println("Error: " + e.getActionCommand());
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX() - (e.getComponent().getWidth() / 2);
		int y = e.getY() - (e.getComponent().getHeight() / 2);
		
		// If the left mouse button is down draw a wall
		if (SwingUtilities.isLeftMouseButton(e)) {
			model.leftMouseDragged(x, y);
		} else if (SwingUtilities.isRightMouseButton(e)) { // If the right mouse button is down invoke udateOffset()
			model.updateOffset(x, y);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX() - (e.getComponent().getWidth() / 2);
		int y = e.getY() - (e.getComponent().getHeight() / 2);
		
		model.mousePressed(x, y);
		
		// Only call the method if the left mouse button is down
		if (SwingUtilities.isLeftMouseButton(e)) {
			model.mousePressed(x, y);
			model.useTool();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		model.mouseReleased();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	// ComponentListner Methods
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		model.updateObserver();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
	}
	
	// Controller Methods
	@Override
	public void setModel(Object o) {
		// TODO Auto-generated method stub
		BluePrintModel m = (BluePrintModel)o;
		model = m;
		model.updateObserver();
	}

	@Override
	public void setView(Object o) {
		// TODO Auto-generated method stub
		BluePrintView v = (BluePrintView)o;
		view = v;
		view.updateMenu(model);
	}
}
