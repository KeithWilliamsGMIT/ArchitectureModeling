// Keith Williams
// 26/10/2015
// WallModel

package model;

import java.util.ArrayList;

public class WallModel {
	// Variables ********************************
	private float startPositionX, startPositionY;
	private float width, height;
	private ArrayList<DoorModel> doors = new ArrayList<DoorModel>();
	
	// Constructors *****************************
	public WallModel(float x, float y) {
		setStartPositionX(x);
		setStartPositionY(y);
	}
	
	public WallModel(float x, float y, float thickness) {
		this(x - (thickness / 2), y - (thickness / 2));
		setWidth(thickness);
		setHeight(thickness);
	}
	
	public WallModel(float x, float y, float width, float height) {
		this(x, y);
		setWidth(width);
		setHeight(height);
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
	
	// Height
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	// Doors
	public ArrayList<DoorModel> getDoors() {
		return doors;
	}
	
}