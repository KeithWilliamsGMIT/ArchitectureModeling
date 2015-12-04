// Keith Williams
// 20/10/2015
//BluePrintModel

package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import files.ErrorLog;

public class BluePrintModel extends DisplayModel {
	// Variables ********************************
	private final int SCALE_INCREMENT = 10;
	
	private BluePrintDrawingModel bluePrintDrawing;
	
	private char currentTool;
	private String backgroundImagePath;
	private int backgroundImageScale;
	private boolean isImageVisible, isGridVisible;
	private char measurement;
	
	// Used to record mouse positions on panel
	private int previousPositionX, previousPositionY;
	private int currentPositionX, currentPositionY;
	
	private int offsetX, offsetY;
	
	// Constructors *****************************
	public BluePrintModel() {
		setBluePrintDrawing(new BluePrintDrawingModel());
		setCurrentTool('w');
		setBackgroundImageScale(0);
		setMeasurement('m'); // Default measurement is meters
		setZoom(100); // default zoom is 100%
		setOffset(0, 0);
		setImageVisibility();
		setGridVisibility();
	}
	
	// Getters and Setters **********************
	// Tools
	public char getCurrentTool() {
		return currentTool;
	}
	
	public void setCurrentTool(char value) {
		currentTool = value;
	}
	
	// Drawing
	public BluePrintDrawingModel getBluePrintDrawing() {
		return bluePrintDrawing;
	}
	
	public void setBluePrintDrawing(BluePrintDrawingModel value) {
		bluePrintDrawing = value;
		updateObserver();
	}
	
	// Image Visibility
	public boolean getImageVisibility() {
		return isImageVisible;
	}
	
	public void setImageVisibility() {
		isImageVisible = !isImageVisible;
		updateObserver();
	}
	
	// Grid Visibility
	public boolean getGridVisibility() {
		return isGridVisible;
	}
	
	public void setGridVisibility() {
		isGridVisible = !isGridVisible;
		updateObserver();
	}
	
	// Offsets
	public int getOffsetX() {
		return offsetX;
	}
	
