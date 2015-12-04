// Keith Williams
// 02/10/2015
// Vertex class - used by all shapes to display position of each vertex

package model;

public class VertexModel {
	// Variables ********************************
	public float[] position = new float[3]; // (x,y,z)

	// Constructors *****************************
	public VertexModel (float x, float y, float z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
}
