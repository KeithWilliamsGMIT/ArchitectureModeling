// Keith Williams
// 03/10/2015
// ToolBarMenu - GUI tool bar displayed across the top

package view;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ToolBarMenu implements Observer {
	// Variables
	JMenuBar menuBar;
	JMenu menu, subMenu;
	JMenuItem exportObjMenuItem;

	// Methods
	public ToolBarMenu(int width, int height) {
		// Create new JMenuBar
		menuBar = new JMenuBar();
		
		// Add menus to menu bar
		addFileMenu();
		
		menuBar.setVisible(true);
	}
	
	// File menu
	private void addFileMenu() {
		menu = new JMenu("File");
		menuBar.add(menu);
		
		// Export sub menu
		subMenu = new JMenu("Export");
		menu.add(subMenu);

		exportObjMenuItem = new JMenuItem("WaveFront (OBJ)");
		subMenu.add(exportObjMenuItem);
	}
	
	// Add ToolBarController object as action listener to all menu items
	public void addController(ActionListener controller) {
		exportObjMenuItem.addActionListener(controller);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