	public int getOffsetY() {
		return offsetY;
	}
	
	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
		updateObserver();
	}
	
	// Background image path
	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}
	
	public void setBackgroundImagePath(String path) {
		backgroundImagePath = path;
		updateObserver();
	}
	
	// Background image scale
	public int getBackgroundImageScale() {
		return backgroundImageScale;
	}
	
	public void setBackgroundImageScale(int value) {
		if (value > -1) {
			backgroundImageScale = value;
			updateObserver();
		}
	}
	
	public float getNumericMeasurement() {
		float value;
		
		switch (measurement) {
			// Feet (3.28084ft in 1m)
			case 'f':
				value = 3.28084f;
				break;
			// Default measurement is meters
			default:
				value = 1f;
				break;
		}
		
		return value;
	}
	
	public char getMeasurement() {
		return measurement;
	}
	
	public void setMeasurement(char value) {
		measurement = value;
		updateObserver();
	}
	
	// Previous position
	public void setPreviousPosition(int x, int y) {
		previousPositionX = (int) (x * (100f / getZoom()));
		previousPositionY = (int) (y * (100f / getZoom()));
	}
	
	// Current position
	public void setCurrentPosition(int x, int y) {
		currentPositionX = (int) (x * (100f / getZoom()));
		currentPositionY = (int) (y * (100f / getZoom()));
	}
	
	// Methods **********************************
	// Blue Print Image
	public void browseBackgroundImage() throws IOException {
		// Only allow images to be opened
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
        	String path = fileChooser.getSelectedFile().getPath();;
        	Path filePath = Paths.get(path);
        	
        	// Check if the file is valid
        	if (Files.notExists(filePath)) {
        		ErrorLog.witeError("File does not exist");
        	} else if (!Files.isReadable(filePath)) {
            	ErrorLog.witeError("File is not readable");
        	} else if (!checkImage(path)) {
        		ErrorLog.witeError("File is not an image");
        	} else {
        		setBackgroundImagePath(path);
        	}
        }
	}
	
	// returns true if the file is an image
	private boolean checkImage(String path) {
        File f = new File(path);
        String mimetype= new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        
        if (type.equals("image")) {
           return true;
        } else {
        	return false;
        }
	}
	
	// Increment Scale
	public void enlargeImage() {
		int scaleValue = getBackgroundImageScale() + SCALE_INCREMENT;
		setBackgroundImageScale(scaleValue);
	}
	
	// Decrement Scale
	public void shrinkImage() {
		int scaleValue = getBackgroundImageScale() - SCALE_INCREMENT;
		setBackgroundImageScale(scaleValue);
	}
	
	// Calculate the length of the wall the user is currently drawing
	private String getLengthOfWall() {
		// TODO Auto-generated method stub
		float answer;

		// Formula = square root of ((x2 - x1)^2 + (y2 - y1)^2)
		answer = (float)Math.sqrt(Math.pow(currentPositionX - previousPositionX, 2) + Math.pow(currentPositionY - previousPositionY, 2));
		answer *= getNumericMeasurement() * getZoom();
		
		return String.format("%.2fm", answer);
	}
	
	public void mousePressed(int x, int y) {
		setPreviousPosition(x, y);
		setCurrentPosition(x, y);
	}
	
	public void useTool() {
		switch (currentTool) {
			case 'w':
				bluePrintDrawing.addWall(getXCoordinate(), getYCoordinate());
				updateGeometry();
				updateObserver();
				break;
			case 'd':
				// If a door is added to a wall update the geometry model and observer
				if (bluePrintDrawing.addDoor(getXCoordinate(), getYCoordinate())) {
					updateGeometry();
					updateObserver();
				}
				
				break;
			case 'r':
				// If a roof is added update the geometry model and observer
				if (bluePrintDrawing.addRoof(getXCoordinate(), getYCoordinate())) {
					updateGeometry();
					updateObserver();
				}
				
				break;
			case 'x':
				// If a wall is removed update the geometry model and observer
				if (bluePrintDrawing.delete(getXCoordinate(), getYCoordinate())) {
					updateGeometry();
					updateObserver();
				}
				
				break;
		}
	}
	
	// Get X and Y coordinate methods calculate the coordinated where the user pressed or released the mouse button on the grid
	// they take into account the offset and zoom values
	public float getXCoordinate() {
		return (currentPositionX / 100f) - (getOffsetX() / getZoom());
	}
	
	public float getYCoordinate() {
		return (currentPositionY / 100f) - (getOffsetY() / getZoom());
	}
	
	public void leftMouseDragged(int x, int y) {
		if (currentTool == 'w') {
			setCurrentPosition(x, y);
			
			bluePrintDrawing.drawWall(previousPositionX / 100f, previousPositionY / 100f, currentPositionX / 100f, currentPositionY / 100f);
			getLengthOfWall();
			
			updateGeometry();
			updateObserver();
		} else if (currentTool == 'r') {
			setCurrentPosition(x, y);
			
			bluePrintDrawing.drawRoof(previousPositionX / 100f, previousPositionY / 100f, currentPositionX / 100f, currentPositionY / 100f);
			
			updateGeometry();
			updateObserver();
		}
	}
	
	public void mouseReleased() {
		if (currentTool == 'w') {
			// If the wall is changed then call the updateGeometry method
			if (bluePrintDrawing.completeWall(getXCoordinate(), getYCoordinate())) {
				updateGeometry();
			}
		} else if (currentTool == 'r') {
			// If the roof is changed then call the updateGeometry method
			if (bluePrintDrawing.completeRoof(getXCoordinate(), getYCoordinate())) {
				updateGeometry();
			}
		}
		
		updateObserver();
	}
	
	public void updateOffset(int x, int y) {
		setCurrentPosition(x, y);
		
		// Calculate new position
		int posX = offsetX + currentPositionX - previousPositionX;
		int PosY = offsetY + currentPositionY - previousPositionY;
		
		// Update rotation values
		setOffset(posX, PosY);
		
		setPreviousPosition(x, y);
	}
	
	// Create new geometry model
	private void updateGeometry() {
		Model.wireFrameModel.createFromBluePrints(getBluePrintDrawing());
	}

}
