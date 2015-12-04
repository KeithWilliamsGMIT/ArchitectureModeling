// Keith Williams
// 13/10/2015
// BluePrintMenu

package view;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class BluePrintMenu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	JMenu menu, subMenu;
	JMenuItem addWallMenuItem, addDoorMenuItem, addRoofMenuItem, deleteMenuItem, zoomInMenuItem, zoomOutMenuItem, openImageMenuItem, enlargeImageMenuItem, shrinkImageMenuItem;
	JCheckBoxMenuItem showImageMenuItem, showGridMenuItem;
	JRadioButtonMenuItem metersMenuItem, feetMenuItem;
	
	int shortcutMask;
	
	// Construction *****************************
	public BluePrintMenu() {
		shortcutMask = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

		// Add menus to menu bar
		addToolMenu();
		addImageMenu();
		addViewMenu();
		addMeasurementsMenu();

		setVisible(true);
	}
	
	// Methods **********************************
	// Tool menu
	private void addToolMenu() {
		menu = new JMenu("Tools");
		add(menu);
		
		// Add Tool
		subMenu = new JMenu("Add");
		menu.add(subMenu);
		
		addWallMenuItem = new JMenuItem("Wall");
		addWallMenuItem.setAccelerator(KeyStroke.getKeyStroke('W', shortcutMask));
		subMenu.add(addWallMenuItem);
		
		addDoorMenuItem = new JMenuItem("Door");
		addDoorMenuItem.setAccelerator(KeyStroke.getKeyStroke('D', shortcutMask));
		subMenu.add(addDoorMenuItem);
		
		addRoofMenuItem = new JMenuItem("Roof");
		addRoofMenuItem.setAccelerator(KeyStroke.getKeyStroke('R', shortcutMask));
		subMenu.add(addRoofMenuItem);
		
		// Delete Tool
		deleteMenuItem = new JMenuItem("Delete");
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke('X', shortcutMask));
		menu.add(deleteMenuItem);
	}

	// Image menu
	private void addImageMenu() {
		menu = new JMenu("Image");
		add(menu);

		// Open Image
		openImageMenuItem = new JMenuItem("Open Image");
		openImageMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', shortcutMask));
		menu.add(openImageMenuItem);
		
		// Show Image
		showImageMenuItem = new JCheckBoxMenuItem("Show Image");
		showImageMenuItem.setAccelerator(KeyStroke.getKeyStroke('H', shortcutMask));
		menu.add(showImageMenuItem);
		
		// Show Grid
		showGridMenuItem = new JCheckBoxMenuItem("Show Grid");
		showGridMenuItem.setAccelerator(KeyStroke.getKeyStroke('G', shortcutMask));
		menu.add(showGridMenuItem);
		
		// Increase Image Scale
		enlargeImageMenuItem = new JMenuItem("Enlarge Image");
		enlargeImageMenuItem.setAccelerator(KeyStroke.getKeyStroke('E', shortcutMask));
		menu.add(enlargeImageMenuItem);
		
		// Decrease Image Scale
		shrinkImageMenuItem = new JMenuItem("Shrink Image");
		shrinkImageMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', shortcutMask));
		menu.add(shrinkImageMenuItem);
	}

	// View menu
	private void addViewMenu() {
		menu = new JMenu("View");
		add(menu);

		// Zoom in
		zoomInMenuItem = new JMenuItem("Zoom In");
		zoomInMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, shortcutMask));
		menu.add(zoomInMenuItem);

		// Zoom out
		zoomOutMenuItem = new JMenuItem("Zoom Out");
		zoomOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, shortcutMask));
		menu.add(zoomOutMenuItem);
	}
	
	// Measurements menu
	public void addMeasurementsMenu() {
		menu = new JMenu("Measurements");
		add(menu);
		
		// Group for measurement radio buttons
		ButtonGroup group = new ButtonGroup();
		
		metersMenuItem = new JRadioButtonMenuItem("Meters");
		metersMenuItem.setAccelerator(KeyStroke.getKeyStroke('M', shortcutMask));
		group.add(metersMenuItem);
		menu.add(metersMenuItem);
		
		feetMenuItem = new JRadioButtonMenuItem("Feet");
		feetMenuItem.setAccelerator(KeyStroke.getKeyStroke('F', shortcutMask));
		group.add(feetMenuItem);
		menu.add(feetMenuItem);
	}
	
	// Add BluePrintContoller object as action listener to all menu items
	public void addController(ActionListener controller) {
		addWallMenuItem.addActionListener(controller);
		addDoorMenuItem.addActionListener(controller);
		addRoofMenuItem.addActionListener(controller);
		deleteMenuItem.addActionListener(controller);
		openImageMenuItem.addActionListener(controller);
		zoomInMenuItem.addActionListener(controller);
		zoomOutMenuItem.addActionListener(controller);
		enlargeImageMenuItem.addActionListener(controller);
		shrinkImageMenuItem.addActionListener(controller);
		showImageMenuItem.addActionListener(controller);
		showGridMenuItem.addActionListener(controller);
		metersMenuItem.addActionListener(controller);
		feetMenuItem.addActionListener(controller);
	}
}
