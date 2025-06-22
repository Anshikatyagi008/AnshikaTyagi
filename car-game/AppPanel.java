import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AppPanel extends JPanel {

    BufferedImage bgImage;
    private ImageIcon image;
    Timer timer;
    int xPos = 210;
    int yPos = 300;
    private ArrayList<Rectangle>oppositeCars;
    private Random random;
    private Image oppositeCarImage;
    private boolean gameRunning = true;

    AppPanel() {
        setSize(500, 500);
        // setBackground(Color.BLUE);
        loadBgImage();
        callPaintAgain();
        addKeyboardControls();
        setFocusable(true);
        random = new Random();
        oppositeCars = new ArrayList<>();
        makeOppositeCars();
       
    }
   
    void loadBgImage() {
        try {
            image = new ImageIcon(AppPanel.class.getResource("rg.gif"));
            bgImage = ImageIO.read(AppPanel.class.getResource("car.png"));
            oppositeCarImage = ImageIO.read(AppPanel.class.getResource("c2.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    void makeOppositeCars(){
            for(int i = 0;i<5; i++){
                int x= random.nextInt(getWidth()-100);
                int y= -random.nextInt(300);
               
                oppositeCars.add(new Rectangle(x, y, 180, 150));
                
            
            }
    }
    
    @Override
    protected void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        Image roadimage = image.getImage();
        //image.paintIcon(this,pen,0,0);
        pen.drawImage(roadimage,0,0,500,500,this);
        pen.drawImage(bgImage, xPos, yPos, 100, 150, null);
        for(Rectangle car : oppositeCars){
            pen.drawImage(oppositeCarImage,car.x,car.y,car.width,car.height,null);
            
        }
    }
    void callPaintAgain() {
        timer = new Timer(50, (a) -> {
            moveOppositeCars();
            checkCollisions();
            repaint();
        });
        timer.start();
    }

    void moveOppositeCars(){ 
        for (Iterator<Rectangle> it = oppositeCars.iterator(); it.hasNext();) {
            Rectangle obstacle = it.next();
            obstacle.y += 5;
            if (obstacle.y >= getHeight()) {
                it.remove();
                moveOppositeCars();
                
            }
        }
    }
    private void checkCollisions() {
        if(!gameRunning)
        return;
        Rectangle playerRect = new Rectangle(xPos+10,yPos+10,80,130);
        for (Rectangle obstacle : oppositeCars) {
            Rectangle obstacleRect = new Rectangle(obstacle.x+10,obstacle.y+10,100,150);
            if (playerRect.intersects(obstacleRect)) {
                gameRunning = false;
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
    
    void addKeyboardControls() {
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(gameRunning){
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        xPos = Math.min(xPos + 10,getWidth()-100); // right move
                    }

                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        xPos = Math.max(xPos - 10,0); // left move
                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        yPos = Math.max(yPos - 10,0);// top move
                    }

                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        yPos = Math.min(yPos + 10,getWidth()-150); // down move
                    }


                
            }
        }
            @Override
            public void keyReleased(KeyEvent e) {
                
            }

        });
    }
}
