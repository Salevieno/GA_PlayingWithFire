package main;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;

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
