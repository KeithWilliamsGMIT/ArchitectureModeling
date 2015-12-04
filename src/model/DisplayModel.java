// Keith Williams
// 27/11/2015
// DisplayModel - contains methods and variables common to BluePrintModel and WireFrameModel to reduce duplicate code.
//				Abstract so an instance of this class cannot be instantiated.
//				Extends ObservanleModel so that the jPanels can be updated

package model;

public abstract class DisplayModel extends ObservableModel {
	// Variables ********************************
	private final int MAX_ZOOM = 500;
	private final int MIN_ZOOM = 50;
	private final int ZOOM_INCREMENT = 10;
	
	private float zoom;
	
	// Getters and Setters **********************
	// Zoom
	public float getZoom () {
		return zoom;
	}
	
	public void setZoom (float value) {
		if (value >= MIN_ZOOM && value <= MAX_ZOOM) {
			zoom = value;
			updateObserver();
		}
	}
	// Methods **********************************
	// Increment Zoom
	public void zoomIn() {
		float zoomValue = getZoom() + ZOOM_INCREMENT;
		setZoom(zoomValue);
	}
	
	// Decrement Zoom
	public void zoomOut() {
		float zoomValue = getZoom() - ZOOM_INCREMENT;
		setZoom(zoomValue);
	}
}
