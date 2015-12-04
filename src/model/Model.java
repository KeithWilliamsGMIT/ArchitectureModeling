// keith Williams
// 19/10/2015
// Model - Contains all models.
//			Allows all models to access data from one another

package model;

import java.util.Observable;

public class Model extends Observable {
	// Variables ********************************
	public static BluePrintModel bluePrintModel;
	public static WireFrameModel wireFrameModel;
	public static ToolBarModel toolBarModel;
	
	// Constructors *****************************
	public Model(BluePrintModel bluePrintModel, WireFrameModel wireFrameModel, ToolBarModel toolBarModel) {
		Model.bluePrintModel = bluePrintModel;
		Model.wireFrameModel = wireFrameModel;
		Model.toolBarModel = toolBarModel;
	}
}
