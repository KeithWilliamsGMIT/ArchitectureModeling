// Keith Williams
// 02/10/2015
// PredefinedGeometryModel class - class that creates predefined shapes. It inherits from the GeometryModel class

package model;

public class PredefinedGeometryModel extends GeometryModel {
	// Constructors *****************************
	public PredefinedGeometryModel () {
		//super("cube");
		createCube();
	}
	
	public PredefinedGeometryModel (String type) {
		switch (type) {
			case "Prism":
				createPrism();
				break;
			default:
				createCube();
		}
	}
	
	// Methods **********************************
	// Cubes can be manipulated into walls
	public void createCube() {
		VertexModel[] variableArray;
		
		VertexModel v0 = new VertexModel(-1, -1, -1);
		VertexModel v1 = new VertexModel(1, -1, -1);
		VertexModel v2 = new VertexModel(-1, 1, -1);
		VertexModel v3 = new VertexModel(1, 1, -1);
		VertexModel v4 = new VertexModel(-1, -1, 1);
		VertexModel v5 = new VertexModel(1, -1, 1);
		VertexModel v6 = new VertexModel(-1, 1, 1);
		VertexModel v7 = new VertexModel(1, 1, 1);
		
		super.vertices.add(v0);
		super.vertices.add(v1);
		super.vertices.add(v2);
		super.vertices.add(v3);
		super.vertices.add(v4);
		super.vertices.add(v5);
		super.vertices.add(v6);
		super.vertices.add(v7);
		
		variableArray = new VertexModel[] {v1, v5, v4, v0};
		FaceModel f0 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v3, v2, v6, v7};
		FaceModel f1 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v1, v3, v7, v5};
		FaceModel f2 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v5, v7, v6, v4};
		FaceModel f3 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v4, v6, v2, v0};
		FaceModel f4 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v3, v1, v0, v2};
		FaceModel f5 = new FaceModel (variableArray);
		
		super.faces.add(f0);
		super.faces.add(f1);
		super.faces.add(f2);
		super.faces.add(f3);
		super.faces.add(f4);
		super.faces.add(f5);
	}
	
	public void setWall(float x, float y, float width, float breath, float base, float height) {
		// Clear all vertices and create a cube if it's not already one
		if (vertices.size() != 8) {
			vertices.clear();
			createCube();
		}
		
		base *= -1;
		height *= -1;
		
		// Set starting position
		super.vertices.get(0).position[0] = x;
		super.vertices.get(2).position[0] = x;
		super.vertices.get(4).position[0] = x;
		super.vertices.get(6).position[0] = x;
		
		super.vertices.get(0).position[1] = y;
		super.vertices.get(1).position[1] = y;
		super.vertices.get(4).position[1] = y;
		super.vertices.get(5).position[1] = y;
		
		// Set width
		super.vertices.get(1).position[0] = x + width;
		super.vertices.get(3).position[0] = x + width;
		super.vertices.get(5).position[0] = x + width;
		super.vertices.get(7).position[0] = x + width;
		
		// Set Breath
		super.vertices.get(2).position[1] = y + breath;
		super.vertices.get(3).position[1] = y + breath;
		super.vertices.get(6).position[1] = y + breath;
		super.vertices.get(7).position[1] = y + breath;
		
		// Set Height
		super.vertices.get(0).position[2] = height;
		super.vertices.get(1).position[2] = height;
		super.vertices.get(2).position[2] = height;
		super.vertices.get(3).position[2] = height;
		
		// Set Base
		super.vertices.get(4).position[2] = base;
		super.vertices.get(5).position[2] = base;
		super.vertices.get(6).position[2] = base;
		super.vertices.get(7).position[2] = base;
	}
	
	// Prism shapes will be used for roofs
	public void createPrism() {
		VertexModel[] variableArray;
		
		VertexModel v0 = new VertexModel(-1, 0, -1);
		VertexModel v1 = new VertexModel(1, 0, -1);
		VertexModel v2 = new VertexModel(-1, -1, 1);
		VertexModel v3 = new VertexModel(1, -1, 1);
		VertexModel v4 = new VertexModel(-1, 1, 1);
		VertexModel v5 = new VertexModel(1, 1, 1);
		
		super.vertices.add(v0);
		super.vertices.add(v1);
		super.vertices.add(v2);
		super.vertices.add(v3);
		super.vertices.add(v4);
		super.vertices.add(v5);
		
		variableArray = new VertexModel[] {v1, v3, v2, v0};
		FaceModel f0 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v1, v5, v3};
		FaceModel f1 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v3, v5, v4, v2};
		FaceModel f2 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v2, v4, v0};
		FaceModel f3 = new FaceModel (variableArray);
		
		variableArray = new VertexModel[] {v5, v1, v0, v4};
		FaceModel f4 = new FaceModel (variableArray);
		
		super.faces.add(f0);
		super.faces.add(f1);
		super.faces.add(f2);
		super.faces.add(f3);
		super.faces.add(f4);
	}
	
	public void setRoof(float x, float y, float width, float breath, float base, float height, char orientation) {
		// Clear all vertices and create a cube if it's not already one
		if (vertices.size() != 6) {
			vertices.clear();
			createPrism();
		}
		
		base *= -1;
		height *= -1;
		
		if (orientation == 'h') {
			// Horizontal
			
			super.vertices.get(0).position[0] = x; // + inset here
			super.vertices.get(2).position[0] = x;
			super.vertices.get(4).position[0] = x;
			
			super.vertices.get(0).position[1] = y + breath / 2;
			super.vertices.get(1).position[1] = y + breath / 2;
			super.vertices.get(2).position[1] = y;
			super.vertices.get(3).position[1] = y;
			
			// Set width
			super.vertices.get(1).position[0] = x + width; // - inset here
			super.vertices.get(3).position[0] = x + width;
			super.vertices.get(5).position[0] = x + width;
			
			// Set Breath
			super.vertices.get(4).position[1] = y + breath;
			super.vertices.get(5).position[1] = y + breath;
			
			// Set Base
			super.vertices.get(2).position[2] = base;
			super.vertices.get(3).position[2] = base;
			super.vertices.get(4).position[2] = base;
			super.vertices.get(5).position[2] = base;
			
			// Set Height
			super.vertices.get(0).position[2] = base + height;
			super.vertices.get(1).position[2] = base + height;
		} else {
			// Vertical
			
			// Set starting position
			super.vertices.get(1).position[0] = x + width / 2;
			super.vertices.get(0).position[0] = x + width / 2;
			super.vertices.get(3).position[0] = x;
			super.vertices.get(2).position[0] = x;
			
			super.vertices.get(1).position[1] = y; // + inset here
			super.vertices.get(3).position[1] = y;
			super.vertices.get(5).position[1] = y;
			
			// Set width
			super.vertices.get(5).position[0] = x + width;
			super.vertices.get(4).position[0] = x + width;
			
			// Set Breath
			super.vertices.get(0).position[1] = y + breath; // - inset here
			super.vertices.get(2).position[1] = y + breath;
			super.vertices.get(4).position[1] = y + breath;
			
			// Set Base
			super.vertices.get(3).position[2] = base;
			super.vertices.get(5).position[2] = base;
			super.vertices.get(2).position[2] = base;
			super.vertices.get(4).position[2] = base;
			
			// Set Height
			super.vertices.get(0).position[2] = base + height;
			super.vertices.get(1).position[2] = base + height;
		}
	}
	
}
