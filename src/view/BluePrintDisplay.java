// Keith Williams
// 03/10/2015
// BluePrintDisplay - display blueprint image for user to trace

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.BluePrintController;
import model.BluePrintDrawingModel;
import model.RoofModel;
import model.WallModel;

public class BluePrintDisplay extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Variables ********************************
	BufferedImage backgroundImage;
	BufferedImage gridImage;
	BufferedImage bluePrintImage;
	BufferedImage image;
	
	Graphics2D graphics2D;
	
	int width, height;
	int offsetX, offsetY;
	
	int backgroundImageScale;
	boolean isImageVisible;
	boolean isGridVisible;
	float zoom;

	// Constructors *****************************
	public BluePrintDisplay() {
		// Set JPanel properties
		setBackground(Color.white);
		setLayout(new BorderLayout());
		
		Dimension screenSize = getToolkit().getScreenSize();
		
		width = screenSize.width;
		height = screenSize.height;
				
		// Initialize images to a new BufferedImage
		gridImage = bluePrintImage = image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
	    setVisible(true);
	}

	// Methods **********************************
	public void paint (Graphics g) {
		// Clear the previous image and create the new one by combining all the images together
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics2D = image.createGraphics();
		graphics2D.setColor(Color.black);
		
		super.paint(g);
		
		// Draw background image
		if (backgroundImage != null && isImageVisible) {
			int x = (int)((super.getWidth() - (backgroundImage.getWidth() + backgroundImageScale) * (zoom / 100f)) / 2) + offsetX;
			int y = (int)((super.getHeight() - (backgroundImage.getHeight() + backgroundImageScale)* (zoom / 100f)) / 2) + offsetY;
			
			graphics2D.drawImage(backgroundImage, x, y, (int)((backgroundImage.getWidth() + backgroundImageScale) * (zoom / 100f)), (int)((backgroundImage.getHeight() + backgroundImageScale) * (zoom / 100f)), null);
		}
		
		// Draw grid image
		if (isGridVisible) {
			graphics2D.drawImage(gridImage, 0, 0, null);
		}
		
		graphics2D.drawImage(bluePrintImage, 0, 0, null);
		
		graphics2D.dispose();
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
	
	// Load background image
	void loadBackgroundImage(String path) {
		try {                
			backgroundImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Redraw the grid when the user zooms in/out
	// Each square represents 1 square meter
	void drawGrid(float measurement) {
		gridImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics2D = gridImage.createGraphics();
		graphics2D.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int i;
		int size = (int) (zoom / measurement);
		
		int centerX = (super.getWidth() / 2) + offsetX;
		int centerY = (super.getHeight() / 2) + offsetY;
		
		// Draw centre lines
		graphics2D.setColor(Color.red);
		graphics2D.drawLine(0, centerY, super.getWidth(), centerY);
		graphics2D.drawLine(centerX, 0, centerX, super.getHeight());
		
		// Draw rest of grid
		graphics2D.setColor(Color.gray);
		
		// Draw horizontal lines
		for (i = centerY - size; i > 0; i -= size) {
			graphics2D.drawLine(0, i, super.getWidth(), i);
		}
		
		for (i = centerY + size; i < super.getHeight(); i += size) {
			graphics2D.drawLine(0, i, super.getWidth(), i);
		}
		
		// Draw vertical lines
		for (i = centerX - size; i > 0; i -= size) {
			graphics2D.drawLine(i, 0, i, super.getHeight());
		}
		
		for (i = centerX + size; i < super.getWidth(); i += size) {
			graphics2D.drawLine(i, 0, i, super.getHeight());
		}
		
		graphics2D.dispose();
	}
	
	// Draw blue prints
	void drawBluePrints(BluePrintDrawingModel drawing) {
		bluePrintImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics2D = bluePrintImage.createGraphics();
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int i;
		
		// Temporary variables used when iterating through ArrayLists
		WallModel wall;
		RoofModel roof;
		
		// Draw walls and doors
		for (i = 0; i < drawing.getWalls().size(); ++i) {
			wall = new WallModel(drawing.getWalls().get(i).getStartPositionX(), drawing.getWalls().get(i).getStartPositionY(), drawing.getWalls().get(i).getWidth(), drawing.getWalls().get(i).getHeight());
			
			wall.setStartPositionX(wall.getStartPositionX() * zoom);
			wall.setStartPositionY(wall.getStartPositionY() * zoom);
			wall.setWidth(wall.getWidth() * zoom);
			wall.setHeight(wall.getHeight() * zoom);
			
			int centerX = super.getWidth() / 2;
			int centerY = super.getHeight() / 2;
			
			// Adjust the start X and Y values by making them relative to the centre of the panel and adding offset
			int startX = (int)(centerX + wall.getStartPositionX() + offsetX); 
			int startY = (int)(centerY + wall.getStartPositionY() + offsetY);
			
			// Draw doors
			for (int j = 0; j < drawing.getWalls().get(i).getDoors().size(); ++j) {
				int x, y, width, height;
				x = startX;
				y = startY;
				width = (int)wall.getWidth();
				height = (int)wall.getHeight();
				
				if (wall.getWidth() < wall.getHeight()) {
					y = (int)(startY + ((drawing.getWalls().get(i).getDoors().get(j).getPosition() * zoom) - (drawing.getWalls().get(i).getDoors().get(j).getWidth() / 2 * zoom)));
					height = (int)(drawing.getWalls().get(i).getDoors().get(j).getWidth() * zoom);
				} else {
					x = (int)(startX + ((drawing.getWalls().get(i).getDoors().get(j).getPosition() * zoom) - (drawing.getWalls().get(i).getDoors().get(j).getWidth() / 2 * zoom)));
					width = (int)(drawing.getWalls().get(i).getDoors().get(j).getWidth() * zoom);
				}
				
				graphics2D.drawRect(x, y, width, height);
			}
			
			// Draw Walls
			graphics2D.drawRect(startX, startY, (int)wall.getWidth(), (int)wall.getHeight());
		}
		
		// Draw Roofs
		for (i = 0; i < drawing.getRoofs().size(); ++i) {
			roof = drawing.getRoofs().get(i).clone(zoom);
			
			int centerX = super.getWidth() / 2;
			int centerY = super.getHeight() / 2;
			
			// Adjust the start X and Y values by making them relative to the center of the panel and adding offset
			int startX = (int)(centerX + roof.getStartPositionX() + offsetX); 
			int startY = (int)(centerY + roof.getStartPositionY() + offsetY);
			
			int endX = (int)(startX + roof.getWidth());
			int endY = (int)(startY + roof.getBreath());
			
			graphics2D.drawRect(startX, startY, (int)roof.getWidth(), (int)roof.getBreath());
			
			if (roof.getOrientation() == 'v') {
				startX += roof.getWidth() / 2;
				endX -= roof.getWidth() / 2;
			} else {
				startY += roof.getBreath() / 2;
				endY -= roof.getBreath() / 2;
			}
			
			graphics2D.drawLine(startX, startY, endX, endY);
		}
		
		graphics2D.dispose();
	}
	
	// Add mouse listeners to the JPanel
	public void addController(BluePrintController controller) {
		addMouseListener(controller);
	    addMouseMotionListener(controller);
	    addComponentListener(controller);
	}
}
