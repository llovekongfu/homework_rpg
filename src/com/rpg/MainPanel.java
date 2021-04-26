package com.rpg;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Main panel
 */
public class MainPanel extends JPanel{
    private ImageIcon mainImage = null;

    public MainPanel(){
        mainImage = new ImageIcon("./image/background.jpg");
    }

    public void paintComponent(Graphics g){
        g.drawImage(this.mainImage.getImage(), 0, 0, this.getWidth(),this.getHeight(),this);
    }
}
