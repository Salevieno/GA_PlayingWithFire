package main;

import java.awt.Image;
import java.awt.Point;

public class Fogueira
{
	private Point pos ;
	private final Image image = Main.loadImage("./fogueira.png");
	private final int intensity = 400 ;
	
	public Fogueira()
	{
		pos = new Point(450, 50) ;
	}
	
	public void display()
	{
		DrawingOnPanel.DrawImage(image, pos, 0, Align.center, 1) ;
	}
	
	public Point getPos() { return pos ;}
	public int getIntensity() { return intensity ;}
	
}
