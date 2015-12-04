// Keith Williams
// 03/11/2015
// ToolBarController

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ToolBarModel;
import view.ToolBarMenu;

public class ToolBarController implements Controller, ActionListener {
	// Variables ********************************
	ToolBarModel model;
	ToolBarMenu view;
	
	// Methods **********************************
	//invoked when a button is pressed
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "WaveFront (OBJ)":
				model.exportObj();
				break;
		}
	}

	// Controller Methods
	@Override
	public void setModel(Object o) {
		// TODO Auto-generated method stub
		ToolBarModel m = (ToolBarModel)o;
		model = m;
	}

	@Override
	public void setView(Object o) {
		// TODO Auto-generated method stub
		ToolBarMenu v = (ToolBarMenu)o;
		view = v;
	}
}
