// Keith Williams
// 22/10/2015
// BluePrintDrawingModel - Generate a list of wall models based on user input. This list is used to create the 3D geometry model.

package model;

import java.util.ArrayList;

public class BluePrintDrawingModel {
	// Variables ********************************
	private ArrayList<WallModel> walls = new ArrayList<WallModel>();
	private ArrayList<RoofModel> roofs = new ArrayList<RoofModel>();
	private WallModel currentWall;
	private WallModel tempWall;
	private RoofModel currentRoof;
	private RoofModel tempRoof;
	private float externalWallThickness = 0.25f; // default thickness of external walls is 25 centimeters
	
	// Constructors *****************************
	public BluePrintDrawingModel() {
		
	}
	
	// Getters and Setters **********************
	// Walls
	public ArrayList<WallModel> getWalls() {
		return walls;
	}
	
	// Roofs
	public ArrayList<RoofModel> getRoofs() {
		return roofs;
	}
	
	public void setTempWall() {
		tempWall = new WallModel(currentWall.getStartPositionX(), currentWall.getStartPositionY(), currentWall.getWidth(), currentWall.getHeight());
	}
	
	// Methods **********************************
	// Add wall to blueprints
	public void addWall(float x, float y) {
		WallModel wall = isOnWall(x, y);
		
		// If there is no wall located where the mouse was pressed create a wall at that point
		if (wall == null) {
			currentWall = new WallModel(x, y, externalWallThickness);
		} else { // else adjust the starting positions and dimensions of the wall slightly to fit with the the wall that was clicked on
			// Create two variables for the new starting X and Y values, which by default are equal to the coordinates the user pressed in the JPanel
			float adjustedX = x;
			float adjustedY = y;
			
			// Check if the wall that was clicked on is a horizontal wall
			if (wall.getWidth() > wall.getHeight()) {
				// Set the walls starting Y position equal to the horizontal walls starting Y position
				adjustedY = wall.getStartPositionY() + (externalWallThickness / 2);
				
				// If the wall is near the bounds of the vertical wall, adjust the walls X position so that it fits within the horizontal walls bounds 
				if (adjustedX < wall.getStartPositionX() + externalWallThickness) {
					adjustedX = wall.getStartPositionX() + (externalWallThickness / 2);
				} else if (adjustedX > wall.getStartPositionX() + wall.getWidth() - externalWallThickness) {
					adjustedX = wall.getStartPositionX() + wall.getWidth() - (externalWallThickness / 2);
				}
			} else { // do the same if it's a vertical wall except swap X and Y values and width and height values
				adjustedX = wall.getStartPositionX() + (externalWallThickness / 2);
				
				if (adjustedY < wall.getStartPositionY() + externalWallThickness) {
					adjustedY = wall.getStartPositionY() + (externalWallThickness / 2);
				} else if (adjustedY > wall.getStartPositionY() + wall.getHeight() - externalWallThickness) {
					adjustedY = wall.getStartPositionY() + wall.getHeight() - (externalWallThickness / 2);
				}
			}
			
			// Create a new wall
			currentWall = new WallModel(adjustedX, adjustedY, externalWallThickness);
		}
		
		
		setTempWall();
		
		// add the new wall to the list of walls
		getWalls().add(currentWall);
	}
	
	// Add a door to the wall that was clicked on and return true
	// If there is no door return false
	public boolean addDoor(float x, float y) {
		WallModel wall = isOnWall(x, y);
		
		if (wall != null) {
			// Get position of the door on the wall
			float position;
			
			if (wall.getWidth() > wall.getHeight()) {
				position = x - wall.getStartPositionX();
			} else {
				position = y - wall.getStartPositionY();
			}
			
			// Create a new door
			DoorModel door = new DoorModel(position);
			
			// Add door to wall
			wall.getDoors().add(door);
			
			return true;
		}
		
		return false;
	}
		
	// Add a roof if a wall is clicked on
	// If a wall wasn't clicked on return false
	public boolean addRoof(float x, float y) {
		WallModel wall = isOnWall(x, y);
		
		if (wall != null) {
			currentRoof = new RoofModel(wall);
			tempRoof = currentRoof.clone(1);
			roofs.add(currentRoof);
			
			return true;
		}
		
		return false;
	}
	
	// Delete the wall that was clicked on from the walls ArrayList
	// Return true if a wall or door was deleted so that the geometry model will be updated
	public boolean delete(float x, float y) {
		WallModel wall = isOnWall(x, y);
		
		if (wall != null) {
			DoorModel door = isOnDoor(wall, x - wall.getStartPositionX(), y - wall.getStartPositionY());
			
			if (door != null) {
				// If a door was clicked remove that door instead of the wall
				wall.getDoors().remove(door);
			} else {
				getWalls().remove(wall);
			}
			
			return true;
		}
		
		RoofModel roof = isOnRoof(x, y);
		
		if (roof != null) {
			// Remove roof from ArrayList
			getRoofs().remove(roof);
			return true;
		}
		 
		return false;
	}
	
