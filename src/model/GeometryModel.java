// Keith Williams
// 02/10/2015
// GeometryModel - base class for all shapes
//					Contains vertices, edges and methods common to all shapes

package model;

import java.util.ArrayList;
//import java.util.Collections; // Painters Algorithm

public class GeometryModel {
	// Variables ********************************
	private final float ROTATION_DAMPING = 57;
	
	public ArrayList<VertexModel> vertices = new ArrayList<VertexModel>();
	public ArrayList<FaceModel> faces = new ArrayList<FaceModel>();
	public String name;
	
	// Constructors *****************************
	public GeometryModel () {
		name = "default";
	}
	
	public GeometryModel (String n) {
		name = n;
	}
	
	// Methods **********************************
	public GeometryModel manipulateGeometry (int x, int y, float zoom) {
		GeometryModel rotatedModel = new GeometryModel();

	    float xx = x / ROTATION_DAMPING;
		float yy = y / ROTATION_DAMPING;
		
		int i;
		int vertexLength = vertices.size();
		int faceLength = faces.size();
	    
	    // Copy scaled vertices
	    for (i = 0; i < vertexLength; ++i) {
	    	VertexModel v = vertices.get(i);
	    	VertexModel vertex = new VertexModel(v.position[0], v.position[1], v.position[2]);
	    	scale(vertex, zoom);
	    	rotatedModel.vertices.add(vertex);
	    }
	    
	    // Copy faces
	    for (i = 0; i < faceLength; ++i) {
	    	VertexModel[] vs = new VertexModel[faces.get(i).vertices.size()];
	    	
	    	for (int j = 0; j < vs.length; ++j) {
	    		//vs[j] = vertices.indexOf(faces.get(i).vertices.get(j));
	    		vs[j] = rotatedModel.vertices.get(vertices.indexOf(faces.get(i).vertices.get(j)));
	    	}
	    	
	    	//FaceModel face = new FaceModel(rotatedModel.vertices.get(vs[0]), rotatedModel.vertices.get(vs[1]), rotatedModel.vertices.get(vs[2]), rotatedModel.vertices.get(vs[3]));
	    	FaceModel face = new FaceModel(vs);
	    	rotatedModel.faces.add(face);
	    }
	    
	    // Rotate vertices
	    rotate (rotatedModel.vertices, xx, yy, 0);
	    
	    // Painters Algorithm
	    // Sort faces by depth
	    //Collections.sort(rotatedModel.faces);
	    
	    return rotatedModel;
	}
	
	private void scale (VertexModel v, float zoom) {
		for (int j = 0; j < v.position.length; ++j) {
			v.position[j] *= zoom;
		}
	}
	
	public void rotate(ArrayList<VertexModel> vertices, float angleX, float angleY, float angleZ) {		
	    double s;
	    double c;
	    
	    int i;
	    
	    int vertexLength = vertices.size();
		
	    // Rotate X
	    if (angleX != 0) {
		    s = Math.sin(angleX);
		    c = Math.cos(angleX);
		    
		    for (i = 0; i < vertexLength; ++i) {
		    	VertexModel v = vertices.get(i);
		        float y = v.position[1];
		        float z = v.position[2];
		        v.position[1] = (float) (y * c - z * s);
		        v.position[2] = (float) (z * c + y * s);
		    }
	    }
	    
	    // Rotate Y
	    if (angleY != 0) {
		    s = Math.sin(angleY);
		    c = Math.cos(angleY);
		    
		    for (i = 0; i < vertexLength; ++i) {
		    	VertexModel v = vertices.get(i);
		        float x = v.position[0];
		        float z = v.position[2];
		        v.position[0] = (float)(x * c - z * s);
		        v.position[2] = (float)(z * c + x * s);
		    }
	    }
	    
	    // Rotate Z
	    if (angleZ != 0) {
		    s = Math.sin(angleZ);
		    c = Math.cos(angleZ);
		    
		    for (i = 0; i < vertexLength; ++i) {
		    	VertexModel v = vertices.get(i);
		        float x = v.position[0];
		        float y = v.position[1];
		        v.position[0] = (int)(x * c - y * s);
		        v.position[1] = (int)(y * c + x * s);
		    }
	    }
	}
	
}
