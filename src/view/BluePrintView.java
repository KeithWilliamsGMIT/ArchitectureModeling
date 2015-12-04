// Keith Williams
// 13/10/2015
// BluePrintView

package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.BluePrintModel;

public class BluePrintView extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	BluePrintDisplay display;
	BluePrintMenu menu;
	
	// Constructors *****************************
	public BluePrintView() {
		// Initialize inner classes
		display = new BluePrintDisplay();
		menu = new BluePrintMenu();
		
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
	
	// Update blue print display and menu
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		BluePrintModel bluePrintModel = (BluePrintModel)o;
		
		// Set variables
		display.offsetX = (bluePrintModel.getOffsetX());
		display.offsetY = (bluePrintModel.getOffsetY());
		
		display.zoom = bluePrintModel.getZoom();
		
		display.isImageVisible = bluePrintModel.getImageVisibility();
		display.backgroundImageScale = bluePrintModel.getBackgroundImageScale();
		
		display.isImageVisible = bluePrintModel.getImageVisibility();
		display.isGridVisible = bluePrintModel.getGridVisibility();
		
		// Call methods
		display.drawBluePrints(bluePrintModel.getBluePrintDrawing());
		
		display.drawGrid(bluePrintModel.getNumericMeasurement());
		
		display.isImageVisible = bluePrintModel.getImageVisibility();
		display.backgroundImageScale = bluePrintModel.getBackgroundImageScale();
		
		if (bluePrintModel.getBackgroundImagePath() != null) {
			display.loadBackgroundImage(bluePrintModel.getBackgroundImagePath());
		}
		
		display.repaint();
	}
	
	// Set check box and radio items in the menu
	public void updateMenu(BluePrintModel bluePrintModel) {
		menu.showImageMenuItem.setState(bluePrintModel.getImageVisibility());
		menu.showGridMenuItem.setState(bluePrintModel.getGridVisibility());
		
		if (bluePrintModel.getMeasurement() == 'f') {
			menu.feetMenuItem.setSelected(true);
		} else {
			menu.metersMenuItem.setSelected(true);
		}
	}
}
