// Keith Williams
// 02/10/2015
// Face class - used to connect vertices together to form faces

package model;

import java.util.ArrayList;

public class FaceModel { // implements Comparable<FaceModel> { // Painters Algorithm
	// Variables ********************************
	public ArrayList<VertexModel> vertices = new ArrayList<VertexModel>();
	
	// Constructors *****************************
	public FaceModel (VertexModel[] vertices) {
		for (int i = 0; i < vertices.length; ++i) {
			this.vertices.add(vertices[i]);
		}
	}
	
	// Methods **********************************
	public float getDepth() {
		float deepestPoint = vertices.get(0).position[2];
		
		for (int i = 1; i < vertices.size(); ++i) {
			if (vertices.get(1).position[2] < deepestPoint) {
				deepestPoint = vertices.get(i).position[2];
			}
		}
		
		return deepestPoint;
	}

	// Painters Algorithm
	// Method from Comparable interface
	/*@Override
	public int compareTo(FaceModel o) {
		// TODO Auto-generated method stub
		float d1 = getDepth(); // Depth of this face
		float d2 = o.getDepth(); // Depth of other face
		int result;
		
		if (d1 < d2) {
			result = 1;
		} else if (d1 > d2) {
			result = -1;
		} else {
			result = 0;
		}
		
		return result;
	}*/
}