	// Manipulate the width and height of the wall
	public void drawWall(float px, float py, float cx, float cy) {
		float halfThickness = externalWallThickness / 2f;
		float startX, startY, width, height;
		
		startX = startY = width = height = 0;
		
		float x = cx - px;
		float y = cy - py;
		
		if (currentWall != null) {
			// This if else structure determines which direction the wall will extrude based on the mouse position
			if (x > Math.abs(y) && x > 0) {
				// Adjust Width (Right)
				currentWall.setStartPositionY(tempWall.getStartPositionY());
				width = (cx - px + halfThickness);
				height = externalWallThickness;
			} else if (y < x && y < 0) {
				// Adjust Starting Y (Up)
				currentWall.setStartPositionX(tempWall.getStartPositionX());
				startY = (tempWall.getStartPositionY() - py + cy + halfThickness);
				currentWall.setStartPositionY(startY);
				height = (halfThickness + py - cy);
				width = externalWallThickness;
			} else if (y > Math.abs(x) && y > 0) {
				// Adjust Height (Down)
				currentWall.setStartPositionX(tempWall.getStartPositionX());
				height = (cy - py + halfThickness);
				width = externalWallThickness;
			} else {
				// Adjust Starting X (Left)
				currentWall.setStartPositionY(tempWall.getStartPositionY());
				startX = (tempWall.getStartPositionX() - px + cx + halfThickness);
				currentWall.setStartPositionX(startX);
				width = (halfThickness + px - cx);
				height = externalWallThickness;
			}
			
			currentWall.setWidth(width);
			currentWall.setHeight(height);
		}
	}
	
	// Manipulate the width and height of the roof
	public void drawRoof(float px, float py, float cx, float cy) {
		float startX, startY, width, breath;
		
		startX = startY = width = breath = 0;
		
		float x = cx - px;
		float y = cy - py;
		
		if (currentRoof != null) {
			if (currentRoof.getOrientation() == 'h') {
				if (x > 0) {
					// Adjust Width (Right)
					currentRoof.setStartPositionX(tempRoof.getStartPositionX());
					width = (cx - px + (currentRoof.getOverhang() * 2));
				} else {
					// Adjust Starting X (Left)
					currentRoof.setStartPositionY(tempRoof.getStartPositionY());
					startX = (tempRoof.getStartPositionX() - px + cx + currentRoof.getOverhang());
					currentRoof.setStartPositionX(startX);
					width = ((currentRoof.getOverhang() * 2) + px - cx);
				}
				
				currentRoof.setWidth(width);
			} else {
				if (y > 0) {
					// Adjust Height (Down)
					currentRoof.setStartPositionY(tempRoof.getStartPositionY());
					breath = (cy - py + (currentRoof.getOverhang() * 2));
				} else {
					// Adjust Starting Y (Up)
					currentRoof.setStartPositionX(tempRoof.getStartPositionX());
					startY = (tempRoof.getStartPositionY() - py + cy + currentRoof.getOverhang());
					currentRoof.setStartPositionY(startY);
					breath = ((currentRoof.getOverhang() * 2) + py - cy);
				}
				
				currentRoof.setBreath(breath);
			}
		}
	}
	
	// Called when the user releases the mouse button after drawing a wall
	// If the mouse is on a wall then the wall that was drawn will resize automatically
	public boolean completeWall(float x, float y) {
		WallModel wall = isOnWall(x, y);
		
		// Check if the mouse was on a wall when released
		if (wall != null) {
			// If the user drew a horizontal wall and mouse was released on a vertical wall adjust the starting X position and width
			if (wall.getWidth() > wall.getHeight() && currentWall.getHeight() > currentWall.getWidth()) {
				if (wall.getStartPositionX() <= currentWall.getStartPositionX() && wall.getStartPositionX() + wall.getWidth() > currentWall.getStartPositionX() + currentWall.getWidth()) {
					if (wall.getStartPositionY() > currentWall.getStartPositionY()) {
						currentWall.setHeight(wall.getStartPositionY() + wall.getHeight() - currentWall.getStartPositionY());
					} else {
						currentWall.setHeight((currentWall.getStartPositionY() + currentWall.getHeight()) - wall.getStartPositionY());
						currentWall.setStartPositionY(wall.getStartPositionY());
					}
				}
			} else { // else if the the user drew a vertical wall and released it on a horizontal wall adjust the starting Y position and height
				if (wall.getStartPositionY() <= currentWall.getStartPositionY() && wall.getStartPositionY() + wall.getHeight() > currentWall.getStartPositionY() + currentWall.getHeight()) {
					if (wall.getStartPositionX() > currentWall.getStartPositionX()) {
						currentWall.setWidth(wall.getStartPositionX() + wall.getWidth() - currentWall.getStartPositionX());
					} else {
						currentWall.setWidth((currentWall.getStartPositionX() + currentWall.getWidth()) - wall.getStartPositionX());
						currentWall.setStartPositionX(wall.getStartPositionX());
					}
				}
			}
			
			// return true so that the geometry model can be updated in the WireFrameDisplay class
			return true;
		}
		
		currentWall = null;
		tempWall = null;
		
		return false;
	}
	
