// Keith Williams
// 25/11/2015
// RoofModel

package model;

public class RoofModel {
	// Variables ********************************
	private float startPositionX, startPositionY;
	private float width, breath, height;
	private float overhang;
	private char orientation;
	
	// Constructors *****************************
	public RoofModel() {
		setOverhang(0.3f); // Default overhang is 30 centimeters
		setHeight(1.2f); // Default height is 1.2 meters
	}
	
	public RoofModel(WallModel wall) {
		this();
		
		// Set starting positions
		setStartPositionX(wall.getStartPositionX() - getOverhang());
		setStartPositionY(wall.getStartPositionY() - getOverhang());
		
		// Set Dimensions
		setBreath(wall.getHeight() + (getOverhang() * 2));
		setWidth(wall.getWidth() + (getOverhang() * 2));
		
		if (wall.getWidth() > wall.getHeight()) {
			setOrientation('v'); // Vertical
		} else {
			setOrientation('h'); // Horizontal
		}
	}
	
	// Getters and Setters **********************
	// Start position x
	public float getStartPositionX() {
		return startPositionX;
	}

	public void setStartPositionX(float startPositionX) {
		this.startPositionX = startPositionX;
	}
	
	// Start position Y
	public float getStartPositionY() {
		return startPositionY;
	}
	
	public void setStartPositionY(float startPositionY) {
		this.startPositionY = startPositionY;
	}
	
	// Width
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	// Breath
	public float getBreath() {
		return breath;	
	}
	
	public void setBreath(float breath) {
		this.breath = breath;
	}
	
	// Height
	public float getHeight() {
		return height;	
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	// Overhang
	public float getOverhang() {
		return overhang;	
	}
	
	public void setOverhang(float overhang) {
		this.overhang = overhang;
	}
	
	// Rotation
	public char getOrientation() {
		return orientation;	
	}
	
	public void setOrientation(char orientation) {
		this.orientation = orientation;
	}
	
	// Methods **********************************
	public RoofModel clone(float zoom) {
		RoofModel clone = new RoofModel();
		
		clone.setStartPositionX(getStartPositionX() * zoom);
		clone.setStartPositionY(getStartPositionY() * zoom);
		clone.setWidth(getWidth() * zoom);
		clone.setBreath(getBreath() * zoom);
		clone.setHeight(getHeight() * zoom);
		clone.setOverhang(getOverhang() * zoom);
		clone.setOrientation(getOrientation());
		
		return clone;
	}
}
