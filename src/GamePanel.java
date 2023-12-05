import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    
    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 700;
    static final int UNIT_SIZE = 50;

    Thread gameThread;
    Graphics graphics;

    // Game Items
    Swoop swoop;
    ArrayList<Obstacle> obstacles;
    ArrayList<BoostPad> boostpads;

    int rightPresses;
    int leftPresses;


    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        spawnGameObjects();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Test Grid, take out later
        for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

        swoop.draw(g);
        for(Obstacle o : obstacles){
            if(o.onPage)
                o.draw(g);
        }
        for(BoostPad b: boostpads){
            if(b.onPage)
                b.draw(g);
        }
    }

    public void spawnGameObjects() {
        this.swoop = new Swoop((SCREEN_WIDTH/2) - (UNIT_SIZE/2), SCREEN_HEIGHT-(2*UNIT_SIZE), UNIT_SIZE, (int)(1.5*UNIT_SIZE));
        // will spawn the obstacles and boost pads
        obstacles = new ArrayList<Obstacle>();
        boostpads = new ArrayList<BoostPad>();
        readInCSV();
    }

    public void move() {
        for(Obstacle o : obstacles){
            o.updateY(swoop.speed);
        }
        for(BoostPad b: boostpads){
            b.updateY(swoop.speed);
        }
    }

    // TODO handle collisions
    public void checkCollision(){
        //boostPads
        for(Obstacle o : obstacles){
            
        }
        
        //obstacles
        for(BoostPad b: boostpads){
            
        }
    }

    public void gameOver(Graphics g) {

    }

    public void run() {
        // game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        double secondDelta = 0;
        double sec = 1000000000;

        while(true) {
            boolean gameStart = false;
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            secondDelta += (now - lastTime)/sec;
            lastTime = now;
            if(delta >=1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
            if(secondDelta > 1 && gameStart == false){
                swoop.speed = 1;
                gameStart = true;
                secondDelta -= 1;
            } else if(secondDelta > 1 && gameStart == true){
                swoop.speed += 1;
                secondDelta -= 1;
            }
        }
    }

    public void readInCSV(){
        String csvFile = "./HelpSrc/TatooineData.csv";
        String line = "";
        String csvDelimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvDelimiter);
                // Do something with the data
                if(data[0].equals("BP")){
                    boostpads.add(new BoostPad(Integer.parseInt(data[1]), SCREEN_HEIGHT - Integer.parseInt(data[2]), UNIT_SIZE, (int)(1.2*UNIT_SIZE)));
                } else {
                    obstacles.add(new Obstacle(Integer.parseInt(data[1]), SCREEN_HEIGHT - Integer.parseInt(data[2]), UNIT_SIZE, UNIT_SIZE));
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
    }

    //TODO make a more adept x velocity system based on keyboard input so it is more smooth for movement (LITERALLY LAST THING TO DO)
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                if(rightPresses > 45){
                    swoop.x += 4;
                    rightPresses++;
                }
                else if(rightPresses > 30){
                    swoop.x += 3;
                    rightPresses++;
                }
                else if(rightPresses > 15){
                    swoop.x += 2;
                    rightPresses++;
                }else{
                    swoop.x++;
                    rightPresses++;
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                if(leftPresses > 45) {
                    swoop.x -= 4;
                    leftPresses++;
                }
                else if(leftPresses > 30) {
                    swoop.x -= 3;
                    leftPresses++;
                }
                else if(leftPresses > 15){
                    swoop.x -= 2;
                    leftPresses++;
                }else{
                    swoop.x--;
                    leftPresses++;
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                rightPresses = 0;
            }

            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                leftPresses = 0;
            }
        }
    }
}
