// Keith Williams
// 25/11/2015
// WireFrameMenu

package view;

import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class WireFrameMenu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	JMenu menu;
	JMenuItem viewTopMenuItem, zoomInMenuItem, zoomOutMenuItem;
	
	int shortcutMask;
	
	// Constuctor *******************************
	public WireFrameMenu() {
		// Add menus to menu bar
		addViewMenu();

		setVisible(true);
	}
	
	// Methods **********************************
	// Tool menu
	private void addViewMenu() {
		menu = new JMenu("View");
		add(menu);
		
		// View Top
		viewTopMenuItem = new JMenuItem("View Top");
		viewTopMenuItem.setAccelerator(KeyStroke.getKeyStroke('T', Event.CTRL_MASK | Event.SHIFT_MASK));
		menu.add(viewTopMenuItem);
		
		// Zoom In
		zoomInMenuItem = new JMenuItem("Zoom In");
		zoomInMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, Event.CTRL_MASK | Event.SHIFT_MASK));
		menu.add(zoomInMenuItem);
		
		// Zoom Out
		zoomOutMenuItem = new JMenuItem("Zoom Out");
		zoomOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, Event.CTRL_MASK | Event.SHIFT_MASK));
		menu.add(zoomOutMenuItem);
	}
	
	// Add WireFrameContoller object as action listener to all menu items
	public void addController(ActionListener controller) {
		viewTopMenuItem.addActionListener(controller);
		zoomInMenuItem.addActionListener(controller);
		zoomOutMenuItem.addActionListener(controller);
	}
}
