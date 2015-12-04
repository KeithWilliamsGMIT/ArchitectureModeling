// Keith Williams
// 15/10/2015
// WireFrameView

package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.WireFrameModel;

public class WireFrameView extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	WireFrameDisplay display;
	WireFrameMenu menu;
	
	// Constructors *****************************
	public WireFrameView() {
		// Initialize inner classes
		display = new WireFrameDisplay();
		menu = new WireFrameMenu();
		
		// Layout
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Display
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(display, constraints);
		
		// Menu
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.PAGE_END;
		add(menu, constraints);
		
		setVisible(true);
	}
	
	// Methods **********************************
	// Update wire frame display and menu
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		WireFrameModel wireFrameModel = (WireFrameModel)o;
		
		display.zoom = wireFrameModel.getZoom();
		display.geometry = wireFrameModel.getGeometry();
		display.rotation = wireFrameModel.getRotation();
		
		if (display.geometry == null) {
			System.out.println("Null");
		}
		
		display.repaint();
	}

}
