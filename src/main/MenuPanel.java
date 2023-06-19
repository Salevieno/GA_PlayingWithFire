package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel
{
	private static final long serialVersionUID = 2167422985223813277L;
	
	public MenuPanel()
	{
//		this.setMaximumSize(new Dimension(20, 80)) ;
		
		
		JButton moveFogueira = new JButton("Mover fogueira") ;
		moveFogueira.setPreferredSize(new Dimension(40, 40)) ;
		moveFogueira.setIcon(new ImageIcon(Fogueira.getImage())) ;
		
		moveFogueira.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e)
			{ 
				Fogueira fogueira = Main.getFogueira() ;
				fogueira.setRandomPos() ;
			} 
		});
		
		this.add(moveFogueira) ;
		this.setVisible(true) ;
	}
	
}