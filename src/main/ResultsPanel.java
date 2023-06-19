package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

public class ResultsPanel extends JPanel
{
	private static final long serialVersionUID = 994460790479943275L;

	public ResultsPanel()
	{
		this.setPreferredSize(new Dimension(200, 800)) ;
		
		this.setVisible(true) ;
	}
	
	private void display()
	{

		Fogueira fogueira = Main.getFogueira() ;
        fogueira.display() ;
        
        List<Double> yValues = Main.getAvrGeneEvolution() ;
        System.out.println(yValues);
        if (yValues.isEmpty()) { return ;}
        
        DrawingOnPanel.PlotGraph(new Point(50, 500), "Média dos genes", yValues, Color.blue) ;
	}
	
	protected void paintComponent(Graphics g)
	{
        super.paintComponent(g) ;
        DrawingOnPanel.setGraphics((Graphics2D) g) ;

		display() ;
        
        Toolkit.getDefaultToolkit().sync() ;
        g.dispose() ;
	}
}
