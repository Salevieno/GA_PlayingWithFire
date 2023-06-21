package main;

import java.awt.Image;
import java.awt.Point;

public class Bichinho
{
	private Point pos  ;
	private double gene ;
	private double temperature ;

	private final static Image image = Main.loadImage("./bichinho.png");
	private final static int behavior = 1 ;
	private final static double MUTATION_RATE = 0.1 ;
	private final static int INIT_TEMPERATURE = 100 ;
	private final static int MAX_TEMPERATURE = 320 ;
	private final static int MIN_TEMPERATURE = 80 ;
	private final static int STEP = 3 ;
	
	public Bichinho()
	{
		pos = generateInitialPos() ;
		gene = Math.random() ;
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
	
	public void move(Point fogueiraPos, int fogueiraIntensity)
	{
		if (behavior == 0)
		{
			double angle = calcAngleToPoint(fogueiraPos, pos) ;
			boolean irParaFogueira = Math.random() <= gene ;
			if (irParaFogueira)
			{
				pos.x += STEP * Math.cos(angle) ;
				pos.y += STEP * Math.sin(angle) ;
			}
			else
			{
				pos.x += -STEP * Math.cos(angle) ;
				pos.y += -STEP * Math.sin(angle) ;
			}
		}
		if (behavior == 1)
		{
//			moveAround(fogueiraPos) ;
			if (distToFogueiraIsFine(fogueiraIntensity))
			{
				moveAround(fogueiraPos) ;
			}
			else
			{
				if (tooFarFromFogueira(fogueiraIntensity))
				{
					moveTowards(fogueiraPos) ;
				}
				else
				{
					moveAway(fogueiraPos) ;
				}
			}
		}
	}
	
	public void move4Dir(Point fogueiraPos)
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
		
		if (Math.random() <= MUTATION_RATE)
		{
			offspring.setGene(Math.random()) ;
		}
		
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
	
	private void moveTowards(Point targetPos)
	{
		double angle = calcAngleToPoint(targetPos, pos) ;

		pos.x += STEP * Math.cos(angle) ;
		pos.y += STEP * Math.sin(angle) ;
	}
	
	private void moveAway(Point targetPos)
	{
		double angle = calcAngleToPoint(targetPos, pos) ;

		pos.x += -STEP * Math.cos(angle) ;
		pos.y += -STEP * Math.sin(angle) ;
	}
	
	private void moveAround(Point targetPos)
	{
		double angle = calcAngleToPoint(targetPos, pos) ;

		pos.x += STEP * Math.cos(angle + Math.PI / 2.0) ;
		pos.y += STEP * Math.sin(angle + Math.PI / 2.0) ;
	}
	
	private double calcAngleToPoint(Point startPoint, Point centerPoint)
	{
		
		if (startPoint.equals(centerPoint)) { return 0 ;}
		
		double dx = centerPoint.x - startPoint.x ;
		double dy = centerPoint.y - startPoint.y ;
		double angle = 0 ;
		
		int quadrant = 0 ;
		if (centerPoint.x <= startPoint.x & centerPoint.y <= startPoint.y) { quadrant = 0 ;}
		if (startPoint.x <= centerPoint.x & centerPoint.y <= startPoint.y) { quadrant = 1 ;}
		if (startPoint.x <= centerPoint.x & startPoint.y <= centerPoint.y) { quadrant = 2 ;}
		if (centerPoint.x < startPoint.x & startPoint.y < centerPoint.y) { quadrant = 3 ;}

		if (quadrant == 0)
		{
			angle = Math.atan(dy / dx) ;
		}
		if (quadrant == 1)
		{
			angle = Math.PI + Math.atan(dy / dx) ;
		}
		if (quadrant == 2)
		{
			angle = Math.PI + Math.atan(dy / dx) ;
		}
		if (quadrant == 3)
		{
			angle = 2 * Math.PI + Math.atan(dy / dx) ;
		}

		return angle ;
		
	}
	
	
	// ********** Second implementation **************
	
	private boolean distToFogueiraIsFine(int fogueiraIntensity)
	{
		double preferredTemperature = fogueiraIntensity * gene ;
		double minAcceptableTemperature = (1 - 0.1) * preferredTemperature ;
		double maxAcceptableTemperature = (1 + 0.1) * preferredTemperature ;
		
		return minAcceptableTemperature <= temperature & temperature <= maxAcceptableTemperature ;		
	}
	
	private boolean tooFarFromFogueira(int fogueiraIntensity)
	{
		double preferredTemperature = fogueiraIntensity * gene ;
		double minAcceptableTemperature = (1 - 0.1) * preferredTemperature ;
		
		return temperature <= minAcceptableTemperature ;		
	}
	
	private boolean tooCloseToFogueira(int fogueiraIntensity)
	{
		double preferredTemperature = fogueiraIntensity * gene ;
		double maxAcceptableTemperature = (1 + 0.1) * preferredTemperature ;
		
		return maxAcceptableTemperature <= temperature ;	
	}
	
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
