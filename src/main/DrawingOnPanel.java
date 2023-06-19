package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawingOnPanel
{
	private static Graphics2D G ;
	
	public static void setGraphics(Graphics2D newG) { G = newG ;}
		
	public static void DrawImage(Image image, Point pos, double angle, Align align, double alpha)
	{       
		if (image == null) { System.out.println("Tentando desenhar imagem nula") ; return ; }
		
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null)) ;
		size = new Dimension (size.width, size.height) ;
		Point offset = OffsetFromPos(align, size) ;
		AffineTransform backup = G.getTransform() ;
		G.transform(AffineTransform.getRotateInstance(-angle * Math.PI / 180, pos.x, pos.y)) ;
		G.setComposite(AlphaComposite.SrcOver.derive((float) alpha)) ;
		
		G.drawImage(image, pos.x + offset.x, pos.y + offset.y, size.width, size.height, null) ;
		
		G.setComposite(AlphaComposite.SrcOver.derive((float) 1.0)) ;
        G.setTransform(backup) ;
	}

	public static void DrawText(Point pos, String alignment, double angle, String text, Font font, Color color)
	{
		// Rectangle by default starts at the left bottom
		//int TextL = UtilG.TextL(Text, font, G), TextH = UtilG.TextH(font.getSize()) ;
		//int[] offset = UtilG.OffsetFromPos(Alignment, TextL, TextH) ;
		int[] offset = new int[2] ;
		AffineTransform backup = G.getTransform() ;		
		G.setColor(color) ;
		G.setFont(font) ;
		//G.setTransform(AffineTransform.getRotateInstance(-angle * Math.PI / 180, pos.x, pos.y)) ;	// Rotate text
		G.drawString(text, pos.x + offset[0], pos.y + offset[1]) ;
        G.setTransform(backup) ;
    }
	
    public static void DrawLine(Point initPos, Point finalPos, int stroke, Color color)
    {
    	G.setColor(color);
    	G.setStroke(new BasicStroke(stroke));
    	G.drawLine(initPos.x, initPos.y, finalPos.x, finalPos.y);
    	G.setStroke(new BasicStroke(1));
    }
    
    public static void DrawPolygon(int[] x, int[] y, boolean fill, Color ContourColor, Color FillColor)
    {
    	G.setColor(ContourColor);
    	G.drawPolygon(x, y, x.length);
    	if (fill)
    	{
    		G.setColor(FillColor);
        	G.fillPolygon(x, y, x.length);
    	}
    }
    
    public static void DrawArrow(Point Pos, int size, double theta, boolean fill, double tipSize, Color color)
    {
    	double open = 0.8;
    	int ax1 = (int)(Pos.x - open*size*Math.cos(theta) - tipSize/3.5*Math.sin(theta));
    	int ay1 = (int)(Pos.y + open*size*Math.sin(theta) - tipSize/3.5*Math.cos(theta));
    	int ax2 = Pos.x;
    	int ay2 = Pos.y;
     	int ax3 = (int)(Pos.x - open*size*Math.cos(theta) + tipSize/3.5*Math.sin(theta));
     	int ay3 = (int)(Pos.y + open*size*Math.sin(theta) + tipSize/3.5*Math.cos(theta));
     	DrawPolygon(new int[] {ax1, ax2, ax3}, new int[] {ay1, ay2, ay3}, fill, color, color);
    }
        
    public static void DrawGraph(Point pos, String Title, int size, Color titleColor, Color lineColor)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 13) ;
		int arrowSize = 8 * size / 100;
		DrawText(new Point (pos.x + size/5, (int) (pos.y - size - 13)), "TopLeft", 0, Title, textFont, titleColor);
		DrawLine(pos, new Point (pos.x, (int) (pos.y - size - arrowSize)), 2, lineColor);
		DrawLine(pos, new Point ((int) (pos.x + size + arrowSize), pos.y), 2, lineColor);
		DrawArrow(new Point (pos.x + size + arrowSize, pos.y), arrowSize, 0, false, 0.4 * arrowSize, lineColor);
		DrawArrow(new Point (pos.x, pos.y - size - arrowSize), arrowSize, Math.PI / 2, false, 0.4 * arrowSize, lineColor);
		//DrawPolyLine(new int[] {pos.x - asize, pos.x, pos.x + asize}, new int[] {(int) (pos.y - 1.1*size) + asize, (int) (pos.y - 1.1*size), (int) (pos.y - 1.1*size) + asize}, 2, ColorPalette[4]);
		//DrawPolyLine(new int[] {(int) (pos.x + 1.1*size - asize), (int) (pos.x + 1.1*size), (int) (pos.x + 1.1*size - asize)}, new int[] {pos.y - asize, pos.y, pos.y + asize}, 2, ColorPalette[4]);
		DrawGrid(pos, new Point (pos.x + size, pos.y - size), 10, Color.black);
	}    

    public static void DrawGrid(Point initPos, Point finalPos, int NumSpacing, Color lineColor)
	{
		int stroke = 1;
		Dimension length = new Dimension(finalPos.x - initPos.x, initPos.y - finalPos.y) ;
		for (int i = 0 ; i <= NumSpacing - 1 ; i += 1)
		{
			// horizontal lines
			Point hPoint1 = new Point (initPos.x, initPos.y - (i + 1)*length.height/NumSpacing) ;
			Point hPoint2 = new Point (initPos.x + length.width, initPos.y - (i + 1)*length.height/NumSpacing) ;
			DrawLine(hPoint1, hPoint2, stroke, lineColor) ;	
			
			// vertical lines
			Point vPoint1 = new Point (initPos.x + (i + 1)*length.width/NumSpacing, initPos.y) ;
			Point vPoint2 = new Point (initPos.x + (i + 1)*length.width/NumSpacing, initPos.y - length.height) ;
			DrawLine(vPoint1, vPoint2, stroke, lineColor) ;					
		}
	}    

    public static void DrawPolyLine(ArrayList<Integer> x, ArrayList<Integer> y, int thickness, Color color)
    {
    	int[] xCoords = new int [x.size()] ;
    	int[] yCoords = new int [y.size()] ;
    	for (int i = 0 ; i <= x.size() - 1 ; i += 1)
    	{
    		xCoords[i] = (int) x.get(i) ;
    		yCoords[i] = (int) y.get(i) ;
    	}
    	G.setColor(color);
    	G.setStroke(new BasicStroke(thickness));
    	G.drawPolyline(xCoords, yCoords, x.size());
    	G.setStroke(new BasicStroke(1));
    }	
    
    public static void PlotMultiGraph(Point pos, String title, List<List<Double>> yValues, double maxYEver, Color[] color)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 12) ;
		int size = 100;
		DrawGraph(pos, title, size, Color.blue, Color.black);
		if (1 <= yValues.size())
		{
			Point maxYPos = new Point (pos.x - size / 4, pos.y - size) ;
			DrawText(maxYPos, "TopLeft", 0, String.valueOf(maxYEver), textFont, Color.blue);
			
			// for each curve
			for (int j = 0; j <= yValues.size() - 1; j += 1)
			{
				ArrayList<Integer> xPos = new ArrayList<>() ;
				ArrayList<Integer> yPos = new ArrayList<>() ;
				for (int i = 0; i <= yValues.get(j).size() - 1; i += 1)
				{
					if (2 <= yValues.get(j).size())
					{
						xPos.add(pos.x + size * i / (yValues.get(j).size() - 1)) ;
					}
					else
					{
						xPos.add(pos.x + size * i) ;
					}
					List<Double> yList = yValues.get(j) ;
					double y = Double.parseDouble(String.valueOf(yList.get(i))) ;
					yPos.add((pos.y - (int) (size * y / maxYEver))) ;
				}
				DrawPolyLine(xPos, yPos, 2, color[j]);
			}
		}
	}
    
    public static void PlotGraph(Point pos, String title, List<Double> yValues, Color color)
	{
    	Font textFont = new Font("SansSerif", Font.BOLD, 12) ;
		int size = 100;
		double maxY = Collections.max(yValues) ;
		
		DrawGraph(pos, title, size, Color.blue, Color.black);
		if (1 <= yValues.size())
		{
			Point maxYPos = new Point (pos.x - size / 4, pos.y - size) ;
			DrawText(maxYPos, "TopLeft", 0, String.valueOf(maxY), textFont, Color.blue);
			
			ArrayList<Integer> xPos = new ArrayList<>() ;
			ArrayList<Integer> yPos = new ArrayList<>() ;
			for (int i = 0; i <= yValues.size() - 1; i += 1)
			{
				if (2 <= yValues.size())
				{
					xPos.add(pos.x + size * i / (yValues.size() - 1)) ;
				}
				else
				{
					xPos.add(pos.x + size * i) ;
				}
				double y = Double.parseDouble(String.valueOf(yValues.get(i))) ;
				yPos.add((pos.y - (int) (size * y / maxY))) ;
			}
			DrawPolyLine(xPos, yPos, 2, color);
		}
	}

	public static Point OffsetFromPos(Align alignment, Dimension size)
	{
		Point offset = new Point(0, 0) ;
		switch (alignment)
		{
			case topLeft:
			{
				offset = new Point(0, 0) ;
				
				break ;
			}
			case centerLeft:
			{
				offset = new Point(0, -size.height / 2) ;
				
				break ;
			}
			case bottomLeft:
			{
				offset = new Point(0, -size.height) ;
				
				break ;
			}
			case topCenter:
			{
				offset = new Point(-size.width / 2, 0) ;
				
				break ;
			}
			case center:
			{
				offset = new Point(-size.width / 2, -size.height / 2) ;
				
				break ;
			}
			case bottomCenter:
			{
				offset = new Point(-size.width / 2, -size.height) ;
				
				break ;
			}
			case topRight:
			{
				offset = new Point(-size.width, 0) ;
				
				break ;
			}
			case centerRight:
			{
				offset = new Point(-size.width,  -size.height / 2) ;
				
				break ;
			}
			case bottomRight:
			{
				offset = new Point(-size.width,  -size.height) ;
				
				break ;
			}
		
		}
			
		return offset ;
	}
}
