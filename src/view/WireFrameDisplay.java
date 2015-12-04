// Keith Williams
// 02/10/2015
// ModelDisplay class - main class to display 3D model

package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import controller.WireFrameController;
import model.PredefinedGeometryModel;
import model.FaceModel;
import model.GeometryModel;

//import model.VertexModel; // Keep this for when drawing vertices

public class WireFrameDisplay extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	GeometryModel geometry = new PredefinedGeometryModel();
	GeometryModel manipulatedGeometry;
	short[] rotation = new short[3]; // (x,y,z)
	float zoom;
	
	// Constructors *****************************
	public WireFrameDisplay() {
		setVisible(true);
	}
	
	// Methods **********************************
	public void paint(Graphics g) {
		int i;
		
		// Variables used when filling walls
		int[] xPoints;
		int[] yPoints;
		
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		
		// Rotate and scale geometry
		manipulatedGeometry = geometry.manipulateGeometry(rotation[0], rotation[1], zoom);
		
		super.paint(g);
		
		// Draw rotation values
		g.drawString("X: " + rotation[0] + " Y: " + rotation[1], 5, 15);
		
		// Draw faces
		for (i = 0; i < manipulatedGeometry.faces.size(); ++i) {
			FaceModel f = manipulatedGeometry.faces.get(i);
			int length = f.vertices.size();
			
			// Draw solid wall
			xPoints = new int[length];
			yPoints = new int[length];
			
			for (int v = 0; v < length; ++v) {
				// Populate arrays used for drawing walls
				xPoints[v] = (int) (f.vertices.get(v).position[0] + centerX);
				yPoints[v] = (int) (f.vertices.get(v).position[1] + centerY);
			}
			
			// Painters Algorithm
			// Set face color
			/*g.setColor(Color.lightGray);
			//g.fillPolygon(xPoints , yPoints, length);*/
			
			// Set color back to black for edges
			g.setColor(Color.black);
			g.drawPolygon(xPoints, yPoints, length);
		}
		
		// Draw and label vertices - useful when testing code
		/*int verticeDiameter = 4;
		int x1, y1;
		
		for (i = 0; i < manipulatedGeometry.vertices.size(); ++i) {
			VertexModel v = manipulatedGeometry.vertices.get(i);
			x1 = (int)(v.position[0] - (verticeDiameter / 2) + centerX);
			y1 = (int)(v.position[1] - (verticeDiameter / 2) + centerY);
		    g.drawOval(x1, y1, verticeDiameter, verticeDiameter);
		    g.drawString(" " + i, x1, y1);
		}*/
		
		g.dispose();
	}
	
	// Add WireFrameController as mouse listener to JPanel
	public void addController(WireFrameController controller) {
		addMouseListener(controller);
		addMouseMotionListener(controller);
	}
}
