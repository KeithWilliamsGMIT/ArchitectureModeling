// Keith Williams
// 14/10/2015
// WireFrameModel

package model;

import java.util.Collections;

public class WireFrameModel extends DisplayModel {
	// Variables ********************************
	private GeometryModel geometry;
	private short[] rotation = new short[3]; // (x,y,z)
	
	private int previousPositionX, previousPositionY;
	private int currentPositionX, currentPositionY;
	
	// Constructors *****************************
	public WireFrameModel() {
		 setGeometry(new PredefinedGeometryModel()); // polymorphism
		 setZoom(100f); // Default zoom is 100%
	}
	
	// Getters and Setters **********************
	// Geometry
	public GeometryModel getGeometry() {
		return geometry;
	}
	
	public void setGeometry(GeometryModel model) {
		geometry = model;
		updateObserver();
	}
	
	// Rotation
	public short[] getRotation() {
		return rotation;
	}
	
	public void setRotation(short x, short y, short z) {
		rotation[0] = x;
		rotation[1] = y;
		rotation[2] = z;
		
		updateObserver();
	}
	
	// Previous position
	public void setPreviousPosition(int x, int y) {
		previousPositionX = x;
		previousPositionY = y;
	}
	
	// Current position
	public void setCurrentPosition(int x, int y) {
		currentPositionX = x;
		currentPositionY = y;
	}
	
	// Methods **********************************
	// Create a new geometry from blueprints provided
	public void createFromBluePrints(BluePrintDrawingModel bluePrints) {
		GeometryModel updateGeometryModel = new GeometryModel();
		WallModel currentWall;
		DoorModel currentDoor;
		RoofModel currentRoof;
		PredefinedGeometryModel predifinedModel;
		
		float x, y, width, height, base;
		int i, j;
		
		// Loop through all the walls in the ArrayList in the blue print model
		for (i = 0; i < bluePrints.getWalls().size(); ++i) {
			currentWall = bluePrints.getWalls().get(i);
			
			// Sort doors from distance from start position
			Collections.sort(currentWall.getDoors());
			
			for (j = 0; j < (currentWall.getDoors().size() * 2) + 1; ++j) {
				predifinedModel = new PredefinedGeometryModel();
				
				x = currentWall.getStartPositionX();
				y = currentWall.getStartPositionY();
				width = currentWall.getWidth();
				height = currentWall.getHeight();
				base = 0;
				
				if (j == 0) {
					if (currentWall.getDoors().size() != 0) {
						currentDoor = currentWall.getDoors().get(0);
						
						if (currentWall.getWidth() > currentWall.getHeight()) {
							width = currentDoor.getPosition() - (currentDoor.getWidth() / 2);
						} else {
							height = currentDoor.getPosition() - (currentDoor.getWidth() / 2);
						}
					}
				} else if ((j / 2) - 1 != currentWall.getDoors().size() - 1) {
					if (j % 2 == 1) {
						currentDoor = currentWall.getDoors().get(j / 2);
						
						if (currentWall.getWidth() > currentWall.getHeight()) {
							x = currentWall.getStartPositionX() + currentDoor.getPosition() - (currentDoor.getWidth() / 2);
							width = currentDoor.getWidth();
						} else {
							y = currentWall.getStartPositionY() + currentDoor.getPosition() - (currentDoor.getWidth() / 2);
							height = currentDoor.getWidth();
						}
						
						base = currentDoor.getHeight();
					} else {
						currentDoor = currentWall.getDoors().get((j / 2) - 1);
						
						if (currentWall.getWidth() > currentWall.getHeight()) {
							x = currentWall.getStartPositionX() + (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
							width = (currentWall.getDoors().get(j / 2).getPosition() - (currentWall.getDoors().get(j / 2).getWidth() / 2)) - (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
						} else {
							y = currentWall.getStartPositionY() + (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
							height = (currentWall.getDoors().get(j / 2).getPosition() - (currentWall.getDoors().get(j / 2).getWidth() / 2)) - (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
						}
					}
				} else {
					currentDoor = currentWall.getDoors().get(currentWall.getDoors().size() - 1);
					
					if (currentWall.getWidth() > currentWall.getHeight()) {
						x = currentWall.getStartPositionX() + (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
						width = currentWall.getWidth() - (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
					} else {
						y = currentWall.getStartPositionY() + (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
						height = currentWall.getHeight() - (currentDoor.getPosition() + (currentDoor.getWidth() / 2));
					}
				}
				
				predifinedModel.setWall(x, y, width, height, base, 2.75f);
				copyToGeometry(updateGeometryModel, predifinedModel);
			}
		}
		
		// Loop through all the roofs in the roofs ArrayList in the blue print model
		for (i = 0; i < bluePrints.getRoofs().size(); ++i) {
			currentRoof = bluePrints.getRoofs().get(i);
			
			predifinedModel = new PredefinedGeometryModel("Prism");
			
			predifinedModel.setRoof(currentRoof.getStartPositionX(), currentRoof.getStartPositionY(), currentRoof.getWidth(), currentRoof.getBreath(), 2.75f, currentRoof.getHeight(), currentRoof.getOrientation());
			copyToGeometry(updateGeometryModel, predifinedModel);
		}
		
		setGeometry(updateGeometryModel);
	}
	
	private void copyToGeometry(GeometryModel geometryModel, PredefinedGeometryModel wall) {
		int j;
		
		// Copy all the vertices from the wall to the updated geometry model
		for (j = 0; j < wall.vertices.size(); ++j) {
			geometryModel.vertices.add(wall.vertices.get(j));
		}
		
		// Copy all the faces from the wall to the updated geometry model
		for (j = 0; j < wall.faces.size(); ++j) {
			geometryModel.faces.add(wall.faces.get(j));
		}
	}
	
	// mouse events
	public void mousePressed(int x, int y) {
		setPreviousPosition(x, y);
	}
	
	public void mouseDragged(int x, int y) {
		setCurrentPosition(x, y);
		
		// Calculate rotation based on how far the mouse was dragged
		short screenX = (short)((rotation[0] + currentPositionY - previousPositionY) % 360);
		short screenY = (short)((rotation[1] + currentPositionX - previousPositionX) % 360);
		short screenZ = 0;
		
		// Don't have minus values rotation
		if (screenY < 0) {
			screenY = (short) (360 + screenY);
		}
		
		if (screenX < 0) {
			screenX = (short) (360 + screenX);
		}
		
		// Update rotation values
		setRotation(screenX, screenY, screenZ);
		
		setPreviousPosition(x, y);
	}
}
