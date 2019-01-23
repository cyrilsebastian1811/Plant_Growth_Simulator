package Plant;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class BGPanel extends JPanel implements Observer{
	Dimension size;				// holds the Dimension of the the drawPanel
	ArrayList<BGGeneration> gentracker=new ArrayList<>();
	BGGenerationSet plant;
	
	public void setPlant(BGGenerationSet plant) {
		this.plant=plant;
	}

	/*
	 * used to clear the draw canvas after the plant growth stops and the stop button is pressed*/
	public void ClearScreen() {
		repaint();
		plant=null;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		this.size=getSize();
		int z=this.size.width/2;
		int h=this.size.height-10;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.size.width, this.size.height);
		
		BGGeneration generations;
		Stem s;
		
		if(plant!=null) {
			for (int i=0;i<plant.noofGenerations();i++) {
				generations=plant.getgeneration(i);
				getColor(g2d,i);
				for(int j=0;j<generations.size();j++) {
					s=generations.getStem(j);
					g2d.drawLine((z+s.xstart), (h-s.ystart), z+s.x, (h-s.y));
				}
			}
		}
	}
	
	/*
	 * Color Change and thickness change function
	 * */
	public void getColor(Graphics g2d,int i) {
		if(i==0) {
			((Graphics2D) g2d).setStroke(new BasicStroke(8));
			g2d.setColor(new Color(102,51,0));
		}else if(i==1) {
			((Graphics2D) g2d).setStroke(new BasicStroke(7));
			g2d.setColor(new Color(153,76,0));
		}else if(i==2) {
			((Graphics2D) g2d).setStroke(new BasicStroke(4));
			g2d.setColor(new Color(204,102,0));
		}else if(i==3) {
			((Graphics2D) g2d).setStroke(new BasicStroke(3));
			g2d.setColor(new Color(102,102,0));
		}else if(i==4) {
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			g2d.setColor(new Color(0,51,0));
		}else {
			((Graphics2D) g2d).setStroke(new BasicStroke(1));
			g2d.setColor(new Color(0,204,0));
		}
	}
	
	/*
	 * gets the height the drawPanel. used to set the height of the tree for 10 generations
	 * */
	public int getheight() {
		return (int) ((this.size.height/2)*0.8);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		repaint();
	}
}
