package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Life extends JPanel
{
	private static final long serialVersionUID = 191402062127579281L;
	
	private Fogueira fogueira ;
	private List<Bichinho> bichinhos ;
	private int numberRound = 1 ;
	
	private List<Double> avrGeneEvolution = new ArrayList<>() ;
	private List<Double> avrDistEvolution = new ArrayList<>() ;
	
	private final static int numBichinhos = 200 ;
	private final static int numberRoundCheck = 300 ;
	
	public Life()
	{
		this.setPreferredSize(new Dimension(500, 500)) ;
		
		fogueira = new Fogueira() ;
		bichinhos = new ArrayList<>() ;
		for (int i = 0 ; i <= numBichinhos - 1; i += 1)
		{
			bichinhos.add(new Bichinho()) ;
		}
	}
	
	public Fogueira getFogueira() { return fogueira ;}
	
	private List<Bichinho> findSurvivors()
	{
		List<Bichinho> survivors = new ArrayList<>() ;
		bichinhos.forEach(bichinho -> { if (bichinho.isAlive()) { survivors.add(bichinho) ;} }) ;
		
		return survivors ;
	}
	
	private void updateGeneration()
	{
		System.out.println("Generation " + numberRound / numberRoundCheck + "!") ;
		avrGeneEvolution.add((int) (100 * calcAvrGene()) / 100.0) ;
		avrDistEvolution.add((int) (100 * calcAvrDist()) / 100.0) ;
		

		bichinhos.forEach(bichinho -> bichinho.updateTemperature(fogueira.getPos(), fogueira.getIntensity())) ;
		
		
		List<Bichinho> survivors = findSurvivors() ;
		
		
		if (survivors.isEmpty()) { bichinhos.removeAll(bichinhos) ;  return ;}
		
		for (int i = 0 ; i <= bichinhos.size() - 1; i += 1)
		{
			Bichinho bichinho = bichinhos.get(i) ;
			if (!bichinho.isAlive())
			{
				Bichinho pai = survivors.get(Main.randomIntFromTo(0, survivors.size() - 1)) ;
				Bichinho mae = survivors.get(Main.randomIntFromTo(0, survivors.size() - 1)) ;
				Bichinho novoBichinho = pai.reproduce(mae) ;
				
				
				bichinhos.set(i, novoBichinho) ;
			}
		}
	}
	
	private double calcAvrGene()
	{
		double sumGenes = 0 ;
		for (int i = 0 ; i <= bichinhos.size() - 1; i += 1)
		{
			sumGenes += bichinhos.get(i).getGene() ;
		}
		return sumGenes / bichinhos.size() ;
	}
	
	private double calcAvrDist()
	{
		double sumDist = 0 ;
		for (int i = 0 ; i <= bichinhos.size() - 1; i += 1)
		{
			sumDist += bichinhos.get(i).getPos().distance(fogueira.getPos()) ;
		}
		return sumDist / bichinhos.size() ;
	}
	
	public void run()
	{
		bichinhos.forEach(bichinho -> bichinho.move(fogueira.getPos())) ;
		
		if (numberRound % numberRoundCheck == 0)
		{
			updateGeneration() ;
			Main.getResultsPanel().repaint();
		}
		
		if (numberRound % 10000 == 0)
		{
			System.out.println("série dos genes: ");
			System.out.println(avrGeneEvolution);
			System.out.println("série das evoluções: ");
			System.out.println(avrDistEvolution);
		}
		
        fogueira.display() ;
		bichinhos.forEach(Bichinho::display) ;
	
		numberRound++ ;
//		System.out.println(numberRound) ;
	}
	
	
	
	public List<Double> getAvrGeneEvolution()
	{
		return avrGeneEvolution;
	}

	public List<Double> getAvrDistEvolution()
	{
		return avrDistEvolution;
	}

	protected void paintComponent(Graphics g)
	{
        super.paintComponent(g) ;
        DrawingOnPanel.setGraphics((Graphics2D) g) ;
//		Border blackline = BorderFactory.createLineBorder(Color.black);		
//		this.setBorder(blackline);

		run() ;
        
        Toolkit.getDefaultToolkit().sync() ;
        g.dispose() ;
	}
	
}
