// Keith Williams
// 03/11/2015
// WireFrameController

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import model.WireFrameModel;
import view.WireFrameView;

public class WireFrameController implements Controller, ActionListener, MouseListener, MouseMotionListener {
	// Variables ********************************
	WireFrameModel model;
	WireFrameView view;
	
	// Methods **********************************
	//invoked when a button is pressed
	@Override
	public void actionPerformed(ActionEvent e){
		// switch here for all commands
		switch (e.getActionCommand()) {
			case "Zoom In":
				model.zoomIn();
				break;
			case "Zoom Out":
				model.zoomOut();
				break;
			case "View Top":
				model.setRotation((short)0, (short)0, (short)0);
				break;
			default: 
				System.out.println("Error: " + e.getActionCommand());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isLeftMouseButton(e)) {
			model.mouseDragged(e.getX(), e.getY());
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
		model.mousePressed(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void setModel(WireFrameModel m){
		model = m;
		model.updateObserver();
	}

	public void setView(WireFrameView wireFrameView){
		view = wireFrameView;
	}
	
	// Controller Methods
	@Override
	public void setModel(Object o) {
		// TODO Auto-generated method stub
		WireFrameModel m = (WireFrameModel)o;
		model = m;
		model.updateObserver();
	}

	@Override
	public void setView(Object o) {
		// TODO Auto-generated method stub
		WireFrameView v = (WireFrameView)o;
		view = v;
	}
}
