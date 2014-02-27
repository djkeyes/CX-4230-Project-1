package evacSim.visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import evacSim.core.Cell;
import evacSim.core.Crosswalk;
import evacSim.core.CrosswalkController;
import evacSim.core.EmptyCell;
import evacSim.core.Obstacle;
import evacSim.core.Person;
import evacSim.core.SimulationController;
import evacSim.core.UpdateHandler;

public class Visualizer {

	private SimulationController mySim;
	private UpdateHandler myHandler;

	private JLabel view;
	private BufferedImage surface;

	public Visualizer(SimulationController sim) {
		mySim = sim;
		myHandler = new VisualizationHandler();
		mySim.setHandler(myHandler);
		
		surface = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
	}

	private class VisualizationHandler implements UpdateHandler {
		public void onUpdate() {
	        Graphics g = surface.getGraphics();
	        
	        int width = surface.getWidth();
	        int height = surface.getHeight();
	        int rows = mySim.getGrid().getRows();
	        int cols = mySim.getGrid().getCols();
	        
	        for(Cell c : mySim.getGrid()){
	        	Color rectColor;
	        	if(c instanceof Obstacle)
	        		rectColor = Color.RED;
	        	else if(c instanceof Person)
	        		rectColor = Color.GREEN;
	        	else if(c instanceof EmptyCell)
	        		rectColor = Color.WHITE;
	        	else if(c instanceof Crosswalk)
	        		if(CrosswalkController.getInstance().isCrosswalkOpen())
	        			rectColor = Color.YELLOW;
	        		else
	        			rectColor = Color.RED;
	        	else
	        		rectColor = Color.GRAY;
	        	
	        	int row = c.getRow();
	        	int col = c.getCol();
	        	
	        	g.setColor(rectColor);
	        	int x1 = col*width/cols;
	        	int x2 =  (col+1)*width/cols;
	        	int y1 = row*height/rows;
	        	int y2 = (row+1)*height/rows;
	        	g.fillRect(x1,y1,x2-x1,y2-y1);
	        }
	        
	        
	        view.repaint();
		}
	}

	public void show() {
		// do some JFrame boilerplate
		JFrame frame = new JFrame("Cellular Automata Hazard Evaculation Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// I'm backing this with a JPanel containing a Bufferent Image. We could probably also just use a
		// JApplet, or a Canvas, or something. Java GUIs aren't my forte.

        frame.setSize(600, 600);
        frame.setContentPane(view);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
	}

}
