package main;

import java.awt.Image;
import java.awt.Point;

public class Bichinho
{
	private Point pos = new Point(250, 250) ;
	private final Image image = Main.loadImage("./bichinho.png");
	private double gene = Math.random() ;
	private double temperature ;
	
	private final static int INIT_TEMPERATURE = 100 ;
	private final static int MAX_TEMPERATURE = 320 ;
	private final static int MIN_TEMPERATURE = 80 ;
	private final static int STEP = 2 ;
	
	public Bichinho()
	{
		pos = generateInitialPos() ;
		temperature = INIT_TEMPERATURE ;
	}
	
	public Point randomMove()
	{
		int direction = Main.randomIntFromTo(0, 3) ;
		Point newPos = new Point() ;
		newPos.setLocation(pos) ;
		
		switch (direction)
		{
			case 0 : newPos.translate(-STEP, 0) ; break ;
			case 1 : newPos.translate(STEP, 0) ; break ;
			case 2 : newPos.translate(0, -STEP) ; break ;
			case 3 : newPos.translate(0, STEP) ; break ;
		}
		
		return newPos ;
	}
	
	public void move(Point fogueiraPos)
	{
		if (Math.random() < gene)
		{
			moveToPoint(fogueiraPos, "toward") ;
		}
		else
		{
			moveToPoint(fogueiraPos, "away") ;
		}
	}
	
	public boolean isAlive() { return (MIN_TEMPERATURE <= temperature & temperature <= MAX_TEMPERATURE) ;}
	
	public Bichinho reproduce(Bichinho partner)
	{
		Bichinho offspring = new Bichinho() ;

		double avrGene = (gene + partner.getGene()) / 2 ;
		offspring.setPos(Math.random() <= 0.5 ? new Point(pos) : new Point(partner.getPos())) ;
		offspring.setGene(avrGene) ;
		
		return offspring ;
	}
	
	public void moveToPoint(Point target, String direction)
	{
		int axis = Main.randomIntFromTo(0, 1) ;
		int mult = direction.equals("toward") ? 1 : -1 ;
		if ( axis == 0 )
		{
			pos.x += pos.x < target.x ? mult * STEP : - mult * STEP ;
		}
		else
		{
			pos.y += pos.y < target.y ? mult * STEP : - mult * STEP ;
		}
	}
	
	public void updateTemperature(Point fogueiraPos, int fogueiraIntensity)
	{
		double dist = pos.distance(fogueiraPos) ;
		temperature = - 5 / 3.0 * dist + fogueiraIntensity ;
	}
	
	public void display()
	{
		DrawingOnPanel.DrawImage(image, pos, 0, Align.center, 1) ;
	}
	
	public static Point generateInitialPos()
	{
		return new Point(Main.randomIntFromTo(100, 360), Main.randomIntFromTo(100, 360)) ;
	}
	
//	private boolean isWithinRange(Point pos)
//	{
//		return (0 <= pos.x & pos.x <= 500 & 0 <= pos.y & pos.y <= 500) ;
//	}
	
	public Point getPos()
	{
		return pos;
	}

	public double getGene()
	{
		return gene;
	}

	public double getTemperature()
	{
		return temperature;
	}
	
	public void setPos(Point pos)
	{
		this.pos = pos;
	}

	public void setGene(double gene)
	{
		this.gene = gene;
	}
	
}
