import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CarGame extends JPanel implements ActionListener {
    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private final int CAR_WIDTH = 40;
    private final int CAR_HEIGHT = 80;
    private final int TIMER_DELAY = 15;
    private final int MAX_OBSTACLES = 5;
    
    private Timer timer;
    private int playerX = PANEL_WIDTH / 2 - CAR_WIDTH / 2;
    private int playerY = PANEL_HEIGHT - CAR_HEIGHT - 20;
    private boolean gameRunning = true;
    private ArrayList<Rectangle> obstacles;
    private Random random;

    private Image playerCarImage;
    private Image roadImage;

    public CarGame() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(new KeyHandler());

        timer = new Timer(TIMER_DELAY, this);
        timer.start();

        obstacles = new ArrayList<>();
        random = new Random();

        loadImages();
    }

    private void loadImages() {
        playerCarImage = new ImageIcon("car.png").getImage();
        roadImage = new ImageIcon("rg.gif").getImage();
    }

    private void addObstacle() {
        if (obstacles.size() < MAX_OBSTACLES) {
            int obstacleX = random.nextInt(PANEL_WIDTH - CAR_WIDTH);
            obstacles.add(new Rectangle(obstacleX, -CAR_HEIGHT, CAR_WIDTH, CAR_HEIGHT));
        }
    }

    private void moveObstacles() {
        for (Iterator<Rectangle> it = obstacles.iterator(); it.hasNext();) {
            Rectangle obstacle = it.next();
            obstacle.y += 5;
            if (obstacle.y > PANEL_HEIGHT) {
                it.remove();
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerRect = new Rectangle(playerX, playerY, CAR_WIDTH, CAR_HEIGHT);
        for (Rectangle obstacle : obstacles) {
            if (playerRect.intersects(obstacle)) {
                gameRunning = false;
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw road background
        g.drawImage(roadImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);

        // Draw player car
        g.drawImage(playerCarImage, playerX, playerY, CAR_WIDTH, CAR_HEIGHT, this);

        // Draw obstacles as red cars
        g.setColor(Color.RED);
        for (Rectangle obstacle : obstacles) {
            g.fillRect(obstacle.x, obstacle.y, CAR_WIDTH, CAR_HEIGHT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            if (random.nextInt(100) < 3) {  // Lower frequency for obstacles
                addObstacle();
            }
            moveObstacles();
            checkCollisions();
            repaint();
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameRunning) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 0) {
                    playerX -= 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < PANEL_WIDTH - CAR_WIDTH) {
                    playerX += 10;
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Game");
        CarGame game = new CarGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}