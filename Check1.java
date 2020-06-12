package Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
/*concept of inheritance*/
public class Check1 extends JPanel implements ActionListener, DisplaySet {
/*data hiding encapsulation*/
    private Dimension d;
    private final Font text = new Font("Helvetica", Font.BOLD, 14);

    private Image image;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean gameOver = true;
    private boolean dying = false;

    private final int MAX_GHOSTS = 12;
    private final int IGGLE_SPEED = 6;

    private int N_GHOSTS = 6;// number of ghosts
    private int lives, score;
    private int[] dx, dy; 
    private int[] Gx, Gy, Gdx, Gdy, ghostSpeed; //Gx, Gy = ghost x and y positions ; Gdx, Gdy= delta changes in x and y directions

    private Image ghost;
    private Image player;

    private int Px, Py, Pdx, Pdy; //Px, Py = player's x and y positions ; Pdx, Pdy= delta changes in x and y directions
    private int temp_x, temp_y;

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;
    private short[] levelData;
    private Timer timer;

    public Check1() {

        addImages();
        initVariables();
        initBoard();
        startGame();
    }
    
    private void initBoard() {
        
        addKeyListener(new KeyClass());//adds key listener

        setFocusable(true);

        setBackground(Color.black);
    }

    private void initVariables() {

        levelData = new short[NO_OF_SQUARES * NO_OF_SQUARES];
        //Creates an opaque sRGB color with the specified red, green, and blue values in the range (0 - 255).
        mazeColor = new Color(5, 100, 5);// creates green color
        d = new Dimension(400, 400);
        Gx = new int[MAX_GHOSTS];
        Gdx = new int[MAX_GHOSTS];
        Gy = new int[MAX_GHOSTS];
        Gdy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        timer = new Timer(50, this);//sets timer(delay, taskPerformer)
        timer.start();
    }
/*DATA HIDING AND ENCAPSULATION*/
    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

        	moveIggle();
            drawIggle(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, GAME_AREA / 2 - 30, GAME_AREA - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, GAME_AREA / 2 - 30, GAME_AREA - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (GAME_AREA - metr.stringWidth(s)) / 2, GAME_AREA / 2);
    }

    private void addScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(text);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, GAME_AREA / 2 + 96, GAME_AREA + 16); 

        /*shows number of lives left*/
        for (i = 0; i < lives; i++) {
            g.drawImage(player, i * 28 + 8, GAME_AREA + 1,SQUARE_SIZE, SQUARE_SIZE, this);
        }
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < NO_OF_SQUARES * NO_OF_SQUARES && finished) {
        	/*It checks if there are any food left for the player to eat. Number 16 stands for food.*/
            if ((levelData[i] & 48) != 0) {
                finished = false;
            }
            i++;
        }
        if (finished) {
            score += 50; 
            /*Increments number of ghosts as the player wins first time*/
            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }
            //increases the speed
            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }
            initLevel();
        }
    }
