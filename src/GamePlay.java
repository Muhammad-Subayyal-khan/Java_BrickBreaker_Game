import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener,ActionListener{
	
	
 private boolean play = false;
 private int Score =0;
 private int TotalBricks =21;
 
 private Timer timer;
 private int DELAY=8;
 
private int PlayerX= 310;
private int ballposX =120;
private int ballposY=350;
private int ballXdir=-1;
private int ballYdir=-2;

private MapGenerator map;

	
public GamePlay() {
map=new MapGenerator (3,7);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(true);
	timer = new Timer(DELAY,this); 
	timer.start();
}

public void paint (Graphics2D g ) {
	g.setColor(Color.black);
	g.fillRect(1,1,692,592);  //background 
	
	map.draw((Graphics2D)g);
    g.setColor(Color.yellow);  
    g.fillRect(0, 0,3, 592);
    g.fillRect(0, 0,692, 3);   //border
    g.fillRect(691, 0,3, 592);
    
    g.setColor(Color.white);
    g.setFont (new Font("serif",Font.BOLD,25));
    g.drawString(""+Score,590,30);
    
    g.setColor(Color.green);       //paddle
    g.fillRect(PlayerX,550,100,8);
    
    g.setColor(Color.yellow);
    g.fillOval(ballposX, ballposY, 20,20); //the ball
    g.dispose();
}
	@Override
	public void actionPerformed(ActionEvent e) {
	 timer.start();
	 if (play ) {
		if(new Rectangle (ballposX,ballposY,20,20).intersects(new Rectangle(PlayerX,550,100,8))) 
		{
			ballYdir=-ballYdir;
			}
	A:	for (int i =0; i <map.map.length;i++) {
			
			for (int j = 0;j <map.map[0].length;j++) {
				
				if (map.map[i][j]>0) {
					int brickX = j*map.brickWidth+80;
					int brickY=i*map.brickHeight+50;
					int brickWidth = map.brickWidth;
					int brickHeight=map.brickHeight;
					Rectangle rect = new Rectangle (brickX,brickY,brickWidth,brickHeight);
					Rectangle ballrect= new Rectangle(ballposX,ballposY,20,20);
					Rectangle brickrect= rect;
					if (ballrect.intersects(brickrect)){
						
						map.setBrickValue(0,i,j);
						TotalBricks--;
						Score+=5;
						if (ballposX+19<= brickrect.x||ballposX+1>=brickrect.x +brickrect.width) {
							
							ballXdir=-ballXdir;
							
						}else {
							ballYdir=-ballYdir;
						}break A;
					}
					
				}
			}
		}
		
		 ballposX	+=ballXdir;
		 ballposY  +=ballYdir;
		 if(ballposX<0) {
			 ballXdir= -ballXdir;
			 
		 }
		 if(ballposY<0) {
			 ballYdir= -ballYdir;
			 
		 }
		 if(ballposX>670) {
			 ballXdir= -ballXdir;
			 
		 }
	 }
	 
	 repaint();
	}	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_RIGHT) {
		if(PlayerX>=600) {
			
			PlayerX =600;
		}	else {
			moveRight();
		}
			
		}
		
			if (e.getKeyCode()== KeyEvent.VK_LEFT) {
				
				
					if(PlayerX<10) {
						
						PlayerX =10;
					}	else {
						moveLeft();
					}
			}
	}


public void moveRight() {
	
	
	play =true;
	PlayerX+=20;
	
}
public void moveLeft() {
	
	
	play =true;
	PlayerX-=20;
	
}

}
