// Keith Williams
// 03/10/2015
// ProgramFrame - parent frame that will encapsulate all panels

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.BluePrintController;
import controller.ToolBarController;
import controller.WireFrameController;
import model.BluePrintModel;
import model.Model;
import model.ToolBarModel;
import model.WireFrameModel;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	final int MIN_WIDTH = 800;
	final int MIN_HEIGHT = 600;
	
	ToolBarMenu toolBarMenu;
	BluePrintView bluePrintView;
	WireFrameView wireFrameView;
	
	int width, height;
	int menuHeight = 35;
	
	// Constructors *****************************
	public MainWindow () {
		// Set JFrame properties
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		width = getWidth();
		height = getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    // Instantiate other panels
	    toolBarMenu = new ToolBarMenu(width, menuHeight);
	    bluePrintView = new BluePrintView();
	    wireFrameView = new WireFrameView();

	    GridLayout layout = new GridLayout(1, 2);

		// Create a panel to hold display panels
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(layout);
		displayPanel.setSize(width, height - menuHeight);

		// Add blue print and model display
		displayPanel.add(bluePrintView);
		displayPanel.add(wireFrameView);
		
		// Add the tool bar menu and the previous panels to the JFrame
		add(toolBarMenu.menuBar, BorderLayout.PAGE_START);
		add(displayPanel, BorderLayout.CENTER);
	    
		setVisible(true);
		setControllers();
	}
	
	// Create blue print controller, wire frame controller and tool bar controller
	// and add them to the corresponding views
	private void setControllers() {
		// Blue Print Controller
		BluePrintModel bluePrintModel = new BluePrintModel();
		bluePrintModel.addObserver(bluePrintView);
		BluePrintController bluePrintcontroller = new BluePrintController();
		bluePrintcontroller.setModel(bluePrintModel);
		bluePrintcontroller.setView(bluePrintView);
		bluePrintView.menu.addController(bluePrintcontroller);
		bluePrintView.display.addController(bluePrintcontroller);
		
		// Wire Frame Controller
		WireFrameModel wireFrameModel = new WireFrameModel();
		wireFrameModel.addObserver(wireFrameView);
		WireFrameController wireFrameController = new WireFrameController();
		wireFrameController.setModel(wireFrameModel);
		wireFrameController.setView(wireFrameView);
		wireFrameView.menu.addController(wireFrameController);
		wireFrameView.display.addController(wireFrameController);
		
		// ToolBar Controller
		ToolBarModel toolBarModel = new ToolBarModel();
		toolBarModel.addObserver(toolBarMenu);
		ToolBarController toolBarController = new ToolBarController();
		toolBarController.setModel(toolBarModel);
		toolBarController.setView(toolBarMenu);
		toolBarMenu.addController(toolBarController);
		
		// Create a model object to hold the blue print and wire frame models so they can communicate
		new Model(bluePrintModel, wireFrameModel, toolBarModel);
	}
	
	// Entry point to program
	public static void main(String[] args){
		new MainWindow();
	}
}
