package main;

import java.awt.Image;
import java.awt.Point;

public class Fogueira
{
	private Point pos ;
	private static final Image image = Main.loadImage("./fogueira.gif");
	private final int intensity = 400 ;
	
	public Fogueira()
	{
		pos = new Point(450, 50) ;
	}
	
	public void display()
	{
		DrawingOnPanel.DrawImage(image, pos, 0, Align.center, 1) ;
	}
	
	public void setRandomPos()
	{
		pos = new Point((int) (500 * Math.random()), (int) (500 * Math.random())) ;
	}
	
	public Point getPos() { return pos ;}
	public int getIntensity() { return intensity ;}
	public static Image getImage() { return image ;}
	
}
