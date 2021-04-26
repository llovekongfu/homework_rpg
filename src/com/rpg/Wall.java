package com.rpg;

import javax.swing.ImageIcon;

public class Wall extends Position
{
     public Wall()
     {
    	 flag = 2;
    	 setIcon(new ImageIcon("./image/wall.png"));
     }
}
