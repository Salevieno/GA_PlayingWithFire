package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -995199956205934258L;
	
	private static Timer timer ;
	private static final Dimension screenSize = new Dimension(1000, 800) ;	
	private static final Point screenPos = new Point(500, 100) ;
	private static MenuPanel menu = new MenuPanel() ;
	private static ResultsPanel resultsPanel = new ResultsPanel() ;
	private static Life life = new Life() ;

	private Main()
	{
		init() ;
        
        timer = new Timer(2, this) ;
    	timer.start() ;					// GA will start checking for keyboard events every "timer" miliseconds
	}
	
	public static ResultsPanel getResultsPanel() { return resultsPanel ;}
	public static Fogueira getFogueira() { return life.getFogueira() ;}
	public static List<Double> getAvrGeneEvolution() { return life.getAvrGeneEvolution() ;}
	public static List<Double>  getAvrDistEvolution() { return life.getAvrDistEvolution() ;}
	
	public static void main(String[] args)
	{
		new Main() ;
	}

	private void init()
	{
		this.setLayout(new GridBagLayout ()) ;

		GridBagConstraints gbcMenuPanel = new GridBagConstraints();
		gbcMenuPanel.anchor = GridBagConstraints.NORTH ;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.insets = new Insets(0, 0, 0, 0);
        
		GridBagConstraints gbcLifePanel = new GridBagConstraints();
		gbcLifePanel.anchor = GridBagConstraints.WEST ;
//        gbc2.gridx = 1;
//        gbc2.gridy = 0;
//        gbc2.insets = new Insets(0, 0, 0, 0);
        
		setTitle("GA da fogueira com bichinhos") ;
		setSize(screenSize) ;
		setLocation(screenPos) ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        add(menu, gbcMenuPanel) ;
        add(life, gbcLifePanel) ;
        add(resultsPanel, gbcMenuPanel) ;
        pack();
        setVisible(true) ;
	}
	
	public static int randomIntFromTo(int min, int max) { return ThreadLocalRandom.current().nextInt(min, max + 1) ;}
	
	public static Image loadImage(String filePath)
	{
		// this is not throwing an exception because it's not loading a file, it's creating a new ImageIcon
		Image image = new ImageIcon(filePath).getImage() ;
		
		if (image.getWidth(null) == -1 | image.getHeight(null) == -1) { System.out.println("Image not found at " + filePath) ; return null ;}
		
		return image ;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		life.repaint() ;
	}
}
