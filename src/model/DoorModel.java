// Keith Williams
// 26/10/2015
// DoorModel - extends Comparable interface so the doors can be sorted by position when drawing the wireframe

package model;

public class DoorModel implements Comparable<DoorModel> {
	// Variables ********************************
	private float position;
	private float width, height;
	
	// Constructors *****************************
	public DoorModel() {
		// Default width and height values
		setWidth(0.927f);
		setHeight(1.981f);
	}
	
	public DoorModel(float position) {
		this();
		setPosition(position);
	}
	
	// Getters and Setters **********************
	// Position
	public float getPosition() {
		return position;
	}
	
	public void setPosition(float position) {
		this.position = position;
	}
	
	// Width
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	// Height
	public float getHeight() {
		return height;	
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	// Methods **********************************
	@Override
	public int compareTo(DoorModel o) {
		// TODO Auto-generated method stub
		int result;
		
		if (getPosition() > o.getPosition()) {
			result = 1;
		} else if (getPosition() < o.getPosition()) {
			result = -1;
		} else {
			result = 0;
		}
		
		return result;
	}
	
}