/*DATA HIDING*/
    private void death() {
        lives--; //decrements life everytime player is eaten by ghost
        if (lives == 0) {
        	//if lives= 0 GameOver
            gameOver = true;
        }
        continueLevel();
    }
    private void moveGhosts(Graphics2D g2d) {
        short i;
        int ghostPos;
        int count;
/*EXCEPTION HANDLING*/
        try {
        for (i = 0; i < N_GHOSTS; i++) {
        	/*The ghosts move one square and then decide if they can change the direction.
        	   We continue only if we have finished moving one square */
            if (Gx[i] % SQUARE_SIZE == 0 && Gy[i] % SQUARE_SIZE == 0) {
            	//this gives the ghost position i.e its x and y coordinate
                ghostPos = Gx[i] / SQUARE_SIZE + NO_OF_SQUARES * (int) (Gy[i] / SQUARE_SIZE);
                count = 0;
                //checks to make sure the ghost do not pass through the boundary
                if ((levelData[ghostPos] & 1) == 0 && Gdx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }
                if ((levelData[ghostPos] & 2) == 0 && Gdy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }
                if ((levelData[ghostPos] & 4) == 0 && Gdx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }
                if ((levelData[ghostPos] & 8) == 0 && Gdy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }
                    count = (int) (Math.random() * count);
                    if (count > 3) {
                        count = 3;
                    }
                    Gdx[i] = dx[count];
                    Gdy[i] = dy[count];
            }
            Gx[i] = Gx[i] + (Gdx[i] * ghostSpeed[i]);
            Gy[i] = Gy[i] + (Gdy[i] * ghostSpeed[i]);
            drawGhost(g2d, Gx[i] + 1, Gy[i] + 1);
            //Player dies if it collides with the ghost
            if (Px > (Gx[i] - 12) && Px < (Gx[i] + 12)
                    && Py > (Gy[i] - 12) && Py < (Gy[i] + 12)
                    && !gameOver) {
                dying = true;
            }}
        }
               catch(ArrayIndexOutOfBoundsException e) {
        	       System.out.println("Index out of bounds!!");
                 }
        
    }
    private void drawGhost(Graphics2D g2d, int x, int y) {
    	//draws ghost up to the size of one Square block
        g2d.drawImage(ghost, x, y, SQUARE_SIZE,SQUARE_SIZE, this);
    }

    private void moveIggle() {

        int pos;
        short ch;
        
        if (temp_x == -Pdx && temp_y == -Pdy) {
            Pdx = temp_x;
            Pdy = temp_y;
        }

        try {
        if (Px % SQUARE_SIZE == 0 && Py % SQUARE_SIZE == 0) {
            pos = Px / SQUARE_SIZE + NO_OF_SQUARES * (int) (Py / SQUARE_SIZE);
            ch = levelData[pos];

            //if player is on the block of food, delete the food
            if ((ch & 16) != 0) {
            	levelData[pos] = (short) (ch & 15);
                score++;
            }

            if (temp_x != 0 || temp_y != 0) {
                if (!((temp_x == -1 && temp_y == 0 && (ch & 1) != 0)
                        || (temp_x == 1 && temp_y == 0 && (ch & 4) != 0)
                        || (temp_x == 0 && temp_y == -1 && (ch & 2) != 0)
                        || (temp_x == 0 && temp_y == 1 && (ch & 8) != 0))) {
                    Pdx = temp_x;
                    Pdy = temp_y;
                }
            }

            // player stops if there is a hurdle
            if ((Pdx == -1 && Pdy == 0 && (ch & 1) != 0)
                    || (Pdx == 1 && Pdy == 0 && (ch & 4) != 0)
                    || (Pdx == 0 && Pdy == -1 && (ch & 2) != 0)
                    || (Pdx == 0 && Pdy == 1 && (ch & 8) != 0)) {
                Pdx = 0;
                Pdy = 0;
            }
        }
        Px = Px + IGGLE_SPEED * Pdx;
        Py = Py + IGGLE_SPEED * Pdy;
    }

        catch(ArrayIndexOutOfBoundsException e) {
        	System.out.println("Index out of bounds!!");
        }
    }
    private void drawIggle(Graphics2D g2d) {
    	//draws player up to the size of one Square block
    	 g2d.drawImage(player, Px + 1, Py + 1, SQUARE_SIZE,SQUARE_SIZE, this);
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < GAME_AREA; y += SQUARE_SIZE) {
            for (x = 0; x < GAME_AREA; x += SQUARE_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                //draws boundary 
                if ((levelData[i] & 1) != 0) { 
                   g2d.drawLine(x, y, x, y + SQUARE_SIZE - 1);
                }

                if ((levelData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + SQUARE_SIZE - 1, y);
                }

                if ((levelData[i] & 4) != 0) { 
                    g2d.drawLine(x + SQUARE_SIZE - 1, y, x + SQUARE_SIZE - 1,
                            y + SQUARE_SIZE - 1);
                }

                if ((levelData[i] & 8) != 0) { 
                   g2d.drawLine(x, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1,
                            y + SQUARE_SIZE - 1);
                }
                //draws food on the screen
                if ((levelData[i] & 16) != 0) { 
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void startGame() {
        lives = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 3;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < NO_OF_SQUARES * NO_OF_SQUARES; i++) {
            levelData[i] = mazeData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        //adds ghosts with random speeds and moving ing random directions
        for (i = 0; i < N_GHOSTS; i++) {

            Gy[i] = 4 * SQUARE_SIZE;
            Gx[i] = 4 * SQUARE_SIZE;
            Gdy[i] = 0;
            Gdx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        Px = 7 * SQUARE_SIZE;
        Py = 11 * SQUARE_SIZE;
        Pdx = 0;
        Pdy = 0;
        temp_x = 0;
        temp_y = 0;
        dying = false;
    }


    /* Add images from pc*/
    private void addImages() {
        ghost = new ImageIcon("images/ghost.png").getImage();
        player = new ImageIcon("images/iggle.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setLevel(g); //sets the level 
    }

    private void setLevel(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        addScore(g2d);

        if (!gameOver) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(image, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
        
    }
/*inheritance*/
 class KeyClass extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (!gameOver) {
                if (key == KeyEvent.VK_LEFT) {
                    temp_x = -1;
                    temp_y = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    temp_x = 1;
                    temp_y = 0;
                } else if (key == KeyEvent.VK_UP) {
                    temp_x = 0;
                    temp_y = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    temp_x = 0;
                    temp_y = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    gameOver = true;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
            	//start the game if user press "s"
                if (key == 's' || key == 'S') {
                    gameOver = false;
                    startGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	//repaints the level and set all the components in place
        repaint();
    }
}