	// Similar to the completeWall() method above except resizes wall instead
	public boolean completeRoof(float x, float y) {
		WallModel wall = isOnWall(x, y);
		
		if (currentRoof != null && tempRoof != null) {
			// Check if the mouse was on a wall when released
			if (wall != null) {
				// Check if the user drew a horizontal roof and mouse was released on a horizontal wall
				if (wall.getWidth() < wall.getHeight() && currentRoof.getOrientation() == 'h') {
					if (x > tempRoof.getStartPositionX()) {
						currentRoof.setWidth((wall.getStartPositionX() + wall.getWidth() + currentRoof.getOverhang()) - currentRoof.getStartPositionX());				
					} else {
						float difference = currentRoof.getStartPositionX();
						currentRoof.setStartPositionX(wall.getStartPositionX() - currentRoof.getOverhang());
						difference -= currentRoof.getStartPositionX();
						currentRoof.setWidth(currentRoof.getWidth() + difference);
					}
				} else { // else if the the user drew a vertical wall and released it on a horizontal wall adjust the starting Y position and height
					if (y > tempRoof.getStartPositionY()) {
						currentRoof.setBreath((wall.getStartPositionY() + wall.getHeight() + currentRoof.getOverhang()) - currentRoof.getStartPositionY());				
					} else {
						float difference = currentRoof.getStartPositionY();
						currentRoof.setStartPositionY(wall.getStartPositionY() - currentRoof.getOverhang());
						difference -= currentRoof.getStartPositionY();
						currentRoof.setBreath(currentRoof.getBreath() + difference);
					}
				}
				
				currentRoof = null;
				tempRoof = null;
				
				// return true so that the geometry model can be updated in the WireFrameDisplay class
				return true;
			}
		}
		
		currentRoof = null;
		tempRoof = null;
		
		return false;
	}
	
	// loop though all the walls to check a wall was clicked
	WallModel isOnWall(float x, float y) {
		// Temporary wall variable used in for loop
		WallModel wall;
		
		// Loop through each wall in the walls ArrayList
		for (int i = 0; i < getWalls().size(); ++i) {
			// Aliasing
			wall = getWalls().get(i);
			
			// Check if point is between the four corners of the wall
			if (x > (wall.getStartPositionX())
				&& x < (wall.getStartPositionX()) + (wall.getWidth())
				&& y > (wall.getStartPositionY())
				&& y < (wall.getStartPositionY()) + (wall.getHeight())) {
				
				return wall;
			}
		}
		
		return null;
	}
	
	// loop though all the doors on a wall to check if a door is clicked on
	DoorModel isOnDoor(WallModel wall, float x, float y) {
		// Temporary door variable used in for loop
		DoorModel door;
		
		// Loop through each door in the wall
		for (int i = 0; i < wall.getDoors().size(); ++i) {
			// Aliasing
			door = wall.getDoors().get(i);

			// If it's a horizontal wall
			if (wall.getWidth() > wall.getHeight()) {
				if (x > door.getPosition() - door.getWidth() / 2 && x < door.getPosition() + door.getWidth() / 2) {
					return door;
				}
			} else { // else it's a vertical wall
				if (y > door.getPosition() - door.getWidth() / 2 && y < door.getPosition() + door.getWidth() / 2) {
					return door;
				}
			}
		}
		
		return null;
	}
	
	// loop though all the roof models to check if a door is clicked on
	RoofModel isOnRoof(float x, float y) {
		// Temporary roof variable used in for loop
		RoofModel roof;
		
		// Loop through each wall in the walls ArrayList
		for (int i = 0; i < getRoofs().size(); ++i) {
			// Aliasing
			roof = getRoofs().get(i);
			
			// Check if point is between the four corners of the wall
			if (x > (roof.getStartPositionX())
				&& x < (roof.getStartPositionX()) + (roof.getWidth())
				&& y > (roof.getStartPositionY())
				&& y < (roof.getStartPositionY()) + (roof.getBreath())) {
				
				return roof;
			}
		}
		
		return null;
	}
}
