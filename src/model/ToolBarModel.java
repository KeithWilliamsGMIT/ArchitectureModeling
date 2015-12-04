// Keith Williams
// 03/11/2015
// ToolBarModel

package model;

import java.io.IOException;

import javax.swing.JFileChooser;

import files.ErrorLog;
import files.ObjWriter;

public class ToolBarModel extends ObservableModel {
	// Variables ********************************
	
	// Constructors *****************************
	public ToolBarModel() {
		
	}
	
	// Methods **********************************
	// Export OBJ
	public void exportObj() {
		// Get path from dialog
		String path = showExportDialog();
		
		// If path is valid
		if (path != null) {
			// Create ObjWriter object
			new ObjWriter(Model.wireFrameModel.getGeometry(), path);
		}
	}
	
	// Export Dialog
	private String showExportDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Export As");
		fileChooser.setApproveButtonText("Export");
		
		int result = fileChooser.showOpenDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			return path;
		} else {
			try {
				ErrorLog.witeError("Error in export dialog");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
	}
}
