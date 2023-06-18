package main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -995199956205934258L;
	
	private static Timer timer ;		// Main timer of the game
	
	private static Life life = new Life() ;

	private Main()
	{
		init() ;
        
        timer = new Timer(2, this) ;
    	timer.start() ;					// GA will start checking for keyboard events every "timer" miliseconds
	}
	
	public static void main(String[] args)
	{
		new Main() ;
	}

	private void init()
	{
		setTitle("GA da fogueira com bichinhos") ;
		setSize(500, 500) ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        add(life) ;
        setVisible(true) ;
	}
	
	public static int randomIntFromTo(int min, int max) { return ThreadLocalRandom.current().nextInt(min, max + 1) ;}
	
	public static Image loadImage(String filePath)
	{
		// this is not throwing an exception because it's not loading a file, it's creating a new ImageIcon
		Image image = new ImageIcon(filePath).getImage() ;
		if (image.getWidth(null) != -1 & image.getHeight(null) != -1)
		{
			return image ;
		}
		else
		{
			System.out.println("Image not found at " + filePath);
			return null ;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		life.repaint() ;
	}
}
