package com.rpg;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;

/**
 * hunter
 */
public class Hunter extends Position {

	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	/*	private int startX;
        private int startY;*/
	public Hunter() {
		xx = 0;
		yy = 0;
		flag = 1;
		ImageIcon icon = new ImageIcon("./image/hunter.png");
		icon.setImage(icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
		this.setIcon(icon);
	}	
/*    public int getStartX()
	{
		return startX;
	}
	public void setStartX(int startX)
	{
		this.startX = startX;
	}
	public int getStartY()
	{
		return startY;
	}*/
/*	public void setStartY(int startY)
	{
		this.startY = startY;
	}*/

	public void moveXX(int xx) {
		this.xx += xx;
	}

	public void moveYY(int yy) {
		this.yy += yy;
	}
}
