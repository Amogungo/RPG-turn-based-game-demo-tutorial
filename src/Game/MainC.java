package Game;

import Characters.ClassType;
import demog.Map;
import demog.Gameframe;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import Game.Items;
import static Game.PartyClass.party;
import java.util.IdentityHashMap;

public class MainC extends JPanel
implements ActionListener, KeyListener {

    static final int WIDTH = 700;
    static final int HEIGHT = 360;
    static final int SIZE = 20;
    static final int DELAY = 1000;
    
    

    ArrayList<Point> movement = new ArrayList<>();
    ArrayList<Point> enemies = new ArrayList<>();


    int dx = 1;
    int dy = 0;

    Timer timer;
    Random random = new Random();
    
    private boolean battleStarted = false;

    //Images for icons
    private Image playerImage; 
    private Image[] enemyImages;
    private IdentityHashMap<Point, Image> enemyImageMap = new IdentityHashMap<>();
    
    public MainC() {

         playerImage = new ImageIcon(getClass().getResource("/images/block.png")).getImage();
        enemyImages = new Image[]{
        new ImageIcon(getClass().getResource("/images/Enemy/Brute.png")).getImage(),
        new ImageIcon(getClass().getResource("/images/Enemy/BUGGER.png")).getImage(),
        new ImageIcon(getClass().getResource("/images/Enemy/GOOMBAFU.jpg")).getImage(),
        new ImageIcon(getClass().getResource("/images/Enemy/Fire Spirit.jpg")).getImage(),
        new ImageIcon(getClass().getResource("/images/Enemy/Enemy1.jpg")).getImage()
};

        setFocusable(true);
        addKeyListener(this);

        // SPAWNS CORRECTLY NOW
    movement.add(new Point(
    (WIDTH / 2 / SIZE) * SIZE,
    (HEIGHT / 2 / SIZE) * SIZE
    ));

        spawnEnemies();

        // enemy movement in game map
        timer = new Timer(DELAY, this);
        timer.start();
    }


public void spawnEnemies() {
    for (int i = 0; i < 5; i++) {
        int x = random.nextInt(WIDTH / SIZE) * SIZE;
        int y = random.nextInt(HEIGHT / SIZE) * SIZE;

        Point enemy = new Point(x, y);
        enemies.add(enemy);

        // assign random image to this enemy
        enemyImageMap.put(enemy, enemyImages[random.nextInt(enemyImages.length)]);
    }
}

    public void moveS() {

        Point head = movement.get(0);

        Point newHead = new Point(
            head.x + dx * SIZE,
            head.y + dy * SIZE
        );
        
        if (newHead.x < 0)
    newHead.x = WIDTH - SIZE;

    if (newHead.x >= WIDTH)
    newHead.x = 0;

    if (newHead.y < 0)
    newHead.y = HEIGHT - SIZE;

    if (newHead.y >= HEIGHT)
    newHead.y = 0;
    
    for (Point enemy : enemies) {

    if (newHead.equals(enemy) && !battleStarted) {
        battleStarted = true;

        Gameframe gameplay = new Gameframe(party);
        
        gameplay.pack();
        gameplay.setLocationRelativeTo(this); // centers
        gameplay.setVisible(true);
        SwingUtilities.getWindowAncestor(this).dispose();

        return;
    }
}
        movement.add(0, newHead);

        movement.remove(movement.size() - 1);
    }

    public void moveEnemies() {

        for (Point enemy : enemies) {

            int dir = random.nextInt(4);

            if (dir == 0)
                enemy.y -= SIZE;

            else if (dir == 1)
                enemy.y += SIZE;

            else if (dir == 2)
                enemy.x -= SIZE;
            else
                enemy.x += SIZE;
        
        
        if (enemy.x < 0) enemy.x = WIDTH - SIZE;
        if (enemy.x >= WIDTH) enemy.x = 0;
        if (enemy.y < 0) enemy.y = HEIGHT - SIZE;
        if (enemy.y >= HEIGHT) enemy.y = 0;

        // Check if enemy collides with any snake cube
        for (Point snakePart : movement) {
            if (enemy.equals(snakePart) && !battleStarted) {
                 battleStarted = true;
                
                Gameframe gameplay = new Gameframe(party);
                gameplay.pack();
                gameplay.setLocationRelativeTo(this);
                gameplay.setVisible(true);
                SwingUtilities.getWindowAncestor(this).dispose();
                timer.stop();
                return;
            }
        }
    }
}
    @Override
    public void actionPerformed(ActionEvent e) {

        moveEnemies();

        repaint();
    }

   
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw enemies with image instead of magenta rect
    for (Point enemy : enemies) {
    Image img = enemyImageMap.get(enemy);
    if (img != null) {
        g.drawImage(img, enemy.x, enemy.y, SIZE, SIZE, this);
    } else {
        g.setColor(Color.MAGENTA); // fallback if image fails to load
        g.fillRect(enemy.x, enemy.y, SIZE, SIZE);
        }
    }

    // Draw player with image instead of green rect
    for (Point p : movement) {
        g.drawImage(playerImage, p.x, p.y, SIZE, SIZE, this);
    }
}

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {

            dx = 0;
            dy = -1;
        }

        else if (key == KeyEvent.VK_S) {

            dx = 0;
            dy = 1;
        }

        else if (key == KeyEvent.VK_A) {

            dx = -1;
            dy = 0;
        }

        else if (key == KeyEvent.VK_D) {

            dx = 1;
            dy = 0;
        }

        moveS();

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}